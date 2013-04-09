package it.mengoni.persistence.dao;

public interface CharsetConverter {

	public static final String UTF_8 = "UTF-8";
	public static final String UTF_16 = "UTF-16";
	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String WINDOWS_1250 = "windows-1250";

	public String convertFromDb(byte[] value);

	public byte[] convertFromApplication(String value);

}
