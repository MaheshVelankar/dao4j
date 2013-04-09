package it.mengoni.persistence.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SqlWhere {

	private StringBuilder sql = new StringBuilder();
	private List<Object> params = new ArrayList<Object>();

	public SqlWhere(Condition ... conditions) {
		super();
		addConditions(conditions);
	}

	public SqlWhere(Collection<Condition> conditions) {
		for (Condition condition : conditions) {
			addCondition(condition);
		}
	}

	public void addCondition(Condition condition){
		if (condition instanceof ConditionParam){
			ConditionParam cp =(ConditionParam)condition;
			for (int n=0; n<cp.getValueCount(); n++)
			params.add(cp.getValue(n));
		}
		if (sql.length()>0)
			sql.append(" and ");
		sql.append(condition.getSqlExpr());
	}

	public String getWhere(){
		if (sql.length()>0)
			return new StringBuilder(" where ").append(sql.toString()).toString();
		return "";
	}

	public List<Object> getParamsList(){
		return params;
	}

	public void addConditions(Condition ... conditions){
		if (conditions!=null)
			for (int i = 0; i < conditions.length; i++) {
				addCondition(conditions[i]);
			}
	}

	public Object[] getParamsArray(){
		Object[] ret = params.toArray(new Object[params.size()]);
		return ret;
	}

}
