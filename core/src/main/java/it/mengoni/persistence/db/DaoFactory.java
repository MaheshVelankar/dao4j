package it.mengoni.persistence.db;

import java.sql.Connection;
import java.sql.Savepoint;

import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.Dao;
import it.mengoni.persistence.dao.JdbcHelper;
import it.mengoni.persistence.dto.PersistentObject;

import javax.sql.DataSource;

public interface DaoFactory {

	public JdbcHelper getHelper();

	public DataSource getDatasource();

	public void setDatasource(DataSource datasource);

	public <T extends PersistentObject> Dao<T> getDao(Class<T> javaClass);

	public CharsetConverter getCharsetConverter();

	public void setCharsetConverter(CharsetConverter charsetConverter);

	public Connection getConnection();

	public void startTransaction();

	public void startTransaction(int isolationLevel);

	public void commit();

	public boolean isTransactionActive();

	public void rollback();

	public void rollback(Savepoint savepoint);

	public void setRollbackOnly();

	public void setTransactionError(Throwable error);

	public void resetTransactionError();

	public Savepoint setTransactionSavepoint(String name);

	public Savepoint setTransactionSavepoint();

	public void releaseTransactionSavepoint(Savepoint savepoint);

	public void setTransactionIsolation(int level);

	public int getTransactionIsolation();

	public void doInTransaction(TransactionMethod method);

	public void doInTransaction(int isolationLevel,
			TransactionMethod method);

}
