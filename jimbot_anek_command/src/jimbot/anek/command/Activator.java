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

package jimbot.anek.command;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import ru.jimbot.modules.anek.AnekCommandBuilder;
import ru.jimbot.modules.anek.commands.CommandBuilder;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class Activator implements BundleActivator {
	public ServiceRegistration registration;
	
	public void start(BundleContext context) throws Exception {
		System.out.println("!!!Start anek.command");
		AnekCommandBuilder cb = new CommandBuilder();
		registration = context.registerService(AnekCommandBuilder.class.getName(), cb, null);
	}

	public void stop(BundleContext context) throws Exception {
		registration.unregister();
		System.out.println("!!!Stop anek.command");
	}

}
