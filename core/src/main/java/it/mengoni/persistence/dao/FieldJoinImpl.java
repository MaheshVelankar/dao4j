package it.mengoni.persistence.dao;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class FieldJoinImpl<T extends PersistentObject, V> extends AbstractField<T, V> implements FieldJoin<T, V>{

	@Override
	public String toString() {
		return "FieldJoinImpl [" +
				"getName()=" + getName() +
				", getLength()=" + getLength() +
				", joinTableName=" + joinTableName +
				", joinKeyField=" + joinKeyField +
				", joinField=" + joinField +
				", localKeyField=" + localKeyField +
				"]";
	}

	private final String joinField;
	private final String joinKeyField;
	private final String joinTableName;
	private final String joinTableAlias;
	private final String localKeyFieldAlias;
	private final String localKeyField;
	private final JoinType joinType;

	public FieldJoinImpl(JoinType joinType, String name, String localKeyField, String joinField,
			String joinTableName, String joinTableAlias, String joinKeyField,
			EditItemValue[] editItemValues) {
		super(name, true, 0, editItemValues);
		this.joinType = joinType;
		int p = localKeyField.indexOf('.');
		if (p>0){
			this.localKeyField = localKeyField.substring(p+1);
			this.localKeyFieldAlias = localKeyField.substring(0,p);
		} else {
			this.localKeyField = localKeyField;
			this.localKeyFieldAlias = null;

		}
		this.joinField = joinField;
		this.joinTableName = joinTableName;
		this.joinKeyField = joinKeyField;
		this.joinTableAlias = joinTableAlias;
	}

	protected void checkValue(Object value) {
	}

	public String getJoinField() {
		return joinField;
	}

	public String getJoinKeyField() {
		return joinKeyField;
	}

	public String getJoinTableName() {
		return joinTableName;
	}

	public String getLocalKeyField() {
		return localKeyField;
	}


	public boolean isKey() {
		return false;
	}

	public boolean isModified() {
		return false;
	}

	public boolean isNullable() {
		return true;
	}

	public boolean isReadOnly(){
		return true;
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
	}

	public void checkValue(T bean) {
	}

	@Override
	public V getValue(T bean) {
		return null;
	}

	public String getFieldSql() {
		StringBuilder ret = new StringBuilder();
		if (joinTableAlias!=null)
			ret.append(identifierQuote(getJoinTableAlias()));
		else
			ret.append(identifierQuote(getJoinTableName()));

		ret.append(".");
		ret.append(identifierQuote(getJoinField())).append(" as ").append(getName());
		return ret.toString();
	}

	public String getJoinSql(String relationName){
		StringBuilder b = new StringBuilder();
		switch (joinType) {
		case inner:b.append(" join ");break;
		case outer:b.append(" outer join ");break;
		case leftOuter:b.append(" left outer join ");break;
		case rightOuter:b.append(" right outer join ");break;
		default:
			break;
		}
		b.append(getJoinTableName());
		if (joinTableAlias!=null)
			b.append(" ").append(identifierQuote(getJoinTableAlias()));
		b.append(" on ");
		if (joinTableAlias!=null)
			b.append(identifierQuote(getJoinTableAlias()));
		else
			b.append(identifierQuote(getJoinTableName()));
		b.append(".").append(getJoinKeyField());

		b.append(" = ");

		if (localKeyFieldAlias==null)
			b.append(relationName).append(".").append(getLocalKeyField());
		else
			b.append(localKeyFieldAlias).append(".").append(getLocalKeyField());
		return b.toString();
	}

	public String getJoinTableAlias() {
		return joinTableAlias;
	}

	protected String getEditItemDisplayValue(String value) {
		if (value==null)
			return null;
		for(int n=0; n<editItemValues.length; n++){
			if (editItemValues[n].getDatabaseValue().equals(value)){
				return(editItemValues[n].getDisplayValue());
			}
		}
		return value;
	}

	public JoinType getJoinType() {
		return joinType;
	}

}
