/**
 * 
 */
package ru.jimbot.core.services;

/**
 * Конфиг для УИНа
 * @author spec
 *
 */
public class UinConfig {
	private String screenName = "";
	private String password = "";
	private String protocol = "icq";
	
	public UinConfig() {
		
	}
	
	public UinConfig(String screenName, String password, String protocol) {
		super();
		this.screenName = screenName;
		this.password = password;
		this.protocol = protocol;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}
