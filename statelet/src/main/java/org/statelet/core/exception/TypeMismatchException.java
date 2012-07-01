package org.statelet.core.exception;

import java.lang.annotation.Annotation;

public class TypeMismatchException extends StateletException {
	
	private Class toBe;
	private Class asIs;
	
	public TypeMismatchException() {
		super();
	}
	
	public TypeMismatchException(String message) {
		super(message);
	}
	
	public TypeMismatchException(Class toBe, Class asIs) {
		super(asIs.getCanonicalName() + " is not type of " + toBe.getCanonicalName());
		this.toBe = toBe;
		this.asIs = asIs;
	}

	public Class getToBe() {
		return toBe;
	}

	public Class getAsIs() {
		return asIs;
	}
}
