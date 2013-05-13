package it.mengoni.persistence.dao.fields;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;


public abstract class PkIntegerField<T extends PersistentObject> extends IntegerField<T> {



	public PkIntegerField(String name, String propertyName,
			EditItemValue[] editItemValues) {
		super(name, propertyName, false, editItemValues);
	}

	public PkIntegerField(String name, String propertyName) {
		super(name, propertyName, false);
	}

	@Override
	public boolean isKey() {
		return true;
	}



}
