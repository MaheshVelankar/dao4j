package it.mengoni.generator;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class JdbcConfig implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String driverClass;
	private String jdbcUrl;
	private String user;
	private String password;

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/*
	 * <property name="driverClass" value="org.firebirdsql.jdbc.FBDriver"/>
	 * <property name="jdbcUrl"
	 * value="jdbc:firebirdsql:localhost/3050:/lorenzo/agenzia2002.fdb"/>
	 * <property name="idleConnectionTestPeriod"
	 * value="${c3p0.idleConnectionTestPeriod}"></property> <property
	 * name="initialPoolSize" value="${c3p0.initialPoolSize}"></property>
	 * <property name="maxIdleTime" value="${c3p0.maxIdleTime}"></property>
	 * <property name="maxPoolSize" value="${c3p0.maxPoolSize}"></property>
	 * <property name="minPoolSize" value="${c3p0.minPoolSize}"></property>
	 * <property name="maxStatements" value="${c3p0.minPoolSize}"></property>
	 * <property name="properties"> <!--
	 * http://www.mchange.com/projects/c3p0/index.html#appendix_a --> <props>
	 * <prop key="user">sysdba</prop> <prop key="password">masterkey</prop>
	 * </props> </property>
	 */
}
