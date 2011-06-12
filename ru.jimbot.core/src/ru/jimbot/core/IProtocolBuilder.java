/**
 * 
 */
package ru.jimbot.core;

import ru.jimbot.core.services.Log;
import ru.jimbot.core.services.Protocol;

/**
 * @author Prolubnikov Dmitry
 *
 */
public interface IProtocolBuilder {
	public IProtocolBuilder pass(String val);
	
	public IProtocolBuilder server(String val);
	
	public IProtocolBuilder port(int val);
	
	public IProtocolBuilder status(int val);
	
	public IProtocolBuilder statustxt(String val);
	
	public IProtocolBuilder xstatus(int val);
	
	public IProtocolBuilder xstatustxt1(String val);
	
	public IProtocolBuilder xstatustxt2(String val);
	
	public IProtocolBuilder logger(Log val);
	
	public Protocol build(String serviceName);
}