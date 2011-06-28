/**
 * 
 */
package ru.jimbot.anekbot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class CommandConnector extends ServiceTracker implements ServiceTrackerCustomizer {
	List<AnekCommandBuilder> cb = Collections.synchronizedList(new ArrayList<AnekCommandBuilder>());
	
	public List<AnekCommandBuilder> getAllCommandBuilders() {
		return cb;
	}
	
	public CommandConnector (BundleContext context) {
		super(context, AnekCommandBuilder.class.getName(), null);
		open();
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
	 */
	public Object addingService(ServiceReference reference) {
		System.out.println("register " + reference.toString());
		cb.add((AnekCommandBuilder) context.getService(reference));
		return super.addingService(reference);
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	public void removedService(ServiceReference reference, Object service) {
		cb.remove(service);
		super.removedService(reference, service);
	}	
}
