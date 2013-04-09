package it.mengoni.jdbc.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Fk extends AbstractSelectableDbItem {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String pktableName;
	private String pkcolumnName;
	private String fktableName;
	private String fkcolumnName;
	private String keySeq;
	private String updateRule;
	private String deleteRule;
	private String fkName;
	private String pkName;
	private String deferrability;
	private transient Fks parent;

	@Override
	public String getLabel() {
		return super.getLabel()+ "  " + fkcolumnName + "->"+ pktableName + "."+pkcolumnName+" (" +keySeq + ")";
	}

	public Fk(String name, String descrizione, Fks parent) {
		super(name, descrizione);
		this.parent = parent;
		if (parent!=null)
			parent.addFk(this);
	}

	public String getPktableName() {
		return pktableName;
	}

	public void setPktableName(String pktableName) {
		this.pktableName = pktableName;
	}

	public String getPkcolumnName() {
		return pkcolumnName;
	}

	public void setPkcolumnName(String pkcolumnName) {
		this.pkcolumnName = pkcolumnName;
	}

	public String getFktableName() {
		return fktableName;
	}

	public void setFktableName(String fktableName) {
		this.fktableName = fktableName;
	}

	public String getFkcolumnName() {
		return fkcolumnName;
	}

	public void setFkcolumnName(String fkcolumnName) {
		this.fkcolumnName = fkcolumnName;
	}

	public String getKeySeq() {
		return keySeq;
	}

	public void setKeySeq(String keySeq) {
		this.keySeq = keySeq;
	}

	public String getUpdateRule() {
		return updateRule;
	}

	public void setUpdateRule(String updateRule) {
		this.updateRule = updateRule;
	}

	public String getDeleteRule() {
		return deleteRule;
	}

	public void setDeleteRule(String deleteRule) {
		this.deleteRule = deleteRule;
	}

	public String getFkName() {
		return fkName;
	}

	public void setFkName(String fkName) {
		this.fkName = fkName;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getDeferrability() {
		return deferrability;
	}

	public void setDeferrability(String deferrability) {
		this.deferrability = deferrability;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Fks getParent() {
		return parent;
	}

	@Override
	public boolean haveChildren() {
		return false;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	protected DbItem getChild(int index) {
		return null;
	}

	/* PKTABLE_NAME=IMMOBILI
PKCOLUMN_NAME=EDITORIALE
FKTABLE_NAME=PROPOSTE
FKCOLUMN_NAME=IMMOBILE*/
}