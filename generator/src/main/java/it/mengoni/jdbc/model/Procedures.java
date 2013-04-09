package it.mengoni.jdbc.model;


public class Procedures extends AbstractDbItem {

	private static final long serialVersionUID = 1L;

	private transient Schema parent;

	private DbItemList<Procedure> children = new DbItemArrayList<Procedure>();

	public Procedures(Schema parent) {
		super("Procedures", "");
		this.parent = parent;
	}

	public Schema getParent() {
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