/**
 * 
 */
package ru.jimbot.protocol.icq;

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
 */
public class IcqProtocolManager implements IProtocolManager, ExtendPoint {
	private HashMap<String, IcqProtocol> protocols = new HashMap<String, IcqProtocol>();
	private HashMap<String, IcqProtocolProperties> props = new HashMap<String, IcqProtocolProperties>(); 
	
	@Override
	public Protocol getProtocol(String sn) {
		return protocols.get(sn);
	}

	@Override
	public Protocol addProtocol(Protocol p) {
		protocols.put(p.getScreenName(), (IcqProtocol)p);
		return p;
	}

	@Override
	public String getProptocolName() {
		return "icq";
	}

	@Override
	public IProtocolBuilder getBuilder(String sn) {
		return new Builder(sn);
	}
	
	class Builder implements IProtocolBuilder {
		private String screenName = "";
		private String pass = "";
//		private String server = "login.icq.com";
//		private int port = 5980;
//		private int status = 0;
//		private String statustxt = "";
//		private int xstatus = 0;
//		private String xstatustxt1 = "";
//		private String xstatustxt2 = "";
		private Log logger = null;
		
		public Builder(String screenName) {
			this.screenName = screenName;
		}
		
		public Builder pass(String val) {
			pass = val;
			return this;
		}
		
//		public Builder server(String val) {
//			server = val;
//			return this;
//		}
//		
//		public Builder port(int val) {
//			port = val;
//			return this;
//		}
//		
//		public Builder status(int val) {
//			status = val;
//			return this;
//		}
//		
//		public Builder statustxt(String val) {
//			statustxt = val;
//			return this;
//		}
//		
//		public Builder xstatus(int val) {
//			xstatus = val;
//			return this;
//		}
//		
//		public Builder xstatustxt1(String val) {
//			xstatustxt1 = val;
//			return this;
//		}
//		
//		public Builder xstatustxt2(String val) {
//			xstatustxt2 = val;
//			return this;
//		}
		
		public Builder logger(Log val) {
			logger = val;
			return this;
		}
		
		@Override
		public Protocol build(String serviceName) {
			IcqProtocolProperties pr = (IcqProtocolProperties) getProtocolProperties(serviceName);
			IcqProtocol p = new IcqProtocol(serviceName, pr);
			p.setConnectionData(pr.getServer(), pr.getPort(), screenName, pass);
			p.setStatusData(pr.getStatus(), pr.getStatustxt());
			p.setXStatusData(pr.getXstatus(), pr.getXstatustxt1(), pr.getXstatustxt2());
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
		return "IcqProtocol";
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
			IcqProtocolProperties p = IcqProtocolProperties.load(serviceName);
			props.put(serviceName, p);
			return p;
		}
	}
}
