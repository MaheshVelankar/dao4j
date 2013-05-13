package it.mengoni.persistence.dao;

import it.mengoni.persistence.dao.JdbcHelper.BeanCreator;
import it.mengoni.persistence.dao.JdbcHelper.BeanSqlSetter;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.LogicError;
import it.mengoni.persistence.exception.SystemError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.javatuples.Tuple;

public abstract class AbstractDao<T extends PersistentObject> implements Dao<T> {

	private final String[] keyNames;
	private KeyGenerator<T> keyGenerator;
	protected final JdbcHelper jdbcHelper;
	protected List<Condition> fixedConditions;
	protected CharsetConverter charsetConverter;
	protected abstract T getBean(ResultSet rs, int rowNum) throws SQLException;
	public abstract T newIstance();
	protected abstract String getSelectSql(int page, int pageSize, String orderBy);
	protected abstract String getSelectSql(int page, int pageSize, String orderBy, String where);
	protected abstract String getInsertSql();
	protected abstract String getDeleteSql();
	protected abstract String getUpdateSql();
	protected abstract int setParams(PreparedStatement stm, T bean) throws SQLException;
	protected it.mengoni.persistence.dao.Dao.DatabaseProductType databaseProductType;

	protected String[] getKeyNames() {
		return keyNames;
	}

	public KeyGenerator<T> getKeyGenerator() {
		return keyGenerator;
	}

