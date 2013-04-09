package it.mengoni.jdbc.model;


public class Procedure extends AbstractDbItem {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private transient Procedures parent;
	private DbItemList<ProcedureColumn> children = new DbItemArrayList<ProcedureColumn>();

	public Procedure(String name, String descrizione, Procedures parent) {
		super(name, descrizione);
		this.parent = parent;
	}

	public Procedures getParent() {
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

	public void addColumn(ProcedureColumn procedureColumn) {
		children.add(procedureColumn);
	}

	@Override
	protected DbItem getChild(int index) {
		return children.get(index);
	}

}
