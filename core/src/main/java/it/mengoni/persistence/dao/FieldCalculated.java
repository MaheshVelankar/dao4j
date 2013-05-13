package it.mengoni.persistence.dao;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class FieldCalculated<T extends PersistentObject, V>  extends AbstractField<T, V> {

	public FieldCalculated(String name, boolean nullable) {
		super(name, nullable, 0);
	}

	public FieldCalculated(String name, boolean nullable, EditItemValue[] editItemValues) {
		super(name, nullable, 0, editItemValues);
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
