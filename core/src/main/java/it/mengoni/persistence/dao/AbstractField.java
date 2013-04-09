package it.mengoni.persistence.dao;

import it.mengoni.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;

public abstract class AbstractField<T extends PersistentObject, V> implements Field<T, V> {

	@Override
	public String toString() {
		return "AbstractField [name=" + name + ", sqlType=" + sqlType
				+ ", length=" + length + ", nullable=" + nullable
				+ ", editItemValues=" + Arrays.toString(editItemValues)
				+ ", euro=" + euro + ", editRoles=" + editRoles
				+ ", viewRoles=" + viewRoles + "]";
	}

	protected CharsetConverter charsetConverter;
	protected EditItemValue[] editItemValues;
	protected Set<String> editRoles;
	protected boolean euro = false;
	private final int length;
	private final String name;
	private final String propertyName;
	private final boolean nullable;
	private final int sqlType;
	protected Set<String> viewRoles;


	public static String toCamel(String value, boolean firstUp) {
		StringBuilder ret = new StringBuilder();
		boolean toUpper = firstUp;
		for (int i = 0; value != null && i < value.length(); i++) {
			String c = "" + value.charAt(i);
			if ("_".equals(c) || " ".equals(c)) {
				toUpper = true;
				continue;
			} else {
				if (toUpper)
					ret.append(c.toUpperCase());
				else
					ret.append(c.toLowerCase());
				toUpper = false;
			}
		}
		return ret.toString();
	}

	public AbstractField(String name, boolean nullable, int length, int sqlType) {
		super();
		propertyName = toCamel(name,false);
		this.name = name;
		this.nullable = nullable;
		this.length = length;
		this.sqlType = sqlType;
	}

	public AbstractField(String name, boolean nullable, int length, int sqlType, EditItemValue[] editItemValues) {
		super();
		propertyName = toCamel(name,false);
		this.name = name;
		this.nullable = nullable;
		this.length = length;
		this.sqlType = sqlType;
		this.editItemValues = editItemValues;
	}

	public AbstractField(String name, String propertyName, boolean nullable, int length, int sqlType) {
		super();
		this.propertyName = propertyName;
		this.name = name;
		this.nullable = nullable;
		this.length = length;
		this.sqlType = sqlType;
	}

	public AbstractField(String name,  String propertyName, boolean nullable, int length, int sqlType, EditItemValue[] editItemValues) {
		super();
		this.name = name;
		this.propertyName = propertyName;
		this.nullable = nullable;
		this.length = length;
		this.sqlType = sqlType;
		this.editItemValues = editItemValues;
	}

	@Override
	public void checkValue(T bean) {
		checkValue(getValue(bean));
	}


	protected void checkValue(Object value) {
		if (value == null && !isNullable())
			throw new IllegalArgumentException(name + " cannot be null");
		if (editItemValues!=null && editItemValues.length>0){
			if(!contains(value))
				throw new IllegalArgumentException(name + " cannot be set with value:"+value);
		}
		if (value instanceof String) {
			String s = (String) value;
			if (length > 0 && s.length() > length)
				throw new IllegalArgumentException(name + " cannot be longer than " + length);
		}
	}

	private boolean contains(Object value) {
		if (value==null)
			return false;
		for (int i = 0; i < editItemValues.length; i++) {
			if (value.equals(editItemValues[i].getDatabaseValue()))
				return true;
		}
		return false;
	}

	public CharsetConverter getCharsetConverter() {
		return charsetConverter;
	}

	public EditItemValue[] getEditItemValues() {
		return editItemValues;
	}

	public Set<String> getEditRoles() {
		return editRoles;
	}

	public int getLength() {
		return length;
	}

	public String getName() {
		return name;
	}

	public int getSqlType() {
		return sqlType;
	}

	protected String getStringValue(ResultSet rs) throws SQLException {
		if (charsetConverter==null){
			String value = rs.getString(getName());
			if (value!=null)
				return value.trim();
			return null;
		}
		byte[] bytes = rs.getBytes(getName());
		return charsetConverter.convertFromDb(bytes);
	}

	protected Integer getIntegerValue(ResultSet rs) throws SQLException {
		Object value = rs.getObject(getName());
		if (value==null)
			return null;
		if (value.getClass().equals(Integer.class))
			return (Integer)value;
		return Integer.valueOf(value.toString());
	}

	protected Integer getShortValue(ResultSet rs) throws SQLException {
		Object value = rs.getObject(getName());
		if (value==null)
			return null;
		if (value.getClass().equals(Integer.class))
			return (Integer)value;
		return Integer.valueOf(value.toString());
	}

	public Set<String> getViewRoles() {
		return viewRoles;
	}

	public boolean isEuro(){
		return euro;
	}

	public boolean isNullable() {
		return nullable;
	}

	public boolean isReadOnly(){
		return false;
	}

	public void setCharsetConverter(CharsetConverter charsetConverter) {
		this.charsetConverter = charsetConverter;
	}

	public void setEditRoles(Set<String> editRoles) {
		this.editRoles = editRoles;
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
		if (bean != null) {
			Object value = getValue(bean);
			if (value != null){
				if(charsetConverter!=null && value.getClass()==String.class){
					byte[] bytes = charsetConverter.convertFromApplication((String)value);
					stm.setBytes(index, bytes);
				} else
					stm.setObject(index, value);
			}else if (!isNullable() || isKey())
				throw new IllegalArgumentException(getName() + " cannot be null");
			else
				stm.setNull(index, getSqlType());
		}
	}

	public void setViewRoles(Set<String> viewRoles) {
		this.viewRoles = viewRoles;
	}


	protected String identifierQuote(String value) {
		if (value!=null){
			value = value.trim();
			if (value.contains("-") || value.contains(" "))
				value = new StringBuilder("\"").append(value).append("\"").toString();
			return value;
		}
		return null;
	}

	public String getPropertyName() {
		return propertyName;
	}


}
