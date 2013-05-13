package it.mengoni.persistence.dao.fields;

import it.mengoni.persistence.dto.PersistentObject;


public abstract class PkStringField<T extends PersistentObject> extends StringField<T> {

	public PkStringField(String name, int length) {
		super(name, name, false, length);
	}

	public PkStringField(String name, String propertyName, int length) {
		super(name, propertyName, false, length);
	}

	@Override
	public boolean isKey() {
		return true;
	}

}
