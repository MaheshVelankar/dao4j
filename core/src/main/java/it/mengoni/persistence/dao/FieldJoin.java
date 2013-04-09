package it.mengoni.persistence.dao;

import it.mengoni.persistence.dto.PersistentObject;


public interface FieldJoin<T extends PersistentObject, V> extends Field<T, V> {

	public enum JoinType {inner, outer, leftOuter, rightOuter}

	public String getJoinTableName();

	public String getJoinKeyField();

	public String getJoinField();

	public String getLocalKeyField();

	public String getFieldSql();

	public String getJoinSql(String relationName);

	public JoinType getJoinType();

}
