package it.mengoni.generator.gui.forms;

import it.mengoni.generator.gui.DbNode;
import it.mengoni.jdbc.model.SelectableDbItem;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

public class DbTree extends JTree{
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isPathEditable(TreePath path) {
		if (isEditable()) {
			Object x = path.getLastPathComponent();
			if (x instanceof DbNode){
				DbNode dbNode = (DbNode) x;
				return dbNode.getDbItem() instanceof SelectableDbItem;
			}
		}
		return false;
	}

}