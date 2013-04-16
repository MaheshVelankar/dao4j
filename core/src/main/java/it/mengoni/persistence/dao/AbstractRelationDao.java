package it.mengoni.persistence.dao;

import it.mengoni.exception.LogicError;
import it.mengoni.exception.SystemError;
import it.mengoni.persistence.dto.PersistentObject;

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

public abstract class AbstractRelationDao<T extends PersistentObject> extends AbstractDao<T> {

	private static Logger logger = LoggerFactory.getLogger(JdbcHelper.class);
	private static final String MODULO = AbstractRelationDao.class.getSimpleName();

	private static final String PK_ERR = "In the table %s the primary key: %s exist";
	private static final String PK_ERR_ITA = "Esiste un oggetto nella tabella %s con la stessa chiave primaria: %s";

	private static boolean isIt = "it".equalsIgnoreCase(System.getProperty("user.language"));

	protected final String relationName;
	protected final List<Field<T, ?>> fields;
	protected final Map<String,Field<T, ?>> fieldMap;
	protected final Map<String,Field<T, ?>> propertyMap;
	private String selectSql;
	private String insertSql;
	private String deleteSql;
	private String pkSql;
	private String updateSql;
	private String countSql;

	@Override
	public int insert(T bean) throws SystemError, LogicError {
		try{
			return super.insert(bean);
		} catch (SystemError e) {
			if (databaseProductType==DatabaseProductType.firebird && e.getMessage().contains("335544665") && e.getMessage().contains("FBSQLException")){
				throw new LogicError(String.format(isIt?PK_ERR_ITA:PK_ERR, relationName, dump(bean.getKey())));
			}
			throw e;
		}

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

	@Override
	public int delete(T bean) throws SystemError,LogicError {
		return super.delete(bean);
	}

	@Override
	public int update(T bean) throws SystemError, LogicError {
		try{
			return super.update(bean);
		} catch (SystemError e) {
			if (databaseProductType==DatabaseProductType.firebird && e.getMessage().contains("335544665") && e.getMessage().contains("FBSQLException")){
				throw new LogicError(String.format(isIt?PK_ERR_ITA:PK_ERR, relationName, dump(bean.getKey())));
			}
			throw e;
		}
	}

	public AbstractRelationDao(JdbcHelper jdbcHelper, CharsetConverter charsetConverter, String relationName, List<Field<T, ?>> fields) {
		super(jdbcHelper, charsetConverter, extractKeyNames(fields));
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

	@Override
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


	@Override
	protected T getBean(ResultSet rs, int rowNum) throws SQLException {
		T bean = newIstance();
		for (Field<T, ?> field : fields) {
			try {
				field.readValueFrom(rs, bean);
			} catch (Exception e) {
				if (e instanceof SQLException)
					throw (SQLException)e;
				throw new SystemError("Error in setValue:" + getRelationName() +"."+ field.getName(), e);
			}
		}
		bean.saved();
		return bean;
	}

	@Override
	protected int setParams(PreparedStatement stm, T bean) throws SQLException {
		int p = 1;
		for (Field<T, ?> field : fields) {
			if (!field.isReadOnly()){
				field.checkValue(bean);
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

	@Override
	protected String getDaoLabel() {
		return " " + relationName;
	}

	@Override
	protected String getSelectSql(int page, int pageSize, String orderBy) {
		return getSelectSql(page, pageSize, orderBy, null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected String getSelectSql(int page, int pageSize, String orderBy,
			String where){
		String preselect = calcPreselect(page, pageSize);
		if (selectSql==null || (preselect!=null && !preselect.isEmpty())){
			List<FieldJoin<T, ?>> joinList = new ArrayList<FieldJoin<T, ?>>();
			StringBuilder ret = new StringBuilder("select ");
			if (databaseProductType==DatabaseProductType.firebird && preselect!=null && !preselect.isEmpty())
				ret.append(preselect).append(" ");
			for (int i = 0; i < fields.size(); i++) {
				Field<T, ?> field = fields.get(i);
				if (i>0)
					ret.append("\n, ");
				if (field instanceof FieldJoinImpl){
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
			_appendWhere(ret, where);
			if (orderBy!=null && !orderBy.trim().isEmpty()){
				ret.append(" order by ").append(orderBy);
			}
			if (databaseProductType==DatabaseProductType.postgresql && preselect!=null && !preselect.isEmpty())
				ret.append(preselect).append(" ");
			selectSql = ret.toString();
		}
		return selectSql;
	};

}