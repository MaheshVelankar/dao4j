package it.mengoni.persistence.dto;

import java.io.Serializable;
import java.util.Collection;

public class PoLazyProperties<T extends PersistentObject> extends PoProperties<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean resolved;

	public PoLazyProperties() {
		super();
	}

	public PoLazyProperties(Collection<T> value) {
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
