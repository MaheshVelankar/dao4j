package it.mengoni.generator;

import java.io.Serializable;

public class FieldData implements Serializable {

	private static final long serialVersionUID = 1L;
	private String javaclass;
	private String getter;

	public FieldData(String javaclass, String getter) {
		super();
		this.javaclass = javaclass;
		this.getter = getter;
	}

	public String getJavaclass() {
		return javaclass;
	}

	public void setJavaclass(String javaclass) {
		this.javaclass = javaclass;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}


}