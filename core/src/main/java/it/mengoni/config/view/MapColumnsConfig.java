package it.mengoni.config.view;

import it.mengoni.db.EditItemValue;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.FieldJoin;
import it.mengoni.persistence.filter.ListProvider;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.LinkedHashMap;

public class MapColumnsConfig extends LinkedHashMap<String, ColumnConfig>{

	private static final long serialVersionUID = 1L;

	private final String tableName;

	public MapColumnsConfig(String tableName) {
		super();
		this.tableName = tableName;
	}

	public Class<?> getJavaClass(int sqlType) {
		if (sqlType==Types.BIGINT) return Long.class;
		if (sqlType==Types.BINARY) return byte[].class;
		if (sqlType==Types.BIT) return Boolean.class;
		if (sqlType==Types.BOOLEAN) return Boolean.class;
		if (sqlType==Types.CHAR) return String.class;
		if (sqlType==Types.DATE) return java.sql.Date.class;
		if (sqlType==Types.DECIMAL) return BigDecimal.class;
		if (sqlType==Types.DOUBLE) return Double.class;
		if (sqlType==Types.FLOAT) return Double.class;
		if (sqlType==Types.INTEGER) return Integer.class;
		if (sqlType==Types.LONGNVARCHAR) return String.class;
		if (sqlType==Types.LONGVARBINARY) return byte[].class;
		if (sqlType==Types.LONGVARCHAR) return String.class;
		if (sqlType==Types.NCHAR) return String.class;
		if (sqlType==Types.NCLOB) return Object.class;
		if (sqlType==Types.NUMERIC) return java.math.BigDecimal.class;
		if (sqlType==Types.NVARCHAR) return String.class;
		if (sqlType==Types.REAL) return Float.class;
		if (sqlType==Types.REF) return Object.class;
		if (sqlType==Types.ROWID) return Object.class;
		if (sqlType==Types.SMALLINT) return Integer.class;
		if (sqlType==Types.SQLXML) return String.class;
		// if (sqlType==Types.STRUCT) return Object","(rs.getObject(g
		if (sqlType==Types.TIME) return java.sql.Time.class;
		if (sqlType==Types.TIMESTAMP) return java.sql.Timestamp.class;
		if (sqlType==Types.TINYINT) return Byte.class;
		if (sqlType==Types.VARBINARY) return byte[].class;
		if (sqlType==Types.VARCHAR) return String.class;
		if (sqlType==Types.BLOB) return byte[].class;
		return String.class;

	}

	public String getAlign(int sqlType, String align) {
		if (align!=null && !align.isEmpty())
			return align;
		if (sqlType==Types.BIGINT) return "right";
		if (sqlType==Types.BINARY) return "right";
		if (sqlType==Types.BIT) return "right"; //,
		if (sqlType==Types.BOOLEAN) return "left";
		if (sqlType==Types.CHAR) return  "left"; //
		if (sqlType==Types.DATE) return "right"; //
		if (sqlType==Types.DECIMAL) return "right";
		if (sqlType==Types.DOUBLE) return "right";
		if (sqlType==Types.FLOAT) return "right";
		if (sqlType==Types.INTEGER) return "right";
		if (sqlType==Types.LONGNVARCHAR) return "left"; //,
		if (sqlType==Types.LONGVARBINARY) return "left"; //
		if (sqlType==Types.LONGVARCHAR) return "left"; //,"
		if (sqlType==Types.NCHAR) return "left"; //,"
		if (sqlType==Types.NCLOB) return "left"; //,"
		if (sqlType==Types.NUMERIC) return "right";
		if (sqlType==Types.NVARCHAR) return "left";
		if (sqlType==Types.REAL) return "right"; //,"
		if (sqlType==Types.REF) return "left"; //,"(r
		if (sqlType==Types.ROWID) return "left"; //,"
		if (sqlType==Types.SMALLINT) return "right";
		if (sqlType==Types.SQLXML) return "left"; //,
		// if (sqlType==Types.STRUCT) return Object",
		if (sqlType==Types.TIME) return "right"; //,"
		if (sqlType==Types.TIMESTAMP) return "right";
		if (sqlType==Types.TINYINT) return "right";
		if (sqlType==Types.VARBINARY) return"right";
		if (sqlType==Types.VARCHAR) return "left"; //
		if (sqlType==Types.BLOB) return "left"; //,"(
		return "left";

	}


	public ColumnConfig addConfig(FieldJoin<?,?> def, String propertyName, String label, boolean filterEnabled, boolean visible, int colWidth, String align) {
		if (def==null)
			throw new IllegalArgumentException("definition for " +tableName+" "+ propertyName + " not assigned");
		ColumnConfig ret = new ColumnConfig();
		ret.setFieldName(def.getName());
		ret.setAlign("left");
		ret.setFilterEnabled(filterEnabled);
		ret.setLabel(label);
		ret.setVisible(visible);
		ret.setJavaClass(getJavaClass(def.getSqlType()));
		ret.setPropertyName(propertyName);
		ret.setJoin(true);
		ret.setJoinKeyFieldName(def.getJoinKeyField());
		ret.setJoinTableName(def.getJoinTableName());
		ret.setJoinFilterFieldName(def.getJoinField());
		ret.setTableName(tableName);
		ret.setEditItemValues(def.getEditItemValues());
		ret.setLength(def.getLength());
		ret.setJoinLocalKeyField(def.getLocalKeyField());
		ret.setColumnWidth(""+colWidth);
		ret.setEuro(def.isEuro());
		put(ret.getPropertyName(), ret);
		return ret;
	}

