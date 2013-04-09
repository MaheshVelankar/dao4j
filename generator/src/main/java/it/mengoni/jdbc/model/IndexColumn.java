package it.mengoni.jdbc.model;



public class IndexColumn extends AbstractColumn {

	private static final long serialVersionUID = 1L;
	private transient Index parent;

	public IndexColumn(String name, String typeName, int sqlType, Index index, boolean nullable, int lenght) {
		super(name, typeName, sqlType, nullable, lenght);
		index.addColumn(this);
	}

	public Index getParent() {
		return parent;
	}

	@Override
	protected DbItem getChild(int index) {
		return null;
	}

}
