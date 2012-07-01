package org.statelet.core.exception;

public class WorkspaceInitiationException extends StateletException {
	private Class workspace;
	
	public WorkspaceInitiationException() {
		super();
	}
	
	public WorkspaceInitiationException(Class workspace) {
		super("Cannot instantiate workspace : " + workspace.getCanonicalName());
		this.workspace = workspace;
	}
	
	public WorkspaceInitiationException(Class workspace, Throwable cause) {
		super("Cannot instantiate workspace : " + workspace.getCanonicalName(), cause);
		this.workspace = workspace;
	}

	public Class getWorkspaceClass() {
		return workspace;
	}
}
