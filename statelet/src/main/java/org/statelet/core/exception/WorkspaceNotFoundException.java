package org.statelet.core.exception;

public class WorkspaceNotFoundException extends StateletException {
	private String workspaceId;
	
	public WorkspaceNotFoundException() {
		super();
	}
	
	public WorkspaceNotFoundException(String workspaceId) {
		super("Cannot find workspace : " + workspaceId + " ==> doesn't exist or already destructed");
		this.workspaceId = workspaceId;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}
}
