import org.statelet.core.Workspace;
import org.statelet.core.annotation.HandlerBean;
import org.statelet.core.annotation.HandlerMethod;

@HandlerBean
public class AnotherHandler {
	@HandlerMethod(event=AnotherEvent.class)
	public void onAnotherEvent(AnotherEvent ev, Workspace workspace)
	{
		System.out.println("another event in handler ========");
	}
	
}
