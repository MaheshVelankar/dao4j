package it.mengoni.persistence.dao;

import it.mengoni.persistence.db.TransactionMethod;
import it.mengoni.persistence.exception.LogicError;
import it.mengoni.persistence.exception.SystemError;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionManager {

	private static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);

	private TransactionManager() {
	}

	private static final TransactionManager instance = new TransactionManager();

	public static TransactionManager getInstance() {
		return instance;
	}

	/**
	 * Transaction status is holded by thread
	 */
	private static final ThreadLocal<TransactionToken> _tokens = new ThreadLocal<TransactionToken>();

	protected TransactionToken getCurrentTransactionToken() {
		return _tokens.get();
	}

	public void start(Connection connection) {
		logger.debug("start, connection:"+connection);
		TransactionToken c = getCurrentTransactionToken();
		if (c != null)
			throw new LogicError("transaction already present");
		TransactionTokenImpl x = new TransactionTokenImpl();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new LogicError("failure in start transaction", e);
		}
		x.setConnection(new WrappedConnection(connection));
		_tokens.set(x);
	}

	/**
	 * Attempts to start the transaction with isolation level for the connection
	 * object to the one given. The constants defined in the interface
	 * Connection are the possible transaction isolation levels.
	 *
	 * @param connection
	 * @param level
	 *            - one of the following Connection constants:
	 *            Connection.TRANSACTION_READ_UNCOMMITTED,
	 *            Connection.TRANSACTION_READ_COMMITTED,
	 *            Connection.TRANSACTION_REPEATABLE_READ, or
	 *            Connection.TRANSACTION_SERIALIZABLE. (Note that
	 *            Connection.TRANSACTION_NONE cannot be used because it
	 *            specifies that transactions are not supported.)
	 */
	public void start(Connection connection, int isolationLevel) {
		logger.debug("start, connection:"+connection + " isolationLevel:"+isolationLevel);
		TransactionToken c = getCurrentTransactionToken();
		if (c != null)
			throw new LogicError("transaction already present");
		TransactionTokenImpl x = new TransactionTokenImpl();
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(isolationLevel);
		} catch (SQLException e) {
			throw new LogicError("failure in start transaction", e);
		}
		x.setConnection(new WrappedConnection(connection));
		_tokens.set(x);
	}

	public void commit() {
		TransactionToken c = checkActive();
		logger.debug("commit, connection:"+c.getConnection());
		if (c.isRollBackOnly())
			throw new LogicError("transaction it's only to roll back");
		try {
			c.getConnection().commit();
			c.setCommitted(true);
		} catch (SQLException e) {
			throw new SystemError("Error in commit:" + e.getMessage(), e);
		}
		try {
			((WrappedConnection) c.getConnection()).realClose();
		} catch (SQLException e) {
			logger.error("Error in closing connection", e);
		}
	}

	public boolean isActive() {
		TransactionToken c = getCurrentTransactionToken();
		if (c == null)
			return false;
		if (c.isCommitted())
			return false;
		if (c.isRollback())
			return false;
		return true;
	}

	private TransactionToken checkActive() {
		TransactionToken c = getCurrentTransactionToken();
		if (c == null)
			throw new LogicError("transaction not present");
		if (c.getConnection() == null)
			throw new LogicError("connection not present");
		if (c.isCommitted())
			throw new LogicError("transaction committed");
		if (c.isRollback())
			throw new LogicError("transaction rolled back");
		return c;
	}

	public void rollback() {
		TransactionToken c = checkActive();
		logger.debug("rollback, connection:"+c.getConnection());
		try {
			c.getConnection().rollback();
		} catch (SQLException e) {
			throw new SystemError("Error in commit:" + e.getMessage(), e);
		}
		try {
			((WrappedConnection) c.getConnection()).realClose();
		} catch (SQLException e) {
			logger.error("Error in closing connection", e);
		}
	}

	public void rollback(Savepoint savepoint) {
		TransactionToken c = checkActive();
		logger.debug("rollback, savepoint: "+savepoint+", connection:"+c.getConnection());
		try {
			c.getConnection().rollback(savepoint);
		} catch (SQLException e) {
			throw new LogicError("Error in setting savepoint. "
					+ e.getMessage(), e);
		}
		try {
			((WrappedConnection) c.getConnection()).realClose();
		} catch (SQLException e) {
			logger.error("Error in closing connection", e);
		}
	}

	public void setRollbackOnly() {
		TransactionToken c = getCurrentTransactionToken();
		if (c == null)
			throw new LogicError("transaction not present");
		logger.debug("setRollbackOnly, connection:"+c.getConnection());
		if (c.isCommitted())
			throw new LogicError("transaction committed");
		if (c.isRollback())
			throw new LogicError("transaction rolled back");
		c.setRollBackOnly(true);
	}

	public void setTransactionError(Throwable error) {
		TransactionToken c = checkActive();
		c.setError(error);
		c.setRollBackOnly(true);
		logger.debug("setTransactionError, connection:"+c.getConnection(), error);
	}

	public void resetTransactionError() {
		TransactionToken c = getCurrentTransactionToken();
		if (c != null) {
			if (!c.isCommitted() && !c.isRollback())
				throw new LogicError("transaction ACTIVE");
			_tokens.set(null);
		}
	}

	public Connection getConnection() {
		TransactionToken t = _tokens.get();
		if (t != null)
			return t.getConnection();
		return null;
	}

	public Savepoint setSavepoint(String name) {
		TransactionToken c = checkActive();
		logger.debug("setSavepoint, name: "+name+", connection:"+c.getConnection());
		try {
			return c.getConnection().setSavepoint(name);
		} catch (SQLException e) {
			throw new LogicError(
					"Error in setting savepoint"
							+ (name == null ? "" : ": " + name) + ". "
							+ e.getMessage(), e);
		}
	}

	public Savepoint setSavepoint() {
		TransactionToken c = checkActive();
		logger.debug("setSavepoint, connection:"+c.getConnection());
		try {
			return c.getConnection().setSavepoint();
		} catch (SQLException e) {
			throw new LogicError("Error in setting savepoint. "
					+ e.getMessage(), e);
		}
	}

	public void releaseSavepoint(Savepoint savepoint) {
		TransactionToken c = checkActive();
		logger.debug("releaseSavepoint, savepoint: "+savepoint+", connection:"+c.getConnection());
		try {
			c.getConnection().releaseSavepoint(savepoint);
		} catch (SQLException e) {
			throw new LogicError("Error in setting savepoint. "
					+ e.getMessage(), e);
		}
	}

	/**
	 * Attempts to change the transaction isolation level for this Connection
	 * object to the one given. The constants defined in the interface
	 * Connection are the possible transaction isolation levels. Note: If this
	 * method is called during a transaction, the result is
	 * implementation-defined.
	 *
	 * @param level
	 *            - one of the following Connection constants:
	 *            Connection.TRANSACTION_READ_UNCOMMITTED,
	 *            Connection.TRANSACTION_READ_COMMITTED,
	 *            Connection.TRANSACTION_REPEATABLE_READ, or
	 *            Connection.TRANSACTION_SERIALIZABLE. (Note that
	 *            Connection.TRANSACTION_NONE cannot be used because it
	 *            specifies that transactions are not supported.)
	 */
	public void setTransactionIsolation(int level) {
		TransactionToken c = checkActive();
		logger.debug("setTransactionIsolation, level: "+level+", connection:"+c.getConnection());
		try {
			c.getConnection().setTransactionIsolation(level);
		} catch (SQLException e) {
			throw new LogicError("Error in setting transaction isolation("
					+ level + "). " + e.getMessage(), e);
		}
	}

	/**
	 * Retrieves this Connection object's current transaction isolation level.
	 *
	 * @returns the current transaction isolation level, which will be one of
	 *          the following constants:
	 *          Connection.TRANSACTION_READ_UNCOMMITTED,
	 *          Connection.TRANSACTION_READ_COMMITTED,
	 *          Connection.TRANSACTION_REPEATABLE_READ,
	 *          Connection.TRANSACTION_SERIALIZABLE, or
	 *          Connection.TRANSACTION_NONE.
	 */
	public int getTransactionIsolation() {
		TransactionToken c = checkActive();
		try {
			return c.getConnection().getTransactionIsolation();
		} catch (SQLException e) {
			throw new LogicError("Error in getting transaction isolation. "
					+ e.getMessage(), e);
		}
	}

	public void doInTransaction(Connection connection, TransactionMethod method){
		doInTransaction(connection, -100000, method);
	}

	public void doInTransaction(Connection connection, int isolationLevel, TransactionMethod method){
		logger.debug("doInTransaction, connection:"+connection + " isolationLevel:"+isolationLevel);
		if (isolationLevel==-100000)
			start(connection);
		else
			start(connection, isolationLevel);
		try {
			method.execute();
		} catch (Exception e) {
			logger.error("Error, connection:"+connection + " isolationLevel:"+isolationLevel, e);
			if (isActive()){
				setTransactionError(e);
				rollback();
			}
			throw new LogicError("transaction failed with error: "
					+ e.getMessage(), e);
		}
		if (canCommit()){
			commit();
			logger.debug("doInTransaction commited, connection:"+connection + " isolationLevel:"+isolationLevel);
			return;
		}
		if (mustRollBack()){
			rollback();
			logger.debug("doInTransaction rollback, connection:"+connection + " isolationLevel:"+isolationLevel);
		}
	}

	private boolean mustRollBack() {
		return isActive() && getCurrentTransactionToken().isRollBackOnly();
	}

	private boolean canCommit() {
		return isActive() && !getCurrentTransactionToken().isRollBackOnly();
	}

}
