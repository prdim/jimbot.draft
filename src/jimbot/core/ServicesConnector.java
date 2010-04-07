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

package jimbot.core;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import ru.jimbot.Manager;
import ru.jimbot.core.api.IServiceBuilder;

/**
 * Отслеживает появление новых плагинов, реализующих билдеры сервисов, и регистрирует их в Manager
 * @author Prolubnikov Dmitry
 *
 */
public class ServicesConnector extends ServiceTracker implements ServiceTrackerCustomizer {
//	private Map<String, IServiceBuilder> srv = new ConcurrentHashMap<String, IServiceBuilder>();
	
//	public Map<String, IServiceBuilder> getConnectedServices() {
//		return srv;
//	}
	
//	public IServiceBuilder getServiceBuilder(String key) {
//		return srv.get(key);
//	}
	
	public ServicesConnector (BundleContext context) {
		super(context, IServiceBuilder.class.getName(), null);
		open();
		System.out.println("open service connector");
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
	 */
	@Override
	public Object addingService(ServiceReference reference) {
		System.out.println("add service " + reference.toString());
		IServiceBuilder sb = (IServiceBuilder) context.getService(reference);
//		srv.put(sb.getServiceType(), sb);
		Manager.getInstance().putServiceBuilder(sb);
		return sb;
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	@Override
	public void removedService(ServiceReference reference, Object service) {
		IServiceBuilder sb = (IServiceBuilder) service;
//		srv.remove(sb.getServiceType());
		Manager.getInstance().removeServiceBuilder(sb);
		super.removedService(reference, service);
	}
	
	
}
