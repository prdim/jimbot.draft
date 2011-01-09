package ru.jimbot.core.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import ru.jimbot.core.ExtendPointRegistry;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static ExtendPointRegistry reg;
	private ServiceRegistration registration;

	static BundleContext getContext() {
		return context;
	}
	
	static ExtendPointRegistry getRegistry() {
		return reg;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("Start core");
		reg = new ExtendPointRegistry();
		registration = bundleContext.registerService(ExtendPointRegistry.class.getName(), reg, null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		reg.unregAll();
		registration.unregister();
		System.out.println("Stop core");
	}

}
