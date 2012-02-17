/**
 * 
 */
package ru.jimbot.protocol.icq;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import ru.caffeineim.protocols.icq.core.OscarConnection;
import ru.caffeineim.protocols.icq.exceptions.ConvertStringException;
import ru.caffeineim.protocols.icq.integration.OscarInterface;
import ru.caffeineim.protocols.icq.integration.events.IncomingMessageEvent;
import ru.caffeineim.protocols.icq.integration.events.IncomingUrlEvent;
import ru.caffeineim.protocols.icq.integration.events.LoginErrorEvent;
import ru.caffeineim.protocols.icq.integration.events.MessageAckEvent;
import ru.caffeineim.protocols.icq.integration.events.MessageErrorEvent;
import ru.caffeineim.protocols.icq.integration.events.MessageMissedEvent;
import ru.caffeineim.protocols.icq.integration.events.OfflineMessageEvent;
import ru.caffeineim.protocols.icq.integration.events.StatusEvent;
import ru.caffeineim.protocols.icq.integration.events.XStatusRequestEvent;
import ru.caffeineim.protocols.icq.integration.events.XStatusResponseEvent;
import ru.caffeineim.protocols.icq.integration.listeners.MessagingListener;
import ru.caffeineim.protocols.icq.integration.listeners.OurStatusListener;
import ru.caffeineim.protocols.icq.integration.listeners.XStatusListener;
import ru.caffeineim.protocols.icq.setting.enumerations.StatusModeEnum;
import ru.caffeineim.protocols.icq.setting.enumerations.XStatusModeEnum;
import ru.jimbot.core.Destroyable;
import ru.jimbot.core.Message;
import ru.jimbot.core.events.EventProxy;
import ru.jimbot.core.events.OutgoingMessageEventHandler;
import ru.jimbot.core.events.OutgoingMessageListener;
import ru.jimbot.core.events.ProtocolCommandEventHandler;
import ru.jimbot.core.events.ProtocolCommandListener;
import ru.jimbot.core.services.Log;
import ru.jimbot.core.services.Protocol;
import ru.jimbot.protocol.icq.internal.ActivatorIcqProtocol;

/**
 * @author spec
 *
 */
