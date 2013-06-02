package it.mengoni.persistence.dao;


import it.mengoni.persistence.dao.Dao.DatabaseProductType;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.LogicError;
import it.mengoni.persistence.exception.SystemError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcHelper {
	private static Logger logger = LoggerFactory.getLogger(JdbcHelper.class);
	private static final String MODULO = JdbcHelper.class.getSimpleName();
	private DataSource datasource;

	public JdbcHelper(DataSource datasource) {
		if (datasource==null)
			throw new SystemError("Datasource not assigned ");
		this.datasource = datasource;
	}

	public String getDatabaseProductName(){
		Connection c = getConnection();
		try {
			String pn;
			pn = c.getMetaData().getDatabaseProductName();
			return pn;
		} catch (SQLException e) {
			return null;
		} finally{
			try {
				c.close();
			} catch (SQLException e) {
			}
		}
	}

	public DatabaseProductType getDatabaseProductType() {
		String pn = getDatabaseProductName();
		if (pn == null)
			return DatabaseProductType.unknow;
		pn = pn.toLowerCase();
		if (pn.contains("firebird"))
			return DatabaseProductType.firebird;
		if (pn.contains("postgresql"))
			return DatabaseProductType.postgresql;
		if (pn.contains("mysql"))
			return DatabaseProductType.mysql;
		if (pn.contains("oracle"))
			return DatabaseProductType.oracle;
		return DatabaseProductType.unknow;
	}

	public Connection getConnection() throws SystemError {
		/**
		 * check if there is a transaction is present
		 */
		Connection c = TransactionManager.getInstance().getConnection();
		if (c!=null)
			return c;
		// if not, get a new connection
		try {
			return datasource.getConnection();
		} catch (SQLException e) {
			throw new SystemError( e.toString(), MODULO, "getConnection()",  e);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, Double valore, int sqlType, boolean eTraduci, boolean crea) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setDouble(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, Integer valore, int sqlType, boolean eTraduci, boolean crea) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setInt(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, String valore, int sqlType, boolean eTraduci, boolean crea) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setString(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, String valore, int sqlType) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setString(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, BigDecimal valore, int sqlType, boolean eTraduci, boolean crea) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setBigDecimal(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, BigDecimal valore, int sqlType) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setBigDecimal(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, Timestamp valore, int sqlType, boolean eTraduci, boolean crea) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setTimestamp(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, Timestamp valore, int sqlType) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setTimestamp(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, Date valore, int sqlType, boolean eTraduci, boolean crea) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setDate(indiceParametro, new java.sql.Date(valore.getTime()));
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, Date valore, int sqlType) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setDate(indiceParametro, new java.sql.Date(valore.getTime()));
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, Boolean valore, int sqlType, boolean eTraduci, boolean crea) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setBoolean(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, Boolean valore, int sqlType) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setBoolean(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, Object valore, int sqlType, boolean eTraduci, boolean crea) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setObject(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, Object valore, int sqlType) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else
				statement.setObject(indiceParametro, valore);
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, File valore, int sqlType, boolean eTraduci, boolean crea) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else{
				File file = (File) valore;
				int lunghezza = (int) file.length();
				InputStream inStream = new FileInputStream(file);
				statement.setBinaryStream(indiceParametro, inStream, lunghezza);
			}
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		} catch (FileNotFoundException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public void setValueStatement(PreparedStatement statement, int indiceParametro, File valore, int sqlType) throws SystemError {
		try{
			if (valore==null)
				statement.setNull(indiceParametro, sqlType);
			else{
				File file = (File) valore;
				int lunghezza = (int) file.length();
				InputStream inStream = new FileInputStream(file);
				statement.setBinaryStream(indiceParametro, inStream, lunghezza);
			}
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		} catch (FileNotFoundException ex) {
			throw new SystemError( ex.toString(), MODULO, "setValueStatement",  ex);
		}
	}

	public ResultSet eseguiQueryStatement(PreparedStatement statement) throws SystemError {
		try {
			return statement.executeQuery();
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "eseguiQueryStatement",  ex);
		}
	}

	public int eseguiUpdateStatement(PreparedStatement statement) throws SystemError {
		try {
			return statement.executeUpdate();
		} catch (SQLException ex) {
			throw new SystemError( ex.toString(), MODULO, "eseguiUpdateStatement",  ex);
		}
	}


	public interface BeanCreator<T extends PersistentObject>{
		/**
		 * Questo metodo viene chiamato per ogni riga del result set, il suo compito è di costruire un bean di classe <T> dalla riga.
		 * NON VA FATTO: chiudere il result set, ovvero non usate close(), chiamare next()
		 * @param rs
		 * @param rowNum
		 * @return il bean di classe <T>
		 * @throws SystemError
		 * @throws LogicError
		 */
		public T getBean(ResultSet rs, int rowNum) throws SystemError, LogicError;
	}

	public static final class NullFor{
		private final int sqlType;

		private NullFor(int sqlType){
			this.sqlType = sqlType;
		}

		public int getSqlType(){
			return sqlType;
		}
	}

	public static final NullFor NULL_BIT = new NullFor(Types.BIT); // -7;
	public static final NullFor NULL_TINYINT = new NullFor(Types.TINYINT); // -6;
	public static final NullFor NULL_SMALLINT = new NullFor(Types.SMALLINT); // 5;
	public static final NullFor NULL_INTEGER = new NullFor(Types.INTEGER); // 4;
	public static final NullFor NULL_BIGINT = new NullFor(Types.BIGINT); // -5;
	public static final NullFor NULL_FLOAT = new NullFor(Types.FLOAT); // 6;
	public static final NullFor NULL_REAL = new NullFor(Types.REAL); // 7;
	public static final NullFor NULL_DOUBLE = new NullFor(Types.DOUBLE); // 8;
	public static final NullFor NULL_NUMERIC = new NullFor(Types.NUMERIC); // 2;
	public static final NullFor NULL_DECIMAL = new NullFor(Types.DECIMAL); // 3;
	public static final NullFor NULL_CHAR = new NullFor(Types.CHAR); // 1;
	public static final NullFor NULL_VARCHAR = new NullFor(Types.VARCHAR); // 12;
	public static final NullFor NULL_LONGVARCHAR = new NullFor(Types.LONGVARCHAR); // -1;
	public static final NullFor NULL_DATE = new NullFor(Types.DATE); // 91;
	public static final NullFor NULL_TIME = new NullFor(Types.TIME); // 92;
	public static final NullFor NULL_TIMESTAMP = new NullFor(Types.TIMESTAMP); // 93;
	public static final NullFor NULL_BINARY = new NullFor(Types.BINARY); // -2;
	public static final NullFor NULL_VARBINARY = new NullFor(Types.VARBINARY); // -3;
	public static final NullFor NULL_LONGVARBINARY = new NullFor(Types.LONGVARBINARY); // -4;
	public static final NullFor NULL_NULL = new NullFor(Types.NULL); // 0;
	public static final NullFor NULL_OTHER = new NullFor(Types.OTHER); // 1111;
	public static final NullFor NULL_JAVA_OBJECT = new NullFor(Types.JAVA_OBJECT); // 2000;
	public static final NullFor NULL_DISTINCT = new NullFor(Types.DISTINCT); // 2001;
	public static final NullFor NULL_STRUCT = new NullFor(Types.STRUCT); // 2002;
	public static final NullFor NULL_ARRAY = new NullFor(Types.ARRAY); // 2003;
	public static final NullFor NULL_BLOB = new NullFor(Types.BLOB); // 2004;
	public static final NullFor NULL_CLOB = new NullFor(Types.CLOB); // 2005;
	public static final NullFor NULL_REF = new NullFor(Types.REF); // 2006;
	public static final NullFor NULL_DATALINK = new NullFor(Types.DATALINK); // 70;
	public static final NullFor NULL_BOOLEAN = new NullFor(Types.BOOLEAN); // 16;
	public static final NullFor NULL_ROWID = new NullFor(Types.ROWID); // -8;
	public static final NullFor NULL_NCHAR = new NullFor(Types.NCHAR); // -15;
	public static final NullFor NULL_NVARCHAR = new NullFor(Types.NVARCHAR); // -9;
	public static final NullFor NULL_LONGNVARCHAR = new NullFor(Types.LONGNVARCHAR); // -16;
	public static final NullFor NULL_NCLOB = new NullFor(Types.NCLOB); // 2011;
	public static final NullFor NULL_SQLXML = new NullFor(Types.SQLXML); // 2009;

	public void setParams(PreparedStatement stm, Object... params) throws LogicError, SQLException{
		for (int i = 0; i < params.length; i++) {
			Object p = params[i];
			if (p instanceof NullFor)
				stm.setNull(i+1,((NullFor)p).sqlType);
			else if (p!=null){
				if (p.getClass()==java.util.Date.class){
					java.sql.Date d = new java.sql.Date(((Date)p).getTime());
					stm.setDate(i+1, d);
				}else
					stm.setObject(i+1, p);
			} else
				throw new LogicError( "Error in query execution : parametro["+i+"] null");
		}
	}

	public <T  extends PersistentObject> List<T> queryForList(String sql, BeanCreator<T> creator, Object... params) throws SystemError, LogicError {
		try {
			List<T> ret = new ArrayList<T>();
			Connection conn = getConnection();
			try{
				PreparedStatement stm = conn.prepareStatement(sql);
				try{
					setParams(stm, params);
					ResultSet rs = stm.executeQuery();
					try{
						int rowNum = 0;
						while (rs.next()) {
							T bean = creator.getBean(rs, rowNum);
							bean.saved();
							ret.add(bean);
							rowNum++;
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
			return ret;
		} catch(LogicError e) {
			logger.error("Error in sql:"+sql,e);
			throw e;
		} catch(SystemError e) {
			logger.error("Error in sql:"+sql,e);
			throw e;
		} catch (SQLException e) {
			logger.error("Error in sql:"+sql,e);
			throw new SystemError( "Error in query execution :"+e, MODULO, DaoUtils.getMethod(e),  e);
		} catch (Exception e) {
			logger.error("Error in sql:"+sql,e);
			throw new SystemError( "Error in esecuzione metodo:"+e, MODULO, DaoUtils.getMethod(e),  e);
		}
	}

	public <T extends PersistentObject> T queryForObject(String sql, BeanCreator<T> creator, Object... params) throws SystemError, LogicError {
		try {
			T ret = null;
			Connection conn = getConnection();
			try{
				PreparedStatement stm = conn.prepareStatement(sql);
				try{
					setParams(stm, params);
					ResultSet rs = stm.executeQuery();
					try{
						int rowNum = 0;
						while (rs.next()) {
							ret = creator.getBean(rs, rowNum);
							ret.saved();
							if (rowNum>1)
								throw new LogicError( "Error in query execution : il resultSet ha più di una riga");
							rowNum++;
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
			return ret;
		} catch(LogicError e) {
			logger.error("Error",e);
			throw e;
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

	public <T extends PersistentObject> int updateObject(String sql, BeanSqlSetter<T> sqlSetter, T bean) throws SystemError, LogicError {
		try {
			Connection conn = getConnection();
			try{
				PreparedStatement stm = conn.prepareStatement(sql);
				try{
					sqlSetter.setParams(stm, bean);
					int res = stm.executeUpdate();
					if (res==1)
						bean.saved();
					return res;
				}finally{
					stm.close();
				}
			}finally{
				conn.close();
			}
		} catch(LogicError e) {
			logger.error("Error",e);
			throw e;
		} catch(SystemError e) {
			logger.error("Error",e);
			throw e;
		} catch (SQLException e) {
			logger.error("Error",e);
			throw new SystemError( "Error in update:"+e, MODULO, DaoUtils.getMethod(e),  e);
		} catch (Exception e) {
			logger.error("Error",e);
			throw new SystemError( "Error in update:"+e, MODULO, DaoUtils.getMethod(e),  e);
		}
	}

	public interface BeanSqlSetter<T extends PersistentObject> {
		/**
		 * Setta i parametri sql da un bean
		 * @param stm dpve settare i parametri
		 * @param bean oggetto da cui prendere i valori
		 * @throws SystemError
		 * @throws LogicError
		 */
		public void setParams(PreparedStatement stm, T bean) throws SystemError, LogicError;
	}

	public int update(String sql, Object... params) throws SystemError, LogicError {
		try {
			Connection conn = getConnection();
			try{
				PreparedStatement stm = conn.prepareStatement(sql);
				try{
					setParams(stm, params);
					return stm.executeUpdate();
				}finally{
					stm.close();
				}
			}finally{
				conn.close();
			}
		} catch(LogicError e) {
			logger.error("Error",e);
			throw e;
		} catch(SystemError e) {
			logger.error("Error",e);
			throw e;
		} catch (SQLException e) {
			logger.error("Error",e);
			throw new SystemError( "Error in update:"+e, MODULO, DaoUtils.getMethod(e),  e);
		} catch (Exception e) {
			logger.error("Error",e);
			throw new SystemError( "Error in update:"+e, MODULO, DaoUtils.getMethod(e),  e);
		}
	}

	public List<Map<String, Object>> queryFor(String sql, Object... params) throws SystemError, LogicError {
		try {
			List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
			Connection conn = getConnection();
			try{
				PreparedStatement stm = conn.prepareStatement(sql);
				try{
					setParams(stm, params);
					ResultSet rs = stm.executeQuery();
					ResultSetMetaData mt = rs.getMetaData();
					try{
						while (rs.next()) {
							Map<String, Object> row = new HashMap<String, Object>();
							for (int i=0; i<mt.getColumnCount(); i++)
								row.put(mt.getColumnName(i+1), rs.getObject(i+1));
							ret.add(row);
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
			return ret;
		} catch(LogicError e) {
			logger.error("Error",e);
			throw e;
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

}