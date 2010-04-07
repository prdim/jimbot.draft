package jimbot.protocol;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import ru.jimbot.core.api.IProtocolManager;
import ru.jimbot.core.api.IServiceBuilder;
import ru.jimbot.protocol.IcqProtocolManager;

/**
 * @author Prolubnikov Dmitry
 */
public class Activator implements BundleActivator {
	public ServiceRegistration registration;
	
    public void start(BundleContext context) throws Exception {
    	System.out.println("!!!Start icq");
        IProtocolManager im = new IcqProtocolManager();
        registration = context.registerService(IProtocolManager.class.getName(), im, null);
    }

    public void stop(BundleContext context) throws Exception {
        registration.unregister();
        System.out.println("!!!Stop icq");
    }
}
