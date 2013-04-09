package it.mengoni.jdbc.model;


public interface SelectableDbItem extends DbItem{

	public boolean isSelected();

	public void setSelected(boolean selected);

}
