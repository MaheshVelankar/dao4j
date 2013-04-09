package it.mengoni.jdbc.model;

import java.util.Iterator;
import java.util.List;


public class TableType extends AbstractDbItem {

	private static final long serialVersionUID = 1L;
	private transient Schema parent;
	private DbItemList<Table> children = new DbItemArrayList<Table>();

	public TableType(String name, String descrizione, Schema parent) {
		super(name, descrizione);
		this.parent = parent;
		if (parent!=null)
			parent.addTableType(this);
	}

	public List<Table> getTables() {
		return children;
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

	public void addTable(Table table) {
		children.add(table);
	}

	@Override
	protected DbItem getChild(int index) {
		return children.get(index);
	}

	public Table find(String tableName)  {
		return children.find(tableName);
	}

	private class ChildIterator implements Iterator<Table>{

		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < getChildCount();
		}

		@Override
		public Table next() {
			return children.get(index++);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public Iterator<Table> getTableIterator() {
		return new ChildIterator();
	}

}
