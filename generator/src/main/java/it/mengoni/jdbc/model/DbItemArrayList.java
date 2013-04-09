package it.mengoni.jdbc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DbItemArrayList<T extends DbItem> extends ArrayList<T> implements DbItemList<T>, Serializable{

	private static final long serialVersionUID = 1L;

	private Map<String, T> itemMap = new HashMap<String, T>();

	@Override
	public boolean add(T element) {
		if (!contains(element)){
			boolean added = super.add(element);
			if (added)
				itemMap.put(element.getDbName(), element);
		}
		return false;
	}

	@Override
	public void add(int index, T element) {
		super.add(index, element);
		itemMap.put(element.getDbName(), element);
	}

	@Override
	public T remove(int index) {
		T element = super.remove(index);
		if (element!=null)
			itemMap.remove(element.getDbName());
		return element;
	}

	@Override
	public boolean remove(Object element) {
		if (element instanceof DbItem){
			boolean removed = super.remove(element);
			if (element!=null && removed)
				itemMap.remove(((DbItem)element).getDbName());
			return removed;
		}
		return false;
	}

	public T find(String name){
		return itemMap.get(name);
	}

	public Set<String> itemNames(){
		return itemMap.keySet();
	}

}