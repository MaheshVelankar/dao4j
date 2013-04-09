package it.mengoni.jdbc.model;

import java.util.List;


public class Pk extends AbstractDbItem {

	private static final long serialVersionUID = 1L;

	private transient Constraints constraints;
	private DbItemList<PkColumn> columns = new DbItemArrayList<PkColumn>();

	public Pk(String name, String descrizione, Constraints constraints) {
		super(name, descrizione);
		this.constraints = constraints;
		constraints.addChild(this);
		prefix="PK: ";
	}

	public List<PkColumn> getColumns() {
		return columns;
	}

	public Constraints getParent() {
		return constraints;
	}

	@Override
	public boolean haveChildren() {
		return columns.size()>0;
	}

	@Override
	public int getChildCount() {
		return columns.size();
	}

	public void addColumn(PkColumn pkColumn) {
		columns.add(pkColumn);
	}

	@Override
	protected DbItem getChild(int index) {
		return columns.get(index);
	}

}