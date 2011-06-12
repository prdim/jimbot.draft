package ru.jimbot.defaults.logger.internal;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;

import ru.jimbot.core.*;
import ru.jimbot.core.services.AbstractProperties;
import ru.jimbot.defaults.logger.DefaultLogConfig;
import ru.jimbot.defaults.logger.LogService;

public class ActivatorDefaultLogger implements BundleActivator {
	private ServiceTracker extendsServiceTracker;
	private ExtendPointRegistry reg;
	private ServiceRegistration registration;
	private static Map<EventHandler,ServiceRegistration> eventHandlers = 
		new HashMap<EventHandler,ServiceRegistration>();
	private static BundleContext context;
	private LogService log;
	
	public static synchronized void regEventHandler(EventHandler e, Dictionary<String, Object>p) {
		ServiceRegistration r = context.registerService(EventHandler.class.getName(), e, p);
		eventHandlers.put(e, r);
	}
	
	public static synchronized void unregEventHandler(EventHandler e) {
		ServiceRegistration r = eventHandlers.get(e);
		r.unregister();
		eventHandlers.remove(e);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		this.context = context;
		registration = context.registerService(AbstractProperties.class.getName(), DefaultLogConfig.getInstance(), null);
		
		extendsServiceTracker = new ServiceTracker(context, ExtendPointRegistry.class.getName(), null) {

			/* (non-Javadoc)
			 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
			 */
			@Override
			public Object addingService(ServiceReference reference) {
				Object service = super.addingService(reference);
				reg = (ExtendPointRegistry) service;
				log = new LogService();
				reg.addExtend(log);
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
//		context.registerService(AbstractProperties.class.getName(), DefaultLogConfig.getInstance(), null);
		if(DefaultLogConfig.getInstance().isDebugMode()) {
			Dictionary<String, Object> p = new Hashtable<String, Object>();
			p.put(EventConstants.EVENT_TOPIC, "*");
			regEventHandler(new DebudEventHandler(), p);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		extendsServiceTracker.close();
		registration.unregister();
	}

	private class DebudEventHandler implements EventHandler {

		@Override
		public void handleEvent(Event event) {
//			System.out.println(">>>" + event.getTopic());
			log.debug("EVENT", ">>>" + event.getTopic());
		}
		
	}
}
