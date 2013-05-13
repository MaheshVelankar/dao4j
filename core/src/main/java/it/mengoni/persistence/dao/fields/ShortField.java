package it.mengoni.persistence.dao.fields;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.SystemError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class ShortField<T extends PersistentObject> extends AbstractField<T, Short> {

	public ShortField(String name, String propertyName,
			boolean nullable,
			EditItemValue[] editItemValues) {
		super(name, propertyName, nullable, 0, editItemValues);
	}

	public ShortField(String name, String propertyName,
			boolean nullable) {
		super(name, propertyName, nullable, 0);
	}

	@Override
	public boolean isKey() {
		return false;
	}

	protected Integer getIntegerValue(ResultSet rs) throws SQLException {
		Object value = rs.getObject(getName());
		if (value==null)
			return null;
		if (value.getClass().equals(Integer.class))
			return (Integer)value;
		return Integer.valueOf(value.toString());
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean) {
		Integer value = null;
		try{
			value = getIntegerValue(rs);
			setValue(value==null?null:new Short(value.shortValue()), bean);
		} catch (Exception e) {
			throw new SystemError("Error:" + getName(), e);
		}
	}

	@Override
	public Class<?> getValueClass() {
		return Short.class;
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
		if (bean != null) {
			Short value = getValue(bean);
			checkValue(value);
			if (value == null)
				stm.setNull(index, Types.SMALLINT);
			else
				stm.setObject(index, value);
		}
	}
}
