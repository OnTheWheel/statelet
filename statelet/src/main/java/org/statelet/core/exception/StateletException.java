package org.statelet.core.exception;

public class StateletException extends RuntimeException {
	public StateletException() {
		super();
	}
	
	public StateletException(String message) {
		super(message);
	}
	
	public StateletException(Throwable cause) {
		super(cause);
	}
	
	public StateletException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
