package org.statelet.core.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class InvalidHandlerMethodException extends StateletException {
	
	private Method method;
	
	public InvalidHandlerMethodException() {
		super();
	}
	
	public InvalidHandlerMethodException(String message) {
		super(message);
	}
	
	public InvalidHandlerMethodException(Method method) {
		super("Invalid handler for " + method.toGenericString() + " in " + method.getDeclaringClass().getCanonicalName() + " ==> check parameters");
		this.method = method;
	}
	
	public InvalidHandlerMethodException(Method method, Throwable cause) {
		super("Invalid handler for " + method.toGenericString() + " in " + method.getDeclaringClass().getCanonicalName() + " ==> check parameters", cause);
		this.method = method;
	}
}
