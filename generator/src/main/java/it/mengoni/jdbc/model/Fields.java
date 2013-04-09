package it.mengoni.jdbc.model;

import java.util.List;


public class Fields extends AbstractDbItem {

	private static final long serialVersionUID = 1L;
	private transient Table parent;
	private DbItemList<TableColunm> children = new DbItemArrayList<TableColunm>();

	public Fields(Table parent) {
		super("Fields", "");
		this.parent = parent;
	}

	public TableColunm find(String dbName) {
		return children.find(dbName);
	}

	public List<TableColunm> getColunms(){
		return children;
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

	public void addField(TableColunm tableColunm) {
		children.add(tableColunm);
	}

}