	public ColumnConfig addConfig(Field<?,?> def, String propertyName, String label, boolean filterEnabled, boolean visible, int colWidth, String align) {
		if (def==null)
			throw new IllegalArgumentException("definition for " +tableName+" "+ propertyName + " not assigned");
		if (def instanceof FieldJoin<?,?>)
			return addConfig((FieldJoin<?,?>)def, propertyName, label, filterEnabled, visible, colWidth, align);
		ColumnConfig ret = new ColumnConfig();
		ret.setAlign(getAlign(def.getSqlType(), align));
		ret.setFieldName(def.getName());
		ret.setFilterEnabled(filterEnabled);
		ret.setLabel(label);
		ret.setVisible(visible);
		ret.setJavaClass(getJavaClass(def.getSqlType()));
		ret.setPropertyName(propertyName);
		ret.setTableName(tableName);
		ret.setEditItemValues(def.getEditItemValues());
		ret.setLength(def.getLength());
		ret.setColumnWidth(""+colWidth);
		ret.setEuro(def.isEuro());
		put(ret.getPropertyName(), ret);
		return ret;
	}


	public ColumnConfig addConfig(String fieldName, String propertyName, Class<?> fieldClass, String label, boolean filterEnabled, boolean visible, int colWidth, String align) {
		ColumnConfig ret = new ColumnConfig();
		ret.setFieldName(fieldName);
		ret.setAlign(align);
		ret.setFilterEnabled(filterEnabled);
		ret.setLabel(label);
		ret.setVisible(visible);
		ret.setJavaClass(fieldClass);
		ret.setPropertyName(propertyName);
		ret.setTableName(tableName);
		ret.setColumnWidth(""+colWidth);
		put(ret.getPropertyName(), ret);
		return ret;
	}

	public ColumnConfig addConfig(String fieldName, String propertyName, Class<?> fieldClass, String label, boolean filterEnabled, boolean visible, int maxFieldLength, int colWidth, String align) {
		ColumnConfig ret = new ColumnConfig();
		ret.setFieldName(fieldName);
		ret.setFilterEnabled(filterEnabled);
		ret.setLabel(label);
		ret.setVisible(visible);
		ret.setJavaClass(fieldClass);
		ret.setPropertyName(propertyName);
		ret.setLength(maxFieldLength);
		ret.setTableName(tableName);
		ret.setColumnWidth(""+colWidth);
		ret.setAlign(align);
		put(ret.getPropertyName(), ret);
		return ret;
	}


	public ColumnConfig addConfig(String fieldName, String propertyName, Class<?> fieldClass, String label, boolean filterEnabled, ListProvider<?> listProvider, int colWidth, String align) {
		ColumnConfig ret = new ColumnConfig();
		ret.setFieldName(fieldName);
		ret.setFilterEnabled(filterEnabled);
		ret.setListProvider(listProvider);
		ret.setLabel(label);
		ret.setVisible(true);
		ret.setJavaClass(fieldClass);
		ret.setPropertyName(propertyName);
		ret.setTableName(tableName);
		ret.setColumnWidth(""+colWidth);
		ret.setAlign(align);
		put(ret.getPropertyName(), ret);
		return ret;
	}

	public ColumnConfig addConfigJoin(String fieldName, String propertyName, Class<?> fieldClass, String label, boolean filterEnabled, String joinKeyFieldName, String joinTableName, String joinFilterFieldName, String joinLocalKeyField, int colWidth, String align) {
		ColumnConfig ret = new ColumnConfig();
		ret.setFieldName(fieldName);
		ret.setAlign(align);
		ret.setFilterEnabled(filterEnabled);
		ret.setLabel(label);
		ret.setVisible(true);
		ret.setJavaClass(fieldClass);
		ret.setPropertyName(propertyName);
		ret.setJoin(true);
		ret.setJoinKeyFieldName(joinKeyFieldName);
		ret.setJoinTableName(joinTableName);
		ret.setJoinFilterFieldName(joinFilterFieldName);
		ret.setTableName(tableName);
		ret.setColumnWidth(""+colWidth);
		ret.setJoinLocalKeyField(joinLocalKeyField);
		put(ret.getPropertyName(), ret);
		return ret;
	}

	public ColumnConfig addConfigEditValues(String fieldName, String propertyName, Class<?> fieldClass, String label, boolean filterEnabled, EditItemValue[] values, int colWidth, String align) {
		ColumnConfig ret = new ColumnConfig();
		ret.setFieldName(fieldName);
		ret.setFilterEnabled(filterEnabled);
		ret.setLabel(label);
		ret.setVisible(true);
		ret.setJavaClass(fieldClass);
		ret.setPropertyName(propertyName);
		ret.setJoin(true);
		ret.setEditItemValues(values);
		ret.setTableName(tableName);
		ret.setColumnWidth(""+colWidth);
		ret.setAlign(align);
		put(ret.getPropertyName(), ret);
		return ret;
	}

	public String getTableName() {
		return tableName;
	}

	@Override
	public ColumnConfig get(Object propertyName) {
		ColumnConfig x = super.get(propertyName);
		if (x==null)
			throw new IllegalStateException("Non trovato:"+tableName+"." + propertyName);
		return x;
	}


}
