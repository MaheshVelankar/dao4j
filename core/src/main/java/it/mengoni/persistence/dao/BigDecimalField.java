package it.mengoni.persistence.dao;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.SystemError;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class BigDecimalField<T extends PersistentObject> extends AbstractField<T, BigDecimal> {

	public BigDecimalField(String name, String propertyName,
			boolean nullable,
			EditItemValue[] editItemValues) {
		super(name, propertyName, nullable, 0, editItemValues);
	}

	public BigDecimalField(String name, String propertyName,
			boolean nullable) {
		super(name, propertyName, nullable, 0);
	}

	@Override
	public boolean isKey() {
		return false;
	}

	protected BigDecimal getBigDecimalValue(ResultSet rs) throws SQLException {
		Object value = rs.getObject(getName());
		if (value==null)
			return null;
		if (value.getClass().equals(BigDecimal.class))
			return (BigDecimal)value;
		return new BigDecimal(value.toString());
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean) {
		BigDecimal value = null;
		try{
			value = getBigDecimalValue(rs);
			setValue(value, bean);
		} catch (Exception e) {
			throw new SystemError("Error:" + getName(), e);
		}
	}

	@Override
	public Class<?> getValueClass() {
		return BigDecimal.class;
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
		if (bean != null) {
			BigDecimal value = getValue(bean);
			checkValue(value);
			if (value == null)
				stm.setNull(index, Types.BIGINT);
			else
				stm.setObject(index, value);
		}
	}

}
