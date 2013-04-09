package it.mengoni.persistence.dao;

import it.mengoni.exception.SystemError;
import it.mengoni.persistence.dto.PersistentObject;

import java.sql.ResultSet;


public abstract class PkStringField<T extends PersistentObject> extends FieldImpl<T, String> {

	public PkStringField(String name, int length,	int sqlType) {
		super(name, true, false, length, sqlType);
	}

	public PkStringField(String name, String propertyName, int length, int sqlType) {
		super(name, propertyName, false, length, sqlType);
	}

	@Override
	public boolean isKey() {
		return true;
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean)	 {
		try{
			String value = getStringValue(rs);
			setValue(value, bean);
		} catch (Exception e) {
			throw new SystemError("Error: sqlType=" + getSqlType() + " " + getName(), e);
		}
	}
}
