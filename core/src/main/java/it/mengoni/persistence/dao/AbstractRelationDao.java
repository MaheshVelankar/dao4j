package it.mengoni.persistence.dao;

import it.mengoni.persistence.dao.JdbcHelper.BeanCreator;
import it.mengoni.persistence.dao.JdbcHelper.BeanSqlSetter;
import it.mengoni.persistence.dao.fields.FieldJoinImpl;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.LogicError;
import it.mengoni.persistence.exception.SystemError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javatuples.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRelationDao<T extends PersistentObject> implements Dao<T>, BeanCreator<T> {

	private static Logger logger = LoggerFactory.getLogger(AbstractRelationDao.class);
	private static final String MODULO = AbstractRelationDao.class.getSimpleName();

	private static final String PK_ERR = "In the table %s the primary key: %s exist";
	private static final String PK_ERR_ITA = "Esiste un oggetto nella tabella %s con la stessa chiave primaria: %s";

	private static boolean isIt = "it".equalsIgnoreCase(System.getProperty("user.language"));

	private final String[] keyNames;
	private KeyGenerator<T> keyGenerator;
	protected final JdbcHelper jdbcHelper;
	protected List<Condition> fixedConditions;
	protected CharsetConverter charsetConverter;
	protected it.mengoni.persistence.dao.Dao.DatabaseProductType databaseProductType;

	protected final String relationName;
	protected final List<Field<T, ?>> fields;
	protected final Map<String,Field<T, ?>> fieldMap;
	protected final Map<String,Field<T, ?>> propertyMap;
	private String insertSql;
	private String deleteSql;
	private String pkSql;
	private String updateSql;
	private String countSql;
	private String innerSelectSql;

	public AbstractRelationDao(JdbcHelper jdbcHelper, CharsetConverter charsetConverter, String relationName, List<Field<T, ?>> fields) {
		if (jdbcHelper==null )
			throw new IllegalArgumentException("jdbcHelper not assigned "+getDaoLabel());
		keyNames = extractKeyNames(fields);
		if (keyNames==null || keyNames.length==0)
			throw new IllegalArgumentException("keyNames not assigned "+getDaoLabel());
		this.charsetConverter = charsetConverter;
		this.jdbcHelper = jdbcHelper;
		this.databaseProductType = jdbcHelper.getDatabaseProductType();
		this.fields = fields;
		this.relationName = relationName;
		fieldMap = new HashMap<String, Field<T, ?>>();
		propertyMap = new HashMap<String, Field<T, ?>>();
		for (Field<T, ?> field : fields) {
			field.setCharsetConverter(charsetConverter);
			fieldMap.put(field.getName(), field);
			propertyMap.put(field.getPropertyName(), field);
		}
	}


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

	//	private BeanCreator<T> beanCreator = new BeanCreator<T>(){
	//
	//		@Override
	//		public T getBean(ResultSet rs, int rowNum) throws SystemError, LogicError {
	//			try{
	//				T bean = AbstractRelationDao.this.getBean(rs, rowNum);
	//				bean.saved();
	//				return bean;
	//			} catch (SQLException e) {
	//				throw new SystemError("Error in query execution "+getDaoLabel() +" :" + e, AbstractRelationDao.this.getClass().getSimpleName(), "getBean",  e);
	//			}
	//		}
	//
	//	};

	private BeanSqlSetter<T> beanPkSetter = new BeanSqlSetter<T>(){
		@Override
		public void setParams(PreparedStatement stm, T bean) throws SystemError, LogicError {
			try{
				int p=1;
				setKey(stm, bean, p);
			} catch (SQLException e) {
				throw new SystemError("Error in query execution "+getDaoLabel() +" :" + e, AbstractRelationDao.this.getClass().getSimpleName(), "setParams",  e);
			}
		}
	};

	private BeanSqlSetter<T> beanUpdateSetter = new BeanSqlSetter<T>(){
		@Override
		public void setParams(PreparedStatement stm, T bean) throws SystemError, LogicError {
			try{
				int p = AbstractRelationDao.this.setParams(stm, bean);
				setKey(stm, bean, p);
			} catch (SQLException e) {
				throw new SystemError("Error in query execution "+getDaoLabel() +" :" + e, AbstractRelationDao.this.getClass().getSimpleName(), "setParams",  e);
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
				AbstractRelationDao.this.setParams(stm, bean);
			} catch (SQLException e) {
				throw new SystemError("Error in query execution "+getDaoLabel() +" :" + e, AbstractRelationDao.this.getClass().getSimpleName(), "setParams",  e);
			}
		}
	};

	public List<T> getAllOrder(int page, int pageSize, String orderBy) throws SystemError, LogicError {
		StringBuilder buf = new StringBuilder(getSelectSql(page, pageSize, orderBy));
		return jdbcHelper.queryForList(buf.toString(), this);
	}

	public List<T> getAllOrder(String orderBy) throws SystemError, LogicError {
		StringBuilder buf = new StringBuilder(getSelectSql(0,0, orderBy));
		return jdbcHelper.queryForList(buf.toString(), this);
	}

	protected String calcPreselect(int page, int pageSize) throws LogicError {
		if (page>0 && pageSize>0){
			StringBuilder preselect = new StringBuilder(" ");
			if (databaseProductType==DatabaseProductType.firebird){
				preselect.append("first ").append(pageSize).append(" ");
				if (page>1)
					preselect.append("skip ").append((page-1)*pageSize).append(" ");
			} else if (databaseProductType==DatabaseProductType.postgresql){
				preselect.append("LIMIT ").append(pageSize).append(" ");
				preselect.append("OFFSET ").append((page-1)*pageSize).append(" ");
			} else if (databaseProductType==DatabaseProductType.mysql){
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
		return jdbcHelper.queryForList(getSelectSql(page, pageSize, null), this);
	}

	@Override
	public int insert(T bean) throws SystemError, LogicError {
		if (bean==null)
			throw new IllegalArgumentException("bean is null" + getDaoLabel());
		checkKey(bean);
		bean.beforeSave();
		try{
			int res;
			res = jdbcHelper.updateObject(getInsertSql(), beanSqlSetter, bean);
			if (res==1)
				bean.saved();
			return res;
		} catch (SystemError e) {
			if (databaseProductType==DatabaseProductType.firebird && e.getMessage().contains("335544665") && e.getMessage().contains("FBSQLException")){
				throw new LogicError(String.format(isIt?PK_ERR_ITA:PK_ERR, relationName, dump(bean.getKey())));
			}
			throw e;
		}
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
		try{
			int res = jdbcHelper.updateObject(getUpdateSql(), beanUpdateSetter, bean);
			if (res==1)
				bean.saved();
			return res;
		} catch (SystemError e) {
			if (databaseProductType==DatabaseProductType.firebird && e.getMessage().contains("335544665") && e.getMessage().contains("FBSQLException")){
				throw new LogicError(String.format(isIt?PK_ERR_ITA:PK_ERR, relationName, dump(bean.getKey())));
			}
			throw e;
		}
	}

	public List<T> getList(String sql, Object ... params) throws SystemError, LogicError {
		return jdbcHelper.queryForList(sql, this, params);
	}

	public List<T> getListOrder(String sql, String orderBy, Object ... params) throws SystemError, LogicError {
		StringBuilder buf = new StringBuilder(sql);
		if (orderBy!=null && !orderBy.trim().isEmpty()){
			buf.append(" order by ").append(orderBy);
		}
		return jdbcHelper.queryForList(buf.toString(), this, params);
	}


	public T get(Tuple key) throws SystemError, LogicError{
		if (key==null)
			throw new IllegalArgumentException("bean key is null" + getDaoLabel());
		return jdbcHelper.queryForObject(getSelectSqlForKey(), this, key.toArray());
	}

	public T reload(final T bean){
		if (bean==null || bean.isNew())
			throw new IllegalArgumentException("bean key is null" + getDaoLabel());
		return jdbcHelper.queryForObject(getSelectSqlForKey(), new BeanCreator<T>() {
			@Override
			public T getBean(ResultSet rs, int rowNum) {
				for (Field<T, ?> field : fields) {
					try {
						field.readValueFrom(rs, bean);
					} catch (Exception e) {
						throw new SystemError("Error in setValue:" + getRelationName() +"."+ field.getName(), e);
					}
				}
				bean.saved();
				return bean;
			}
		}, bean.getKey().toArray());
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
		return jdbcHelper.queryForList(buf.toString(), this, where.getParamsArray());
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

	private String dump(Tuple key) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < key.getSize(); i++) {
			if (i>0)
				ret.append(",");
			ret.append(key.getValue(i));
		}
		return ret.toString();
	}

	protected String identifierQuote(String value) {
		if (value!=null){
			value = value.trim();
			if (value.contains("-") || value.contains(" "))
				value = new StringBuilder("\"").append(value).append("\"").toString();
			return value;
		}
		return null;
	}

	private static String[] extractKeyNames(@SuppressWarnings("rawtypes") List fields) {
		ArrayList<String> buf = new ArrayList<String>();
		for (Object obj: fields) {
			@SuppressWarnings("rawtypes")
			Field field = (Field)obj;
			if (field.isKey())
				buf.add(field.getName());
		}
		String[] a = new String[]{};
		return buf.toArray(a);
	}

	public String getRelationName() {
		return relationName;
	}

	protected String getInsertSql(){
		if (insertSql==null){
			StringBuilder ret = new StringBuilder("insert into ");
			ret.append(identifierQuote(relationName)).append(" (");
			int c=0;
			for (int i = 0; i < fields.size(); i++) {
				Field<T, ?> field = fields.get(i);
				if (!field.isReadOnly()){
					if (c>0)
						ret.append("\n, ");
					ret.append(field.getName());
					c++;
				}
			}
			ret.append("\n) values ( \n");
			c=0;
			for (int i = 0; i < fields.size(); i++) {
				Field<T, ?> field = fields.get(i);
				if (!field.isReadOnly()){
					if (c>0)
						ret.append("\n, ");
					ret.append("?");
					c++;
				}
			}
			ret.append(" )");
			insertSql= ret.toString();
		}
		return insertSql;
	};

	protected String getDeleteSql(){
		if (deleteSql==null){
			StringBuilder ret = new StringBuilder("delete from ");
			ret.append(identifierQuote(relationName));
			ret.append("\n where ").append(getPkSql());
			deleteSql = ret.toString();
		}
		return deleteSql;
	};

	protected String getUpdateSql(){
		if (updateSql==null){
			StringBuilder ret = new StringBuilder("update ");
			ret.append(identifierQuote(relationName)).append(" set ");
			int c=0;
			for (int i = 0; i < fields.size(); i++) {
				Field<T, ?> field = fields.get(i);
				if (!field.isReadOnly()){
					if (c>0)
						ret.append("\n, ");
					ret.append(identifierQuote(field.getName())).append(" = ? ");
					c++;
				}
			}
			ret.append(" where ").append(getPkSql());
			updateSql = ret.toString();
		}
		return updateSql;
	};

	protected String getPkSql() {
		if (pkSql==null){
			StringBuilder ret = new StringBuilder();
			for (int i = 0; i < getKeyNames().length; i++) {
				if (i>0)
					ret.append("\n and ");
				ret.append(relationName).append(".").append(identifierQuote(fields.get(i).getName())).append(" = ? ");
			}
			pkSql = ret.toString();
		}
		return pkSql;
	}

	public T getBean(ResultSet rs, int rowNum)  {
		T bean = newIstance();
		for (Field<T, ?> field : fields) {
			try {
				field.readValueFrom(rs, bean);
			} catch (Exception e) {
				//				if (e instanceof SQLException)
				//					throw (SQLException)e;
				throw new SystemError("Error in setValue:" + getRelationName() +"."+ field.getName(), e);
			}
		}
		bean.saved();
		return bean;
	}

	protected int setParams(PreparedStatement stm, T bean) throws SQLException {
		int p = 1;
		for (Field<T, ?> field : fields) {
			if (!field.isReadOnly()){
				field.setParam(stm, p++, bean);
			}
		}
		return p;
	}

	public List<T> getByWhere(String where, Object ... params) throws SystemError, LogicError {
		StringBuilder sql = new StringBuilder(getSelectSql(0, 0, null, where));
		return getList(sql.toString(), params);
	}

	public List<T> getByWhereOrder(String where, String orderBy, Object ... params) throws SystemError, LogicError {
		StringBuilder sql = new StringBuilder(getSelectSql(0, 0, orderBy, where));
		return getList(sql.toString(), params);
	}

	protected int selectInteger(String sql, Object ... params) throws LogicError {
		try {
			Connection conn = jdbcHelper.getConnection();
			try{
				PreparedStatement stm = conn.prepareStatement(sql);
				jdbcHelper.setParams(stm, params);
				try{
					ResultSet rs = stm.executeQuery();
					try{
						while (rs.next()) {
							Object x = rs.getObject(1);
							return Integer.valueOf(x.toString());
						}
					}finally{
						rs.close();
					}
				}finally{
					stm.close();
				}
			}finally{
				conn.close();
			}
			return 0;
		} catch(SystemError e) {
			logger.error("Error",e);
			throw e;
		} catch (SQLException e) {
			logger.error("Error",e);
			throw new SystemError( "Error in query execution :"+e, MODULO, DaoUtils.getMethod(e),  e);
		} catch (Exception e) {
			logger.error("Error",e);
			throw new SystemError( "Error in esecuzione metodo:"+e, MODULO, DaoUtils.getMethod(e),  e);
		}
	}

	@Override
	public int getCount() throws LogicError {
		return selectInteger(getCountSelect());
	}

	protected String getCountSelect(){
		if (countSql==null){
			List<FieldJoin<T,?>> joinList = new ArrayList<FieldJoin<T,?>>();
			StringBuilder ret = new StringBuilder("select count(*) ");
			for (int i = 0; i < fields.size(); i++) {
				Field<T, ?> field = fields.get(i);
				if (field instanceof FieldJoinImpl){
					@SuppressWarnings({ "rawtypes", "unchecked" })
					FieldJoin<T,?> fieldJoin = (FieldJoinImpl) field;
					joinList.add(fieldJoin);
				}
			}
			ret.append("\n from ").append(identifierQuote(relationName)).append("\n");
			Set<String> joinset = new LinkedHashSet<String>();
			for (FieldJoin<T,?> fieldJoin : joinList) {
				joinset.add(fieldJoin.getJoinSql(relationName));
			}
			for(String join:joinset){
				ret.append(join).append("\n");
			}
			countSql = ret.toString();
		}
		return countSql;
	}

	@Override
	public int getCountByWhere(String where, Object... params)
			throws LogicError {
		StringBuilder sql = new StringBuilder(getCountSelect());
		_appendWhere(sql, where);
		return selectInteger(sql.toString(), params);
	}

	public int getCountByWhere(SqlWhere where)
			throws LogicError {
		StringBuilder sql = new StringBuilder(getCountSelect());
		sql.append(where.getWhere());
		return selectInteger(sql.toString(), where.getParamsArray());
	}

	@Override
	public int getCountFor(Condition... conditions) throws LogicError {
		SqlWhere where = new SqlWhere(conditions);
		return getCountByWhere(where);
	}

	@Override
	public int getCountFor(Collection<Condition> conditions)
			throws LogicError {
		SqlWhere where = new SqlWhere(conditions);
		return getCountByWhere(where);
	}

	@Override
	public int getPageCount(int pageSize, String where, Object ... params) throws LogicError{
		int count = getCountByWhere(where, params);
		int pc = count / pageSize;
		if (count % pageSize>0)
			pc++;
		return pc;
	}

	public List<T> getByWhere(int page, int pageSize, String where, Object ... params) throws SystemError, LogicError {
		StringBuilder sql = new StringBuilder(getSelectSql(page, pageSize, null, where));
		return getList(sql.toString(), params);
	}

	protected void _appendWhere(StringBuilder sql, String where){
		if (where!=null){
			where = where.trim();
			if (!where.isEmpty()){
				if (!where.toLowerCase().startsWith("where "))
					sql.append("\n where ");
				sql.append(" ").append(where);
			}
		}
	}

	public List<T> getByWhereOrder(int page, int pageSize, String where, String orderBy, Object ... params) throws SystemError, LogicError {
		StringBuilder sql = new StringBuilder(getSelectSql(page, pageSize, orderBy, where));
		return getList(sql.toString(), params);
	}

	public List<String> getFieldNames(){
		List<String> ret = new ArrayList<String>();
		for (Field<T, ?> field : fields){
			ret.add(field.getName());
		}
		return ret;
	}

	public List<Field<T, ?>> getFields(){
		return Collections.unmodifiableList(fields);
	}

	public <V> Field<T, V> getFieldDef(String fieldName){
		@SuppressWarnings("unchecked")
		Field<T, V> x = (Field<T, V>) fieldMap.get(fieldName);
		return x;
	}

	public <V> Field<T, V> getProperty(String fieldName){
		@SuppressWarnings("unchecked")
		Field<T, V> x = (Field<T, V>) propertyMap.get(fieldName);
		return x;
	}

	public Object getPropertyValue(T bean, String propertyName){
		if (bean==null)
			return null;
		Field<T, ?> field = getProperty(propertyName);
		if (field!=null)
			return field.getValue(bean);
		return null;
	}

	public <V> void setPropertyValue(T bean, String propertyName, V value){
		if (bean==null)
			return;
		@SuppressWarnings("unchecked")
		Field<T, V> field = (Field<T, V>) getProperty(propertyName);
		if (field!=null)
			field.setValue(value, bean);
	}

	protected static <T extends PersistentObject>  Field<T, ?> setRoles(
			Set<String> viewRoles, Set<String> editRoles,Field<T, ?> field) {
		field.setEditRoles(editRoles);
		field.setViewRoles(viewRoles);
		return field;
	}

	protected String getDaoLabel() {
		return " " + relationName;
	}

	protected String getSelectSql(int page, int pageSize, String orderBy) {
		return getSelectSql(page, pageSize, orderBy, null);
	}

	protected void calcInnerSelect(StringBuilder select){
		if (innerSelectSql==null){
			StringBuilder ret = new StringBuilder();
			List<FieldJoin<T, ?>> joinList = new ArrayList<FieldJoin<T, ?>>();
			for (int i = 0; i < fields.size(); i++) {
				Field<T, ?> field = fields.get(i);
				if (i>0)
					ret.append("\n, ");
				if (field instanceof FieldJoinImpl){
					@SuppressWarnings({ "unchecked", "rawtypes" })
					FieldJoin<T, ?> fieldJoin = (FieldJoinImpl) field;
					ret.append(fieldJoin.getFieldSql());
					joinList.add(fieldJoin);
				} else
					ret.append(identifierQuote(relationName)).append(".").append(identifierQuote(field.getName()));
			}
			ret.append("\n from ").append(identifierQuote(relationName)).append("\n");
			Set<String> joinset = new LinkedHashSet<String>();
			for (FieldJoin<T, ?> fieldJoin : joinList) {
				joinset.add(fieldJoin.getJoinSql(relationName));
			}
			for(String join:joinset){
				ret.append(join).append("\n");
			}
			innerSelectSql = ret.toString();
		}
		select.append(innerSelectSql);
	}

	protected String getSelectSql(int page, int pageSize, String orderBy,
			String where){
		boolean havePaging = (page>0 && pageSize>0);
		String preselect = "";
		if (havePaging)
			preselect = calcPreselect(page, pageSize);
		StringBuilder ret = new StringBuilder("select ");
		if (havePaging && databaseProductType==DatabaseProductType.firebird)
			ret.append(preselect).append(" ");
		calcInnerSelect(ret);
		_appendWhere(ret, where);
		if (orderBy!=null && !orderBy.trim().isEmpty()){
			ret.append(" order by ").append(orderBy);
		}
		if (havePaging && databaseProductType==DatabaseProductType.postgresql )
			ret.append(preselect).append(" ");
		return ret.toString();

	};

}