package it.mengoni.jdbc.model;

public class TableReference extends AbstractSelectableDbItem implements SelectableDbItem{

	private static final long serialVersionUID = 1L;
	private transient TableReferences parent;
	private Table target;
	private Fk fk;

	public TableReference(TableReferences parent, Fk fk) {
		this.parent = parent;
		this.fk = fk;
		if (parent!=null)
			parent.addRererence(this);
		setSelected(true);
	}

	public TableReferences getParent() {
		return parent;
	}

	@Override
	protected DbItem getChild(int i) {
		return null;
	}

	@Override
	public boolean haveChildren() {
		return false;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	public Table getTarget() {
		return target;
	}

	public void setTarget(Table target) {
		this.target = target;
	}

	@Override
	public String getLabel() {
		if (target!=null)
			return target.getLabel();
		return "...";
	}

	public Fk getFk() {
		return fk;
	}

	public void setFk(Fk fk) {
		this.fk = fk;
	}

}