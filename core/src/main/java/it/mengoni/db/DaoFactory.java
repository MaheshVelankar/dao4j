package it.mengoni.db;

import java.sql.Connection;

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

}
