package it.mengoni.persistence.dao;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.SystemError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class StringField<T extends PersistentObject> extends
AbstractField<T, String> {

	public StringField(String name, String propertyName, boolean nullable,
			int length, EditItemValue[] editItemValues) {
		super(name, propertyName, nullable, length, editItemValues);
	}

	public StringField(String name, String propertyName, boolean nullable,
			int length) {
		super(name, propertyName, nullable, length);
	}

	@Override
	public boolean isKey() {
		return false;
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

	@Override
	public void readValueFrom(ResultSet rs, T bean) {
		String value = null;
		try {
			value = getStringValue(rs);
			setValue(value, bean);
		} catch (Exception e) {
			throw new SystemError("Error:" + getName(), e);
		}
	}

	@Override
	public Class<?> getValueClass() {
		return String.class;
	}

	protected void checkValue(String value) {
		super.checkValue(value);
		if (value instanceof String) {
			String s = (String) value;
			if (getLength() > 0 && s.length() > getLength())
				throw new IllegalArgumentException(getName() + " cannot be longer than " + getLength());
		}
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
		if (bean != null) {
			String value = getValue(bean);
			checkValue(value);
			if (value == null){
				stm.setNull(index, Types.VARCHAR);
			}else{
				if(charsetConverter!=null){
					byte[] bytes = charsetConverter.convertFromApplication((String)value);
					stm.setBytes(index, bytes);
				} else
					stm.setObject(index, value);
			}
		}
	}


}
