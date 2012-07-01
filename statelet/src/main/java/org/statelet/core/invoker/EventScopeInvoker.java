package org.statelet.core.invoker;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.statelet.core.Workspace;
import org.statelet.core.exception.HandlerInitiationException;
import org.statelet.core.exception.InvalidHandlerMethodException;
import org.statelet.core.exception.StateletException;

public class EventScopeInvoker implements HandlerInvoker {
	Map<Class, Method> callMap = new HashMap<Class, Method>();
	
	public void invoke(Object event, Workspace workspace)
			throws StateletException {
		Method method = callMap.get(event.getClass());
		Object handler = null;
		try {
			handler = method.getDeclaringClass().newInstance();
		} catch (Throwable e) {
			throw new HandlerInitiationException(handler.getClass());
		}
		
		try {
			method.invoke(handler, event, workspace);
		} catch (Throwable e) {
			throw new InvalidHandlerMethodException(method, e);
		}
	}

}
