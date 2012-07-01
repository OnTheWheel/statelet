package org.statelet.core.invoker;

import org.statelet.core.Workspace;
import org.statelet.core.exception.StateletException;

public interface HandlerInvoker {
	public void invoke(Object event, Workspace workspace) throws StateletException;
}
