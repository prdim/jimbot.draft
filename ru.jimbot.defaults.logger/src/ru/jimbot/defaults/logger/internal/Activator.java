package ru.jimbot.defaults.logger.internal;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

//import ru.jimbot.core.ExtendPointRegistry;
import ru.jimbot.core.*;
import ru.jimbot.defaults.logger.LogService;

public class Activator implements BundleActivator {
	private ServiceTracker extendsServiceTracker;
	private ExtendPointRegistry reg;
//	private SimpleLogService simpleLogService;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
//		// register the service
//		context.registerService(
//				SimpleLogService.class.getName(), 
//				new SimpleLogServiceImpl(), 
//				new Hashtable());
//		
//		// create a tracker and track the log service
//		simpleLogServiceTracker = 
//			new ServiceTracker(context, SimpleLogService.class.getName(), null);
//		simpleLogServiceTracker.open();
//		
//		// grab the service
//		simpleLogService = (SimpleLogService) simpleLogServiceTracker.getService();
//
//		if(simpleLogService != null)
//			simpleLogService.log("Yee ha, I'm logging! (start)");
		extendsServiceTracker = new ServiceTracker(context, ExtendPointRegistry.class.getName(), null) {

			/* (non-Javadoc)
			 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
			 */
			@Override
			public Object addingService(ServiceReference reference) {
				Object service = super.addingService(reference);
				reg = (ExtendPointRegistry) service;
				reg.addExtend(new LogService());
				return service;
			}

			/* (non-Javadoc)
			 * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
			 */
			@Override
			public void removedService(ServiceReference reference,
					Object service) {
				// TODO Auto-generated method stub
				super.removedService(reference, service);
			}
			
		};
		extendsServiceTracker.open();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
//		if(simpleLogService != null)
//			simpleLogService.log("Yee ha, I'm logging! (stop)");
//		
//		// close the service tracker
//		simpleLogServiceTracker.close();
//		simpleLogServiceTracker = null;
//		
//		simpleLogService = null;
		extendsServiceTracker.close();
	}

}
