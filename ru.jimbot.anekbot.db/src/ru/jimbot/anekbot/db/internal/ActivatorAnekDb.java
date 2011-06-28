package ru.jimbot.anekbot.db.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import ru.jimbot.anekbot.AnekDBExtendPoint;
import ru.jimbot.anekbot.IAnekBotDB;
import ru.jimbot.anekbot.db.AnekDB;

public class ActivatorAnekDb implements BundleActivator {

	private static BundleContext context;
//	private ServiceTracker anekBotServiceTracker;
	private ServiceRegistration registration;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		ActivatorAnekDb.context = bundleContext;
//		anekBotServiceTracker = new ServiceTracker(context, AnekDBExtendPoint.class.getName(), null) {
//
//			/* (non-Javadoc)
//			 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
//			 */
//			@Override
//			public Object addingService(ServiceReference reference) {
//				Object service = super.addingService(reference);
//				((AnekDBExtendPoint)service).regDB(new AnekDB(""));
//				return service;
//			}
//			
//		};
//		anekBotServiceTracker.open();
		registration = context.registerService(IAnekBotDB.class.getName(), new AnekDB(""), null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		ActivatorAnekDb.context = null;
		registration.unregister();
	}

}
