package ru.jimbot.http.admin.internal;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;
import org.eclipse.equinox.http.jetty.JettyConfigurator;

import ru.jimbot.http.admin.AdminServlet;
import ru.jimbot.http.admin.HttpProps;
import ru.jimbot.http.admin.ViewAddon;
import ru.jimbot.http.admin.ViewAddonRegistry;
import ru.jimbot.core.ExtendPointRegistry;
import ru.jimbot.core.services.AbstractProperties;

public class ActivatorHttpAdmin implements BundleActivator {
	private HttpServiceConnector httpServiceConnector;
	private static PropsConnector propsServiceConnector;
	private static BundleContext context;
	
	private static final String HTTP_PORT_KEY = "http.port";
    private static final int HTTP_PORT = 8081;
    private static final String SERVER_NAME = "demojetty";
    private static ExtendPointRegistry reg;
    private static EventAdmin eventAdmin;
	private static Map<EventHandler,ServiceRegistration> eventHandlers = 
		new HashMap<EventHandler,ServiceRegistration>();
	private ServiceTracker serviceTracker;
	private ServiceTracker extendsServiceTracker;
	private static ViewAddonRegistry viewAddon;
	private ServiceRegistration viewAddonService;

	public static BundleContext getContext() {
		return context;
	}
	
	public static List<AbstractProperties> getProps() {
		return propsServiceConnector.getProps();
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
	
	public static ViewAddonRegistry getViewAddonRegistry() {
		return viewAddon;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		ActivatorHttpAdmin.context = bundleContext;
//		httpServiceConnector = new HttpServiceConnector(context, "/admin", new AdminPageServlet());
//		httpServiceConnector = new HttpServiceConnector(context, "/admin", new com.vaadin.terminal.gwt.server.ApplicationServlet());
		
		httpServiceConnector = new HttpServiceConnector(context, "/admin", new AdminServlet());
		
//		Dictionary<String, Object> properties = new Hashtable<String, Object>();
//		properties.put(HTTP_PORT_KEY, HTTP_PORT);
//		JettyConfigurator.startServer(SERVER_NAME, properties);
		propsServiceConnector = new PropsConnector(context);
		context.registerService(AbstractProperties.class.getName(), HttpProps.getInstance(), null);
		extendsServiceTracker = new ServiceTracker(context, ExtendPointRegistry.class.getName(), null) {

			/* (non-Javadoc)
			 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
			 */
			@Override
			public Object addingService(ServiceReference reference) {
				Object service = super.addingService(reference);
				reg = (ExtendPointRegistry) service;
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
		
		viewAddon = new ViewAddonRegistry();
		viewAddonService = context.registerService(ViewAddonRegistry.class.getName(), viewAddon, null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		httpServiceConnector.close();
		propsServiceConnector.close();
		JettyConfigurator.stopServer(SERVER_NAME);
		extendsServiceTracker.close();
		serviceTracker.close();
		for(EventHandler i : eventHandlers.keySet()) {
			unregEventHandler(i);
		}
		System.out.println("Server " + SERVER_NAME + " has been stoped");
		ActivatorHttpAdmin.context = null;
		viewAddonService.unregister();
	}

}
