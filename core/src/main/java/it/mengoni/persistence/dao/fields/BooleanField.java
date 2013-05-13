package it.mengoni.persistence.dao.fields;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.SystemError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class BooleanField<T extends PersistentObject> extends AbstractField<T, Boolean> {

	public BooleanField(String name, String propertyName, boolean nullable,
			EditItemValue[] editItemValues) {
		super(name, propertyName, nullable, 0, editItemValues);
	}

	public BooleanField(String name, String propertyName,
			boolean nullable) {
		super(name, propertyName, nullable, 0);
	}

	@Override
	public boolean isKey() {
		return false;
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean) {
		Boolean value = null;
		try{
			value = getBooleanValue(rs);
			setValue(value, bean);
		} catch (Exception e) {
			throw new SystemError("Error:" + getName(), e);
		}
	}

	private Boolean getBooleanValue(ResultSet rs) throws SQLException {
		Object value = rs.getObject(getName());
		if (value==null)
			return null;
		if (value.getClass().equals(Boolean.class))
			return (Boolean)value;
		return Boolean.valueOf(value.toString());
	}

	@Override
	public Class<?> getValueClass() {
		return Boolean.class;
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
		if (bean != null) {
			Boolean value = getValue(bean);
			checkValue(value);
			if (value == null)
				stm.setNull(index, Types.BOOLEAN);
			else
				stm.setObject(index, value);
		}
	}
}
