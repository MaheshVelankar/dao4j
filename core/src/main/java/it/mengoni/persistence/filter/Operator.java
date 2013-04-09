package it.mengoni.persistence.filter;


public enum Operator {

	IGNORE("  "),
	EQUAL("="),
	NOT_EQUAL("<>"), // null, "%s <> ?"), // false),
	GREATER(">"), // null, "%s > ?"), // false),
	LESS("<"), // null, "%s < ?"), // false),
	LIKE("contiene"), // "%val%"), // "UPPER(%s) like ?"), // false),
	NOT_LIKE("non contiene"), // "%val%"), // "UPPER(%s) not like ?"), // false),
	BEGINS("inizia"), // "val%"), // "UPPER(%s) like ?"), // false),
	ENDS("finisce"), // "%val"), // "UPPER(%s) like ?"), // false),
	NOT_BEGINS("non inizia"), // "val%"), // "UPPER(%s) not like ?"), // false),
	NOT_ENDS("non finisce"), // "%val"), // "UPPER(%s) not like ?"), // false),
	IS_NULL("è vuoto"), // null, "%s is null"), // true),
	IS_NOT_NULL("non è vuoto"); // null, "%s is not null", true);

	private String displayLabel;

	private Operator(String displayLabel){
		this.displayLabel = displayLabel;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	@Override
	public String toString() {
		return displayLabel;
	}

}

