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

package ru.jimbot.modules.anek;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import ru.jimbot.core.api.ICommandBuilder;
import ru.jimbot.core.api.IServiceBuilder;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class AnekCommandConnector  extends ServiceTracker implements ServiceTrackerCustomizer {
	List<AnekCommandBuilder> cb = Collections.synchronizedList(new ArrayList<AnekCommandBuilder>());
	
	public List<AnekCommandBuilder> getAllCommandBuilders() {
		return cb;
	}
	
	public AnekCommandConnector (BundleContext context) {
		super(context, AnekCommandBuilder.class.getName(), null);
		open();
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
	 */
	@Override
	public Object addingService(ServiceReference reference) {
		System.out.println("register " + reference.toString());
		cb.add((AnekCommandBuilder) context.getService(reference));
		return super.addingService(reference);
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	@Override
	public void removedService(ServiceReference reference, Object service) {
		cb.remove(service);
		super.removedService(reference, service);
	}
	
	
}
