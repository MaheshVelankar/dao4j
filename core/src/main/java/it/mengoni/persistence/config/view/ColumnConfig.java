package it.mengoni.persistence.config.view;

import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.filter.ListProvider;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ColumnConfig {

	private String fieldName;
	private String tableName;
	private boolean visible;
	private boolean euro;
	private Set<String> role  = new HashSet<String>();
	private Set<String> editRole = new HashSet<String>();
	private boolean filterEnabled = true;
	private String columnWidth;
	private String align;
	private Class<?> javaClass;
	private int length;
	private ListProvider<?> listProvider;
	private String label;
	private String propertyName;
	private boolean join;
//	private final static String joinTemplate = "%s in (select %s from %s where %s)";
//	// 1 campo del where esterno
//	// 2 campo estratto dalla sql annidata
//	// 3 relazione esterna (tabella/vista)
//	// 4 espressione di filtro

	private String joinKeyFieldName;
	private String joinTableName;
	private String joinFilterFieldName;
	private String joinLocalKeyField;

	private Map<Object,EditItemValue> editItemValues;


	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public String toString() {
		return "ColumnConfig [fieldName=" + fieldName + ", visible=" + visible
				+ "]";
	}

	public boolean isEuro() {
		return euro;
	}

	public void setEuro(boolean euro) {
		this.euro = euro;
	}

	public boolean isFilterEnabled() {
		return filterEnabled;
	}

	public void setFilterEnabled(boolean filterEnabled) {
		this.filterEnabled = filterEnabled;
	}

	public Class<?> getJavaClass() {
		return javaClass;
	}

	public void setJavaClass(Class<?> javaClass) {
		this.javaClass = javaClass;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public ListProvider<?> getListProvider() {
		return listProvider;
	}

	public void setListProvider(ListProvider<?> listProvider) {
		this.listProvider = listProvider;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public boolean isJoin() {
		return join;
	}

	public void setJoin(boolean join) {
		this.join = join;
	}

	public String getJoinTableName() {
		return joinTableName;
	}

	public void setJoinTableName(String joinTableName) {
		this.joinTableName = joinTableName;
	}

	public String getJoinKeyFieldName() {
		return joinKeyFieldName;
	}

	public void setJoinKeyFieldName(String joinKeyFieldName) {
		this.joinKeyFieldName = joinKeyFieldName;
	}

	public String getJoinFilterFieldName() {
		return joinFilterFieldName;
	}

	public void setJoinFilterFieldName(String joinFilterFieldName) {
		this.joinFilterFieldName = joinFilterFieldName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<Object,EditItemValue> getEditItemValues() {
		return editItemValues;
	}

	public void setEditItemValues(EditItemValue[] editItemValues) {
		if (editItemValues!=null){
			this.editItemValues = new LinkedHashMap<Object, EditItemValue>();
			for (int i = 0; i < editItemValues.length; i++) {
				this.editItemValues.put(editItemValues[i].getDatabaseValue(), editItemValues[i]);
			}
		}  else
			this.editItemValues = null;
	}

	public String getDisplayValue(Object databaseValue){
		if (editItemValues==null)
			return null;
		EditItemValue item = editItemValues.get(databaseValue);
		if (item!=null)
			return item.getDisplayValue();
		return null;
	}

	public boolean withEditValues(){
		return editItemValues!=null;
	}

	public String getJoinLocalKeyField() {
		return joinLocalKeyField;
	}

	public void setJoinLocalKeyField(String joinLocalKeyField) {
		this.joinLocalKeyField = joinLocalKeyField;
	}

	public String getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String[] getEditRole() {
		return editRole.toArray(new String[]{});
	}

	public void setEditRole(String[] editRole) {
		this.editRole.clear();
		for (int i = 0; i < editRole.length; i++) {
			this.editRole.add(editRole[i]);
		}
	}

	public static <T> boolean containsOne(Set<T> data, Set<T> toCheck){
		if (data==null || data.isEmpty())
			return true;
		for (T value : toCheck) {
			if (data.contains(value))
				return true;
		}
		return false;
	}

	public boolean canEdit(Set<String> roles){
		return containsOne(this.editRole, roles);
	}

	public boolean canView(Set<String> role){
		return containsOne(this.role, role);
	}

	public String[] getRole() {
		return role.toArray(new String[]{});
	}

	public void setRole(String[] role) {
		this.role.clear();
		for (int i = 0; i < role.length; i++) {
			this.role.add(role[i]);
		}
	}

}
