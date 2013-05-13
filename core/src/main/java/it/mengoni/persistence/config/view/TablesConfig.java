package it.mengoni.persistence.config.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TablesConfig {

	
	private List<ColumnsConfig> items;
	
	private transient Map<String, ColumnsConfig> mapItems = null; 


	public List<ColumnsConfig> getItems() {
		return items;
	}

	public void setItems(List<ColumnsConfig> items) {
		this.items = items;
		calcMap();
	}

	private void calcMap() {
		mapItems = new HashMap<String, ColumnsConfig>();
		if (items!=null)
			for (ColumnsConfig columnConfig : items) {
				mapItems.put(columnConfig.getTableName(), columnConfig);
			}
	}

	public ColumnsConfig get(String fieldName){
		if (mapItems==null)
			calcMap();
		return mapItems.get(fieldName);
	}

	public ColumnConfig get(String tableName, String fieldName){
		if (mapItems==null)
			calcMap();
		ColumnsConfig x = mapItems.get(tableName);
		if (x!=null)
			return x.get(fieldName);
		return null;
	}
	
	@Override
	public String toString() {
		return "TablesConfig [items=" + items	+ "]";
	}

}
