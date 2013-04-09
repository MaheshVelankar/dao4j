package it.mengoni.persistence.dao;

import it.mengoni.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public interface Field<T extends PersistentObject, V> {

	public boolean isNullable();

	public boolean isKey();

	public String getName();

	public int getLength();

	public void checkValue(T bean);

	public void readValueFrom(ResultSet rs, T bean);

	public void setParam(PreparedStatement stm, int index, T bean)	throws SQLException;

	public V getValue(T bean);

	public void setValue(V value, T bean);

	public int getSqlType();

	public EditItemValue[] getEditItemValues();

	public boolean isReadOnly();

	public boolean isEuro();

	public Set<String> getViewRoles();

	public Set<String> getEditRoles();

	public void setViewRoles(Set<String> viewRoles);

	public void setEditRoles(Set<String> editRoles);

	public CharsetConverter getCharsetConverter();

	public void setCharsetConverter(CharsetConverter charsetConverter);

	public String getPropertyName();


}
