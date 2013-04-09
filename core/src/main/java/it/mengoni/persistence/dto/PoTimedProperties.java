package it.mengoni.persistence.dto;

import java.io.Serializable;
import java.util.Collection;


public class PoTimedProperties<T extends PersistentObject> extends PoProperties<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	long expired = 0;
	long expirePeriod = 60*1000;

	public PoTimedProperties() {
		super();
	}

	public PoTimedProperties(Collection<T> value) {
		super(value);
		expired = System.currentTimeMillis() + expirePeriod;
	}

	@Override
	protected void setResolved() {
		expired = System.currentTimeMillis() + expirePeriod;

	}

	@Override
	protected boolean isResolved() {
		return expired<System.currentTimeMillis();
	}

	@Override
	public void unResolve() {
		expired = 0;
	}

}
