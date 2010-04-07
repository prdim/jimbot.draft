/*
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2010 JimBot project
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package ru.jimbot.protocol.test;

import java.util.HashMap;

import ru.jimbot.core.api.IProtocolBuilder;
import ru.jimbot.core.api.IProtocolManager;
import ru.jimbot.core.api.Protocol;
import ru.jimbot.util.Log;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class TestProtocolManager implements IProtocolManager {
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
		private String server = "login.icq.com";
		private int port = 5980;
		private int status = 0;
		private String statustxt = "";
		private int xstatus = 0;
		private String xstatustxt1 = "";
		private String xstatustxt2 = "";
		private Log logger = Log.getDefault();
		
		public Builder(String screenName) {
			this.screenName = screenName;
		}
		
		public Builder pass(String val) {
			pass = val;
			return this;
		}
		
		public Builder server(String val) {
			server = val;
			return this;
		}
		
		public Builder port(int val) {
			port = val;
			return this;
		}
		
		public Builder status(int val) {
			status = val;
			return this;
		}
		
		public Builder statustxt(String val) {
			statustxt = val;
			return this;
		}
		
		public Builder xstatus(int val) {
			xstatus = val;
			return this;
		}
		
		public Builder xstatustxt1(String val) {
			xstatustxt1 = val;
			return this;
		}
		
		public Builder xstatustxt2(String val) {
			xstatustxt2 = val;
			return this;
		}
		
		public Builder logger(Log val) {
			logger = val;
			return this;
		}
		
		public TestProtocol build() {
			TestProtocol p = new TestProtocol();
			p.setConnectionData(server, port, screenName, pass);
			p.setStatusData(status, statustxt);
			p.setXStatusData(xstatus, xstatustxt1, xstatustxt2);
			p.setLogger(logger);
			return p;
		}
	}
}
