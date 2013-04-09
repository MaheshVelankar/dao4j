package it.mengoni.jdbc.model;

import java.util.Iterator;
import java.util.List;


public class Fks extends AbstractDbItem {

	private static final long serialVersionUID = 1L;
	private transient Table parent;
	private DbItemList<Fk> children = new DbItemArrayList<Fk>();

	public Fks(Table parent) {
		super("Foreign Keys", "");
		this.parent = parent;
	}

	public List<Fk> getChildren() {
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

	public void addFk(Fk fk) {
		children.add(fk);
	}

	@Override
	protected DbItem getChild(int index) {
		return children.get(index);
	}

	private class ChildIterator implements Iterator<Fk>{

		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < getChildCount();
		}

		@Override
		public Fk next() {
			return children.get(index++);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public Iterator<Fk> getFkIterator() {
		return new ChildIterator();
	}

}