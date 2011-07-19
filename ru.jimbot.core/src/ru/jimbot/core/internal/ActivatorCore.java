package ru.jimbot.core.internal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;

import ru.jimbot.core.ExtendPointRegistry;
import ru.jimbot.core.MainProps;
import ru.jimbot.core.services.AbstractProperties;

public class ActivatorCore implements BundleActivator {
	private static BundleContext context;
	private static ExtendPointRegistry reg;
	private ServiceRegistration registration;
	private ServiceTracker serviceTracker;
	private static EventAdmin eventAdmin;
	private static Map<EventHandler,ServiceRegistration> eventHandlers = 
		new HashMap<EventHandler,ServiceRegistration>();
	Worker tester;

	public static BundleContext getContext() {
		return context;
	}
	
	public static ExtendPointRegistry getRegistry() {
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

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		ActivatorCore.context = bundleContext;
		System.out.println("Start core");
		reg = new ExtendPointRegistry();
		registration = bundleContext.registerService(ExtendPointRegistry.class.getName(), reg, null);
		context.registerService(AbstractProperties.class.getName(), MainProps.getInstance(), null);
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
		tester = new Worker();
		tester.createState();
		tester.start();
//		eventAdmin = (EventAdmin) serviceTracker.getService();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		tester.stop();
		ActivatorCore.context = null;
		reg.unregAll();
		registration.unregister();
		serviceTracker.close();
		for(EventHandler i : eventHandlers.keySet()) {
			unregEventHandler(i);
		}
		System.out.println("Stop core");
	}

	private class Worker extends Thread {

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				testState();
			}
		}
		
		/**
	     * Проверка состояния файла. При необходимости - выключение
	     */
	    public void testState(){
	    	BufferedReader r = null;
	        try{
	            File f = new File("./state");
	            if(!f.exists()) {
	                createState();
	                return;
	            }
	            r = new BufferedReader(new InputStreamReader(new FileInputStream("state")));
	            String s = r.readLine();
	            if(s.equals("Stop")) {
	                exit();
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } finally {
	        	if(r!=null){
	        		try{r.close();}catch(Exception e) {}
	        	}
	        }
	    }
	    
	    /**
	     * Создает файл статуса программы
	     */
	    public void createState(){
	        try {
	            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("state"))); 
	            w.write("start");
	            w.newLine();
	            w.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	    
	    /**
	     * 
	     */
	    public void exit() {
	    	try {
				context.getBundle(0).stop();
			} catch (BundleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
}
