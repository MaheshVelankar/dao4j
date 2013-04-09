package it.mengoni.generator;

import it.mengoni.jdbc.model.Catalog;
import it.mengoni.jdbc.model.Constraints;
import it.mengoni.jdbc.model.Fields;
import it.mengoni.jdbc.model.Fk;
import it.mengoni.jdbc.model.Fks;
import it.mengoni.jdbc.model.Index;
import it.mengoni.jdbc.model.IndexColumn;
import it.mengoni.jdbc.model.Indexes;
import it.mengoni.jdbc.model.Pk;
import it.mengoni.jdbc.model.PkColumn;
import it.mengoni.jdbc.model.Procedure;
import it.mengoni.jdbc.model.ProcedureColumn;
import it.mengoni.jdbc.model.Procedures;
import it.mengoni.jdbc.model.Root;
import it.mengoni.jdbc.model.Schema;
import it.mengoni.jdbc.model.Table;
import it.mengoni.jdbc.model.TableColunm;
import it.mengoni.jdbc.model.TableReference;
import it.mengoni.jdbc.model.TableType;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Helper {

	private static final Logger logger = LoggerFactory.getLogger(Helper.class);

	private static CodeGenConfig config;

	private static Map<Integer, FieldData> fieldTypeMap = new HashMap<Integer, FieldData>();

	static {
		fieldTypeMap.put(Types.BIGINT, new FieldData("Long", "(rs.getLong(getName()))"));
		fieldTypeMap.put(Types.BINARY, new FieldData("byte[]", "(rs.getBytes(getName()))"));
		fieldTypeMap.put(Types.BIT, new FieldData("Boolean", "(rs.getBoolean(getName()))"));
		fieldTypeMap.put(Types.BOOLEAN, new FieldData("Boolean", "(rs.getBoolean(getName()))")); // 16;
		fieldTypeMap.put(Types.CHAR, new FieldData("String", "(getStringValue(rs))"));
		fieldTypeMap.put(Types.DATE, new FieldData("java.sql.Date", "(rs.getDate(getName()))"));
		fieldTypeMap.put(Types.DECIMAL, new FieldData("BigDecimal", "(rs.getBigDecimal(getName()))"));
		fieldTypeMap.put(Types.DOUBLE, new FieldData("Double", "(rs.getDouble(getName()))")); // 8;
		fieldTypeMap.put(Types.FLOAT, new FieldData("Double", "(rs.getDouble(getName()))")); // 6;
		fieldTypeMap.put(Types.INTEGER, new FieldData("Integer", "getIntegerValue(rs)")); // 4;
		fieldTypeMap.put(Types.LONGNVARCHAR, new FieldData("String", "(getStringValue(rs))")); // -16;
		fieldTypeMap.put(Types.LONGVARBINARY, new FieldData("byte[]", "(rs.getBytes(getName()))"));
		fieldTypeMap.put(Types.LONGVARCHAR, new FieldData("String", "(getStringValue(rs))")); // -1;
		fieldTypeMap.put(Types.NCHAR, new FieldData("String", "(getStringValue(rs))")); // -15;
		fieldTypeMap.put(Types.NCLOB, new FieldData("Object", "(rs.getObject(getName()))")); // 2011;
		fieldTypeMap.put(Types.NUMERIC, new FieldData("java.math.BigDecimal", "(rs.getBigDecimal(getName()))")); // 2;
		fieldTypeMap.put(Types.NVARCHAR, new FieldData("String", "(getStringValue(rs))")); // -9;
		fieldTypeMap.put(Types.REAL, new FieldData("Float", "(rs.getFoat(getName()))")); // 7;
		fieldTypeMap.put(Types.REF, new FieldData("Object", "(rs.getObject(getName()))")); // 2006;
		fieldTypeMap.put(Types.ROWID, new FieldData("Object", "(rs.getObject(getName()))")); // -8;
		fieldTypeMap.put(Types.SMALLINT, new FieldData("Short", "getShortValue(rs)"));
		fieldTypeMap.put(Types.SQLXML, new FieldData("String", "(getStringValue(rs))"));
		// fieldTypeMap.put(Types.STRUCT, new FieldData("Object","(rs.getObject(getName()))"));
		fieldTypeMap.put(Types.TIME, new FieldData("java.sql.Time", "(rs.getTime(getName()))"));
		fieldTypeMap.put(Types.TIMESTAMP, new FieldData("java.sql.Timestamp", "(rs.getTimestamp(getName()))"));
		fieldTypeMap.put(Types.TINYINT, new FieldData("Byte", "(rs.getByte(getName()))"));
		fieldTypeMap.put(Types.VARBINARY, new FieldData("byte[]", "(rs.getBytes(getName()))"));
		fieldTypeMap.put(Types.VARCHAR, new FieldData("String", "(getStringValue(rs))"));
		fieldTypeMap.put(Types.BLOB, new FieldData("byte[]", "(rs.getBytes(getName()))"));

	}

	public static FieldData getFieldData(int type) {
		FieldData f = fieldTypeMap.get(type);
		if (f == null)
			f = generic;
		return f;
	}

	static FieldData generic = new FieldData("Object", "(rs.getObject(getName()))");

	public static String getJavaType(int type, Set<String> importSet) {
		FieldData f = getFieldData(type);
		int p = f.getJavaclass().lastIndexOf('.');
		if (p>0){
			importSet.add(f.getJavaclass());
			return f.getJavaclass().substring(p+1);
		}
		return f.getJavaclass();
	}

	public static String getRsGetter(int sqlType, Set<String> importSet) {
		FieldData f = getFieldData(sqlType);
		if (f.getJavaclass().contains("."))
			importSet.add(f.getJavaclass());
		return f.getGetter();
	}

	public static void loadTreeData(String name, Connection connection, Root root) throws ClassNotFoundException, SQLException {
		root.setDbName(name);
		DatabaseMetaData meta = connection.getMetaData();
		dumpCatalog(meta, meta.getCatalogs(), root);
	}

	public static void dumpCatalog(DatabaseMetaData meta, ResultSet res, Root root) throws SQLException {
		int c=0;
		while (res.next()) {
			String s = res.getString(1);
			c++;
			Catalog catalog = new Catalog(s, "", root);
			addSchema(meta, catalog);
		}
		if (c==0){
			Catalog catalog = new Catalog(root);
			addSchema(meta, catalog);
		}
	}

	public static void addSchema(DatabaseMetaData meta, Catalog catalog) throws SQLException {
		String cat;
		if (catalog.isDefault())
			cat = null;
		else
			cat = catalog.getDbName();
		int c=0;
		try {
			ResultSet res = meta.getSchemas(cat, null);
			while (res.next()) {
				String s = res.getString(1);
				Schema tableSchema = new Schema(s, "", catalog);
				addTableTypes(meta, tableSchema);
				addProcedures(meta, tableSchema);
				c++;
			}
			if (c==0){
				Schema tableSchema = new Schema(catalog);
				addTableTypes(meta, tableSchema);
				addProcedures(meta, tableSchema);
			}
		} catch (SQLException e) {
			logger.error("Error", e);
			throw e;
		} catch (AbstractMethodError e) {
			System.err.println(e);
			addSchemaJdk15(meta, catalog);
		}
	}

	public static void addSchemaJdk15(DatabaseMetaData meta, Catalog catalog) throws SQLException {
		int c=0;
		try {
			ResultSet res = meta.getSchemas();
			ResultSetMetaData rm = res.getMetaData();
			for (int n=0; n<rm.getColumnCount(); n++){
				System.out.println(rm.getColumnName(n+1));
			}
			while (res.next()) {
				String s = res.getString(1);
				Schema tableSchema = new Schema(s, "", catalog);
				addTableTypes(meta, tableSchema);
				addProcedures(meta, tableSchema);
				c++;
			}
			if (c==0){
				Schema tableSchema = new Schema(catalog);
				addTableTypes(meta, tableSchema);
				addProcedures(meta, tableSchema);
			}
		} catch (SQLException e) {
			logger.error("Error", e);
			throw e;
		} catch (AbstractMethodError e) {
			System.err.println(e);
		}
	}

	public static void addProcedures(DatabaseMetaData meta, Schema schema) throws SQLException {
		String cat = null;
		String sc = null;
		if(!schema.isDefault()){
			Catalog catalog = (Catalog) schema.getParent();
			if (!catalog.isDefault()){
				cat = catalog.getDbName();
				sc = schema.getDbName();
			}
		}
		Procedures procs = new Procedures(schema);
		ResultSet res = meta.getProcedures(cat, sc, null);
		while (res.next()) {
			String s = res.getString(3);
			Procedure procedure = new Procedure(s, "", procs);
			dumpFields(meta, procedure);
		}
	}

	public static void dumpFields(DatabaseMetaData meta, Procedure procedure) throws SQLException {
		String cat = null;
		String schema = null;
		Catalog c = procedure.getParent().getParent().getParent();
		if(c!=null && !c.isDefault()){
			cat = c.getDbName();
		}
		Schema s = procedure.getParent().getParent();
		if (s!=null && s.isDefault())
			schema = s.getDbName();
		ResultSet res = meta.getProcedureColumns(cat, schema, procedure.getDbName(), null);
		//		PROCEDURE_CAT,	PROCEDURE_SCHEM,	PROCEDURE_NAME,	COLUMN_NAME,	COLUMN_TYPE, DATA_TYPE,	TYPE_NAME,	PRECISION,	LENGTH,	SCALE,	RADIX,	NULLABLE,	REMARKS
		boolean haveColumnSize = haveColumn(res, "COLUMN_SIZE");
		while (res.next()) {
			boolean nullable = res.getInt("NULLABLE")>0;
			int p = 0;
			if (haveColumnSize)
			try{
				p = res.getInt("COLUMN_SIZE");
			}catch(Exception e){
			}
			//			new ProcedureColumn(res.getString(4), res.getInt("DATA_TYPE"),
			//			getProcColType(res.getInt(5)) + " " + getProcSqlColTypeExpr(res),
			//			procedure, nullable, p);
			new ProcedureColumn(res.getString(4), res.getString("TYPE_NAME"), res.getInt("DATA_TYPE"), procedure, nullable, p, getProcColType(res.getInt(5)));
		}
	}

	//	private static String getProcSqlColTypeExpr(ResultSet res) {
	//		StringBuffer x = new StringBuffer();
	//		try {
	//			x.append(res.getString("TYPE_NAME"));
	//			int sqlType = res.getInt("DATA_TYPE");
	//			if (sqlType == java.sql.Types.CHAR
	//					|| sqlType == java.sql.Types.NUMERIC
	//					|| sqlType == java.sql.Types.ARRAY
	//					|| sqlType == java.sql.Types.LONGNVARCHAR
	//					|| sqlType == java.sql.Types.VARCHAR
	//					|| sqlType == java.sql.Types.NCHAR
	//					|| sqlType == java.sql.Types.NVARCHAR
	//			){
	//				Integer p = res.getInt("PRECISION");
	//				if (p!=null)
	//					x.append("(").append(p).append(")");
	//			}
	//		} catch (SQLException e) {
	//			logger.error("Error", e);
	//		}
	//		return x.toString();
	//	}

	private static boolean haveColumn(ResultSet res, String colname) throws SQLException {
		ResultSetMetaData mt = res.getMetaData();
		for (int n=0; n<mt.getColumnCount(); n++)
			if (mt.getColumnName(n+1).equals(colname))
				return true;
		return false;
	}

	public static String getProcColType(int int1) {
		//	  *      <LI> procedureColumnUnknown - nobody knows
		//    *      <LI> procedureColumnIn - IN parameter
		//	  *      <LI> procedureColumnInOut - INOUT parameter
		//	  *      <LI> procedureColumnOut - OUT parameter
		//	  *      <LI> procedureColumnReturn - procedure return value
		//	  *      <LI> procedureColumnResult - result column in <code>ResultSet</code>
		switch (int1) {
		case 0: return "?";
		case 1: return "IN";
		case 2: return "IN-OUT";
		case 3: return "OUT";
		case 4: return "Return Value";
		case 5: return "Column in ResultSet";
		}
		return null;
	}

	public static void addTableTypes(DatabaseMetaData meta, Schema tableSchema) throws SQLException {
		ResultSet res = meta.getTableTypes();
		while (res.next()) {
			String s = res.getString(1);
			TableType tableType = new TableType(s, "", tableSchema);
			dumpTables(meta, tableType, tableSchema);
		}
	}

	public static void dumpTables(DatabaseMetaData meta, TableType tableType, Schema tableSchema) throws SQLException {
		String[] types = new String[]{tableType.getDbName()};
		String cat = null;
		String schema = null;
		if(!tableSchema.isDefault() && !tableSchema.getParent().isDefault()){
			cat = tableSchema.getParent().getDbName();
			schema = tableSchema.getDbName();
		}
		ResultSet res = meta.getTables(cat, schema, null, types);
		while (res.next()) {
			String s = res.getString(3);
			Table table = new Table(s, "", tableType);
			dumpFields(meta, table, schema);
			dumpFk(cat, schema, meta, table);
		}
		Iterator<Table> it = tableType.getTableIterator();
		while (it.hasNext()){
			Table t = (Table)it.next();
			findRefs(t);
			Pk pk = t.getConstraints().getPk();
			if (pk!=null){
				for (PkColumn f : pk.getColumns()) {
					TableColunm x = t.getColumns().find(f.getDbName());
					if (x != null)
						x.setPk(true);
				}
			}
		}
	}

	private static void dumpFk(String cat, String schema, DatabaseMetaData meta, Table table) throws SQLException {
		ResultSet rsk = meta.getImportedKeys(cat, schema, table.getDbName());
		Fks fks = table.getForeignKeys();
		while (rsk.next()){
			if (fks==null)
				fks = new Fks(table);
			Fk fk = new Fk(rsk.getString("FK_NAME"), "", fks);
			fk.setPktableName(rsk.getString("PKTABLE_NAME"));
			fk.setPkcolumnName(rsk.getString("PKCOLUMN_NAME"));
			fk.setFktableName(rsk.getString("FKTABLE_NAME"));
			fk.setFkcolumnName(rsk.getString("FKCOLUMN_NAME"));
			fk.setKeySeq(rsk.getString("KEY_SEQ"));
			fk.setUpdateRule(rsk.getString("UPDATE_RULE"));
			fk.setDeleteRule(rsk.getString("DELETE_RULE"));
			fk.setFkName(rsk.getString("FK_NAME"));
			fk.setPkName(rsk.getString("PK_NAME"));
			fk.setDeferrability(rsk.getString("DEFERRABILITY"));
		}
	}

	private static void findRefs(Table table) {
		Iterator<Fk> it = table.getForeignKeys().getFkIterator();
		while (it.hasNext()){
			findRef(table, it.next());
		}
	}

	private static void findRef(Table table, Fk fk) {
		Table refTable = table.getParent().find(fk.getPktableName());
		if (refTable!=null){
			TableReference tr = new TableReference(refTable.getRefs(), fk);
			tr.setTarget(table);
		}
	}

	public static void dumpFields(DatabaseMetaData meta, Table table, String schema) throws SQLException {
		String cat = null;
		ResultSet res = meta.getColumns(cat, schema, table.getDbName(), null);
		Fields cols  = table.getColumns();
		boolean haveColumnSize = haveColumn(res, "COLUMN_SIZE");
		while (res.next()) {
			String s = res.getString(4);
			boolean nullable = res.getInt("NULLABLE")>0;
			Integer p = 0;
			if (haveColumnSize)
			try{
				p = res.getInt("COLUMN_SIZE");
			}catch(Exception e){
			}
			new TableColunm(s, res.getString("TYPE_NAME"), res.getInt("DATA_TYPE"), nullable, p, cols);
		}

		Constraints cons = table.getConstraints();
		res = meta.getPrimaryKeys(cat, schema, table.getDbName());
		//TABLE_CAT, TABLE_SCHEM, TABLE_NAME, COLUMN_NAME, KEY_SEQ, PK_NAME
		Pk cpk = null;
		while (res.next()) {
			String s = res.getString(6);
			if (cpk==null){
				cpk = new Pk(s, "", cons);
			} else if(!s.equals(cpk.getDbName())){
				cpk = new Pk(s, "", cons);
			}
			boolean nullable = false;
			Integer p = 0;
			String dbName = res.getString(4);
			TableColunm x = table.getColumns().find(dbName);
			if (x!=null)
				new PkColumn(dbName, x.getTypeName(), x.getSqlType(), cpk, nullable, x.getLenght());
			else
				new PkColumn(dbName, "", 0, cpk, nullable, p);
		}
		Index current = null;
		Indexes indexes  = table.getIndexes();
		res = meta.getIndexInfo(cat, schema, table.getDbName(), false, false);
		while (res.next()) {
			String s = res.getString(6);
			if (current==null){
				current = new Index(s, "", indexes);
			} else if(!s.equals(current.getDbName())){
				current = new Index(s, "", indexes);
			}
			boolean nullable = true;
			Integer p = 0;
			String dbName = res.getString(9);
			TableColunm x = table.getColumns().find(dbName);
			if (x!=null)
				new IndexColumn(dbName, x.getTypeName(), x.getSqlType(), current, nullable, x.getLenght());
			else
				new IndexColumn(dbName, "", 0, current, nullable, p);

		}

	}

	public static String toCamel(String value, boolean firstUp) {
		StringBuilder ret = new StringBuilder();
		boolean toUpper = firstUp;
		for (int i = 0; value != null && i < value.length(); i++) {
			String c = "" + value.charAt(i);
			if ("_".equals(c) || " ".equals(c)) {
				toUpper = true;
				continue;
			} else {
				if (toUpper)
					ret.append(c.toUpperCase());
				else
					ret.append(c.toLowerCase());
				toUpper = false;
			}
		}
		return ret.toString();
	}

	public static String toCamel(String value) {
		return toCamel(value, true);
	}

	public static String getTypeSqlString(int sqlType) {
		switch (sqlType) {
		case Types.ARRAY:
			return "Types.ARRAY";
		case Types.BIGINT:
			return "Types.BIGINT";
		case Types.BINARY:
			return "Types.BINARY";
		case Types.BIT:
			return "Types.BIT";
		case Types.BLOB:
			return "Types.BLOB";
		case Types.BOOLEAN:
			return "Types.BOOLEAN";
		case Types.CHAR:
			return "Types.CHAR";
		case Types.CLOB:
			return "Types.CLOB";
		case Types.DATALINK:
			return "Types.DATALINK";
		case Types.DATE:
			return "Types.DATE";
		case Types.DECIMAL:
			return "Types.DECIMAL";
		case Types.DISTINCT:
			return "Types.DISTINCT";
		case Types.DOUBLE:
			return "Types.DOUBLE";
		case Types.FLOAT:
			return "Types.FLOAT";
		case Types.INTEGER:
			return "Types.INTEGER";
		case Types.JAVA_OBJECT:
			return "Types.JAVA_OBJECT";
		case Types.LONGVARBINARY:
			return "Types.LONGVARBINARY";
		case Types.LONGVARCHAR:
			return "Types.LONGVARCHAR";
		case Types.NULL:
			return "Types.NULL";
		case Types.NUMERIC:
			return "Types.NUMERIC";
		case Types.OTHER:
			return "Types.OTHER";
		case Types.REAL:
			return "Types.REAL";
		case Types.REF:
			return "Types.REF";
		case Types.ROWID:
			return "Types.ROWID";
		case Types.SMALLINT:
			return "Types.SMALLINT ";
		case Types.STRUCT:
			return "Types.STRUCT";
		case Types.TIME:
			return "Types.TIME";
		case Types.TIMESTAMP:
			return "Types.TIMESTAMP";
		case Types.TINYINT:
			return "Types.TINYINT";
		case Types.VARBINARY:
			return "Types.VARBINARY";
		case Types.VARCHAR:
			return "Types.VARCHAR";
		case Types.LONGNVARCHAR:
			return "Types.LONGNVARCHAR";
		case Types.NCHAR:
			return "Types.NCHAR";
		case Types.NCLOB:
			return "Types.NCLOB";
		case Types.NVARCHAR:
			return "Types.NVARCHAR";
		case Types.SQLXML:
			return "Types.SQLXML";
		}
		return "Types.NULL";
	}

	public static CodeGenConfig getConfig() {
		return config;
	}

	public static void setConfig(CodeGenConfig config) {
		Helper.config = config;
	}

	public static String getJavaClass(int sqlType) {
		FieldData data = fieldTypeMap.get(sqlType);
		if (data!=null)
			return data.getJavaclass();
		return Object.class.getSimpleName();
	}

}