package it.mengoni.persistence.dao;


public class JdbcFactory {

	private static JdbcFactory instance  = new JdbcFactory();

	public static JdbcFactory getInstance(){
		return instance;
	}


}
