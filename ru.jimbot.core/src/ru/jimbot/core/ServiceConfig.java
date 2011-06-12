package ru.jimbot.core;

public class ServiceConfig {
	String serviceName = "";
	private String serviceType = "";
	

	public ServiceConfig() {

	}

	/**
	 * @param serviceName
	 * @param serviceType
	 */
	public ServiceConfig(String serviceName, String serviceType) {
		this.serviceName = serviceName;
		this.serviceType = serviceType;
	}
	
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
}