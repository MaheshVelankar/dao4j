package it.mengoni.persistence.dao;

import it.mengoni.exception.LogicError;

import java.io.UnsupportedEncodingException;

public class BaseCharsetConverter implements CharsetConverter{

	private final String databaseCS;
	private final String applicationCS;

	public BaseCharsetConverter(String databaseCS, String applicationCS) {
		super();
		this.databaseCS = databaseCS;
		this.applicationCS = applicationCS;
	}

	@Override
	public String convertFromDb(byte[] value) {
		if (value==null)
			return null;
		try {
			byte[] converted = new String(value, databaseCS).getBytes(applicationCS);
			return new String(converted, applicationCS);
		} catch (java.io.UnsupportedEncodingException e) {
			return "!!!"+value;
		}
	}

	@Override
	public byte[] convertFromApplication(String value) {
		try {
			return new String(value.getBytes(applicationCS)).getBytes(databaseCS);
		} catch (UnsupportedEncodingException e) {
			throw new LogicError("Error in conversione valore:" + value, e);
		}
	}

}