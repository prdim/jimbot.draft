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

package ru.jimbot.protocol;

import ru.caffeineim.protocols.icq.core.OscarConnection;
import ru.caffeineim.protocols.icq.exceptions.ConvertStringException;
import ru.caffeineim.protocols.icq.integration.events.*;
import ru.caffeineim.protocols.icq.integration.listeners.MessagingListener;
import ru.caffeineim.protocols.icq.integration.listeners.OurStatusListener;
import ru.caffeineim.protocols.icq.integration.listeners.XStatusListener;
import ru.caffeineim.protocols.icq.setting.enumerations.StatusModeEnum;
import ru.caffeineim.protocols.icq.setting.enumerations.XStatusModeEnum;
import ru.caffeineim.protocols.icq.tool.OscarInterface;
import ru.jimbot.core.*;
import ru.jimbot.core.events.ProtocolLogonEvent;
import ru.jimbot.core.events.ProtocolLogoutEvent;
import ru.jimbot.util.Log;
import ru.jimbot.util.MainProps;

/**
 * Работа с протоколом ICQ
 *
 * @author Prolubnikov Dmitry
 */
public class IcqProtocol implements Protocol, CommandProtocolListener,
        OurStatusListener, MessagingListener, XStatusListener {
    private OscarConnection con=null;
    	private String lastInfo = "";
    private Service srv; // Ссылка на сервис
    private String screenName; // УИН
    private int screenNameID = 0; // ИД УИНа в настройках (чтобы вытащить параметры, пароль и т.п.
    private int status = 0;
    private String statustxt1 = "";
    private String statustxt2 = "";
    private boolean connected = false;

    public IcqProtocol(Service s, int id) {
        srv = s;
        screenNameID = id;
        screenName = srv.getProps().getUin(id);
        // Добавляемся в слушатели
        srv.addCommandProtocolListener(this);
    }

    private void notifyMsg(Message m) {
        for(ProtocolListener i:srv.getProtocolListeners()) {
            i.onTextMessage(m);
        }
    }

    private void notifyStatus(Message m) {
        for(ProtocolListener i:srv.getProtocolListeners()) {
            i.onStatusMessage(m);
        }
    }

    private void notifyLogon() {
        srv.createEvent(new ProtocolLogonEvent(srv, screenName));
    }

    private void notifyLogout() {
        srv.createEvent(new ProtocolLogoutEvent(srv, screenName));
    }


    public void connect() {
        status = srv.getProps().getIntProperty("icq.xstatus");
        statustxt1 = srv.getProps().getStringProperty("icq.STATUS_MESSAGE1");
        statustxt2 = srv.getProps().getStringProperty("icq.STATUS_MESSAGE2");
		con = new OscarConnection(MainProps.getServer(), MainProps.getPort(), screenName, srv.getProps().getPass(screenNameID));
        con.getPacketAnalyser().setDebug(false);
        con.addOurStatusListener(this);
        con.addMessagingListener(this);
        con.addXStatusListener(this);
        con.connect();
        connected = true;
    }

    public void reConnect() {
        try {
            con.close();
            notifyLogout();
        } catch (Exception ex) {
            ex.printStackTrace();
            connected = false;
        }
        con.connect();
//        connected = true;
    }

    public void disconnect() {
        try {
            con.close();
            con.removeOurStatusListener(this);
            con.removeMessagingListener(this);
            con.removeXStatusListener(this);
            notifyLogout();
        } catch (Exception ex) {
            ex.printStackTrace();
            connected = false;
        }
    }

    public void setStatus(int status) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isOnLine() {
        if(con==null) return false;
        return connected;
    }

    public void sendMsg(String sn, String msg) {
        try {
			OscarInterface.sendBasicMessage(con, sn, msg);
		} catch (ConvertStringException e) {
			Log.getLogger(srv.getName()).info("ERROR send message: " + msg);
			e.printStackTrace();
		}
    }

    public void getStatus(String sn, int status) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addContactList(String sn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void RemoveContactList(String sn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setConnectData(String server, int port, String screenName, String pass) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getScreenName() {
        return screenName;
    }

    /**
     *************************************************************************************
     */

    /**
     *
     * @param id
     * @param text
     */
    public void onChangeStatus(int id, String text) {
        srv.getProps().setIntProperty("icq.status", id);
        OscarInterface.changeStatus(con, new StatusModeEnum(srv.getProps().getIntProperty("icq.status")));
    }

    public void onChangeXStatus(int id, String text1, String text2) {
        status = id;
        statustxt1 = text1;
        statustxt2 = text2;

        OscarInterface.changeXStatus(con, new XStatusModeEnum(status));
    }

    public void sendMessage(String in, String out, String text) {
        if(screenName.equalsIgnoreCase(in)) sendMsg(out,text);
    }

    public void logOn() {
        connect();
    }

    public void logOut() {
        disconnect();
    }

    /**
     * *********************************************************************************
     */

    /**
     *
     * @param exception
     */
    public void onLogout(Exception exception) {
		Log.getLogger(srv.getName()).error("Разрыв соединения: " + screenName);
        notifyLogout();
        connected = false;
    }

    public void onLogin() {
        OscarInterface.changeStatus(con, new StatusModeEnum(srv.getProps().getIntProperty("icq.status")));
		OscarInterface.changeXStatus(con, new XStatusModeEnum(status));
        notifyLogon();
        connected = true;
    }

    public void onAuthorizationFailed(LoginErrorEvent e) {
        Log.getLogger(srv.getName()).error("Authorization for " + screenName + " failed, reason " + e.getErrorMessage());
        con.close();
        connected = false;
    }

    public void onStatusResponse(StatusEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * *******************************************************************************
     */

    /**
     *
     * @param e
     */
    public void onIncomingMessage(IncomingMessageEvent e) {
        if(MainProps.isIgnor(e.getSenderID())){
			Log.getLogger(srv.getName()).flood2("IGNORE LIST: " + e.getMessageId() + "->" + screenName + ": " + e.getMessage());
			return;
		}
		// Сервисное сообщение от УИНа 1
		if(e.getSenderID().equals("1")){
		    Log.getLogger(srv.getName()).error("Ошибка совместимости клиента ICQ. Будет произведена попытка переподключения...");
		    try{
                con.close();
                notifyLogout();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
		    return;
		}
        notifyMsg(new Message(e.getSenderID(),screenName,e.getMessage(),Message.TYPE_TEXT));
    }

    public void onIncomingUrl(IncomingUrlEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onOfflineMessage(OfflineMessageEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onMessageAck(MessageAckEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onMessageError(MessageErrorEvent e) {
        Log.getLogger(srv.getName()).error("Message error " + e.getError().toString());
    }

    public void onMessageMissed(MessageMissedEvent e) {
        Log.getLogger(srv.getName()).debug("Message from " + e.getUin() + " can't be recieved because " + e.getReason()  +
				" count="+e.getMissedMsgCount());
    }

    /**
     * *********************************************************************************************
     */

    public void onXStatusRequest(XStatusRequestEvent e) {
        try {
    		OscarInterface.sendXStatus(con, new XStatusModeEnum(status),
    				statustxt1,
    				statustxt2, e.getTime(), e.getMsgID(), e.getSenderID(), e.getSenderTcpVersion());
    	}
    	catch(ConvertStringException ex) {
    		System.err.println(ex.getMessage());
    	}
    }

    public void onXStatusResponse(XStatusResponseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
