package it.mengoni.jdbc.model;

import java.util.List;
import java.util.Set;

public interface DbItemList<T extends DbItem> extends List<T> {

	public T find(String name);

	public Set<String> itemNames();


}
