package it.mengoni.persistence.dao;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;

public abstract class PKBigDecimalField<T extends PersistentObject> extends BigDecimalField<T> {



	public PKBigDecimalField(String name, String propertyName,
			 EditItemValue[] editItemValues) {
		super(name, propertyName, false, editItemValues);
	}

	public PKBigDecimalField(String name, String propertyName) {
		super(name, propertyName, false);
	}

	@Override
	public boolean isKey() {
		return true;
	}


}
