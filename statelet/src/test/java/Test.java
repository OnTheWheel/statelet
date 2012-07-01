import org.statelet.core.WorkspaceManager;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		WorkspaceManager mgr = WorkspaceManager.getWorkspaceManager();
		ExampleEvent ev = new ExampleEvent();
		String id = mgr.postEvent(ev, null);
		mgr.postEvent(new AnotherEvent(), id);
		//Thread.currentThread().sleep(1000);
		
		System.out.print(id);
		return;

	}

}
