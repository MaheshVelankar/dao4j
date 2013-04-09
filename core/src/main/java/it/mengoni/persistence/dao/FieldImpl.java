package it.mengoni.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import it.mengoni.db.EditItemValue;
import it.mengoni.exception.SystemError;
import it.mengoni.persistence.dto.PersistentObject;

public abstract class FieldImpl<T extends PersistentObject, V>  extends AbstractField<T, V> {

	private final boolean key;
	private final ValueReader reader;

	public FieldImpl(String name, boolean key, boolean nullable, int length, int sqlType) {
		super(name, nullable, length, sqlType);
		this.key = key;
		this.reader = initValueReader();
	}

	public FieldImpl(String name, boolean key, boolean nullable, int length, int sqlType, EditItemValue[] editItemValues) {
		super(name, nullable, length, sqlType, editItemValues);
		this.key = key;
		this.reader = initValueReader();
	}

	public FieldImpl(String name, String propertyName, boolean nullable, int length, int sqlType) {
		super(name, propertyName, nullable, length, sqlType);
		this.key = false;
		this.reader = initValueReader();
	}

	public FieldImpl(String name, String propertyName, boolean nullable, int length, int sqlType, EditItemValue[] editItemValues) {
		super(name, propertyName, nullable, length, sqlType, editItemValues);
		this.key = false;
		this.reader = initValueReader();
	}

	public boolean isKey() {
		return key;
	}

	public boolean isReadOnly(){
		return false;
	}

	interface ValueReader{
		Object readValue (ResultSet rs) throws SQLException;
	}


	protected ValueReader initValueReader() {
		switch (getSqlType()) {
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.NCHAR:
		case Types.NVARCHAR:
		case Types.LONGVARCHAR:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return getStringValue(rs);
				}
			};
		case Types.NUMERIC:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return rs.getBigDecimal(getName());
				}
			};
		case Types.DECIMAL:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return rs.getBigDecimal(getName());
				}
			};
		case Types.BIT:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return rs.getBoolean(getName());
				}
			};
		case Types.TINYINT:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return rs.getByte(getName());
				}
			};
		case Types.SMALLINT:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return getShortValue(rs);
				}
			};
		case Types.INTEGER:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return getIntegerValue(rs);
				}
			};
		case Types.BIGINT:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return getIntegerValue(rs);
				}
			};
		case Types.REAL:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return rs.getFloat(getName());
				}
			};
		case Types.FLOAT:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return rs.getDouble(getName());
				}
			};
		case Types.DOUBLE:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return rs.getDouble(getName());
				}
			};
		case Types.BINARY:
		case Types.VARBINARY:
		case Types.LONGVARBINARY:
		case Types.BLOB:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return rs.getBytes(getName());
				}
			};
		case Types.TIMESTAMP:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return rs.getTimestamp(getName());
				}
			};
		case Types.DATE:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return rs.getDate(getName());
				}
			};
		case Types.TIME:
			return  new ValueReader() {
				@Override
				public Object readValue(ResultSet rs)
						throws SQLException {
					return rs.getTime(getName());
				}
			};
		default:
			throw new SystemError("Error non gestito sqlType=" + getSqlType() + " " + getName());
		}
	}

	@Override
	public void readValueFrom(ResultSet rs, T bean)	 {
		Object value = null;
		try {
			value = reader.readValue(rs);
		} catch (SQLException e) {
			throw new SystemError("Error: sqlType=" + getSqlType() + " " + getName(), e);
		}
		try{
			@SuppressWarnings("unchecked")
			V v = (V)value;
			setValue(v, bean);
		} catch (Exception e) {
			throw new SystemError("Error: sqlType=" + getSqlType() + " " + getName(), e);
		}
	}

}
