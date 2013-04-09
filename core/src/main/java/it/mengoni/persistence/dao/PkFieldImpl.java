package it.mengoni.persistence.dao;

import it.mengoni.persistence.dto.PersistentObject;


public abstract class PkFieldImpl<T extends PersistentObject, V> extends FieldImpl<T, V> {

	public PkFieldImpl(String name, int length,	int sqlType) {
		super(name, true, false, length, sqlType);
	}

	public PkFieldImpl(String name, String propertyName, int length, int sqlType) {
		super(name, propertyName, false, length, sqlType);
	}

	@Override
	public boolean isKey() {
		return true;
	}


}
