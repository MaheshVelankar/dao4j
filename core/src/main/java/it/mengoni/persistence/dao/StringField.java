package it.mengoni.persistence.dao;

import it.mengoni.db.EditItemValue;
import it.mengoni.exception.SystemError;
import it.mengoni.persistence.dto.PersistentObject;

import java.sql.ResultSet;

public abstract class StringField<T extends PersistentObject> extends AbstractField<T, String> {

	public StringField(String name, String propertyName,
			boolean nullable, int length, int sqlType,
			EditItemValue[] editItemValues) {
		super(name, propertyName, nullable, length, sqlType, editItemValues);
	}

	public StringField(String name, String propertyName,
			boolean nullable, int length, int sqlType) {
		super(name, propertyName, nullable, length, sqlType);
	}

	@Override
	public boolean isKey() {
		return false;
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean) {
		String value = null;
		try{
			value = getStringValue(rs);
			setValue(value, bean);
		} catch (Exception e) {
			throw new SystemError("Error: sqlType=" + getSqlType() + " " + getName(), e);
		}
	}

}
