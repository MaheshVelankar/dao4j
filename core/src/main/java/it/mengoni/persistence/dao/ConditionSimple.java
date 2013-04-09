package it.mengoni.persistence.dao;


public class ConditionSimple implements Condition{

	private static final long serialVersionUID = 1L;
	private String sqlExpr;

	public ConditionSimple(String sqlExpr) {
		super();
		this.sqlExpr = sqlExpr;
	}

	public String getSqlExpr() {
		return sqlExpr;
	}

	public void setSqlExpr(String sqlExpr) {
		this.sqlExpr = sqlExpr;
	}

	@Override
	public String toString() {
		return "ConditionSimple [sqlExpr=" + sqlExpr + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sqlExpr == null) ? 0 : sqlExpr.hashCode());
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
		ConditionSimple other = (ConditionSimple) obj;
		if (sqlExpr == null) {
			if (other.sqlExpr != null)
				return false;
		} else if (!sqlExpr.equals(other.sqlExpr))
			return false;
		return true;
	}

}