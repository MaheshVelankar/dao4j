package it.mengoni.jdbc.model;



public class ProcedureColumn extends AbstractColumn {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private transient Procedure parent;
	private String columnType;

	public ProcedureColumn(String name, String typeName, int sqlType, Procedure procedure, boolean nullable, int lenght, String columnType) {
		super(name, typeName, sqlType, nullable, lenght);
		this.parent = procedure;
		this.columnType = columnType;
		parent.addColumn(this);
	}

	public Procedure getParent() {
		return parent;
	}

	@Override
	protected DbItem getChild(int i) {
		return null;
	}

	public String getColumnType() {
		return columnType;
	}

	public String getTypeDecl(){
		return super.getTypeDecl() + " " + columnType;
	}
}
