package org.statelet.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseWorkspace implements Workspace {
	private Map<String, Object> properties = new HashMap<String, Object>();
	private String id;
	
	public BaseWorkspace() {
		id = UUID.randomUUID().toString();
	}
	
	public final Object getProperty(String key) {
		return properties.get(key);
	}

	public final Object putProperty(String key, Object value) {
		return properties.put(key, value);
	}

	public final String getWorkspaceId() {
		return id;
	}

	public void onInitiate() {
		// TODO Auto-generated method stub
		
	}

	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}
}
