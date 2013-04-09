package it.mengoni.generator.gui.forms;

public class RowBean {
	private String key;
	private String value;

	public RowBean(String key, String value) {
		super();
		this.key = key.trim();
		this.value = value.trim();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}