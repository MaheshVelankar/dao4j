package it.mengoni.persistence.filter;

import it.mengoni.persistence.dto.PersistentObject;

import java.io.Serializable;
import java.util.List;

public interface ListProvider<T extends PersistentObject> extends Serializable {
	
	public List<T> getList();

}
