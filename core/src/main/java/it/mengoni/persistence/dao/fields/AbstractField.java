package it.mengoni.persistence.dao.fields;

import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.db.EditItemValue;
import it.mengoni.persistence.dto.PersistentObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;

public abstract class AbstractField<T extends PersistentObject, V> implements Field<T, V> {

	@Override
	public String toString() {
		return "AbstractField [name=" + name
				+ ", length=" + length + ", nullable=" + nullable
				+ ", editItemValues=" + Arrays.toString(editItemValues)
				+ ", euro=" + euro + ", editRoles=" + editRoles
				+ ", viewRoles=" + viewRoles + "]";
	}

	protected CharsetConverter charsetConverter;
	protected EditItemValue[] editItemValues;
	protected Set<String> editRoles;
	protected boolean euro = false;
	private final int length;
	private final String name;
	private final String propertyName;
	private final boolean nullable;
	protected Set<String> viewRoles;


	public static String toCamel(String value, boolean firstUp) {
		StringBuilder ret = new StringBuilder();
		boolean toUpper = firstUp;
		for (int i = 0; value != null && i < value.length(); i++) {
			String c = "" + value.charAt(i);
			if ("_".equals(c) || " ".equals(c)) {
				toUpper = true;
				continue;
			} else {
				if (toUpper)
					ret.append(c.toUpperCase());
				else
					ret.append(c.toLowerCase());
				toUpper = false;
			}
		}
		return ret.toString();
	}

	public AbstractField(String name, boolean nullable, int length) {
		super();
		propertyName = toCamel(name,false);
		this.name = name;
		this.nullable = nullable;
		this.length = length;
	}

	public AbstractField(String name, boolean nullable, int length, EditItemValue[] editItemValues) {
		super();
		propertyName = toCamel(name,false);
		this.name = name;
		this.nullable = nullable;
		this.length = length;
		this.editItemValues = editItemValues;
	}

	public AbstractField(String name, String propertyName, boolean nullable, int length) {
		super();
		this.propertyName = propertyName;
		this.name = name;
		this.nullable = nullable;
		this.length = length;
	}

	public AbstractField(String name,  String propertyName, boolean nullable, int length, EditItemValue[] editItemValues) {
		super();
		this.name = name;
		this.propertyName = propertyName;
		this.nullable = nullable;
		this.length = length;
//		this.sqlType = sqlType;
		this.editItemValues = editItemValues;
	}

//	@Override
//	public void checkValue(T bean) {
//		checkValue(getValue(bean));
//	}

	protected void checkValue(V value) {
		if (value == null && !isNullable())
			throw new IllegalArgumentException(name + " cannot be null");
		if (editItemValues!=null && editItemValues.length>0){
			if(!contains(value))
				throw new IllegalArgumentException(name + " cannot be set with value:"+value);
		}
	}

	protected boolean contains(Object value) {
		if (value==null)
			return false;
		for (int i = 0; i < editItemValues.length; i++) {
			if (value.equals(editItemValues[i].getDatabaseValue()))
				return true;
		}
		return false;
	}

	public CharsetConverter getCharsetConverter() {
		return charsetConverter;
	}

	public EditItemValue[] getEditItemValues() {
		return editItemValues;
	}

	public Set<String> getEditRoles() {
		return editRoles;
	}

	public int getLength() {
		return length;
	}

	public String getName() {
		return name;
	}

	public Set<String> getViewRoles() {
		return viewRoles;
	}

	public boolean isEuro(){
		return euro;
	}

	public boolean isNullable() {
		return nullable;
	}

	public boolean isReadOnly(){
		return false;
	}

	public void setCharsetConverter(CharsetConverter charsetConverter) {
		this.charsetConverter = charsetConverter;
	}

	public void setEditRoles(Set<String> editRoles) {
		this.editRoles = editRoles;
	}

	public abstract void setParam(PreparedStatement stm, int index, T bean) throws SQLException;


//	private int calcType() {
//		Integer t = sqlTypeMap.get(getValueClass());
//		if (t==null) return Types.VARCHAR;
//		return t.intValue();
//	}
//
//	private static Map<Class<?>, Integer> sqlTypeMap = new HashMap<Class<?>, Integer>();
//
//	private static void addMap(int type, Class<?> class1) {
//		sqlTypeMap.put(class1, type);
//	}
//
//	static {
//		addMap(Types.BIGINT, Long.class);
//		addMap(Types.BINARY, byte[].class);
//		addMap(Types.BOOLEAN, Boolean.class);
//		addMap(Types.DATE, java.sql.Date.class);
//		addMap(Types.DECIMAL, BigDecimal.class);
//		addMap(Types.DOUBLE, Double.class);
//		addMap(Types.INTEGER, Integer.class);
//		addMap(Types.NCLOB, Object.class);
//		addMap(Types.NUMERIC, java.math.BigDecimal.class);
//		addMap(Types.REAL, Float.class);
//		addMap(Types.REF, Object.class);
//		addMap(Types.TIME, java.sql.Time.class);
//		addMap(Types.TIMESTAMP, java.sql.Timestamp.class);
//		addMap(Types.TINYINT, Byte.class);
//		addMap(Types.VARCHAR, String.class);
//		addMap(Types.BLOB, byte[].class);
//	}


	public void setViewRoles(Set<String> viewRoles) {
		this.viewRoles = viewRoles;
	}


	protected String identifierQuote(String value) {
		if (value!=null){
			value = value.trim();
			if (value.contains("-") || value.contains(" "))
				value = new StringBuilder("\"").append(value).append("\"").toString();
			return value;
		}
		return null;
	}

	public String getPropertyName() {
		return propertyName;
	}

}