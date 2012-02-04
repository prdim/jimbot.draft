/**
 * 
 */
package ru.jimbot.protocol.xmpp;

import java.util.HashMap;

import ru.jimbot.core.ExtendPoint;
import ru.jimbot.core.IProtocolBuilder;
import ru.jimbot.core.services.AbstractProperties;
import ru.jimbot.core.services.IProtocolManager;
import ru.jimbot.core.services.Log;
import ru.jimbot.core.services.Protocol;

/**
 * Создает и хранит объекты, реализующие подключение по протоколу ICQ
 *
 * @author Prolubnikov Dmitry
 * @author Black_Kot
 */
public class XmppProtocolManager implements IProtocolManager, ExtendPoint {
	private HashMap<String, XmppProtocol> protocols = new HashMap<String, XmppProtocol>(5);
	private HashMap<String, XmppProtocolProperties> props = new HashMap<String, XmppProtocolProperties>(5);
	
	@Override
	public Protocol getProtocol(String sn) {
		return protocols.get(sn);
	}

	@Override
	public Protocol addProtocol(Protocol p) {
		protocols.put(p.getScreenName(), (XmppProtocol)p);
		return p;
	}

	@Override
	public String getProptocolName() {
		return "xmpp";
	}

	@Override
	public IProtocolBuilder getBuilder(String sn) {
		return new Builder(sn);
	}
	
	class Builder implements IProtocolBuilder {
		private String screenName = "";
		private String pass = "";
		private Log logger = null;
		
		public Builder(String screenName) {
			this.screenName = screenName;
		}
		
		public Builder pass(String val) {
			pass = val;
			return this;
		}
		
		public Builder logger(Log val) {
			logger = val;
			return this;
		}
		
		@Override
		public Protocol build(String serviceName) {
			XmppProtocolProperties pr = (XmppProtocolProperties) getProtocolProperties(serviceName);
			XmppProtocol p = new XmppProtocol(serviceName, pr);
			p.setConnectionData(screenName.split("@")[1], 5222, screenName, pass);
			p.setStatusData(pr.getStatus(), pr.getStatustxt());
			p.setXStatusData(pr.getXstatus(), pr.getXstatustxt(),null);
			p.setLogger(logger);
			return p;
		}
	}

	@Override
	public String getType() {
		return "ru.jimbot.core.services.IProtocolManager";
	}

	@Override
	public String getPointName() {
		return "XmmpProtocol";
	}

	@Override
	public void unreg() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbstractProperties getProtocolProperties(String serviceName) {
		if(props.containsKey(serviceName)) {
			return props.get(serviceName);
		} else {
			XmppProtocolProperties p = XmppProtocolProperties.load(serviceName);
			props.put(serviceName, p);
			return p;
		}
	}
}