public class IcqProtocol extends Destroyable implements Protocol, ProtocolCommandListener, OutgoingMessageListener,
														OurStatusListener, MessagingListener, XStatusListener {
	private Log logger = ActivatorIcqProtocol.getExtendPointRegistry().getLogger();
	private OscarConnection con=null;
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
    private String serviceName = "";
    private EventProxy eva;
    private OutgoingMessageEventHandler h1;
    private ProtocolCommandEventHandler h2;
    private long pauseOutMsg = 2000;
    private int maxOutQueue = 20;
    private ConcurrentLinkedQueue<Message> q = new ConcurrentLinkedQueue<Message>();
    private long timeLastOutMsg = 0; // Время последнего отправленного сообщения
    private Timer timer;
    private TimerTask qt;
    private IcqProtocolProperties p;
    
	public IcqProtocol(String serviceName, IcqProtocolProperties p) {
		super();
		this.serviceName = serviceName;
		this.p = p;
//		// Зарегистрируем себя в качестве обработчика событий
//		h1 = new OutgoingMessageEventHandler(screenName, this);
//		h2 = new ProtocolCommandEventHandler(screenName, this);
//		ActivatorIcqProtocol.regEventHandler(h1, h1.getHandlerServiceProperties());
//		ActivatorIcqProtocol.regEventHandler(h2, h2.getHandlerServiceProperties());
		eva = new EventProxy(ActivatorIcqProtocol.getEventAdmin(), serviceName);
//		timer = new Timer("queue out " + screenName);
//		qt = new TimerTask() {
//			
//			@Override
//			public void run() {
//				if(q.size()==0) return;
//				if((System.currentTimeMillis() - timeLastOutMsg) < pauseOutMsg) return;
//				Message m = q.poll();
//				sendMsg(m.getSnOut(), m.getMsg());
//				timeLastOutMsg = System.currentTimeMillis();
//			}
//		};
	}

	@Override
	public void onMessage(Message m) {
		if(m.getMsg().length() > p.getMaxOutMsgSize()) {
			int cnt = m.getMsg().length()/p.getMaxOutMsgSize()+1;
			int max = p.getMaxOutMsgCount() > cnt ? cnt : p.getMaxOutMsgCount();
			int maxx = p.getMaxOutMsgSize();
			for(int i=0; i<max; i++) {
				if(((i+1) * maxx - 1) < m.getMsg().length()) {
					if(i == (max - 1)) {
						q.add(m.getCopy(m.getMsg().substring(i*maxx, (i+1)*maxx) + "\nЧасть сообщения была обрезана..."));
					} else {
						q.add(m.getCopy(m.getMsg().substring(i*maxx, (i+1)*maxx)));
					}
				} else {
					q.add(m.getCopy(m.getMsg().substring(i * maxx)));
				}
			}
			return;
		}
		if(q.size()>0 || (System.currentTimeMillis()-timeLastOutMsg) < pauseOutMsg) {
			if(q.size()<=maxOutQueue) {
				q.add(m);
			} else {
				q.poll();
				q.add(m);
			}
		} else {
			sendMsg(m.getSnOut(), m.getMsg());
			timeLastOutMsg = System.currentTimeMillis();
		}
	}

	@Override
	public void logon(String sn) {
		connect();
		pauseOutMsg = p.getPauseOut();
		maxOutQueue = p.getMsgOutLimit();
		timer = new Timer("queue out " + screenName);
		qt = new TimerTask() {
			
			@Override
			public void run() {
				if(q.size()==0) return;
				if((System.currentTimeMillis() - timeLastOutMsg) < pauseOutMsg) return;
				Message m = q.poll();
				sendMsg(m.getSnOut(), m.getMsg());
				timeLastOutMsg = System.currentTimeMillis();
			}
		};
		timer.schedule(qt, pauseOutMsg/2, pauseOutMsg/2);
	}

	@Override
	public void logout(String sn) {
		timer.cancel();
		timer.purge();
		q.clear();
		disconnect();
	}

	@Override
	public void changeStatus(int id, String txt) {
		this.status = id;
        this.statustxt = txt;
        OscarInterface.changeStatus(con, new StatusModeEnum(id));
	}

	@Override
	public void changeXStatus(int id, String txt1, String txt2) {
		xstatus = id;
        xstatustxt1 = txt1;
        xstatustxt2 = txt2;
        OscarInterface.changeXStatus(con, new XStatusModeEnum(status));
	}

	@Override
	public void setConnectionData(String server, int port, String sn,
			String pass) {
		this.server = server;
        this.port = port;
        this.screenName = sn;
        this.pass = pass;
		// Зарегистрируем себя в качестве обработчика событий
		h1 = new OutgoingMessageEventHandler(screenName, this);
		h2 = new ProtocolCommandEventHandler(screenName, this);
		ActivatorIcqProtocol.regEventHandler(h1, h1.getHandlerServiceProperties());
		ActivatorIcqProtocol.regEventHandler(h2, h2.getHandlerServiceProperties());
	}

	@Override
	public void setLogger(Log logger) {
		this.logger = logger;
	}

	@Override
	public void setStatusData(int status, String text) {
		this.status = status;
        this.statustxt = text;
	}

	@Override
	public void setXStatusData(int status, String text1, String text2) {
		this.xstatus = status;
        this.xstatustxt1 = text1;
        this.xstatustxt2 = text2;
	}

	@Override
	public void connect() {
		con = new OscarConnection(server, port, screenName, pass);
        con.addOurStatusListener(this);
        con.addMessagingListener(this);
        con.addXStatusListener(this);
        con.connect();
	}

	@Override
	public void disconnect() {
		try {
//            System.out.println("Disconnect...");
            con.close();
            con.removeOurStatusListener(this);
            con.removeMessagingListener(this);
            con.removeXStatusListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        connected = false;
	}

	@Override
	public boolean isOnLine() {
		if(con==null) return false;
        return connected;
	}

	@Override
	public void sendMsg(String sn, String msg) {
		try {
			OscarInterface.sendBasicMessage(con, sn, msg);
		} catch (ConvertStringException e) {
			logger.error(screenName,"ERROR send message: " + msg);
			e.printStackTrace();
		}
	}

	@Override
	public void addContactList(String sn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RemoveContactList(String sn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getScreenName() {
		return screenName;
	}

	@Override
	public String getLastError() {
		return lastError;
	}

	@Override
	public void destroy() {
		ActivatorIcqProtocol.unregEventHandler(h1);
		ActivatorIcqProtocol.unregEventHandler(h2);
	}

	/****************************************
	 * Обработка событий библиотеки Icq     *
	 ****************************************/
	@Override
	public void onXStatusRequest(XStatusRequestEvent e) {
		try {
    		OscarInterface.sendXStatus(con, new XStatusModeEnum(xstatus),
    				xstatustxt1,
    				xstatustxt2, e.getTime(), e.getMsgID(), e.getSenderID(), e.getSenderTcpVersion());
    	}
    	catch(ConvertStringException ex) {
    		System.err.println(ex.getMessage());
    	}
	}

	@Override
	public void onXStatusResponse(XStatusResponseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIncomingMessage(IncomingMessageEvent e) {
//		if(FileUtils.isIgnor(e.getSenderID())){
//			logger.flood2("IGNORE LIST: " + e.getMessageId() + "->" + screenName + ": " + e.getMessage());
//			return;
//		}
		// Сервисное сообщение от УИНа 1
		if(e.getSenderID().equals("1")){
		    logger.error(screenName, "Ошибка совместимости клиента ICQ. Будет произведена попытка переподключения...");
		    try{
                con.close();
                logout(screenName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
		    return;
		}
//        notifyMsg(new Message(e.getSenderID(),screenName,e.getMessage(),Message.TYPE_TEXT));
		if(e.getMessage().indexOf(p.getIgnoreSubstring())>0) {
			logger.print("IGNORE", screenName, e.getMessage());
			return;
		}
        eva.incomingMessage(new Message(e.getSenderID(),screenName,e.getMessage(),Message.TYPE_TEXT));
	}

	@Override
	public void onIncomingUrl(IncomingUrlEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOfflineMessage(OfflineMessageEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageAck(MessageAckEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageError(MessageErrorEvent e) {
		logger.error(screenName, "Message error " + e.getError().toString());
	}

	@Override
	public void onMessageMissed(MessageMissedEvent e) {
		logger.debug(screenName, "Message from " + e.getUin() + " can't be recieved because " + e.getReason()  +
				" count="+e.getMissedMsgCount());
	}

	@Override
	public void onLogout(Exception exception) {
		System.err.println("Разрыв соединения: " + screenName + " - " + exception.getMessage());
		logger.error(screenName, "Разрыв соединения: " + screenName + " - " + exception.getMessage());
        lastError = exception.getMessage();
        disconnect();
//        notifyLogout();
        eva.protocolChangeState(screenName, EventProxy.STATE_LOGOFF, null);
        // TODO подумать где отключать таймер и нужно ли в случае ошибок очищать очередь
        try {
        	System.err.println("Pause reConnect...");
			Thread.sleep(60000);
			connect();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onLogin() {
		OscarInterface.changeStatus(con, new StatusModeEnum(status));
		OscarInterface.changeXStatus(con, new XStatusModeEnum(xstatus));
//        notifyLogon();
        connected = true;
        eva.protocolChangeState(screenName, EventProxy.STATE_LOGON, null);
	}

	@Override
	public void onAuthorizationFailed(LoginErrorEvent e) {
		logger.error(screenName, "Authorization for " + screenName + " failed, reason " + e.getErrorMessage());
        lastError = e.getErrorMessage();
        disconnect();
//        notifyLogout();
	}

	@Override
	public void onStatusResponse(StatusEvent e) {
		// TODO Auto-generated method stub
		
	}

}
