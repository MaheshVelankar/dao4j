package it.mengoni.exception;


public class SystemError extends Error {

	private final static long serialVersionUID = 1;

	public SystemError(String msg, String mod, String met, Throwable eccezione) {
		super(msg + " " + mod + " " + met, eccezione);
	}

	public SystemError(String message) {
		super(message);
	}

	public SystemError(Throwable cause) {
		super(cause);
	}

	public SystemError(String message, Throwable cause) {
		super(message, cause);
	}

}
