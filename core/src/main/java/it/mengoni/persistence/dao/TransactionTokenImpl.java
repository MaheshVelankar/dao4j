package it.mengoni.persistence.dao;

import java.sql.Connection;

public class TransactionTokenImpl implements TransactionToken {

	private Connection connection;

	private boolean rollBackOnly;

	private boolean committed;

	private boolean rollback;

	private Throwable error;

	TransactionTokenImpl() {
		super();
		rollBackOnly = false;
		committed = false;
		rollback = false;
		error = null;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public boolean isRollBackOnly() {
		return rollBackOnly;
	}

	public void setRollBackOnly(boolean rollBackOnly) {
		this.rollBackOnly = rollBackOnly;
	}

	public boolean isCommitted() {
		return committed;
	}

	public void setCommitted(boolean committed) {
		this.committed = committed;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	public boolean isRollback() {
		return rollback;
	}

	public void setRollback(boolean rollback) {
		this.rollback = rollback;
	}


}
