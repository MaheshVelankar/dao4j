package it.mengoni.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.mengoni.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;

public abstract class FieldCalculated<T extends PersistentObject, V>  extends AbstractField<T, V> {

	public FieldCalculated(String name, boolean nullable, int length, int sqlType) {
		super(name, nullable, length, sqlType);
	}

	public FieldCalculated(String name, boolean nullable, int length, int sqlType, EditItemValue[] editItemValues) {
		super(name, nullable, length, sqlType, editItemValues);
	}

	public boolean isKey() {
		return false;
	}

	public boolean isReadOnly(){
		return true;
	}

	public void setParam(PreparedStatement stm, int index, T bean) throws SQLException {
	}

	public void checkValue(T bean) {
	}

	@Override
	public V getValue(T bean) {
		return null;
	}

}
