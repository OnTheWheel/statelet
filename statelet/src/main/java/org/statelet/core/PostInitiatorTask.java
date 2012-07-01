package org.statelet.core;

import org.statelet.core.pool.BaseTask;

public class PostInitiatorTask extends BaseTask {
	private Object event;
	private WorkspaceManager manager;
	private String workspaceId;
	
	
	
	public PostInitiatorTask(Object event, WorkspaceManager manager,
			String workspaceId) {
		super();
		this.event = event;
		this.manager = manager;
		this.workspaceId = workspaceId;
	}

	public void execute() throws Exception {
		manager.processEvent(event, workspaceId);
	}

	public String getDetail() {
		// TODO Auto-generated method stub
		return null;
	}

}
