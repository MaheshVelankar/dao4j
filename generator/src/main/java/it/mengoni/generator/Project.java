package it.mengoni.generator;

import it.mengoni.jdbc.model.Root;
import it.mengoni.jdbc.model.Schema;
import it.mengoni.persistence.exception.SystemError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Project implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(Project.class);

	private CodeGenConfig codeGenConfig = new CodeGenConfig();

	private JdbcConfig jdbcConfig  = new JdbcConfig();

	private Root model = new Root();

	public CodeGenConfig getCodeGenConfig() {
		return codeGenConfig;
	}

	public void setCodeGenConfig(CodeGenConfig codeGenConfig) {
		this.codeGenConfig = codeGenConfig;
	}

	public JdbcConfig getJdbcConfig() {
		return jdbcConfig;
	}

	public void setJdbcConfig(JdbcConfig jdbcConfig) {
		this.jdbcConfig = jdbcConfig;
	}

	public Root getModel() {
		return model;
	}

	public void setModel(Root model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public static Project load(String filename){
		return load(new File(filename));
	}

	public static Project load(File file){
		try {
			XStream xStream = getXStream();
			InputStream input = new FileInputStream(file);
			return (Project) xStream.fromXML(input);
		} catch (Throwable e) {
			throw new SystemError("Errore in lettura nel file: " +file.getPath(),e);
		}
	}

	public static void save(Project project, String filename){
		save(project, new File(filename));
	}

	public static void save(Project project, File file){
		try {
			XStream xStream = getXStream();
			OutputStream out = new FileOutputStream(file);
			xStream.toXML(project, out);
		} catch (Throwable e) {
			logger.error("Errore in scrittura nel file: " +file.getPath() + ", errore:" + e, e);
		}
	}

	private static XStream getXStream() {
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("project", Project.class);
		xStream.alias("catalog", it.mengoni.jdbc.model.Catalog.class);
		xStream.alias("field", it.mengoni.jdbc.model.TableColunm.class);
		xStream.alias("columns", it.mengoni.jdbc.model.Fields.class);
		xStream.alias("constraints", it.mengoni.jdbc.model.Constraints.class);
		xStream.alias("fk", it.mengoni.jdbc.model.Fk.class);
		xStream.alias("fks", it.mengoni.jdbc.model.Fks.class);
		xStream.alias("index", it.mengoni.jdbc.model.Index.class);
		xStream.alias("indexes", it.mengoni.jdbc.model.Indexes.class);
		xStream.alias("pk", it.mengoni.jdbc.model.Pk.class);
		xStream.alias("procedure", it.mengoni.jdbc.model.Procedure.class);
		xStream.alias("procedures", it.mengoni.jdbc.model.Procedures.class);
		xStream.alias("schema", it.mengoni.jdbc.model.Schema.class);
		xStream.alias("table", it.mengoni.jdbc.model.Table.class);
		xStream.alias("tableType", it.mengoni.jdbc.model.TableType.class);
		xStream.alias("child",it.mengoni.jdbc.model.AbstractDbItem.class);
		xStream.alias("children", it.mengoni.jdbc.model.DbItemArrayList.class);
		xStream.alias("pkColumn", it.mengoni.jdbc.model.PkColumn.class);
		xStream.alias("tableColumn", it.mengoni.jdbc.model.TableColunm.class);
		xStream.alias("indexColumn", it.mengoni.jdbc.model.IndexColumn.class);
		xStream.alias("procedureColumn", it.mengoni.jdbc.model.ProcedureColumn.class);
		xStream.alias("tableReference", it.mengoni.jdbc.model.TableReference.class);
		xStream.alias("tableReferences", it.mengoni.jdbc.model.TableReferences.class);
//		xStream.addImplicitCollection(it.mengoni.jdbc.model.DbItemArrayList.class, "children");
		return xStream;
	}

	public static void saveDbRoot(Root root, File file) {
		try {
			XStream xStream = getXStream();
			OutputStream out = new FileOutputStream(file);
			xStream.toXML(root, out);
		} catch (Throwable e) {
			logger.error("Errore in scrittura nel file: " +file.getPath() + ", errore:" + e, e);
		}

	}

	public static void saveSchema(Schema schema, File file) {
		try {
			XStream xStream = getXStream();
			OutputStream out = new FileOutputStream(file);
			xStream.toXML(schema, out);
		} catch (Throwable e) {
			logger.error("Errore in scrittura nel file: " +file.getPath() + ", errore:" + e, e);
		}

	}

	public static Project loadDbRoot(Project _project, File file) {
		if (_project==null)
			_project = new Project();
		try {
			XStream xStream = getXStream();
			InputStream input = new FileInputStream(file);
			Root root = (Root) xStream.fromXML(input);
			_project.setModel(root);
		} catch (Throwable e) {
			throw new SystemError("Errore in lettura nel file: " +file.getPath(),e);
		}
		return _project;
	}

	public static Schema loadSchema(File file) {
		try {
			XStream xStream = getXStream();
			InputStream input = new FileInputStream(file);
			return (Schema) xStream.fromXML(input);
		} catch (Throwable e) {
			throw new SystemError("Errore in lettura nel file: " +file.getPath(),e);
		}
	}

}