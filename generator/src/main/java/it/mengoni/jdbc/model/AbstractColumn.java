package it.mengoni.jdbc.model;

import it.mengoni.generator.Helper;

import java.util.Set;


public abstract class AbstractColumn extends AbstractDbItem {

	private static final long serialVersionUID = 1L;
	private final String typeName;
	private final int sqlType;
	private final boolean nullable;
	private final int lenght;
	private boolean pk;

	private transient String typeDecl;

	@Override
	public String getLabel() {
		return super.getLabel() + " - " + getTypeDecl();
	}

	public String getTypeDecl() {
		if (typeDecl==null){
			StringBuilder buf = new StringBuilder(typeName.toLowerCase());
			getProcSqlColTypeExpr(buf);
			if (!nullable)
				buf.append(" not null ");
			if (pk)
				buf.append(" PK ");
			typeDecl = buf.toString();
		}
		return typeDecl;
	}

	protected void getProcSqlColTypeExpr(StringBuilder buf) {
		if (sqlType == java.sql.Types.CHAR
				|| sqlType == java.sql.Types.NUMERIC
				|| sqlType == java.sql.Types.ARRAY
				|| sqlType == java.sql.Types.LONGNVARCHAR
				|| sqlType == java.sql.Types.VARCHAR
				|| sqlType == java.sql.Types.NCHAR
				|| sqlType == java.sql.Types.NVARCHAR
				){
			if (lenght>0)
				buf.append("(").append(lenght).append(")");
		}
	}

	public AbstractColumn(String name, String typeName, int sqlType, boolean nullable, int lenght) {
		super(name, "");
		this.sqlType = sqlType;
		this.nullable = nullable;
		this.lenght = lenght;
		this.typeName = typeName;
	}

	public int getSqlType() {
		return sqlType;
	}

	public boolean isNullable() {
		return nullable;
	}

	public int getLenght() {
		return lenght;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public String getSetterDecl(Set<String> importSet){
		StringBuilder buf = new StringBuilder();
		String cn1;
		if (ModelFactory.isReserved(getDbName())){
			cn1 = "m"+Helper.toCamel(getJavaName(), true);
		} else
			cn1 = Helper.toCamel(getJavaName(), false);
		String cn = Helper.toCamel(getJavaName());
		String type = Helper.getJavaType(getSqlType(), importSet);
		buf.append("public void set").append(cn).append("(").append(type).append(" ").append(cn1).append(");\n").toString();
		return buf.toString();
	}

	public String getGetterDecl(Set<String> importSet){
		StringBuilder buf = new StringBuilder();
		String cn = Helper.toCamel(getJavaName());
		String type = Helper.getJavaType(getSqlType(), importSet);
		buf.append("public ").append(type).append(" get").append(cn).append("();\n").toString();
		return buf.toString();
	}

	public String getSetterBody(Set<String> importSet){
		StringBuilder buf = new StringBuilder();
		String cn1;
		if (ModelFactory.isReserved(getDbName())){
			cn1 = "m"+Helper.toCamel(getJavaName(), true);
		} else
			cn1 = Helper.toCamel(getJavaName(), false);
		String cn = Helper.toCamel(getJavaName());
		String type = Helper.getJavaType(getSqlType(), importSet);
		buf.append("@Override\n");
		buf.append("public void set").append(cn).append("(").append(type).append(" ").append(cn1).append(") {\n");
		buf.append("this.").append(cn1).append(" = ").append(cn1).append(";\n");
		buf.append("}\n");
		return buf.toString();
	}

	public String getGetterBody(Set<String> importSet){
		StringBuilder buf = new StringBuilder();
		String cn1;
		if (ModelFactory.isReserved(getDbName())){
			cn1 = "m"+Helper.toCamel(getJavaName(), true);
		} else
			cn1 = Helper.toCamel(getJavaName(), false);
		String cn = Helper.toCamel(getJavaName());
		String type = Helper.getJavaType(getSqlType(), importSet);
		buf.append("@Override\n");
		buf.append("public ").append(type).append(" get").append(cn).append("() {\n");
		buf.append("return ").append(cn1).append(";\n");
		buf.append("}\n");
		return buf.toString();

	}

	public String getFieldDecl(Set<String> importSet){
		StringBuilder buf = new StringBuilder();
		String cn1;
		if (ModelFactory.isReserved(getDbName())){
			cn1 = "m"+Helper.toCamel(getJavaName(), true);
		} else
			cn1 = Helper.toCamel(getJavaName(), false);
		String type = Helper.getJavaType(getSqlType(), importSet);
		buf.append("protected ").append(type).append(" ").append(cn1).append(" ;\n");
		return buf.toString();
	}

	public String getPropertyName(){
		String cn1;
		if (ModelFactory.isReserved(getDbName())){
			cn1 = "m"+Helper.toCamel(getJavaName(), true);
		} else
			cn1 = Helper.toCamel(getJavaName(), false);
		return cn1;
	}

	@Override
	public boolean haveChildren() {
		return false;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	public String getTypeName() {
		return typeName;
	}

}
