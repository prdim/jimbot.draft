/**
 * 
 */
package ru.jimbot.protocol.test;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import ru.jimbot.core.Destroyable;
import ru.jimbot.core.Message;
import ru.jimbot.core.events.EventProxy;
import ru.jimbot.core.events.IncomingMessageEventHandler;
import ru.jimbot.core.events.OutgoingMessageEventHandler;
import ru.jimbot.core.events.OutgoingMessageListener;
import ru.jimbot.core.events.ProtocolCommandEventHandler;
import ru.jimbot.core.events.ProtocolCommandListener;
import ru.jimbot.core.events.ProtocolStateEventHandler;
import ru.jimbot.core.services.Log;
import ru.jimbot.core.services.Protocol;
import ru.jimbot.protocol.test.internal.ActivatorTestProtocol;

/**
 * Тестовый протокол, для тестирования работы остальных компонентов бота
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class TestProtocol extends Destroyable implements Protocol, ProtocolCommandListener, OutgoingMessageListener {
	private static Map<String, TestProtocol> prt = new ConcurrentHashMap<String, TestProtocol>();
//    private List<ProtocolListener> protList = new Vector<ProtocolListener>();
    Log logger = ActivatorTestProtocol.getExtendPointRegistry().getLogger();
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
  private ProtocolStateEventHandler ps;
  private IncomingMessageEventHandler im;
  private String serviceName = "";
  private EventProxy eva;
  private OutgoingMessageEventHandler h1;
  private ProtocolCommandEventHandler h2;

	/**
	 * 
	 */
	public TestProtocol(String serviceName) {
		super();
		this.serviceName = serviceName;
//		// Зарегистрируем себя в качестве обработчика событий
//		h1 = new OutgoingMessageEventHandler(screenName, this);
//		h2 = new ProtocolCommandEventHandler(screenName, this);
//		ActivatorTestProtocol.regEventHandler(h1, h1.getHandlerServiceProperties());
//		ActivatorTestProtocol.regEventHandler(h2, h2.getHandlerServiceProperties());
		eva = new EventProxy(ActivatorTestProtocol.getEventAdmin(), serviceName);
	}
	
	/* (non-Javadoc)
	 * @see ru.jimbot.core.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		ActivatorTestProtocol.unregEventHandler(h1);
		ActivatorTestProtocol.unregEventHandler(h2);
	}



	public void reciveMsg(Message m) {
//		for(ProtocolListener i:protList) {
//			i.onTextMessage(m);
//		}
		eva.incomingMessage(m);
	}
	
	public static void testError(String sn) {
		prt.get(sn).onError();
	}
	
	private static void testSend(String from, String to, String msg) {
		if(prt.get(to).isOnLine()) 
			prt.get(to).reciveMsg(new Message(from, to, msg, Message.TYPE_TEXT));
	}
	
	private void notifyLogon() {
//      for(ProtocolListener i:protList) {
//          i.logOn(screenName);
//      }
		eva.protocolChangeState(screenName, EventProxy.STATE_LOGON, null);
  }

  private void notifyLogout() {
//      for(ProtocolListener i:protList) {
//          i.logOut(screenName);
//      }
	  eva.protocolChangeState(screenName, EventProxy.STATE_LOGOFF, null);
  }
  
  public void onError() {
	  lastError = "Error!";
	  Message m = new Message("", "", lastError);
	  eva.protocolChangeState(screenName, EventProxy.STATE_ERROR, m);
	  logout(screenName);
  }

	public void RemoveContactList(String sn) {
		// TODO Auto-generated method stub
		
	}

	public void addContactList(String sn) {
		// TODO Auto-generated method stub
		
	}

//	public void addProtocolListener(ProtocolListener e) {
//		protList.add(e);
//		
//	}

	public void connect() {
		logger.print("test", "test bot", "Test protocol connected");
		connected = true;
	}

	public void disconnect() {
		logger.print("test", "test bot", "Test protocol disconnected");
		connected = false;
	}

	public String getLastError() {
		return lastError;
	}

//	public List<ProtocolListener> getProtocolListeners() {
//		return protList;
//	}

	public String getScreenName() {
		return screenName;
	}

	public boolean isOnLine() {
		return connected;
	}

//	public boolean removeProtocolListener(ProtocolListener e) {
//		return protList.remove(e);
//	}

	public void sendMsg(String sn, String msg) {
		logger.print("test", "test bot", "Send: " + sn + ">>" + msg);
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
        // Зарегистрируем себя в качестве обработчика событий
		h1 = new OutgoingMessageEventHandler(screenName, this);
		h2 = new ProtocolCommandEventHandler(screenName, this);
		ActivatorTestProtocol.regEventHandler(h1, h1.getHandlerServiceProperties());
		ActivatorTestProtocol.regEventHandler(h2, h2.getHandlerServiceProperties());
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

	@Override
	public void logon(String sn) {
		connect();
		notifyLogon();
	}

	@Override
	public void logout(String sn) {
		disconnect();
		notifyLogout();
	}

	@Override
	public void changeStatus(int id, String txt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeXStatus(int id, String txt1, String txt2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(Message m) {
		sendMsg(m.getSnOut(), m.getMsg());
	}

//	public void logOn() {
//		connect();
//		notifyLogon();
//	}
//
//	public void logOut() {
//		disconnect();
//        notifyLogout();
//	}
//
//	public void onChangeStatus(int id, String text) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void onChangeXStatus(int id, String text1, String text2) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void sendMessage(String in, String out, String text) {
//		if(screenName.equalsIgnoreCase(in)) sendMsg(out,text);
//	}

}
