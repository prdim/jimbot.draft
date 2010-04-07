package jimbot.protocol.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import ru.jimbot.core.api.IProtocolManager;
import ru.jimbot.protocol.test.TestProtocolManager;

public class Activator implements BundleActivator {
	public ServiceRegistration registration;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("!!!Start TestProtocol");
        IProtocolManager im = new TestProtocolManager();
        registration = context.registerService(IProtocolManager.class.getName(), im, null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
        System.out.println("!!!Stop icq");
	}

}
