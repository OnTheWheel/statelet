package org.statelet.core;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.statelet.core.pool.BaseTask;

public class PostEventTask extends BaseTask 
{
	private Object event;
	private WorkspaceManager manager;
	private String workspaceId;
	
	protected PostEventTask(Object event, String workspaceId, WorkspaceManager manager) {
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
