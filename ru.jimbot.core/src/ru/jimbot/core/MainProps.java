/**
 * 
 */
package ru.jimbot.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ru.jimbot.core.services.AbstractProperties;


/**
 * Основные настройки программы
 * @author spec
 *
 */
public class MainProps implements AbstractProperties {
	public static final String VERSION = "0.6.0 alpha 2 (17/02/2012)";
	private static transient MainProps me = null;
	private static final String FILE_NAME = "jimbot_config";
	
//    private boolean autoStart = false;
    private boolean startHTTP = true;
    private boolean checkNewVer = true;
//    private List<String> serviceNames = new ArrayList<String>();
//    private List<String> serviceTypes = new ArrayList<String>();
    private List<ServiceConfig> services = new ArrayList<ServiceConfig>();
    
    /* (non-Javadoc)
	 * @see ru.jimbot.core.services.AbstractProperties#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Основные настройки";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.AbstractProperties#getExtendInfo()
	 */
	@Override
	public String getExtendInfo() {
		return "Основные настройки программы";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.AbstractProperties#save()
	 */
	@Override
	public void save() {
		try {
			File t = new File("./config");
			if(!t.exists()) t.mkdir();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./config/" + FILE_NAME), "UTF8"));
			w.write(gson.toJson(this));
			w.close();
		} catch (Exception e) {
			System.err.println("Error saving configuration " + e.getMessage());
		}
	}

	private static synchronized MainProps load(String f) {
		Object o = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./config/" + FILE_NAME),"UTF8")); 
			StringBuilder sb = new StringBuilder();
			while(br.ready()) {
				sb.append(br.readLine());
			}
			br.close();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			o = gson.fromJson(sb.toString(), MainProps.class);
		} catch (Exception e) {
			System.err.println("Error loading configuration " + e.getMessage());
		}
		return (MainProps)o;
	}
	
	public static MainProps getInstance() {
		if(me == null) {
			try {
				me = load(FILE_NAME);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(me == null) me = new MainProps();
		}
		return me;	
	}

//	/**
//	 * @return the autoStart
//	 */
//	public boolean isAutoStart() {
//		return autoStart;
//	}
//
//	/**
//	 * @param autoStart the autoStart to set
//	 */
//	public void setAutoStart(boolean autoStart) {
//		this.autoStart = autoStart;
//	}

	/**
	 * @return the startHTTP
	 */
	public boolean isStartHTTP() {
		return startHTTP;
	}

	/**
	 * @param startHTTP the startHTTP to set
	 */
	public void setStartHTTP(boolean startHTTP) {
		this.startHTTP = startHTTP;
	}

	/**
	 * @return the checkNewVer
	 */
	public boolean isCheckNewVer() {
		return checkNewVer;
	}

	/**
	 * @param checkNewVer the checkNewVer to set
	 */
	public void setCheckNewVer(boolean checkNewVer) {
		this.checkNewVer = checkNewVer;
	}

	/**
	 * @return the serviceNames
	 */
	public List<String> getServiceNames() {
		List<String> s = new ArrayList<String>();
		for(ServiceConfig i : services) {
			s.add(i.serviceName);
		}
		return s;
	}
	
	public String getTypeForService(String name) {
		for(ServiceConfig i : services) {
			if(name.equals(i.getServiceName())) {
				return i.getServiceType();
			}
		}
		return null;
	}

	/**
	 * @param serviceNames the serviceNames to set
	 */
	public void addService(String name, String type) {
		services.add(new ServiceConfig(name, type));
	}
	
	public void removeService(String name) {
		for(ServiceConfig i : services) {
			if(name.equals(i.getServiceName())) {
				services.remove(i);
				return;
			}
		}
	}

	/**
	 * @return the services
	 */
	public List<ServiceConfig> getServices() {
		return services;
	}

	/**
	 * @param services the services to set
	 */
	public void setServices(List<ServiceConfig> services) {
		this.services = services;
	}

	/**
	 * @return the serviceTypes
	 */
//	public List<String> getServiceTypes() {
//		return serviceTypes;
//	}

	/**
	 * @param serviceTypes the serviceTypes to set
	 */
//	public void setServiceTypes(List<String> serviceTypes) {
//		this.serviceTypes = serviceTypes;
//	}
	
}
