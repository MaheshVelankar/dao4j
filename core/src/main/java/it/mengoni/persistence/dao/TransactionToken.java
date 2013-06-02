package it.mengoni.persistence.dao;

import java.sql.Connection;

public interface TransactionToken {

	public Connection getConnection();

	public void setConnection(Connection connection);

	public boolean isRollBackOnly();

	public void setRollBackOnly(boolean rollBackOnly);

	public boolean isCommitted();

	void setCommitted(boolean committed);

	public boolean isRollback();

	void setRollback(boolean rollback);

	public Throwable getError();

	public void setError(Throwable error);


}
