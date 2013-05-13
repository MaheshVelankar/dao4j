package it.mengoni.persistence.dao;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;


public abstract class PkShortField<T extends PersistentObject> extends ShortField<T> {

	public PkShortField(String name, String propertyName,
			EditItemValue[] editItemValues) {
		super(name, propertyName, false, editItemValues);
	}

	public PkShortField(String name, String propertyName) {
		super(name, propertyName, false);
	}

	@Override
	public boolean isKey() {
		return true;
	}


}
