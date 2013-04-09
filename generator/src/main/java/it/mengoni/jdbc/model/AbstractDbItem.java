package it.mengoni.jdbc.model;

import java.util.Iterator;

import it.mengoni.generator.Helper;

public abstract class AbstractDbItem implements DbItem {

	private static final long serialVersionUID = 1L;

	private String dbName;
	private String descrizione;
	protected String label=null;
	protected String prefix="";
	private String javaName;
	private boolean selected;

	public AbstractDbItem(String name, String descrizione) {
		super();
		setDbName(name);
		this.descrizione = descrizione;
	}

	public AbstractDbItem() {
		super();
		dbName = "no name";
		descrizione = "";
	}

	@Override
	public String toString() {
		return dbName;
	}

	@Override
	public String getLabel() {
		if (label==null)
			label = prefix + dbName;
		return label;
	}

	public boolean isDefault(){
		return DEFAULT_ITEM.equalsIgnoreCase(dbName);
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String name) {
		this.dbName = name;
		label = null;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getJavaName() {
		if (javaName==null)
			javaName = Helper.getConfig().getNameSubst(dbName);
		return javaName;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public int hashCode() {
		if (dbName==null)
			return 1;
		return dbName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null)
			return false;
		if (this == obj)
			return true;
		if (this.getClass().equals(obj.getClass())){
			return getDbName().equals(((AbstractDbItem)obj).getDbName());
		}
		return false;
	}

	private class ChildIterator implements Iterator<DbItem>{

		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < getChildCount();
		}

		@Override
		public DbItem next() {
			return getChild(index++);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	@Override
	public Iterator<DbItem> getChildIterator() {
		return new ChildIterator();
	}

	protected abstract DbItem getChild(int index);

}