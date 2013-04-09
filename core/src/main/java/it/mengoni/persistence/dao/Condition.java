package it.mengoni.persistence.dao;

import java.io.Serializable;

public interface Condition extends Serializable{


	public String getSqlExpr();

	public void setSqlExpr(String sqlExpr);

}
