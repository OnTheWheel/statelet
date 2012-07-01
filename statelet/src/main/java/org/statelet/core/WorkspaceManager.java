package org.statelet.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLEngineResult.HandshakeStatus;

import org.statelet.core.annotation.HandlerBean;
import org.statelet.core.annotation.HandlerBeanScope;
import org.statelet.core.annotation.HandlerMethod;
import org.statelet.core.annotation.Initiator;
import org.statelet.core.annotation.WorkspaceBean;
import org.statelet.core.exception.AnnotationNotFoundException;
import org.statelet.core.exception.HandlerMethodNotFoundException;
import org.statelet.core.exception.InvalidHandlerMethodException;
import org.statelet.core.exception.StateletException;
import org.statelet.core.exception.TypeMismatchException;
import org.statelet.core.exception.WorkspaceInitiationException;
import org.statelet.core.exception.WorkspaceNotFoundException;
import org.statelet.core.invoker.HandlerInvoker;
import org.statelet.core.pool.TaskThreadPool;

@SuppressWarnings("rawtypes")
public class WorkspaceManager {
	private static WorkspaceManager instance = null;
	
	private TaskThreadPool pool;
	private ConcurrentHashMap<String, BaseWorkspace> workspaces;
	private ConcurrentHashMap<Class, CallMap > callMaps;
	private ConcurrentHashMap<Class, Object > envHandlers;
	private ConcurrentHashMap<String, HandlerInvoker > workspaceInvoker;
	
	public static synchronized WorkspaceManager getWorkspaceManager() throws Exception{
		if(instance == null)
			return ( instance = new WorkspaceManager() );
		else return instance;
	}
	
	private WorkspaceManager() throws Exception {
		pool = new TaskThreadPool();
		workspaces = new ConcurrentHashMap<String, BaseWorkspace>();
		callMaps = new ConcurrentHashMap<Class,CallMap>();
		envHandlers = new ConcurrentHashMap<Class, Object>();
	}
	
	public String postEvent(Object event, String workspaceId) throws Exception {
		if(workspaceId == null)
			return postInitiator(event);
		else pool.push(new PostEventTask(event, workspaceId, this));
		
		return workspaceId;
	}
	
	public void processEvent(Object event, String workspaceId) throws Exception {
		BaseWorkspace workspace = workspaces.get(workspaceId);
		if(workspace != null) {
			CallMap callMap = callMaps.get(workspace.getClass());
			if(callMap == null)
				throw new HandlerMethodNotFoundException(event.getClass(), workspace.getClass());
			callMap.callEventHandler(event, workspace);
		} else throw new WorkspaceNotFoundException(workspaceId);
	}
	
	public String postInitiator(Object initiator) throws StateletException
	{
		Class klass = initiator.getClass();
		
		if(klass.isAnnotationPresent(Initiator.class))
		{
			Initiator init = (Initiator) klass.getAnnotation(Initiator.class);
			if(init.target().isAnnotationPresent(WorkspaceBean.class))
			{
				WorkspaceBean spec = (WorkspaceBean) init.target().getAnnotation(WorkspaceBean.class);
				Class workspaceClass = init.target();
				
				if(!callMaps.containsKey(workspaceClass))
					callMaps.put(init.target(), CallMap.createCallMap(spec, workspaceClass));
				
				if(BaseWorkspace.class.isAssignableFrom(workspaceClass))
				{
					BaseWorkspace workspace = null;
					try {
						workspace = (BaseWorkspace) workspaceClass.newInstance();
					} catch (Exception e) { 
						throw new WorkspaceInitiationException(workspaceClass, e);
					}
					
					String workspaceId = workspace.getWorkspaceId();
					
					try {
						workspace.onInitiate();
					} catch (Throwable e) {
						throw new WorkspaceInitiationException(workspaceClass, e);
					}
					
					workspaces.put(workspaceId, workspace);
					pool.push(new PostEventTask(initiator, workspaceId, this));
					return workspaceId;
				} else throw new TypeMismatchException(BaseWorkspace.class, init.target());
			} else throw new AnnotationNotFoundException(WorkspaceBean.class, init.target());
		} else throw new AnnotationNotFoundException(Initiator.class, initiator.getClass());
	}
	
	/*private void scanEventHandlers(WorkspaceBean spec, Class workspace){
		Class[] handlers = spec.handlers();
		Map<Class, Method> callMap = new HashMap<Class, Method>();
		Map<Class, Object> workspaceHandlers = new HashMap<Class, Object>();
		
		scanHandlerMethods(workspace, workspace, callMap);
		
		for(Class handler : handlers) { 
			if(handler.isAnnotationPresent(HandlerBean.class)) {
				HandlerBeanScope scope = ((HandlerBean)handler.getAnnotation(HandlerBean.class)).scope();
				
				switch(scope) {
				case ENVIRONMENT:
					if(envHandlers.get(handler) == null)
						envHandlers.put(handler, handler.newInstance());
					
					break;
				case WORKSPACE:
					break;
				case EVENT:
					break;
				}
				
				scanHandlerMethods(handler, workspace, callMap);
			}  else throw new AnnotationNotFoundException(HandlerBean.class, handler);
		} 
		
	}
	
	private static void scanEnvHandlerMethods(Class handler, Class workspace,
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
	}*/
}
