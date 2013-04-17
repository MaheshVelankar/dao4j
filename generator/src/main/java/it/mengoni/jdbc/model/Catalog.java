package it.mengoni.jdbc.model;

import it.mengoni.persistence.dao.Dao.DatabaseProductType;



public class Catalog extends AbstractDbItem {

	private static final long serialVersionUID = 1L;

	private transient Root parent;
	private DbItemList<Schema> children = new DbItemArrayList<Schema>();
	private final DatabaseProductType databaseProductType;

	public DatabaseProductType getDatabaseProductType() {
		return databaseProductType;
	}

	public Catalog(DatabaseProductType databaseProductType, String name, String descrizione, Root parent) {
		super(name, descrizione);
		this.databaseProductType = databaseProductType;
		this.parent = parent;
		prefix = "Catalog:";
		if (parent!=null)
			parent.addCatalog(this);
	}

	public Catalog(DatabaseProductType databaseProductType, Root parent) {
		this(databaseProductType, DEFAULT_ITEM, "", parent);
	}

	public Schema find(String schemaName) {
		return children.find(schemaName);
	}

	public void addSchema(Schema schema) {
		children.add(schema);
	}

	public Root getParent() {
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

}