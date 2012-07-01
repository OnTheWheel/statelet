package org.statelet.core.exception;

import java.lang.annotation.Annotation;

public class AnnotationNotFoundException extends StateletException {
	
	private Class annotation;
	private Class target;
	
	public AnnotationNotFoundException() {
		super();
	}
	
	public AnnotationNotFoundException(String message) {
		super(message);
	}
	
	public AnnotationNotFoundException(Class annotation, Class target) {
		super("Cannot find " + annotation.getCanonicalName() + " in " + target.getCanonicalName());
		this.annotation = annotation;
		this.target = target;
	}

	public Class getAnnotation() {
		return annotation;
	}

	public Class getTarget() {
		return target;
	}
}
