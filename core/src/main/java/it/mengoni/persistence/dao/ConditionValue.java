package it.mengoni.persistence.dao;

import java.util.ArrayList;
import java.util.List;


public class ConditionValue implements ConditionParam{

	private static final long serialVersionUID = 1L;
	private String sqlExpr;
	private List<Object> values = new ArrayList<Object>();

	public ConditionValue(String sqlExpr, Object ... values) {
		super();
		this.sqlExpr = sqlExpr;
		for (int n=0; n<values.length; n++) {
			this.values.add(values[n]);
		}
	}

	public String getSqlExpr() {
		return sqlExpr;
	}

	public void setSqlExpr(String sqlExpr) {
		this.sqlExpr = sqlExpr;
	}

	public Object getValue(int index) {
		return values.get(index);
	}

	public void setValue(int index, Object value) {
		this.values.set(index, value);
	}

	@Override
	public String toString() {
		return "ConditionValue [sqlExpr=" + sqlExpr + ", value=" + values + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sqlExpr == null) ? 0 : sqlExpr.hashCode());
		//result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConditionValue other = (ConditionValue) obj;
		if (sqlExpr == null) {
			if (other.sqlExpr != null)
				return false;
		} else if (!sqlExpr.equals(other.sqlExpr))
			return false;
//		if (value == null) {
//			if (other.value != null)
//				return false;
//		} else if (!value.equals(other.value))
//			return false;
		return true;
	}

	public int getValueCount() {
		return values.size();
	}

	public boolean add(Object value) {
		return values.add(value);
	}

	public int indexOf(Object value) {
		return values.indexOf(value);
	}

}
