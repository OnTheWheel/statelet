package org.statelet.core;

public interface Workspace {
	public Object getProperty(String key); 
	public Object putProperty(String key, Object value);
	public String getWorkspaceId();
	
	public void onInitiate();
	public void onDestroy();
}
