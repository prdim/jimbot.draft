/**
 * 
 */
package ru.jimbot.protocol.xmpp;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;
import ru.jimbot.core.Destroyable;
import ru.jimbot.core.Message;
import ru.jimbot.core.events.EventProxy;
import ru.jimbot.core.events.OutgoingMessageEventHandler;
import ru.jimbot.core.events.OutgoingMessageListener;
import ru.jimbot.core.events.ProtocolCommandEventHandler;
import ru.jimbot.core.events.ProtocolCommandListener;
import ru.jimbot.core.services.Log;
import ru.jimbot.core.services.Protocol;
import ru.jimbot.protocol.xmpp.internal.ActivatorXmppProtocol;

/**
 * @author Prolubnikov Dmitry
 * @author Black_Kot
 * 
 */
public class XmppProtocol extends Destroyable implements Protocol,
		ProtocolCommandListener, OutgoingMessageListener, MessageListener,
		ChatManagerListener {
	private Log logger = ActivatorXmppProtocol.getExtendPointRegistry()
			.getLogger();
	private XMPPConnection con = null;
	private String screenName = ""; // УИН
	private String pass = "";
	private String server = "";
	private int port = 5222;
	private int status = 0;
	private String statustxt = "";
	// private int xstatus = 0;
	// private String xstatustxt = "";
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
	private XmppProtocolProperties p;

	public XmppProtocol(String serviceName, XmppProtocolProperties p) {
		super();
		this.serviceName = serviceName;
		this.p = p;
		// // Зарегистрируем себя в качестве обработчика событий
		// h1 = new OutgoingMessageEventHandler(screenName, this);
		// h2 = new ProtocolCommandEventHandler(screenName, this);
		// ActivatorIcqProtocol.regEventHandler(h1,
		// h1.getHandlerServiceProperties());
		// ActivatorIcqProtocol.regEventHandler(h2,
		// h2.getHandlerServiceProperties());
		eva = new EventProxy(ActivatorXmppProtocol.getEventAdmin(), serviceName);
		// timer = new Timer("queue out " + screenName);
		// qt = new TimerTask() {
		//
		// @Override
		// public void run() {
		// if(q.size()==0) return;
		// if((System.currentTimeMillis() - timeLastOutMsg) < pauseOutMsg)
		// return;
		// Message m = q.poll();
		// sendMsg(m.getSnOut(), m.getMsg());
		// timeLastOutMsg = System.currentTimeMillis();
		// }
		// };
	}

	@Override
	public void onMessage(Message m) {
		if (m.getMsg().length() > p.getMaxOutMsgSize()) {
			int cnt = m.getMsg().length() / p.getMaxOutMsgSize() + 1;
			int max = p.getMaxOutMsgCount() > cnt ? cnt : p.getMaxOutMsgCount();
			int maxx = p.getMaxOutMsgSize();
			for (int i = 0; i < max; i++) {
				if (((i + 1) * maxx - 1) < m.getMsg().length()) {
					if (i == (max - 1)) {
						q.add(m.getCopy(m.getMsg().substring(i * maxx,
								(i + 1) * maxx)
								+ "\nЧасть сообщения была обрезана..."));
					} else {
						q.add(m.getCopy(m.getMsg().substring(i * maxx,
								(i + 1) * maxx)));
					}
				} else {
					q.add(m.getCopy(m.getMsg().substring(i * maxx)));
				}
			}
			return;
		}
		if (q.size() > 0
				|| (System.currentTimeMillis() - timeLastOutMsg) < pauseOutMsg) {
			if (q.size() <= maxOutQueue) {
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
				if (q.isEmpty())
					return;
				if ((System.currentTimeMillis() - timeLastOutMsg) < pauseOutMsg)
					return;
				Message m = q.poll();
				sendMsg(m.getSnOut(), m.getMsg());
				timeLastOutMsg = System.currentTimeMillis();
			}
		};
		timer.schedule(qt, pauseOutMsg / 2, pauseOutMsg / 2);
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
		Presence.Type type = Type.available;
		Presence.Mode mode = Mode.chat;
		Presence presence = new Presence(type, statustxt, 30, mode);
		con.sendPacket(presence);
	}

	@Override
	public void changeXStatus(int id, String txt1, String txt2) {
		// xstatus = id;
		// xstatustxt = txt1;
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
		ActivatorXmppProtocol.regEventHandler(h1,
				h1.getHandlerServiceProperties());
		ActivatorXmppProtocol.regEventHandler(h2,
				h2.getHandlerServiceProperties());
	}

	@Override
	public void setLogger(Log logger) {
		this.logger = logger;
	}

	@Override
	public void setStatusData(int status, String text) {
		this.status = status;
		this.statustxt = text;
		setStatus(this.status, this.statustxt);
	}

	@Override
	public void setXStatusData(int status, String text1, String text2) {
		// this.xstatus = status;
		// this.xstatustxt = text1;
		// setStatus(this.status,this.xstatustxt);
	}

	@Override
	public void connect() {
		try {
			ConnectionConfiguration config = new ConnectionConfiguration(
					server, port, server);
			SASLAuthentication.supportSASLMechanism("PLAIN");
			con = new XMPPConnection(config);
			con.connect();
			con.login(screenName.split("@")[0], pass);
			con.getChatManager().addChatListener(this);
			setStatus(status, statustxt);
			connected = true;
		} catch (Exception ex) {
			logger.error(screenName, ex.getMessage(), ex);
		}

	}

	@Override
	public void disconnect() {
		try {
			// System.out.println("Disconnect...");
			con.disconnect();
			con.getChatManager().removeChatListener(this);
			con = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		connected = false;
	}

	@Override
	public boolean isOnLine() {
		if (con == null)
			return false;
		return connected;
	}

	public void sendMsg(String to, String message) {
		try {
			Chat chat = con.getChatManager().getThreadChat(to);
			if (chat == null)
				chat = con.getChatManager().createChat(to, to, this);
			chat.sendMessage(message);
			// con.getChatManager().removeChatListener(this);
			// chat.removeMessageListener(this);
		} catch (Exception e) {
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
		ActivatorXmppProtocol.unregEventHandler(h1);
		ActivatorXmppProtocol.unregEventHandler(h2);
	}

	/****************************************
	 * Обработка событий библиотеки Xmpp *
	 ****************************************/

	@Override
	public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message msg) {
		logger.debug(screenName, ">>> " + msg.getType() + ":" + msg.toXML());
		System.out.println(">>> " + msg.getType() + ":" + msg.toXML());
		if (msg.getType() == org.jivesoftware.smack.packet.Message.Type.chat) {
			if (msg.getBody() == null) return; // извещения о наборе сообщения
			String SenderID = chat.getParticipant().split("/")[0];
			eva.incomingMessage(new Message(SenderID, screenName, msg.getBody(), Message.TYPE_TEXT));
		}
	}

	@Override
	public void chatCreated(Chat chat, boolean bln) {
		if (!bln) {
			chat.addMessageListener(this);
		}
	}

	public void setStatus(int status, String statustxt) {
		Presence.Type type = Type.available;
		Presence.Mode mode = Mode.chat;
		switch (status) {
		case 1:
			mode = Mode.available;
			break;
		case 2:
			mode = Mode.chat;
			break;
		case 3:
			mode = Mode.away;
			break;
		case 4:
			mode = Mode.dnd;
			break;
		case 5:
			mode = Mode.xa;
			break;
		default:
			mode = Mode.available;
			break;
		}
		Presence presence = new Presence(type, statustxt, 30, mode);
		if (con == null)
			return;
		con.sendPacket(presence);
	}

}
