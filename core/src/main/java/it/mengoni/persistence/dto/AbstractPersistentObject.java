package it.mengoni.persistence.dto;


import it.mengoni.persistence.dao.DaoUtils;

import org.javatuples.Tuple;

public abstract class AbstractPersistentObject implements PersistentObject {

	private static final long serialVersionUID = 1L;
	private transient boolean saved = false;
	private transient boolean deleted = false;
	private transient boolean edit = false;

	protected Tuple key = null;

	public boolean isNew(){
		return !saved;
	}

	@Override
	public void saved(){
		saved = true;
		key = newKey();
	}

	protected abstract Tuple newKey();

	protected void updateKey() {
		if (!saved)
			key = null;
	}

	public void deleted(){
		deleted=true;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isKeyAssigned() {
		return !DaoUtils.isEmpty(key);
	}

	public Tuple getKey() {
		if (DaoUtils.isEmpty(key))
			key = newKey();
		return key;
	}

	@Override
	public boolean equals(Object other) {
		if (other==null)
			return false;
		if (other==this)
			return true;
		if (other.getClass().equals(this.getClass()))
			return ((AbstractPersistentObject)other).getKey().equals(getKey());
		return false;
	}

	@Override
	public int hashCode() {
		return getKey().hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() +"->"+ getKey() + " " + getDisplayLabel();
	}


}