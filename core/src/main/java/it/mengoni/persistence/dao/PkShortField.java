package it.mengoni.persistence.dao;

import it.mengoni.exception.SystemError;
import it.mengoni.persistence.dto.PersistentObject;

import java.sql.ResultSet;


public abstract class PkShortField<T extends PersistentObject> extends FieldImpl<T, Short> {

	public PkShortField(String name, int length,	int sqlType) {
		super(name, true, false, length, sqlType);
	}

	public PkShortField(String name, String propertyName, int length, int sqlType) {
		super(name, propertyName, false, length, sqlType);
	}

	@Override
	public boolean isKey() {
		return true;
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean)	 {
		try{
			Integer value = getIntegerValue(rs);
			setValue(value==null?null:new Short(value.shortValue()), bean);
		} catch (Exception e) {
			throw new SystemError("Error: sqlType=" + getSqlType() + " " + getName(), e);
		}
	}
}
