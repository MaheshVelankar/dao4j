package it.mengoni.generator.gui.forms;


import java.util.List;

import javax.swing.table.AbstractTableModel;

public class NameSubstTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Column Name",
                                    "Property Name"};
    List<RowBean> data;
    public NameSubstTableModel(List<RowBean> data) {
		super();
		this.data = data;
	}
	public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
    	if (col==0)
    		return data.get(row).getKey();
    	if (col==1)
    		return data.get(row).getValue();
    	return "";
    }

	public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col > 1) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col) {
    	if (col==0)
    		data.get(row).setKey((String)value);
    	if (col==1)
    		data.get(row).setValue((String)value);
        fireTableCellUpdated(row, col);

    }
}