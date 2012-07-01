package org.statelet.core.invoker;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.statelet.core.Workspace;
import org.statelet.core.exception.InvalidHandlerMethodException;
import org.statelet.core.exception.StateletException;

public class WorkspaceScopeInvoker implements HandlerInvoker {
	
	private Map<Class, Entry<Object, Method>> callMap = new HashMap<Class, Entry<Object, Method>>();
	
	public void invoke(Object event, Workspace workspace)
			throws StateletException {
		Entry<Object, Method> caller = callMap.get(event.getClass());
		
		try {
			if(workspace == caller.getKey())
				caller.getValue().invoke(workspace, event);
			else caller.getValue().invoke(caller.getKey(), event, workspace);
		} catch (Throwable e) {
			throw new InvalidHandlerMethodException(caller.getValue(), e);
		}
	}

}
