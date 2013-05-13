package it.mengoni.daocore.test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import it.mengoni.persistence.exception.SystemError;

import org.junit.BeforeClass;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public abstract class AbstractBaseT {


	private static TestDaoFactory _df;
	private static FileSystemXmlApplicationContext applicationContext;

	public static TestDaoFactory getDf() {
		if (_df==null){
			_df = getBean("a2002DaoFactory");
		}
		if (_df==null)
			throw new SystemError("a2002DaoFactory not assigned ");
		return _df;
	}

	@BeforeClass
	public static void setup() {
	}

	public AbstractBaseT() {
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
