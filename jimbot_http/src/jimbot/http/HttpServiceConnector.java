/*
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2010 JimBot project
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package jimbot.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import ru.jimbot.MainConfig;
import ru.jimbot.core.api.IHTTPService;
import ru.jimbot.http.HandlerFactory;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class HttpServiceConnector  extends ServiceTracker implements ServiceTrackerCustomizer {
    private List<IHTTPService> slist = Collections.synchronizedList(new ArrayList<IHTTPService>());
    private Server server = null;

    public HttpServiceConnector(BundleContext context) {
        super(context, IHTTPService.class.getName(), null);
        open();
        startHTTPServer();
    }

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
	 */
	@Override
	public Object addingService(ServiceReference reference) {
		System.out.println("register " + reference.toString());
		slist.add((IHTTPService) context.getService(reference));
		stopHTTPServer();
		startHTTPServer();
		return super.addingService(reference);
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	@Override
	public void removedService(ServiceReference reference, Object service) {
		slist.remove(service);
		//TODO Подумать...
		stopHTTPServer();
		startHTTPServer();
		super.removedService(reference, service);
	}
	
	   public synchronized void startHTTPServer() {
	        server = new org.eclipse.jetty.server.Server();
	        try {
	            SelectChannelConnector connector = new SelectChannelConnector();
	            connector.setPort(MainConfig.getInstance().getHttpPort());
	            server.addConnector(connector);
	            server.setHandler(HandlerFactory.getAvailableHandlers(slist));
	            server.start();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

	    public synchronized void stopHTTPServer() {
	        try {
	            if(server != null) server.stop();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
}
