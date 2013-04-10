package it.mengoni.jdbc.model;

import it.mengoni.generator.GeneratorConst.DatabaseProductType;



public class Table extends AbstractSelectableDbItem {

	private static final long serialVersionUID = 1L;

	private transient TableType parent;

	private Fields fields = new Fields(this);
	private Fks fks = new Fks(this);
	private Constraints constraints = new Constraints(this);
	private Indexes indexes = new Indexes(this);
	private TableReferences refs = new TableReferences(this);
	private final DatabaseProductType databaseProductType;

	@Override
	protected DbItem getChild(int index) {
		switch (index) {
		case 0: return fields;
		case 1: return fks;
		case 2: return indexes;
		case 3: return constraints;
		case 4: return refs;
		default: return null;
		}
	}

	@Override
	public int getChildCount() {
		return 5;
	}

	public Table(DatabaseProductType databaseProductType, String name, String descrizione, TableType parent) {
		super(name, descrizione);
		this.parent = parent;
		this.databaseProductType = databaseProductType;
		if (parent!=null)
			parent.addTable(this);
	}

	public DatabaseProductType getDatabaseProductType() {
		return databaseProductType;
	}

	public Fields getColumns() {
		return fields;
	}

	public void setColumns(Fields columns) {
		this.fields = columns;
	}

	public Constraints getConstraints() {
		return constraints;
	}

	public void setConstraints(Constraints constraints) {
		this.constraints = constraints;
	}

	public Fks getForeignKeys() {
		return fks;
	}

	public TableType getParent() {
		return parent;
	}

	@Override
	public boolean haveChildren() {
		return true;
	}

	public Indexes getIndexes() {
		return indexes;
	}

	public TableReferences getRefs() {
		return refs;
	}

}