package it.mengoni.jdbc.model;



public class Root extends AbstractDbItem {

	private static final long serialVersionUID = 1L;

	private DbItemList<Catalog> catalogs = new DbItemArrayList<Catalog>();

	public Root(String name, String descrizione) {
		super(name, descrizione);
	}

	public Root() {
		super("no name", "");
	}

	public Schema getSchema(String catalogName, String schemaName) {
		Catalog catalog = find(catalogName);
		if (catalog!=null)
			return catalog.find(schemaName);
		return null;
	}

	private Catalog find(String catalogName) {
		return catalogs.find(catalogName);
	}

	@Override
	public boolean haveChildren() {
		return catalogs.size()>0;
	}

	@Override
	public int getChildCount() {
		return catalogs.size();
	}

	public void addCatalog(Catalog catalog) {
		catalogs.add(catalog);
	}

	@Override
	protected DbItem getChild(int index) {
		return catalogs.get(index);
	}


}