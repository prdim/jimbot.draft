package ru.jimbot.anekbot.commands.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import ru.jimbot.anekbot.AnekCommandBuilder;
import ru.jimbot.anekbot.commands.CommandBuilder;

public class ActivatorAnekbotCommands implements BundleActivator {

	private static BundleContext context;
	public ServiceRegistration registration;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		ActivatorAnekbotCommands.context = bundleContext;
		AnekCommandBuilder cb = new CommandBuilder();
		registration = context.registerService(AnekCommandBuilder.class.getName(), cb, null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		ActivatorAnekbotCommands.context = null;
		registration.unregister();
	}

}
