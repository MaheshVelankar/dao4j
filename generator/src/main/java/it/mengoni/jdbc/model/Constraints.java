package it.mengoni.jdbc.model;

import java.util.List;


public class Constraints extends AbstractDbItem {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private transient Table parent;
	private DbItemList<DbItem> children = new DbItemArrayList<DbItem>();
	private Pk pk = new Pk("", "", this);

	public Constraints(Table parent) {
		super("Constraints", "");
		this.parent = parent;
	}

	public void addChild(DbItem child){
		if (child instanceof Pk){
			setPk((Pk) child);
		} else
			children.add(child);
	}

	public List<DbItem> getChildren() {
		return children;
	}

	public Pk getPk() {
		return pk;
	}

	public void setPk(Pk pk) {
		if (this.pk!=null)
			children.remove(this.pk);
		this.pk = pk;
		children.add(0,pk);
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
