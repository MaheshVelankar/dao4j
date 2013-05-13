package it.mengoni.persistence.dao.fields;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.LogicError;
import it.mengoni.persistence.exception.SystemError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class BytesField<T extends PersistentObject> extends AbstractField<T, byte[]> {

	public BytesField(String name, String propertyName,
			boolean nullable,
			EditItemValue[] editItemValues) {
		super(name, propertyName, nullable, 0, editItemValues);
	}

	public BytesField(String name, String propertyName,
			boolean nullable) {
		super(name, propertyName, nullable, 0);
	}

	@Override
	public boolean isKey() {
		return false;
	}

	protected byte[] getBytesValue(ResultSet rs) throws SQLException {
		Object value = rs.getBytes(getName());
		if (value==null)
			return null;
		if (value.getClass().equals(byte[].class))
			return (byte[])value;
//		return Double.valueOf(value.toString());
		throw new LogicError("Unsupported value type for byte[]:"+value.getClass().getName());
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean) {
		byte[] value = null;
		try{
			value = getBytesValue(rs);
			setValue(value, bean);
		} catch (Exception e) {
			throw new SystemError("Error:" + getName(), e);
		}
	}

	@Override
	public Class<?> getValueClass() {
		return byte[].class;
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
		if (bean != null) {
			byte[] value = getValue(bean);
			checkValue(value);
			if (value == null)
				stm.setNull(index, Types.BINARY);
			else
				stm.setObject(index, value);
		}
	}
}
