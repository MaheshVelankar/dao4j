package it.mengoni.jdbc.model;


public abstract class AbstractSelectableDbItem extends AbstractDbItem implements SelectableDbItem {

	private static final long serialVersionUID = 1L;
	private boolean selected = true;

	public AbstractSelectableDbItem() {
		super();
	}

	public AbstractSelectableDbItem(String name, String descrizione) {
		super(name, descrizione);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
