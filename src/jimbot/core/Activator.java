package jimbot.core;

import java.io.File;
import java.io.PrintStream;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import ru.jimbot.Manager;
import ru.jimbot.util.Log;
import ru.jimbot.util.SystemErrLogger;

public class Activator implements BundleActivator {
	private ServicesConnector servicesConnector;
	private ProtocolConnector protocolConnector;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("!!!Start main");
		System.out.println(new File("./").getAbsolutePath());
		servicesConnector = new ServicesConnector(context);
		protocolConnector = new ProtocolConnector(context);
		System.setErr(new PrintStream(new SystemErrLogger(), true));
		try {
            Manager.getInstance().startAll();
        } catch (Exception ex) {
            Log.getDefault().error(ex.getMessage(), ex);
        }
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		servicesConnector.close();
		System.out.println("!!!Stop main");
	}

}
