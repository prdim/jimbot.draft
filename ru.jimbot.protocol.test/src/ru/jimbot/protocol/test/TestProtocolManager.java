/**
 * 
 */
package ru.jimbot.protocol.test;

import java.util.HashMap;

import ru.jimbot.core.ExtendPoint;
import ru.jimbot.core.IProtocolBuilder;
import ru.jimbot.core.services.AbstractProperties;
import ru.jimbot.core.services.IProtocolManager;
import ru.jimbot.core.services.Log;
import ru.jimbot.core.services.Protocol;
import ru.jimbot.protocol.test.internal.ActivatorTestProtocol;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class TestProtocolManager implements IProtocolManager, ExtendPoint {
	private HashMap<String, TestProtocol> protocols = new HashMap<String, TestProtocol>();
	
	public TestProtocolManager() {
		
	}
	
	public String getProptocolName(){
		return "test";
	}
	
	public TestProtocol getProtocol(String sn) {
		return protocols.get(sn);
	}
	
	public Protocol addProtocol(Protocol p) {
		protocols.put(p.getScreenName(), (TestProtocol)p);
		return p;
	}
	
	

	/* (non-Javadoc)
	 * @see ru.jimbot.core.IProtocolManager#getBuilder(java.lang.String)
	 */
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
		private Log logger = ActivatorTestProtocol.getExtendPointRegistry().getLogger();
		
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
		
		public TestProtocol build(String serviceName) {
			TestProtocol p = new TestProtocol(serviceName);
			p.setConnectionData("", 0, screenName, pass);
//			p.setStatusData(status, statustxt);
//			p.setXStatusData(xstatus, xstatustxt1, xstatustxt2);
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
		return "TestProtocol";
	}

	@Override
	public void unreg() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbstractProperties getProtocolProperties(String serviceName) {
		// TODO Auto-generated method stub
		return null;
	}
}