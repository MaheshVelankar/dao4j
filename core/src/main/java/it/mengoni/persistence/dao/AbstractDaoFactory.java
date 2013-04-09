package it.mengoni.persistence.dao;

import it.mengoni.db.DaoFactory;
import it.mengoni.exception.SystemError;
import it.mengoni.persistence.dto.PersistentObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public abstract class AbstractDaoFactory implements DaoFactory {

	private DataSource datasource;
	private CharsetConverter charsetConverter;

	public void init() {
		if (datasource==null)
			throw new IllegalStateException("dataSource not assigned ");
	}


	public CharsetConverter getCharsetConverter() {
		return charsetConverter;
	}

	public void setCharsetConverter(CharsetConverter charsetConverter) {
		this.charsetConverter = charsetConverter;
	}

	private Map<Class<?>, Constructor<Dao<? extends PersistentObject>>> daoClassMap = new HashMap<Class<?>, Constructor<Dao<? extends PersistentObject>>>();

	protected void daoClassAdd(Class<?> javaClass, Class<Dao<? extends PersistentObject>> daoClass) {
		try {
			Constructor<Dao<? extends PersistentObject>> constructor;
			constructor = daoClass.getConstructor(JdbcHelper.class);
			daoClassMap.put(javaClass, constructor);
		} catch (SecurityException e) {
			throw new IllegalArgumentException(e);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public <T extends PersistentObject> Dao<T> getDao(Class<T> javaClass) {
		try {
			Constructor<Dao<?>> constructor = daoClassMap.get(javaClass);
			Dao<?> x = constructor.newInstance(new JdbcHelper(datasource));
			x.setCharsetConverter(charsetConverter);
			@SuppressWarnings("unchecked")
			Dao<T> x2 = (Dao<T>) x;
			return x2;
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (SecurityException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public JdbcHelper getHelper() {
		if (datasource==null)
			throw new IllegalStateException("datasource not assigned ");
		return new JdbcHelper(datasource);
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public Connection getConnection(){
		if (datasource==null)
			throw new IllegalStateException("dataSource not assigned ");
		try {
			return datasource.getConnection();
		} catch (Exception e) {
			throw new SystemError("Error connection open",e);
		}
	}

}