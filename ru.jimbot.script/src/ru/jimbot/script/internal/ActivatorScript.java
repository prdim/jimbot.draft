package ru.jimbot.script.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import ru.jimbot.core.ExtendPointRegistry;

public class ActivatorScript implements BundleActivator {

	private static BundleContext context;
	private static ExtendPointRegistry reg;
	private ServiceTracker extendsServiceTracker;

	static BundleContext getContext() {
		return context;
	}
	
	public static ExtendPointRegistry getExtendPointRegistry() {
		return reg;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		ActivatorScript.context = bundleContext;
		extendsServiceTracker = new ServiceTracker(context, ExtendPointRegistry.class.getName(), null) {

			/* (non-Javadoc)
			 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
			 */
			@Override
			public Object addingService(ServiceReference reference) {
				Object service = super.addingService(reference);
				reg = (ExtendPointRegistry) service;
				return service;
			}
			
		};
		extendsServiceTracker.open();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		ActivatorScript.context = null;
		extendsServiceTracker.close();
	}

}
