package it.mengoni.persistence.dao;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.SystemError;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class SqlDateField<T extends PersistentObject> extends
		AbstractField<T, java.sql.Date> {

	public SqlDateField(String name, String propertyName, boolean nullable,
			EditItemValue[] editItemValues) {
		super(name, propertyName, nullable, 0, editItemValues);
	}

	public SqlDateField(String name, String propertyName, boolean nullable) {
		super(name, propertyName, nullable, 0);
	}

	@Override
	public boolean isKey() {
		return false;
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean) {
		java.sql.Date value = null;
		try {
			value = rs.getDate(getName());
			setValue(value, bean);
		} catch (Exception e) {
			throw new SystemError("Error:" + getName(), e);
		}
	}

	@Override
	public Class<?> getValueClass() {
		return java.sql.Date.class;
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
		if (bean != null) {
			Date value = getValue(bean);
			checkValue(value);
			if (value == null)
				stm.setNull(index, Types.DATE);
			else
				stm.setObject(index, value);
		}
	}
}
