package it.mengoni.jdbc.model;

import it.mengoni.generator.Helper;

public class TableColunm extends AbstractColumn implements SelectableDbItem{

	private static final long serialVersionUID = 1L;
	private transient Fields parent;
	private String javaClass;

	public TableColunm(String name, String typeName, int sqlType, boolean nullable, int lenght, Fields parent) {
		super(name, typeName, sqlType, nullable, lenght);
		this.parent = parent;
		if (parent!=null)
			parent.addField(this);
		setSelected(true);
		javaClass = Helper.getJavaClass(sqlType);
	}

	public Fields getParent() {
		return parent;
	}

	@Override
	protected DbItem getChild(int i) {
		return null;
	}

	public String getJavaClass() {
		return javaClass;
	}

}