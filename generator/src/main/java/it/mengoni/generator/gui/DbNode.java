package it.mengoni.generator.gui;

import java.util.Iterator;

import it.mengoni.jdbc.model.DbItem;

import javax.swing.tree.DefaultMutableTreeNode;


public class DbNode extends DefaultMutableTreeNode {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private DbItem dbItem;
//	private boolean areChildrenDefined = false;

	public DbNode(DbItem dbItem) {
		this.dbItem = dbItem;
		defineChildNodes();
	}

	public boolean isLeaf() {
		return dbItem==null || dbItem.haveChildren();
	}

//	public int getChildCount() {
//		if (!areChildrenDefined)
//			defineChildNodes();
//		return (super.getChildCount());
//	}

	private void defineChildNodes() {
//		areChildrenDefined = true;
		if (dbItem!=null){
			Iterator<DbItem> it = dbItem.getChildIterator();
			while(it.hasNext()){
				add(new DbNode(it.next()));
			}
		}
	}

	public String toString() {
		String l = dbItem!=null?dbItem.getLabel():"";
		return l;
	}

	public DbItem getDbItem() {
		return dbItem;
	}

}
