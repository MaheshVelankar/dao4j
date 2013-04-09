package it.mengoni.jdbc.model;



public class PkColumn extends AbstractColumn {

	private static final long serialVersionUID = 1L;
	private transient Pk parent;

	public PkColumn(String name, String typeName, int sqlType, Pk pk, boolean nullable, int lenght) {
		super(name, typeName, sqlType, nullable, lenght);
		this.parent = pk;
		parent.addColumn(this);
	}

	public Pk getParent() {
		return parent;
	}

	@Override
	protected DbItem getChild(int i) {
		return null;
	}
}
