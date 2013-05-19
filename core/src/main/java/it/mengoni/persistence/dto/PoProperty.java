package it.mengoni.persistence.dto;


import it.mengoni.persistence.dao.Dao;

import java.io.Serializable;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Octet;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.javatuples.Unit;

public abstract class PoProperty<T extends PersistentObject> implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private T value = null;

	public PoProperty() {
		super();
	}

	public PoProperty(T value) {
		super();
		this.value = value;
	}

	public T getValue(Dao<T> dao, Object...keys) {
		if (!isResolved()){
			value = resolve(dao, keys);
			setResolved();
		}
		return value;
	}

	public T getValue() {
		return value;
	}

	protected abstract void setResolved();

	protected abstract boolean isResolved();

	public abstract void unResolve();

	public void setValue(T value) {
		this.value = value;
		setResolved();
	}

	public void clear() {
		value = null;
		unResolve();
	}

	private Tuple newTuple(Object[] keys) {
		switch (keys.length) {
		case 1: return Unit.fromArray(keys);
		case 2: return Pair.fromArray(keys);
		case 3: return Triplet.fromArray(keys);
		case 4: return Quartet.fromArray(keys);
		case 5: return Quintet.fromArray(keys);
		case 6: return Sextet.fromArray(keys);
		case 7: return Septet.fromArray(keys);
		case 8: return Octet.fromArray(keys);
		case 9: return Ennead.fromArray(keys);
		case 10: return Decade.fromArray(keys);
		default:
			break;
		}
		return null;
	}

	protected T resolve(Dao<T> dao, Object ... keys) {
		Tuple tuple = newTuple(keys);
		return dao.get(tuple);
	}


}
