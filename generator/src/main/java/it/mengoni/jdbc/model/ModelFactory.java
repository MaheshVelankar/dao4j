package it.mengoni.jdbc.model;

import it.mengoni.persistence.dao.Dao.DatabaseProductType;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ModelFactory {

	public static boolean isReserved(String value){
		return reserved.contains(value.toLowerCase());
	}

	public static final Set<String> reserved = new HashSet<String>();
	public static final Set<String> sqlReserved = new HashSet<String>();

	static{
		reserved.add("abstract");
		reserved.add("double");
		reserved.add("int");
		reserved.add("static");
		reserved.add("boolean");
		reserved.add("else");
		reserved.add("interface");
		reserved.add("super");
		reserved.add("break");
		reserved.add("extends");
		reserved.add("long");
		reserved.add("switch");
		reserved.add("byte");
		reserved.add("final");
		reserved.add("native");
		reserved.add("synchronized");
		reserved.add("case");
		reserved.add("finally");
		reserved.add("new");
		reserved.add("this");
		reserved.add("catch");
		reserved.add("float");
		reserved.add("null");
		reserved.add("throw");
		reserved.add("char");
		reserved.add("for");
		reserved.add("package");
		reserved.add("throws");
		reserved.add("class");
		reserved.add("goto");
		reserved.add("private");
		reserved.add("transient");
		reserved.add("const");
		reserved.add("if");
		reserved.add("protected");
		reserved.add("try");
		reserved.add("continue");
		reserved.add("implements");
		reserved.add("public");
		reserved.add("void");
		reserved.add("default");
		reserved.add("import");
		reserved.add("return");
		reserved.add("volatile");
		reserved.add("do");
		reserved.add("instanceof");
		reserved.add("short");
		reserved.add("while");
		try {
			InputStream in = ModelFactory.class.getResourceAsStream("/sql-reserved-fb.txt");
			Scanner scanner = new Scanner(in);
			try{
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				if (line!=null)
					sqlReserved.add(line.trim().toLowerCase());
			}
			}finally{
				scanner.close();
				in.close();
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public static String quoteFBSqlReserved(String dbName) {
		if (dbName==null)
			return null;
		if (sqlReserved.contains(dbName.trim().toLowerCase()))
				return "\\\""+dbName+"\\\"";
		return dbName;
	}

	public static Object quoteSqlReserved(String name,
			DatabaseProductType databaseProductType) {
		if (databaseProductType == DatabaseProductType.firebird)
			return quoteFBSqlReserved(name);
		return name;
	}

}