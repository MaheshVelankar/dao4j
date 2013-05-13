package it.mengoni.persistence.dao;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.SystemError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public abstract class TimestampField<T extends PersistentObject> extends AbstractField<T, Timestamp> {

	public TimestampField(String name, String propertyName,
			boolean nullable,
			EditItemValue[] editItemValues) {
		super(name, propertyName, nullable, 0, editItemValues);
	}

	public TimestampField(String name, String propertyName,
			boolean nullable) {
		super(name, propertyName, nullable, 0);
	}

	@Override
	public boolean isKey() {
		return false;
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean) {
		Timestamp value = null;
		try{
			value = rs.getTimestamp(getName());
			setValue(value, bean);
		} catch (Exception e) {
			throw new SystemError("Error:" + getName(), e);
		}
	}

	@Override
	public Class<?> getValueClass() {
		return Timestamp.class;
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
		if (bean != null) {
			Timestamp value = getValue(bean);
			checkValue(value);
			if (value == null)
				stm.setNull(index, Types.TIMESTAMP);
			else
				stm.setObject(index, value);
		}
	}
}
