package it.mengoni.generator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class CodeGenConfig implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String rootOut;
	private String basePackage;
	private String catalogName;
	private String schemaName;
	private Map<String, String> nameSubstMap = new HashMap<String, String>();

	public String getRootOut() {
		return rootOut;
	}

	public String getRootTestOut() {
		if (rootOut.endsWith("src/main/java"))
			return rootOut.replace("src/main/java", "src/test/java");
		if (rootOut.endsWith("src/main/java/"))
			return rootOut.replace("src/main/java/", "src/test/java/");
		return rootOut;
	}

	public void setRootOut(String rootOut) {
		this.rootOut = rootOut;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public Map<String, String> getNameSubstMap() {
		return nameSubstMap;
	}

	public void setNameSubstMap(Map<String, String> nameSubstMap) {
		this.nameSubstMap = nameSubstMap;
	}

	public String getNameSubst(String name){
		if (nameSubstMap==null)
			return name;
		String sName = nameSubstMap.get(name);
		if (sName==null)
			return name;
		return sName;
	}

	public void addNameSubst(String name, String nameSubst){
		nameSubstMap.put(name.trim(), nameSubst.trim());
	}

	public void addNameSubst(Map<String, String> toAdd){
		for (String key:toAdd.keySet()){
			nameSubstMap.put(key.trim(), toAdd.get(key).trim());
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
