package it.mengoni.persistence.dto;

import it.mengoni.persistence.dao.Condition;
import it.mengoni.persistence.dao.Dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class PoProperties<T extends PersistentObject> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List<T> value = new ArrayList<T>();

	public PoProperties() {
		super();
	}

	public PoProperties(Collection<T> values) {
		super();
		this.value.addAll(values);
	}

	public List<T> getValue(Dao<T> dao, Condition... conditions) {
		if (!isResolved()) {
			value = resolve(dao, conditions);
			setResolved();
		}
		return value;
	}

	protected abstract void setResolved();

	protected abstract boolean isResolved();

	public abstract void unResolve();

	public void setValue(Collection<T> values) {
		this.value.addAll(values);
		setResolved();
	}

	public void clear() {
		this.value.clear();
		unResolve();
	}

	protected List<T> resolve(Dao<T> dao, Condition[] conditions) {
		return dao.getListFor(conditions);
	}

	public List<T> getValue() {
		return value;
	}

}
