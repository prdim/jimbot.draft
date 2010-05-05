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

import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import ru.jimbot.MainConfig;
import ru.jimbot.http.HandlerFactory;
import ru.jimbot.http.MainPageServlet;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class Activator implements BundleActivator {
//	private org.eclipse.jetty.server.Server server = null;
	private HttpServiceConnector httpServiceConnector;
	
	public void start(BundleContext context) throws Exception {
		System.out.println("!!!Start http");
		httpServiceConnector = new HttpServiceConnector(context);
//		Dictionary<String, Object> properties = new Hashtable<String, Object>();
//        properties.put(JettyConstants.HTTP_PORT, 8080);
//        JettyConfigurator.startServer("JimBot", properties);
//		startHTTPServer();
		
	}

	public void stop(BundleContext context) throws Exception {
//		stopHTTPServer();
		httpServiceConnector.stopHTTPServer();
		System.out.println("!!!Stop http");
	}
	
//    public synchronized void startHTTPServer() {
//        server = new org.eclipse.jetty.server.Server();
//        try {
//            SelectChannelConnector connector = new SelectChannelConnector();
//            connector.setPort(MainConfig.getInstance().getHttpPort());
//            server.addConnector(connector);
//            server.setHandler(HandlerFactory.getAvailableHandlers());
//
////            HandlerFactory.setAvailableHandlers(server);
//            server.start();
////            server.join();
//            
////            new HandlerFactory().setAvailableHandlers2(server);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public synchronized void stopHTTPServer() {
//        try {
//            if(server != null) server.stop();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
