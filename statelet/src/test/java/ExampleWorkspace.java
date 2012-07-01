import org.statelet.core.BaseWorkspace;
import org.statelet.core.Workspace;
import org.statelet.core.annotation.HandlerMethod;
import org.statelet.core.annotation.WorkspaceBean;

@WorkspaceBean(handlers= {ExampleHandler.class, AnotherHandler.class})
public class ExampleWorkspace extends BaseWorkspace {
	public ExampleWorkspace() {
	}	
	
	@HandlerMethod(event=AnotherEvent.class)
	public void onAnotherEvent(AnotherEvent ev)
	{
		System.out.println("another event ========");
	}
}
