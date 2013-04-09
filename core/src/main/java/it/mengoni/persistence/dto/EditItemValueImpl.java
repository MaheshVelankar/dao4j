package it.mengoni.persistence.dto;

import it.mengoni.db.EditItemValue;

import java.io.Serializable;

public class EditItemValueImpl implements EditItemValue, Serializable {


	private static final long serialVersionUID = 1L;

	private Object databaseValue;

    private String displayValue;

	public EditItemValueImpl(Object databaseValue, String displayValue) {
		super();
		this.databaseValue = databaseValue;
		this.displayValue = displayValue;
	}

	public Object getDatabaseValue() {
		return databaseValue;
	}

	public void setDatabaseValue(Object databaseValue) {
		this.databaseValue = databaseValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}


}
