import org.statelet.core.Workspace;
import org.statelet.core.annotation.HandlerBean;
import org.statelet.core.annotation.HandlerMethod;

@HandlerBean
public class ExampleHandler
{
	@HandlerMethod(event=ExampleEvent.class)
	public void onEvent(ExampleEvent ev, Workspace workspace)
	{
		System.out.println("event ========");
	}
	
	
}
