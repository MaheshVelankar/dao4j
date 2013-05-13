package it.mengoni.persistence.dao.fields;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.SystemError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;

public abstract class TimeField<T extends PersistentObject> extends
		AbstractField<T, Time> {

	public TimeField(String name, String propertyName, boolean nullable,
			EditItemValue[] editItemValues) {
		super(name, propertyName, nullable, 0, editItemValues);
	}

	public TimeField(String name, String propertyName, boolean nullable) {
		super(name, propertyName, nullable, 0);
	}

	@Override
	public boolean isKey() {
		return false;
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean) {
		Time value = null;
		try {
			value = rs.getTime(getName());
			setValue(value, bean);
		} catch (Exception e) {
			throw new SystemError("Error:" + getName(), e);
		}
	}

	@Override
	public Class<?> getValueClass() {
		return Time.class;
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
		if (bean != null) {
			Time value = getValue(bean);
			checkValue(value);
			if (value == null)
				stm.setNull(index, Types.TIME);
			else
				stm.setObject(index, value);
		}
	}
}
