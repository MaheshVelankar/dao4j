package org.example.petstore.dao.test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import it.mengoni.exception.SystemError;

import org.example.petstore.dao.DaoFactory;
import org.junit.BeforeClass;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public abstract class AbstractTest {


	private static DaoFactory _df;
	private static FileSystemXmlApplicationContext applicationContext;

	public static DaoFactory getDf() {
		if (_df==null){
			_df = getBean("DaoFactory");
		}
		if (_df==null)
			throw new SystemError("DaoFactory not assigned ");
		return _df;
	}

	@BeforeClass
	public static void setup() {
	}

	public AbstractTest() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected static <T> T getBean(String id){
		return (T)getApplicationContext().getBean(id);
	}

	private static AbstractApplicationContext getApplicationContext() {
		if (applicationContext==null)
			applicationContext = new FileSystemXmlApplicationContext("src/test/resources/applicationContext.xml");
		return applicationContext;
	}

	protected void dump(ResultSet rs) throws SQLException {
		ResultSetMetaData mt = rs.getMetaData();
		while (rs.next()) {
			for (int i=1; i<=mt.getColumnCount(); i++){
				if (i>1)
					System.out.print(", ");
				System.out.print(mt.getColumnLabel(i)+"="+rs.getObject(i));
			}
			System.out.println("");
		}

	}
}
