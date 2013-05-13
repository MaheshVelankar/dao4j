package it.mengoni.persistence.dao;

import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.exception.LogicError;
import it.mengoni.persistence.exception.SystemError;

import org.javatuples.Tuple;

public interface KeyGenerator<T extends PersistentObject> {

	public Tuple newKey(T bean, String[] keyNames) throws SystemError, LogicError;

}