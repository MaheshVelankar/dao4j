package it.mengoni.persistence.dao;

import it.mengoni.exception.LogicError;
import it.mengoni.exception.SystemError;
import it.mengoni.persistence.dto.PersistentObject;

import java.util.Collection;
import java.util.List;

import org.javatuples.Tuple;

public interface Dao<T extends PersistentObject> {

	public int delete(T bean) throws LogicError;

	public int delete(Tuple key) throws LogicError;

	public T get(Tuple key) throws LogicError;

	public List<T> getAll() throws LogicError;

	public List<T> getAllOrder(String orderBy) throws LogicError;

	public List<T> getAll(int page, int pageSize) throws LogicError;

	public List<T> getAllOrder(int page, int pageSize, String orderBy) throws LogicError;

	public int getCount() throws LogicError;

	public List<T> getByWhere(String where, Object ... params) throws LogicError;

	public List<T> getByWhere(int page, int pageSize, String where, Object ... params) throws LogicError;

	public List<T> getByWhereOrder(String where, String orderby, Object ... params) throws LogicError;

	public List<T> getByWhereOrder(int page, int pageSize, String where, String orderby, Object ... params) throws LogicError;

	public int getPageCount(int pageSize, String where, Object ... params) throws LogicError;

	public int getCountByWhere(String where, Object ... params) throws LogicError;

	public KeyGenerator<T> getKeyGenerator();

	public List<T> getList(String sql, Object ... params) throws LogicError;

	public List<T> getListOrder(String sql, String orderBy, Object ... params) throws LogicError;

	public List<T> getListFor(Collection<Condition> conditions) throws LogicError;

	public List<T> getListFor(int page, int pageSize, Collection<Condition> conditions) throws LogicError;

	public List<T> getListFor(int page, int pageSize, Condition ... conditions) throws LogicError;

	public List<T> getListForOrder(String orderBy, Condition... conditions) throws LogicError;

	public List<T> getListForOrder(int page, int pageSize, String orderBy, Condition ... conditions) throws LogicError;

	public List<T> getListForOrder(String orderBy, Collection<Condition> conditions) throws LogicError;

	public List<T> getListForOrder(int page, int pageSize, String orderBy, Collection<Condition> conditions) throws LogicError;

	public int getCountFor(Condition ... conditions) throws LogicError;

	public int getCountFor(Collection<Condition> conditions) throws LogicError;

	public List<T> getListFor(Condition ... conditions) throws LogicError;

	public int insert(T bean) throws SystemError, LogicError;

	public T newIstance();

	public void setKeyGenerator(KeyGenerator<T> keyGenerator);

	public int update(T bean) throws SystemError, LogicError;

	public List<String> getFieldNames();

	public List<Field<T, ?>> getFields();

	public <V> Field<T, V> getFieldDef(String fieldName);

	public <V> Field<T, V> getProperty(String fieldName);

	public Object getPropertyValue(T bean, String propertyName);

	public <V> void setPropertyValue(T bean, String propertyName, V value);

	public CharsetConverter getCharsetConverter();

	public void setCharsetConverter(CharsetConverter charsetConverter);

}