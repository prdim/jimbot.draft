package jimbot.testbot;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import ru.jimbot.core.api.IServiceBuilder;
import ru.jimbot.testbot.CommandConnector;
import ru.jimbot.testbot.TestBotServiceBuilder;

public class Activator implements BundleActivator {
	public ServiceRegistration registration;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("!!!Start testbot");
		CommandConnector con = new CommandConnector(context);
		IServiceBuilder s = new TestBotServiceBuilder(con);
		registration = context.registerService(IServiceBuilder.class.getName(), s, null);
		System.out.println("register " + s.getClass().getName());
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
		System.out.println("!!!Stop testbot");
	}

}
