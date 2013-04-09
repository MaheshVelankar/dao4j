package it.mengoni.jdbc.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableReferences extends AbstractDbItem {

	private static final long serialVersionUID = 1L;
	private transient Table parent;

	private List<TableReference> children = new ArrayList<TableReference>();

	public TableReferences(Table table) {
		super("Refs", "");
		this.parent = table;
	}

	@Override
	protected DbItem getChild(int i) {
		return children.get(i);
	}

	@Override
	public boolean haveChildren() {
		return children.size()>0;
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	public void addRererence(TableReference tableReference) {
		children.add(tableReference);
	}

	public Table getParent() {
		return parent;
	}

	private class ChildIterator implements Iterator<TableReference>{

		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < getChildCount();
		}

		@Override
		public TableReference next() {
			return children.get(index++);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public Iterator<TableReference> getRefIterator() {
		return new ChildIterator();
	}
}