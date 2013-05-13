package it.mengoni.persistence.exception;


public class LogicException extends Exception {

	public final static long serialVersionUID = 1;

	public LogicException() {
		super();
	}

	public LogicException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogicException(String message) {
		super(message);
	}

	public LogicException(Throwable cause) {
		super(cause);
	}




}