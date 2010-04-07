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

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import ru.jimbot.core.Message;
import ru.jimbot.core.api.CommandProtocolListener;
import ru.jimbot.core.api.Protocol;
import ru.jimbot.core.api.ProtocolListener;
import ru.jimbot.util.Log;

/**
 * Тестовый протокол, для тестирования работы остальных компонентов бота
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class TestProtocol implements Protocol, CommandProtocolListener {
	private static Map<String, TestProtocol> prt = new ConcurrentHashMap<String, TestProtocol>();
    private List<ProtocolListener> protList = new Vector<ProtocolListener>();
    Log logger = Log.getDefault();
    private String screenName = ""; // УИН
  private String pass = "";
  private String server = "login.icq.com";
  private int port = 5980;
  private int status = 0;
  private String statustxt = "";
  private int xstatus = 0;
  private String xstatustxt1 = "";
  private String xstatustxt2 = "";
  private boolean connected = false;
  private String lastError = "";

	/**
	 * 
	 */
	public TestProtocol() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void reciveMsg(Message m) {
		for(ProtocolListener i:protList) {
			i.onTextMessage(m);
		}
	}
	
	public static void testError(String sn) {
		prt.get(sn).onError();
	}
	
	private static void testSend(String from, String to, String msg) {
		if(prt.get(to).isOnLine()) 
			prt.get(to).reciveMsg(new Message(from, to, msg, Message.TYPE_TEXT));
	}
	
	private void notifyLogon() {
//      srv.createEvent(new ProtocolLogonEvent(srv, screenName));
      for(ProtocolListener i:protList) {
          i.logOn(screenName);
      }
  }

  private void notifyLogout() {
//      srv.createEvent(new ProtocolLogoutEvent(srv, screenName));
      for(ProtocolListener i:protList) {
          i.logOut(screenName);
      }
  }
  
  public void onError() {
	  lastError = "Error!";
	  logOut();
  }

	public void RemoveContactList(String sn) {
		// TODO Auto-generated method stub
		
	}

	public void addContactList(String sn) {
		// TODO Auto-generated method stub
		
	}

	public void addProtocolListener(ProtocolListener e) {
		protList.add(e);
		
	}

	public void connect() {
		logger.info("Test protocol connected");
		connected = true;
	}

	public void disconnect() {
		logger.info("Test protocol disconnected");
		connected = false;
	}

	public String getLastError() {
		return lastError;
	}

	public List<ProtocolListener> getProtocolListeners() {
		return protList;
	}

	public String getScreenName() {
		return screenName;
	}

	public boolean isOnLine() {
		return connected;
	}

	public boolean removeProtocolListener(ProtocolListener e) {
		return protList.remove(e);
	}

	public void sendMsg(String sn, String msg) {
		logger.info("Send: " + sn + ">>" + msg);
		if(prt.containsKey(sn)) {
			prt.get(sn).reciveMsg(new Message(screenName, sn, msg, Message.TYPE_TEXT));
		}
	}

	public void setConnectionData(String server, int port, String sn,
			String pass) {
		this.server = server;
        this.port = port;
        this.screenName = sn;
        this.pass = pass;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
		
	}

	public void setStatusData(int status, String text) {
		this.status = status;
        this.statustxt = text;
	}

	public void setXStatusData(int status, String text1, String text2) {
		this.xstatus = status;
        this.xstatustxt1 = text1;
        this.xstatustxt2 = text2;
	}

	public void logOn() {
		connect();
		notifyLogon();
	}

	public void logOut() {
		disconnect();
        notifyLogout();
	}

	public void onChangeStatus(int id, String text) {
		// TODO Auto-generated method stub
		
	}

	public void onChangeXStatus(int id, String text1, String text2) {
		// TODO Auto-generated method stub
		
	}

	public void sendMessage(String in, String out, String text) {
		if(screenName.equalsIgnoreCase(in)) sendMsg(out,text);
	}

}
