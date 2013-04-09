package it.mengoni.generator.gui.forms;

import it.mengoni.generator.gui.DbNode;
import it.mengoni.jdbc.model.DbItem;
import it.mengoni.jdbc.model.SelectableDbItem;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class DbItemCellEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 1L;
	protected SelectableDbItem nodeData;

	public DbItemCellEditor() {
		super(new JCheckBox());
	}

	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row) {
		JCheckBox editor = null;
		nodeData = getQuestionFromValue(value);
		if (nodeData != null) {
			editor = (JCheckBox) (super.getComponent());
			editor.setText(nodeData.getLabel());
			editor.setSelected(nodeData.isSelected());
			return editor;
		}
		return null;
	}

	public Object getCellEditorValue() {
		JCheckBox editor = (JCheckBox) (super.getComponent());
		nodeData.setSelected(editor.isSelected());
		return nodeData;
	}

	public static SelectableDbItem getQuestionFromValue(Object value) {
		if (value instanceof DefaultMutableTreeNode) {
			DbNode node = (DbNode) value;
			DbItem userObject = node.getDbItem();
			if (userObject instanceof SelectableDbItem) {
				return (SelectableDbItem) userObject;
			}
		}
		return null;
	}

}