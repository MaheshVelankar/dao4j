package it.mengoni.persistence.filter;

import it.mengoni.persistence.dao.Condition;
import it.mengoni.persistence.dao.ConditionSimple;
import it.mengoni.persistence.dao.ConditionValue;

import java.util.HashMap;
import java.util.Map;

public class ConditionBuilder {

	private static final ConditionBuilder instance = new ConditionBuilder();

	protected static class BuildData{
		String sql;
		String formatValue;
		boolean unary;
		boolean upCase;

		public String getSql() {
			return sql;
		}

		public boolean isUnary() {
			return unary;
		}

		public Object formatValue(Object value, int maxFieldLength){
			if (unary)
				return "";
			if (formatValue==null)
				return value;
			if (value==null)
				return null;
			String fval = upCase?value.toString().toUpperCase():value.toString();
			if (value instanceof String){
				int formatInc = formatValue.length()-3;
				if (maxFieldLength<formatInc){
					if (fval.length()>maxFieldLength)
						return fval.subSequence(0, maxFieldLength);
					return fval;
				}
//				if (maxFieldLength==2 && fval.length()==2)
//					return fval;
				if (maxFieldLength>0 &&(fval.length()+formatInc)>maxFieldLength){
					int e = maxFieldLength-formatInc;
					if (e>fval.length())
						e=fval.length();
					return formatValue.replaceAll("val", fval.substring(0, e));
				}
				return formatValue.replaceAll("val", fval);
			}
			return formatValue.replaceAll("val", fval);
		}

	}

	protected static final Map<Operator, BuildData> dataMap = new HashMap<Operator, ConditionBuilder.BuildData>();
	static{
		add(Operator.IGNORE, "  ", null, "", false, false);
		add(Operator.EQUAL,"=", null, "%s = ?", false, false);
		add(Operator.NOT_EQUAL,"<>", null, "%s <> ?", false, false);
		add(Operator.GREATER,">", null, "%s > ?", false, false);
		add(Operator.LESS,"<", null, "%s < ?", false, false);
		add(Operator.LIKE,"contiene", "%val%", "UPPER(%s) like ?", false, true);
		add(Operator.NOT_LIKE,"non contiene", "%val%", "UPPER(%s) not like ?", false, true);
		add(Operator.BEGINS, "inizia", "val%", "UPPER(%s) like ?", false, true);
		add(Operator.ENDS, "finisce", "%val", "UPPER(%s) like ?", false, true);
		add(Operator.NOT_BEGINS, "non inizia", "val%", "UPPER(%s) not like ?", false, true);
		add(Operator.NOT_ENDS, "non finisce", "%val", "UPPER(%s) not like ?", false, true);
		add(Operator.IS_NULL, "è vuoto", null, "%s is null", true, false);
		add(Operator.IS_NOT_NULL, "non è vuoto", null, "%s is not null", true, false);
	}

	private static void add(Operator operator, String displayLabel, String format, String sql, boolean unary, boolean upCase){
		BuildData item = new BuildData();
		item.formatValue = format;
		item.unary = unary;
		item.sql = sql;
		item.upCase = upCase;
		dataMap.put(operator, item);
	}

	public String concatena(String ... identifiers){
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < identifiers.length; i++) {
			if (i>0) ret.append(".");
			ret.append(identifiers[i]);
		}
		return ret.toString();
	}

	public Condition toCondition(Operator operator, String tableName, String fieldName, Object value, int maxFieldLength){
		if(value==null)
			return new ConditionSimple("true");
		BuildData data = dataMap.get(operator);
		Condition cond;
		if (data.isUnary()){
			ConditionSimple x  = new ConditionSimple(String.format(data.getSql(), concatena(tableName,fieldName)));
			cond = x;
		} else{
			ConditionValue x  = new ConditionValue(String.format(data.getSql(), concatena(tableName,fieldName)), data.formatValue(value, maxFieldLength));
			cond = x;
		}
		return cond;
	}

	public Condition toCondition(FilterControl filterControl){
		if(filterControl.getValue()==null)
			return new ConditionSimple("true");
		if (filterControl.isJoin()){
			return toJoinCondition(filterControl);
		}
		BuildData data = dataMap.get(filterControl.getOperator());
		Condition cond;
		if (data.isUnary()){
			ConditionSimple x  = new ConditionSimple(String.format(data.getSql(), concatena(filterControl.getTableName(),filterControl.getFieldName())));
			cond = x;
		} else{
			ConditionValue x  = new ConditionValue(String.format(data.getSql(), concatena(filterControl.getTableName(),filterControl.getFieldName())),
					data.formatValue(filterControl.getValue(), filterControl.getMaxFieldLength()));
			cond = x;
		}
		return cond;

	}

	public static ConditionBuilder getInstance() {
		return instance;
	}

	private final static String joinTemplate = "%s in (select %s from %s where %s)";
	// 1 campo del where esterno localField
	// 2 campo estratto dalla sql annidata foreignKey
	// 3 relazione esterna (tabella/vista) foreignTable
	// 4 espressione di filtro foreignField


	public Condition toJoinCondition(FilterControl filterControl) {
		if(filterControl.getValue()==null)
			return new ConditionSimple("true");
		Condition innerCond = toCondition(filterControl.getOperator(), filterControl.getJoinTableName(), filterControl.getJoinFilterFieldName(), filterControl.getValue(),
				filterControl.getMaxFieldLength());
		StringBuilder buf = new StringBuilder();
		String innerWhere = innerCond.getSqlExpr();
		buf.append(String.format(joinTemplate, concatena(filterControl.getTableName(),filterControl.getJoinLocalKeyFieldName()), concatena(filterControl.getJoinTableName(), filterControl.getJoinKeyFieldName()), filterControl.getJoinTableName(), innerWhere));
		BuildData data = dataMap.get(filterControl.getOperator());
		Condition ret = new ConditionValue(buf.toString(), data.formatValue(filterControl.getValue(), filterControl.getMaxFieldLength()));
		return ret;
	}

}
