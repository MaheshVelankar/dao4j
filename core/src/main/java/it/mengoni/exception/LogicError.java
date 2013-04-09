package it.mengoni.exception;


public class LogicError extends Error {

	private final static long serialVersionUID = 1;

	public LogicError(String msg, String mod, String met, Throwable eccezione) {
		super(msg + " " + mod + " " + met, eccezione);
	}

	public LogicError(String message) {
		super(message);
	}

	public LogicError(Throwable cause) {
		super(cause);
	}

	public LogicError(String message, Throwable cause) {
		super(message, cause);
	}

}
