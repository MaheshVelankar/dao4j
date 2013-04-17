package it.mengoni.generator;

import it.mengoni.jdbc.model.Catalog;
import it.mengoni.jdbc.model.Root;
import it.mengoni.jdbc.model.Schema;
import it.mengoni.jdbc.model.TableType;
import it.mengoni.persistence.dao.Dao.DatabaseProductType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchemaReader {

	private final CodeGenConfig config;

	public SchemaReader(CodeGenConfig config) {
		super();
		this.config = config;
	}

	public Root readSchema(Connection connection) throws SQLException, IOException {
		DatabaseMetaData meta = connection.getMetaData();
		Root root = new Root();
		try {
			ResultSet rs = meta.getCatalogs();
			Helper.dumpCatalog(meta, rs, root);
		} catch (Exception e) {
			System.err.println(e);
		}
		if (root.getChildCount()<=0){
			String pn = meta.getDatabaseProductName();
			DatabaseProductType databaseProductType = Helper.getDatabaseProductType(pn);
			Catalog cat = new Catalog(databaseProductType, root);
			cat.setDbName(config.getCatalogName());
			Schema schema = new Schema(cat);
			if (config.getSchemaName() != null)
				schema.setDbName(config.getSchemaName());
			TableType tableType = new TableType("TABLE", "table", schema);
			Helper.dumpTables(databaseProductType, meta, tableType, schema);
		}
		return root;
	}


}
