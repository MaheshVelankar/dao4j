package it.mengoni.persistence.dao;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.SystemError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class DoubleField<T extends PersistentObject> extends AbstractField<T, Double> {

	public DoubleField(String name, String propertyName,
			boolean nullable,
			EditItemValue[] editItemValues) {
		super(name, propertyName, nullable, 0, editItemValues);
	}

	public DoubleField(String name, String propertyName,
			boolean nullable) {
		super(name, propertyName, nullable, 0);
	}

	@Override
	public boolean isKey() {
		return false;
	}

	protected Double getDoubleValue(ResultSet rs) throws SQLException {
		Object value = rs.getObject(getName());
		if (value==null)
			return null;
		if (value.getClass().equals(Double.class))
			return (Double)value;
		return Double.valueOf(value.toString());
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean) {
		Double value = null;
		try{
			value = getDoubleValue(rs);
			setValue(value, bean);
		} catch (Exception e) {
			throw new SystemError("Error:" + getName(), e);
		}
	}

	@Override
	public Class<?> getValueClass() {
		return Double.class;
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
		if (bean != null) {
			Double value = getValue(bean);
			checkValue(value);
			if (value == null)
				stm.setNull(index, Types.DOUBLE);
			else
				stm.setObject(index, value);
		}
	}
}
