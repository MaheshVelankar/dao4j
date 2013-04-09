package it.mengoni.config.view;

import it.mengoni.persistence.filter.ListProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColumnsConfig {

	private String tableName;

	private List<ColumnConfig> items = new  ArrayList<ColumnConfig>();

	private transient Map<String, ColumnConfig> mapItems = new HashMap<String, ColumnConfig>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<ColumnConfig> getItems() {
		return items;
	}

	public void setItems(List<ColumnConfig> items) {
		this.items = items;
		calcMap();
	}

	private void calcMap() {
		mapItems = new HashMap<String, ColumnConfig>();
		if (items!=null)
			for (ColumnConfig columnConfig : items) {
				mapItems.put(columnConfig.getFieldName(), columnConfig);
			}
	}

	public ColumnConfig get(String fieldName){
		if (mapItems==null)
			calcMap();
		return mapItems.get(fieldName);
	}

	@Override
	public String toString() {
		return "ColumnsConfig [tableName=" + tableName + ", items=" + items	+ "]";
	}

	public void addConfig(String fieldName, String propertyName, Class<?> fieldClass, String label, boolean filterEnabled, ListProvider<?> listProvider) {
		ColumnConfig ret = new ColumnConfig();
		ret.setFieldName(fieldName);
		ret.setFilterEnabled(filterEnabled);
		ret.setListProvider(listProvider);
		ret.setLabel(label);
		ret.setVisible(true);
		ret.setJavaClass(fieldClass);
		ret.setPropertyName(propertyName);
		ColumnConfig x = mapItems.get(ret.getFieldName());
		if (x!=null)
			items.remove(x);
		items.add(ret);
		mapItems.put(ret.getFieldName(), ret);
	}

}
