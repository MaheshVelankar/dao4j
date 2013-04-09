package it.mengoni.jdbc.model;


public class Index extends AbstractDbItem {

	private static final long serialVersionUID = 1L;
	private transient Indexes parent;
	private DbItemList<IndexColumn> children = new DbItemArrayList<IndexColumn>();

	public Index(String name, String descrizione, Indexes parent) {
		super(name, descrizione);
		this.parent = parent;
	}

	public Indexes getParent() {
		return parent;
	}

	public IndexColumn find(String schemaName) {
		return children.find(schemaName);
	}

	@Override
	public boolean haveChildren() {
		return children.size()>0;
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	public void addColumn(IndexColumn indexColumn) {
		children.add(indexColumn);
	}

	@Override
	protected DbItem getChild(int index) {
		return children.get(index);
	}

}
