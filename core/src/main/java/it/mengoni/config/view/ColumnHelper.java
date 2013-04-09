package it.mengoni.config.view;

import it.mengoni.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.filter.ListProvider;

import java.util.Map;
import java.util.Set;

public interface ColumnHelper<T extends PersistentObject> {

	public String getPropertyName();

	public String getFieldName();

	public boolean isVisible();

	public void setVisible(boolean visible);

	public String getLabel();

	public String getDisplayValue(T bean);

	public String doDisplayValue(T bean);

	public boolean isEuro();

	public void setEuro(boolean euro);

	public String[] getRole();

	public void setRole(String[] role);

	public boolean isFilterEnabled();

	public void setFilterEnabled(boolean filterEnabled);

	public Class<?> getJavaClass();

	public void setJavaClass(Class<?> javaClass);

	public int getLength();

	public void setLength(int length);

	public ListProvider<?> getListProvider();

	public void setListProvider(ListProvider<?> listProvider);

	public void setLabel(String label);

	public boolean isJoin();

	public void setJoin(boolean join);

	public String getJoinTableName();

	public void setJoinTableName(String joinTableName);

	public String getJoinKeyFieldName();

	public void setJoinKeyFieldName(String joinKeyFieldName);

	public String getJoinFilterFieldName();

	public void setJoinFilterFieldName(String joinFilterFieldName);

	public String getJoinLocalKeyField();

	public void setJoinLocalKeyField(String joinLocalKeyField);

	public String getTableName();

	public void setTableName(String tableName);

	public Map<Object,EditItemValue> getEditItemValues();

	public void setColumnWidth(String columnWidth);

	public String getColumnWidth();

	public String getAlign();

	public void setAlign(String align);

	public boolean canEdit(Set<String> role);

	public boolean canView(Set<String> role);


}