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
import ru.jimbot.core.api.IProtocolManager;

/**
 * Отслеживает появление новых плагинов, реализующих менеджеры протоколов
 * @author Prolubnikov Dmitry
 *
 */
public class ProtocolConnector extends ServiceTracker implements ServiceTrackerCustomizer {

	public ProtocolConnector (BundleContext context) {
		super(context, IProtocolManager.class.getName(), null);
		open();
		System.out.println("open protocol connector");
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
	 */
	@Override
	public Object addingService(ServiceReference reference) {
		System.out.println("add service " + reference.toString());
		IProtocolManager pm = (IProtocolManager) context.getService(reference);
		Manager.getInstance().putProtocolManager(pm);
		return pm;
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	@Override
	public void removedService(ServiceReference reference, Object service) {
		IProtocolManager pm = (IProtocolManager)service;
		Manager.getInstance().removeProtocolManager(pm);
		super.removedService(reference, service);
	}
	
	
}
