package ru.jimbot.anekbot.dbadmin.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import ru.jimbot.anekbot.IAnekBotDB;
import ru.jimbot.anekbot.dbadmin.WebDbAdmin;
import ru.jimbot.core.MainProps;
import ru.jimbot.core.ServiceConfig;
import ru.jimbot.http.admin.ViewAddonRegistry;

public class ActivatorAnekDbadmin implements BundleActivator {

	private static BundleContext context;
	private ServiceTracker anekBotDBServiceTracker;
	private static IAnekBotDB db;
	private ServiceTracker httpAddonServiceTracker;

	static BundleContext getContext() {
		return context;
	}
	
	public static IAnekBotDB getAnekDB() {
		return db;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		ActivatorAnekDbadmin.context = bundleContext;
		anekBotDBServiceTracker = new ServiceTracker(context, IAnekBotDB.class.getName(), null) {

			/* (non-Javadoc)
			 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
			 */
			@Override
			public Object addingService(ServiceReference reference) {
				Object service = super.addingService(reference);
				db = (IAnekBotDB)service;
				return service;
			}
			
		};
		anekBotDBServiceTracker.open();
		httpAddonServiceTracker = new ServiceTracker(context, ViewAddonRegistry.class.getName(), null) {

			/* (non-Javadoc)
			 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
			 */
			@Override
			public Object addingService(ServiceReference reference) {
				Object service = super.addingService(reference);
				for(ServiceConfig i : MainProps.getInstance().getServices()) {
					if("AnekBot".equals(i.getServiceType())) {
						((ViewAddonRegistry)service).add(new WebDbAdmin(i.getServiceName()));
					}
				}
				
				return service;
			}
			
		};
		httpAddonServiceTracker.open();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		ActivatorAnekDbadmin.context = null;
		anekBotDBServiceTracker.close();
		httpAddonServiceTracker.close();
	}

}
