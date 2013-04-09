package it.mengoni.generator;

public abstract class AbstractGenerator {


	protected String concatPath(String ... values ) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if (buf.length()>0 && !buf.toString().endsWith("/")){
				buf.append("/");
			}
			buf.append(values[i]);
		}
		return buf.toString();
	}

	protected String concatPackage(String ... values ) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if (buf.length()>0 && !buf.toString().endsWith(".")){
				buf.append(".");
			}
			buf.append(values[i]);
		}
		return buf.toString();
	}


}
