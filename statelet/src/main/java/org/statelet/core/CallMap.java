package org.statelet.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.statelet.core.annotation.HandlerBean;
import org.statelet.core.annotation.HandlerMethod;
import org.statelet.core.annotation.WorkspaceBean;
import org.statelet.core.exception.AnnotationNotFoundException;
import org.statelet.core.exception.InvalidHandlerMethodException;
import org.statelet.core.exception.StateletException;

public class CallMap {
	public static CallMap createCallMap(WorkspaceBean spec, Class workspace) throws StateletException {
		Class[] handlers = spec.handlers();
		Map<Class, Method> callMap = new HashMap<Class, Method>();
		
		scanHandlerMethods(workspace, workspace, callMap);
		
		for(Class handler : handlers) { 
			scanHandlerMethods(handler, workspace, callMap);
		}
		
		return new CallMap(callMap);
	}
	
	private static void scanHandlerMethods(Class handler, Class workspace,
			Map<Class, Method> callMap) throws StateletException {
		if(handler == workspace || handler.isAnnotationPresent(HandlerBean.class)) {
			for (Method method : handler.getDeclaredMethods()) {
				if (method.isAnnotationPresent(HandlerMethod.class)) {
					HandlerMethod handlerMethod = method.getAnnotation(HandlerMethod.class);
					Class[] types = method.getParameterTypes();
	
					if (handler == workspace && types.length == 1
							&& types[0].isAssignableFrom(handlerMethod.event()))
						callMap.put(handlerMethod.event(), method);
					else if (handler != workspace && types.length == 2
							&& types[0].isAssignableFrom(handlerMethod.event())
							&& types[1].isAssignableFrom(workspace))
						callMap.put(handlerMethod.event(), method);
					else
						throw new InvalidHandlerMethodException(method);
				}
			}
		} else throw new AnnotationNotFoundException(HandlerBean.class, handler);
	}
	
	private Map<Class, Method> callMap;

	private CallMap(Map<Class, Method> callMap) {
		super();
		this.callMap = callMap;
	}
	
	public void callEventHandler(Object event, Workspace workspace) throws Exception {
		Method method = callMap.get(event.getClass());
		boolean workspaceMethod = method.getDeclaringClass() == workspace.getClass();
		synchronized (workspace) {
			if(workspaceMethod)
				method.invoke(workspace, event);
			else method.invoke(method.getDeclaringClass().newInstance(), event, workspace);
		}
	}
}
