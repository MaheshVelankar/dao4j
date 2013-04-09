package it.mengoni.generator.gui.forms;

import it.mengoni.generator.gui.DbNode;
import it.mengoni.jdbc.model.SelectableDbItem;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class DbItemCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;
	protected JCheckBox checkBoxRenderer = new JCheckBox();

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		if (value instanceof DbNode) {
			DbNode node = (DbNode) value;
			if (node.getDbItem() instanceof SelectableDbItem) {
				prepareQuestionRenderer((SelectableDbItem) node.getDbItem(), selected);
				return checkBoxRenderer;
			}
		}
		return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
	}

	protected void prepareQuestionRenderer(SelectableDbItem tfq,
			boolean selected) {
		checkBoxRenderer.setText(tfq.getLabel());
		checkBoxRenderer.setSelected(tfq.isSelected());
		if (selected) {
			checkBoxRenderer.setForeground(getTextSelectionColor());
			checkBoxRenderer.setBackground(getBackgroundSelectionColor());
		} else {
			checkBoxRenderer.setForeground(getTextNonSelectionColor());
			checkBoxRenderer.setBackground(getBackgroundNonSelectionColor());
		}
	}
}