package it.mengoni.persistence.dto;

import java.io.Serializable;

public class PoLazyProperty<T extends PersistentObject> extends PoProperty<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean resolved = false;

	public PoLazyProperty() {
		super();
	}

	public PoLazyProperty(T value) {
		super(value);
		resolved = true;
	}

	@Override
	protected void setResolved() {
		resolved = true;
	}

	@Override
	protected boolean isResolved() {
		return resolved;
	}

	@Override
	public void unResolve() {
		resolved = false;
	}

}
