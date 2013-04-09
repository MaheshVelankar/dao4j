package it.mengoni.persistence.dao;

import it.mengoni.exception.LogicError;
import it.mengoni.exception.SystemError;
import it.mengoni.persistence.dto.PersistentObject;

import org.javatuples.Tuple;

public interface KeyGenerator<T extends PersistentObject> {

	public Tuple newKey(T bean, String[] keyNames) throws SystemError, LogicError;

}