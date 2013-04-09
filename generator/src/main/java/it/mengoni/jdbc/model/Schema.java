package it.mengoni.jdbc.model;


public class Schema extends AbstractDbItem {

	private static final long serialVersionUID = 1L;

	private transient Catalog parent;
	private DbItemList<TableType> tableTypes = new DbItemArrayList<TableType>();

	public Schema(String name, String descrizione, Catalog catalog) {
		super(name, descrizione);
		this.parent = catalog;
		catalog.addSchema(this);
		prefix = "Schema:";
	}

	public Schema(Catalog catalog) {
		super(DEFAULT_ITEM, "");
		this.parent = catalog;
		catalog.addSchema(this);
		prefix = "Schema:";
	}

	public String getLabel() {
		if (label==null)
			if (getDbName()==null)
				label = prefix + "default";
			else
				label = prefix + getDbName();
		return label;
	}

	public TableType find(String name) {
		return tableTypes.find(name);
	}

	public Catalog getParent() {
		return parent;
	}

	@Override
	public boolean haveChildren() {
		return tableTypes.size()>0;
	}

	@Override
	public int getChildCount() {
		return tableTypes.size();
	}

	public void addTableType(TableType tableType) {
		tableTypes.add(tableType);
	}

	@Override
	protected DbItem getChild(int index) {
		return tableTypes.get(index);
	}

}