	public void setKeyGenerator(KeyGenerator<T> keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	public CharsetConverter getCharsetConverter() {
		return charsetConverter;
	}

	@Override
	public void setCharsetConverter(CharsetConverter charsetConverter) {
		this.charsetConverter = charsetConverter;
	}

	private BeanCreator<T> beanCreator = new BeanCreator<T>(){

		@Override
		public T getBean(ResultSet rs, int rowNum) throws SystemError, LogicError {
			try{
				T bean = AbstractDao.this.getBean(rs, rowNum);
				bean.saved();
				return bean;
			} catch (SQLException e) {
				throw new SystemError("Error in query execution "+getDaoLabel() +" :" + e, AbstractDao.this.getClass().getSimpleName(), "getBean",  e);
			}
		}

	};

	private BeanSqlSetter<T> beanPkSetter = new BeanSqlSetter<T>(){
		@Override
		public void setParams(PreparedStatement stm, T bean) throws SystemError, LogicError {
			try{
				int p=1;
				setKey(stm, bean, p);
			} catch (SQLException e) {
				throw new SystemError("Error in query execution "+getDaoLabel() +" :" + e, AbstractDao.this.getClass().getSimpleName(), "setParams",  e);
			}
		}
	};

	private BeanSqlSetter<T> beanUpdateSetter = new BeanSqlSetter<T>(){
		@Override
		public void setParams(PreparedStatement stm, T bean) throws SystemError, LogicError {
			try{
				int p = AbstractDao.this.setParams(stm, bean);
				setKey(stm, bean, p);
			} catch (SQLException e) {
				throw new SystemError("Error in query execution "+getDaoLabel() +" :" + e, AbstractDao.this.getClass().getSimpleName(), "setParams",  e);
			}
		}
	};

	private void setKey(PreparedStatement stm, T bean, int p) throws SQLException, LogicError{
		checkKey(bean);
		Tuple k = bean.getKey();
		for (int i=0; i<k.getSize(); i++){
			Object v = k.getValue(i);
			stm.setObject(p++, v);
		}
	}

	private BeanSqlSetter<T> beanSqlSetter = new BeanSqlSetter<T>(){
		@Override
		public void setParams(PreparedStatement stm, T bean) throws SystemError, LogicError {
			try{
				AbstractDao.this.setParams(stm, bean);
			} catch (SQLException e) {
				throw new SystemError("Error in query execution "+getDaoLabel() +" :" + e, AbstractDao.this.getClass().getSimpleName(), "setParams",  e);
			}
		}
	};

	public AbstractDao(JdbcHelper jdbcHelper, CharsetConverter charsetConverter, String... keyNames) {
		if (jdbcHelper==null )
			throw new IllegalArgumentException("jdbcHelper not assigned "+getDaoLabel());
		if (keyNames==null || keyNames.length==0)
			throw new IllegalArgumentException("keyNames not assigned "+getDaoLabel());
		this.charsetConverter = charsetConverter;
		this.jdbcHelper = jdbcHelper;
		this.keyNames = keyNames;
		this.databaseProductType = jdbcHelper.getDatabaseProductType();
	}


	public List<T> getAllOrder(int page, int pageSize, String orderBy) throws SystemError, LogicError {
		StringBuilder buf = new StringBuilder(getSelectSql(page, pageSize, orderBy));
		return jdbcHelper.queryForList(buf.toString(), beanCreator);
	}

	public List<T> getAllOrder(String orderBy) throws SystemError, LogicError {
		StringBuilder buf = new StringBuilder(getSelectSql(0,0, orderBy));
		return jdbcHelper.queryForList(buf.toString(), beanCreator);
	}

	protected String calcPreselect(int page, int pageSize) throws LogicError {
		if (page>0 && pageSize>0){
			StringBuilder preselect = new StringBuilder(" ");
			if (databaseProductType==DatabaseProductType.firebird){
				preselect.append("first ").append(pageSize).append(" ");
				if (page>1)
					preselect.append("skip ").append((page-1)*pageSize).append(" ");
			}
			if (databaseProductType==DatabaseProductType.postgresql){
				preselect.append("LIMIT ").append(pageSize).append(" ");
					preselect.append("OFFSET ").append((page-1)*pageSize).append(" ");
			}
			if (databaseProductType==DatabaseProductType.mysql){
				preselect.append("LIMIT ").append((page-1)*pageSize).append(", ").append(pageSize);
			}
			return preselect.toString();
		}
		if (page<=0 && pageSize>0)
			throw new LogicError( "Error in query execution : le pagine sono numerate da 1"+getDaoLabel());
		return null;
	}

	@Override
	public List<T> getAll() throws SystemError, LogicError {
		return getAllOrder(null);
	}

	@Override
	public List<T> getAll(int page, int pageSize) throws LogicError {
		return jdbcHelper.queryForList(getSelectSql(page, pageSize, null), beanCreator);
	}

	@Override
	public int insert(T bean) throws SystemError, LogicError {
		if (bean==null)
			throw new IllegalArgumentException("bean is null" + getDaoLabel());
		checkKey(bean);
		bean.beforeSave();
		int res;
		res = jdbcHelper.updateObject(getInsertSql(), beanSqlSetter, bean);
		if (res==1)
			bean.saved();
		return res;
	}

	private void checkKey(T bean) throws SystemError, LogicError {
		Tuple k = bean.getKey();
		if (DaoUtils.isEmpty(k)){
			if(keyGenerator==null)
				throw new IllegalArgumentException("bean key is null" + getDaoLabel());
			else
				k = keyGenerator.newKey(bean, keyNames);
		}
		checkKey(k);
	}

	protected abstract String getDaoLabel();

	private void checkKey(Tuple k) {
		if (k==null )
			throw new IllegalArgumentException("key is null" + getDaoLabel());
		if (keyNames==null || keyNames.length!=k.getSize())
			throw new IllegalArgumentException("keyNames error" + getDaoLabel());
		for (int i=0; i<k.getSize(); i++){
			Object v = k.getValue(i);
			if (v instanceof String){
				if (((String)v).trim().isEmpty())
					throw new IllegalArgumentException("value for "+keyNames[i]+" is undefined" + getDaoLabel());
			} else
				if (v==null )
					throw new IllegalArgumentException("value for "+keyNames[i]+" is undefined" + getDaoLabel());
		}
	}


	@Override
	public int delete(Tuple key) throws SystemError, LogicError {
		if (key==null)
			throw new IllegalArgumentException("key is null" + getDaoLabel());
		checkKey(key);
		int res = jdbcHelper.update(getDeleteSql(), key.toArray());
		return res;

	}

	@Override
	public int delete(T bean) throws SystemError, LogicError {
		if (bean==null)
			throw new IllegalArgumentException("bean is null" + getDaoLabel());
		checkKey(bean);
		int res = jdbcHelper.updateObject(getDeleteSql(), beanPkSetter, bean);
		if (res==1)
			bean.deleted();
		return res;
	}

	@Override
	public int update(T bean) throws SystemError, LogicError {
		if (bean==null)
			throw new IllegalArgumentException("bean is null" + getDaoLabel());
		checkKey(bean);
		bean.beforeSave();
		int res = jdbcHelper.updateObject(getUpdateSql(), beanUpdateSetter, bean);
		if (res==1)
			bean.saved();
		return res;
	}

	public List<T> getList(String sql, Object ... params) throws SystemError, LogicError {
		return jdbcHelper.queryForList(sql, beanCreator, params);
	}

	public List<T> getListOrder(String sql, String orderBy, Object ... params) throws SystemError, LogicError {
		StringBuilder buf = new StringBuilder(sql);
		if (orderBy!=null && !orderBy.trim().isEmpty()){
			buf.append(" order by ").append(orderBy);
		}
		return jdbcHelper.queryForList(buf.toString(), beanCreator, params);
	}


	public T get(Tuple key) throws SystemError, LogicError{
		if (key==null)
			throw new IllegalArgumentException("bean key is null" + getDaoLabel());
		return jdbcHelper.queryForObject(getSelectSqlForKey(), beanCreator, key.toArray());
	}

	protected String getSelectSqlForKey() {
		StringBuilder ret = new StringBuilder(getSelectSql(0,0,null));
		String s = getPkSql();
		if (s!=null && !s.trim().isEmpty()){
			ret.append(" where ");
			ret.append(s);
		}
		return ret.toString();
	}

	protected abstract String getPkSql();

	public List<T> getListFor(Condition ... conditions) throws LogicError {
		return getListForInner(0, 0, null, conditions);
	}

	protected List<T> getListForInner(int page, int pageSize, String orderBy, Condition ... conditions) throws LogicError {
		SqlWhere where = new SqlWhere(conditions);
		if (fixedConditions!=null){
			for (Condition condition : conditions) {
				where.addCondition(condition);
			}
		}
		StringBuilder buf = new StringBuilder(getSelectSql(page, pageSize, orderBy, where.getWhere()));
		return jdbcHelper.queryForList(buf.toString(), beanCreator, where.getParamsArray());
	}

	@Override
	public List<T> getListForOrder(int page, int pageSize, String orderBy,	Condition... conditions) throws LogicError {
		return getListForInner(page, pageSize, orderBy, conditions);
	}

	@Override
	public List<T> getListForOrder(String orderBy,
			Collection<Condition> conditions) throws LogicError {
		return getListForInner(0,0, orderBy, conditions.toArray(new Condition[0]));
	}

	@Override
	public List<T> getListForOrder(int page, int pageSize, String orderBy,
			Collection<Condition> conditions) throws LogicError {
		return getListForInner(page, pageSize, orderBy, conditions.toArray(new Condition[0]));
	}

	public List<T> getListFor(Collection<Condition> conditions) throws LogicError {
		return getListForInner(0,0, null, conditions.toArray(new Condition[0]));
	}

	@Override
	public List<T> getListFor(int page, int pageSize, Condition... conditions) throws LogicError {
		return getListForInner(page, pageSize, null, conditions);
	}

	@Override
	public List<T> getListFor(int page, int pageSize,
			Collection<Condition> conditions) throws LogicError {
		return getListForInner(page, pageSize, null, conditions.toArray(new Condition[0]));
	}

	public List<T> getListForOrder(String orderBy, Condition... conditions) throws LogicError{
		return getListForInner(0,0, orderBy, conditions);
	}
	public it.mengoni.persistence.dao.Dao.DatabaseProductType getDatabaseProductType() {
		return databaseProductType;
	}
	public void setDatabaseProductType(
			it.mengoni.persistence.dao.Dao.DatabaseProductType databaseProductType) {
		this.databaseProductType = databaseProductType;
	}



}