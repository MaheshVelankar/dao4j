package it.mengoni.jdbc.model;


public class Indexes extends AbstractDbItem {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private transient Table parent;
	private DbItemList<Index> children = new DbItemArrayList<Index>();

	public Indexes(Table parent) {
		super("Index", "");
		this.parent = parent;
	}

	public Table getParent() {
		return parent;
	}

	@Override
	public boolean haveChildren() {
		return children.size()>0;
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	protected DbItem getChild(int index) {
		return children.get(index);
	}

}
