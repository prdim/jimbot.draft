package ru.jimbot.testbot.internal;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;

import ru.jimbot.core.ExtendPointRegistry;
import ru.jimbot.testbot.CommandConnector;
import ru.jimbot.testbot.TestBotServiceBuilder;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceTracker extendsServiceTracker;
	private static ExtendPointRegistry reg;
	CommandConnector con;
	private static EventAdmin eventAdmin;
	private static Map<EventHandler,ServiceRegistration> eventHandlers = 
		new HashMap<EventHandler,ServiceRegistration>();
	private ServiceTracker serviceTracker;

	public static BundleContext getContext() {
		return context;
	}
	
	public static ExtendPointRegistry getExtendPointRegistry() {
		return reg;
	}
	
	public static EventAdmin getEventAdmin() {
		return eventAdmin;
	}
	
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
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		serviceTracker = new ServiceTracker(context, EventAdmin.class.getName(), null){

			/* (non-Javadoc)
			 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
			 */
			@Override
			public Object addingService(ServiceReference reference) {
				Object service = super.addingService(reference);
				eventAdmin = (EventAdmin)service;
//				System.out.println(">>>" + eventAdmin);
				return service;
			}
			
		};
		serviceTracker.open();
//		eventAdmin = (EventAdmin) serviceTracker.getService();
		System.out.println(">>>>" + eventAdmin);
		con = new CommandConnector(context);
		extendsServiceTracker = new ServiceTracker(context, ExtendPointRegistry.class.getName(), null) {

			/* (non-Javadoc)
			 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
			 */
			@Override
			public Object addingService(ServiceReference reference) {
				Object service = super.addingService(reference);
				reg = (ExtendPointRegistry) service;
				TimerTask t = new TimerTask() {
					
					@Override
					public void run() {
						reg.addExtend(new TestBotServiceBuilder(con));
					}
				};
				// Регистрируем бота в сервисах с задержкой, для того чтобы 
				// вспомогательные сервисы к этому времени уже были зарегистрированы и запущены
				new Timer().schedule(t, 1000);
//				reg.addExtend(new TestBotServiceBuilder(con));
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
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		extendsServiceTracker.close();
		serviceTracker.close();
		for(EventHandler i : eventHandlers.keySet()) {
			unregEventHandler(i);
		}
	}

}
