package org.statelet.core.exception;

import java.lang.annotation.Annotation;

public class HandlerMethodNotFoundException extends StateletException {
	
	private Class event;
	private Class target;
	
	public HandlerMethodNotFoundException() {
		super();
	}
	
	public HandlerMethodNotFoundException(String message) {
		super(message);
	}
	
	public HandlerMethodNotFoundException(Class event, Class target) {
		super("Cannot find a handler for " + event.getCanonicalName() + " in " + target.getCanonicalName());
		this.event = event;
		this.target = target;
	}

	public Class getEvent() {
		return event;
	}

	public Class getTarget() {
		return target;
	}
}
