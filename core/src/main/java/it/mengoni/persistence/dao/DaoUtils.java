package it.mengoni.persistence.dao;

import it.mengoni.db.DaoFactory;
import it.mengoni.persistence.dto.PersistentObject;

import java.util.HashMap;
import java.util.Map;

import org.javatuples.Tuple;

public class DaoUtils {

	private static final Map<String, DaoFactory> factoryMap = new HashMap<String, DaoFactory>();

	public static <T extends PersistentObject> Dao<T> getDao(String factoryName, Class<T> javaClass){
		return getDaoFactory(factoryName).getDao(javaClass);
	}

	public static DaoFactory getDaoFactory(String factoryName) {
		return factoryMap.get(factoryName);
	}

	public static void setDaoFactory(String factoryName, DaoFactory daoFactory) {
		factoryMap.put(factoryName, daoFactory);
	}

	public static boolean isEmpty(Tuple k) {
		if (k!=null){
			for (int i=0; i<k.getSize(); i++){
				Object v = k.getValue(i);
				if (v instanceof String){
					if (((String)v).trim().isEmpty())
						return true;
				} else
					if (v==null )
						return true;
			}
			return false;
		}
		return true;
	}

	public static String getMethod(Exception x) {
		StackTraceElement[] st = x.getStackTrace();
		if (st.length > 2)
			return st[1].getMethodName();
		if (st.length == 2)
			return st[1].getMethodName();
		if (st.length == 1)
			return st[0].getMethodName();
		return "non definito";
	}

}