package it.mengoni.persistence.filter;



public interface FilterControl {

	public enum BooleanValue {
		IGNORE("Ignora", ""), TRUE("Si", "true"), FALSE("No", "false");

		private String label;
		private String value;

		private BooleanValue(String label, String value){
			this.label = label;
			this.value = value;
		}

		public String getLabel(){
			return label;
		}

		public String getValue() {
			return value;
		}

	}

	public final BooleanValue[] BOOLEAN_VALUES = new BooleanValue[]{BooleanValue.IGNORE, BooleanValue.TRUE, BooleanValue.FALSE};

	public String getProperty() ;

	public Object getValue() ;

	public void setValue(Object value) ;

	public Operator getOperator();

	public void setOperator(Operator operator);

	public void setMaxFieldLength(int maxFieldLength);

	public int getMaxFieldLength();

	public boolean isEmpty();

	public String getFieldName();

	public boolean isJoin();

	public String getTableName();

	public String getJoinTableName();

	public String getJoinKeyFieldName();

	public String getJoinFilterFieldName();

	public String getJoinLocalKeyFieldName();

}
