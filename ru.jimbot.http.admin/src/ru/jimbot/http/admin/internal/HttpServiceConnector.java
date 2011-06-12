/**
 * 
 */
package ru.jimbot.http.admin.internal;

import javax.servlet.http.HttpServlet;

import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

/**
 * @author spec
 *
 */
public class HttpServiceConnector extends ServiceTracker implements
		ServiceTrackerCustomizer {
	private String path;
	private HttpServlet servlet;
	
	public HttpServiceConnector(BundleContext context, String path, HttpServlet servlet) {
		super(context, HttpService.class.getName(), null);
		this.path = path;
		this.servlet = servlet;
		open();
	}
	
	@Override
	public Object addingService(ServiceReference reference){
		HttpService httpService = (HttpService) super.addingService(reference);
		if (httpService == null) return null;
		try {
			System.out.println("Registering servlet at " + path);
			httpService.registerServlet(path, servlet, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpService;
	}
	
	@Override
	public void removedService(ServiceReference reference, Object service) {
		HttpService httpService = (HttpService) service;
		System.out.println("Unregistering " + path);
		httpService.unregister(path);
		super.removedService(reference, service);
	}
}
