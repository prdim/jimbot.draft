/**
 * 
 */
package ru.jimbot.core.services;

import ru.jimbot.core.IProtocolBuilder;

/**
 * 
 * @author Prolubnikov Dmitry
 */
public interface IProtocolManager {
	public Protocol getProtocol(String sn);
	public Protocol addProtocol(Protocol p);
	public String getProptocolName();
	public IProtocolBuilder getBuilder(String sn);
	public AbstractProperties getProtocolProperties(String serviceName);
}
