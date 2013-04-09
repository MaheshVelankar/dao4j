package it.mengoni.persistence.dao;


public interface ConditionParam extends Condition{

	public int getValueCount();

	public Object getValue(int index);

	public void setValue(int index, Object value);

	public boolean add(Object value);

	public int indexOf(Object value);

}
