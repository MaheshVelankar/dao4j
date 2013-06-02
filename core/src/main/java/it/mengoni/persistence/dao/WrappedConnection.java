package it.mengoni.persistence.dao;

import it.mengoni.persistence.exception.LogicError;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

public class WrappedConnection implements Connection {

	private final Connection cnt;

	WrappedConnection(Connection connection) {
		this.cnt = connection;
		if (cnt==null)
			throw new LogicError("Connection not assigned");
	}

	public void clearWarnings() throws SQLException {
		cnt.clearWarnings();
	}

	public void close() throws SQLException {
		//cnt.close();
	}

	public void realClose() throws SQLException {
		cnt.close();
	}

	public void commit() throws SQLException {
		cnt.commit();
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return cnt.createArrayOf(typeName, elements);
	}

	public Blob createBlob() throws SQLException {
		return cnt.createBlob();
	}

	public Clob createClob() throws SQLException {
		return cnt.createClob();
	}

	public NClob createNClob() throws SQLException {
		return cnt.createNClob();
	}

	public SQLXML createSQLXML() throws SQLException {
		return cnt.createSQLXML();
	}

	public Statement createStatement() throws SQLException {
		return cnt.createStatement();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return cnt.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return cnt.createStatement(resultSetType, resultSetConcurrency);
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return cnt.createStruct(typeName, attributes);
	}

	public boolean getAutoCommit() throws SQLException {
		return cnt.getAutoCommit();
	}

	public String getCatalog() throws SQLException {
		return cnt.getCatalog();
	}

	public Properties getClientInfo() throws SQLException {
		return cnt.getClientInfo();
	}

	public String getClientInfo(String name) throws SQLException {
		return cnt.getClientInfo(name);
	}

	public int getHoldability() throws SQLException {
		return cnt.getHoldability();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return cnt.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException {
		return cnt.getTransactionIsolation();
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return cnt.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException {
		return cnt.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return cnt.isClosed();
	}

	public boolean isReadOnly() throws SQLException {
		return cnt.isReadOnly();
	}

	public boolean isValid(int timeout) throws SQLException {
		return cnt.isValid(timeout);
	}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return cnt.isWrapperFor(arg0);
	}

	public String nativeSQL(String sql) throws SQLException {
		return cnt.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return cnt.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return cnt.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		return cnt.prepareCall(sql);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return cnt.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return cnt.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql, int arg1)
			throws SQLException {
		return cnt.prepareStatement(sql, arg1);
	}

	public PreparedStatement prepareStatement(String sql, int[] arg1)
			throws SQLException {
		return cnt.prepareStatement(sql, arg1);
	}

	public PreparedStatement prepareStatement(String sql, String[] arg1)
			throws SQLException {
		return cnt.prepareStatement(sql, arg1);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return cnt.prepareStatement(sql);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		cnt.releaseSavepoint(savepoint);
	}

	public void rollback() throws SQLException {
		cnt.rollback();
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		cnt.rollback(savepoint);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		//cnt.setAutoCommit(arg0);
	}

	public void setCatalog(String catalog) throws SQLException {
		cnt.setCatalog(catalog);
	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		cnt.setClientInfo(properties);
	}

	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		cnt.setClientInfo(name, value);
	}

	public void setHoldability(int holdability) throws SQLException {
		cnt.setHoldability(holdability);
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		cnt.setReadOnly(readOnly);
	}

	public Savepoint setSavepoint() throws SQLException {
		return cnt.setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		return cnt.setSavepoint(name);
	}

	public void setTransactionIsolation(int level) throws SQLException {
		cnt.setTransactionIsolation(level);
	}

	public void setTypeMap(Map<String, Class<?>> typeMap) throws SQLException {
		cnt.setTypeMap(typeMap);
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return cnt.unwrap(arg0);
	}



}
