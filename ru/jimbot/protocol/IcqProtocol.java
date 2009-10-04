/**
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2009 JimBot project
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

import java.util.Observable;
import java.util.Observer;

import ru.caffeineim.protocols.icq.contacts.ContactList;
import ru.caffeineim.protocols.icq.core.OscarConnection;
import ru.caffeineim.protocols.icq.exceptions.ConvertStringException;
import ru.caffeineim.protocols.icq.integration.events.ContactListEvent;
import ru.caffeineim.protocols.icq.integration.events.IncomingMessageEvent;
import ru.caffeineim.protocols.icq.integration.events.IncomingUrlEvent;
import ru.caffeineim.protocols.icq.integration.events.IncomingUserEvent;
import ru.caffeineim.protocols.icq.integration.events.LoginErrorEvent;
import ru.caffeineim.protocols.icq.integration.events.MessageAckEvent;
import ru.caffeineim.protocols.icq.integration.events.MessageErrorEvent;
import ru.caffeineim.protocols.icq.integration.events.MessageMissedEvent;
import ru.caffeineim.protocols.icq.integration.events.MetaAffilationsUserInfoEvent;
import ru.caffeineim.protocols.icq.integration.events.MetaBasicUserInfoEvent;
import ru.caffeineim.protocols.icq.integration.events.MetaEmailUserInfoEvent;
import ru.caffeineim.protocols.icq.integration.events.MetaInterestsUserInfoEvent;
import ru.caffeineim.protocols.icq.integration.events.MetaMoreUserInfoEvent;
import ru.caffeineim.protocols.icq.integration.events.MetaNoteUserInfoEvent;
import ru.caffeineim.protocols.icq.integration.events.MetaShortUserInfoEvent;
import ru.caffeineim.protocols.icq.integration.events.MetaWorkUserInfoEvent;
import ru.caffeineim.protocols.icq.integration.events.OffgoingUserEvent;
import ru.caffeineim.protocols.icq.integration.events.OfflineMessageEvent;
import ru.caffeineim.protocols.icq.integration.events.SsiAuthReplyEvent;
import ru.caffeineim.protocols.icq.integration.events.SsiAuthRequestEvent;
import ru.caffeineim.protocols.icq.integration.events.SsiFutureAuthGrantEvent;
import ru.caffeineim.protocols.icq.integration.events.SsiModifyingAckEvent;
import ru.caffeineim.protocols.icq.integration.events.StatusEvent;
import ru.caffeineim.protocols.icq.integration.events.UINRegistrationFailedEvent;
import ru.caffeineim.protocols.icq.integration.events.UINRegistrationSuccessEvent;
import ru.caffeineim.protocols.icq.integration.events.XStatusRequestEvent;
import ru.caffeineim.protocols.icq.integration.events.XStatusResponseEvent;
import ru.caffeineim.protocols.icq.integration.listeners.ContactListListener;
import ru.caffeineim.protocols.icq.integration.listeners.MessagingListener;
import ru.caffeineim.protocols.icq.integration.listeners.MetaInfoListener;
import ru.caffeineim.protocols.icq.integration.listeners.StatusListener;
import ru.caffeineim.protocols.icq.integration.listeners.XStatusListener;
import ru.caffeineim.protocols.icq.setting.enumerations.AffilationEnum;
import ru.caffeineim.protocols.icq.setting.enumerations.InterestsEnum;
import ru.caffeineim.protocols.icq.setting.enumerations.LanguagesEnum;
import ru.caffeineim.protocols.icq.setting.enumerations.PostBackgroundEnum;
import ru.caffeineim.protocols.icq.setting.enumerations.StatusModeEnum;
import ru.caffeineim.protocols.icq.setting.enumerations.XStatusModeEnum;
import ru.caffeineim.protocols.icq.tool.OscarInterface;
import ru.jimbot.core.*;
import ru.jimbot.core.events.ProtocolLogonEvent;
import ru.jimbot.core.events.ProtocolLogoutEvent;
import ru.jimbot.modules.chat.Users;
import ru.jimbot.util.Log;
import ru.jimbot.util.MainProps;

/**
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class IcqProtocol implements Protocol, MessagingListener, StatusListener, XStatusListener,
						 ContactListListener, MetaInfoListener, Observer, CommandProtocolListener {
	private OscarConnection con = null;
//	private AbstractProps props;
	private String lastInfo = "";
    private Service srv; // Ссылка на сервис
    private String screenName; // УИН
    private int screenNameID = 0; // ИД УИНа в настройках (чтобы вытащить параметры, пароль и т.п.
    private int status = 0;
    private String statustxt1 = "";
    private String statustxt2 = "";
	
	public IcqProtocol(Service s, int id) {
//		this.props = props;
//		server = MainProps.getServer();
//		port = MainProps.getPort();
		
//		mq = new MsgOutQueue(this, props.getIntProperty("bot.pauseOut"),
//                props.getIntProperty("bot.pauseRestart"),
//                props.getIntProperty("bot.msgOutLimit"));
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
//        for(ProtocolListener i:srv.getProtocolListeners()) {
//            i.logOn(screenName);
//        }
    }

    private void notifyLogout() {
        srv.createEvent(new ProtocolLogoutEvent(srv, screenName));
//        for(ProtocolListener i:srv.getProtocolListeners()) {
//            i.logOut(screenName);
//        }
    }
	
//	public AbstractProps getProps(){
//		return props;
//	}
	
//	public int getOuteqSize(){
//		return mq.size();
//	}

	public void RemoveContactList(String sn) {
		// TODO Auto-generated method stub

	}

    /**
     * Установить параметры соединения
     *
     * @param server
     * @param port
     * @param screenName
     * @param pass
     */
    public void setConnectData(String server, int port, String screenName, String pass) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Возвращает УИН
     *
     * @return
     */
    public String getScreenName() {
        return screenName;  //To change body of implemented methods use File | Settings | File Templates.
    }

	public void addContactList(String sn) {
		// TODO Auto-generated method stub

	}

    /**
     * Добавить слушатель
     *
     */
//    public void addListener(ProtocolListener p) {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }

	public void connect() {
//		mq.start();
        status = srv.getProps().getIntProperty("icq.xstatus");
        statustxt1 = srv.getProps().getStringProperty("icq.STATUS_MESSAGE1");
        statustxt2 = srv.getProps().getStringProperty("icq.STATUS_MESSAGE2");
		con = new OscarConnection(MainProps.getServer(), MainProps.getPort(), screenName, srv.getProps().getPass(screenNameID));
		con.getPacketAnalyser().setDebug(false);
		con.getPacketAnalyser().setDump(false);
		con.addMessagingListener(this);
		con.addStatusListener(this);
        con.addXStatusListener(this);
        con.addContactListListener(this);
        con.addMetaInfoListener(this);
		// Запросим сообщения, присланные нам в оффлайн
//        OscarInterface.requestOfflineMessages(con); 
        
		con.addObserver(this);
	}
	
	/**
	 * Подключение после разрыва соединения. Добавление слушателей не требуется.
	 */
	public void reConnect(){
		try {
			con.close();
            notifyLogout();
//			con.removeContactListListener(this);
//			con.removeMessagingListener(this);
//			con.removeStatusListener(this);
//			con.removeXStatusListener(this);
//			con.deleteObservers();
//			con = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
//		con = new OscarConnection(server, port, screenName, password);
//		con.getPacketAnalyser().setDebug(false);
//		con.getPacketAnalyser().setDump(false);
//		con.addMessagingListener(this);
//		con.addStatusListener(this);
//        con.addXStatusListener(this);
//        con.addContactListListener(this);
//        con.addObserver(this);
        con.getClient().connectToServer();
	}

	public void disconnect() {
//		mq.stop();
		try {
//			System.out.println("Close: " + con.getUserId());
			con.close();
			con.removeContactListListener(this);
			con.removeMessagingListener(this);
			con.removeStatusListener(this);
			con.removeXStatusListener(this);
			con.deleteObservers();
            notifyLogout();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

//	@Override
//	public void getMsg(String sendSN, String recivSN, String msg,
//			boolean isOffline) {
//		protList.getMsg(sendSN, recivSN, msg, isOffline);
//
//	}

	public void getStatus(String sn, int status) {
		// TODO Auto-generated method stub

	}

	public boolean isOnLine() {
		if(con==null) return false;
		return con.isLogged();
	}

//	@Override
//	public boolean isOnlineStatus(int i) {
//		// TODO Auto-generated method stub
//		return true;
//	}

	public void sendMsg(String sn, String msg) {
		try {
			OscarInterface.sendBasicMessage(con, sn, msg);
//			OscarInterface.sendExtendedMessage(con, sn, msg);
		} catch (ConvertStringException e) {
			Log.info("ERROR send message: " + msg);
			e.printStackTrace();
		}

	}

	public void setStatus(int status) {
		// TODO Auto-generated method stub

	}
	
	public boolean userInfoRequest(String sn, String rsn){
		lastInfo = sn;
//		OscarInterface.requestFullUserInfo(con, sn);
		OscarInterface.requestShortUserInfo(con, sn);
		return true;
	}

//	public boolean isNoAuthUin(String uin){
//		return props.getBooleanProperty("chat.isAuthRequest");
//	}
	
	public void authRequest(String uin, String msg){
		try {
			ContactList.sendAuthRequestMessage(con, uin, msg);
		} catch (ConvertStringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/********************************************************************************
	 * 
	 */
	
	/**
	 * Входящее сообщение
	 */
	public void onIncomingMessage(IncomingMessageEvent e) {
//		System.out.println(e.getSenderID() + " MSG : " + e.getMessage());
		if(MainProps.isIgnor(e.getSenderID())){
			Log.flood2("IGNORE LIST: " + e.getMessageId() + "->" + screenName + ": " + e.getMessage());
			return;
		}
		// Сервисное сообщение от УИНа 1
		if(e.getSenderID().equals("1")){
		    Log.error("Ошибка совместимости клиента ICQ. Будет произведена попытка переподключения...");
		    try{
                con.close();
                notifyLogout();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
		    return;
		}
        notifyMsg(new Message(e.getSenderID(),screenName,e.getMessage(),Message.TYPE_TEXT));
//		protList.getMsg(e.getSenderID(), screenName, e.getMessage(), false);
	}

	public void onIncomingUrl(IncomingUrlEvent e) {
//		System.out.println(e.getSenderID() + " MSG URL : " + e.getUrl());
		
	}

	public void onMessageAck(MessageAckEvent e) {
//		System.out.println("MessageAck " + e.getRcptUin());
		
	}

	public void onMessageError(MessageErrorEvent e) {
		Log.error("Message error code " + e.getErrorCode() + " occurred");
//		System.out.println("Message error code " + e.getErrorCode() + " occurred");
		
	}

	public void onMessageMissed(MessageMissedEvent e) {
		Log.debug("Message from " + e.getUin() + " can't be recieved because " + e.getReason()  +
				" count="+e.getMissedMsgCount());
//		System.out.println("Message from " + e.getUin() + " can't be recieved because " + e.getReason());
		
	}

	/**
	 * Офлайновые сообщения
	 */
	public void onOfflineMessage(OfflineMessageEvent e) {
//		System.out.println(e.getSenderUin() + " sent new offline message");
//        System.out.println(" text: " + e.getMessage());
//        System.out.println(" date: " + e.getSendDate());
//        System.out.println(" type: " + e.getMessageType());
//        System.out.println(" flag: " + e.getMessageFlag()); 
		
	}

	/**
	 * ошибка авторизации
	 */
	public void onAuthorizationFailed(LoginErrorEvent arg0) {
        Log.error("Authorization for " + screenName + " failed, reason " + arg0.getErrorMessage());
//		System.out.println("Authorization Failed! You UIN or Password is not valid");
		
	}

	/**
	 * юзер пришел
	 */
	public void onIncomingUser(IncomingUserEvent e) {
		Log.debug(e.getIncomingUserId() + " has just signed on.");
        notifyStatus(new Message(e.getIncomingUserId(),screenName,e.getStatusMode().toString(), e.getStatusFlag().getFlag()));
//		protList.getStatus(e.getIncomingUserId(), 0);
//		System.out.println(e.getIncomingUserId() + " has just signed on.");
		
	}

	public void onLogout() {
		Log.error("Разрыв соединения: " + screenName /*+ " - " + server + ":" + port*/);
        notifyLogout();
//		try {
//			con.close();
//		} catch (IOException e) {		
//			e.printStackTrace();
//		}		
	}

	/**
	 * юзер ушел
	 */
	public void onOffgoingUser(OffgoingUserEvent e) {
//		Log.info(e.getOffgoingUserId() + " went offline.");
        notifyStatus(new Message(e.getOffgoingUserId(),screenName,"",-1));
//		protList.getStatus(e.getOffgoingUserId(), -1);
//		System.out.println(e.getOffgoingUserId() + " went offline.");
		
	}

	public void onStatusChange(StatusEvent e) {
		Log.debug("StatusEvent: " + e.getStatusMode());
//		protList.getStatus(e.getStatusMode(), status)
//		System.out.println("StatusEvent: " + e.getStatusMode());
		
	}

	public void onXStatusChange(XStatusResponseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onXStatusRequest(XStatusRequestEvent e) {
		// Посылаем свой статус, если просят
    	try {
    		OscarInterface.sendXStatus(con, new XStatusModeEnum(status),
    				statustxt1,
    				statustxt2, e.getTime(), e.getMsgID(), e.getSenderID(), e.getSenderTcpVersion());
    	}
    	catch(ConvertStringException ex) {
    		System.err.println(ex.getMessage());
    	}
		
	}

    /**
     * Метод будет вызван при установке соединения
     * @param arg0
     * @param arg1
     */
	public void update(Observable arg0, Object arg1) {
		OscarInterface.changeStatus(con, new StatusModeEnum(srv.getProps().getIntProperty("icq.status")));
		OscarInterface.changeXStatus(con, new XStatusModeEnum(status));
        notifyLogon();
	}

	public void onSsiAuthReply(SsiAuthReplyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onSsiAuthRequest(SsiAuthRequestEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onSsiFutureAuthGrant(SsiFutureAuthGrantEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onSsiModifyingAck(SsiModifyingAckEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void updateContactList(ContactListEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onAffilationsUserInfo(MetaAffilationsUserInfoEvent e) {
		System.out.println("PostBackgrounds User Info: ");
		for (PostBackgroundEnum code : e.getPostBackgrounds().keySet()) {
			System.out.println("Category: " + code + " postbackground: " + e.getPostBackgrounds().get(code));
		}
		
		System.out.println("Affilations User Info: ");
		for (AffilationEnum code : e.getAffilations().keySet()) {
			System.out.println("Category: " + code + " affilations: " + e.getAffilations().get(code));
		}
		
	}

	public void onBasicUserInfo(MetaBasicUserInfoEvent e) {
		Users u = new Users();
		u.sn = lastInfo;
		u.nick = e.getNickName();
		u.fname = e.getFirstName();
		u.lname = e.getLastName();
		u.email = e.getEmail();
		u.city = e.getHomeCity();
		u.country = e.getHomeCountry().getCountry();
//		protList.getInfo(u, 1);
		
//		System.out.println("Basic User Info: ");
//    	System.out.println("  Nick Name = "  + e.getNickName());
//    	System.out.println("  First Name = " + e.getFirstName());
//    	System.out.println("  Last Name = "  + e.getLastName());
//    	System.out.println("  Email = "      + e.getEmail());   
//    	System.out.println("  Home City = "  + e.getHomeCity());
//    	System.out.println("  Home State = " + e.getHomeState());
//    	System.out.println("  Home Phone = " + e.getHomePhone());
//    	System.out.println("  Home Fax = "   + e.getHomeFax());
//    	System.out.println("  Home Address = "  + e.getHomeAddress());
//    	System.out.println("  Cell Phone = "  + e.getCellPhone());
//    	System.out.println("  Zip = "         + e.getZipCode());
//    	System.out.println("  Home Country = "  + e.getHomeCountry());
//    	System.out.println("  GMT offset = "  + e.getTimeZone());
//    	
//    	System.out.println("  Auth = "       + e.isAuth());
//    	System.out.println("  WebAware = "       + e.isWebaware());
//    	System.out.println("  DirectConnection = " + e.isDirectConnection());
//    	System.out.println("  PublishPrimaryEmail = " + e.isPublishPrimaryEmail());		
	}

	public void onEmailUserInfo(MetaEmailUserInfoEvent e) {
		System.out.println("Email User Info: ");
		for (String email : e.getEmails())
		{
			System.out.println("Email: " + email);
		}
		
	}

	public void onInterestsUserInfo(MetaInterestsUserInfoEvent e) {
		System.out.println("Interests User Info: ");
		for (InterestsEnum code : e.getInterests().keySet()) {
			System.out.println("Category: " + code + " interest: " + e.getInterests().get(code));
		}
		
	}

	public void onMoreUserInfo(MetaMoreUserInfoEvent e) {
		System.out.println("More User Info");
		System.out.println(" age = " + e.getAge());
		System.out.println(" gender = " + e.getGender());
		System.out.println(" homePage = " + e.getHomePage());
		System.out.println(" birth = " + e.getBirth());
		System.out.println(" languages:");
		for (LanguagesEnum lang : e.getLanguages()) {
			System.out.println(lang);
		}
		System.out.println(" original City = " + e.getOriginalCity());
		System.out.println(" original State = " + e.getOriginalState());
		System.out.println(" original Country = " + e.getOriginalCountry());
		System.out.println(" marital Status = " + e.getMaritalStatus());
		
	}

	public void onNotesUserInfo(MetaNoteUserInfoEvent e) {
		System.out.println(" About info = " + e.getNote());
		
	}

	public void onRegisterNewUINFailed(UINRegistrationFailedEvent e) {
		System.out.println("UIN Registration filed!");
		
	}

	public void onRegisterNewUINSuccess(UINRegistrationSuccessEvent e) {
		System.out.println("Registration of new number complete");
        System.out.println("New UIN: " + e.getNewUIN());
		
	}

	public void onShortUserInfo(MetaShortUserInfoEvent e) {
		Users u = new Users();
		u.sn = lastInfo;
		u.nick = e.getNickName();
		u.fname = e.getFirstName();
		u.lname = e.getLastName();
		u.email = e.getEmail();
//		protList.getInfo(u, 1);
		
//		System.out.println("Short User Info: ");
//    	System.out.println("  Nick Name = "  + e.getNickName());
//    	System.out.println("  First Name = " + e.getFirstName());
//    	System.out.println("  Last Name = "  + e.getLastName());
//    	System.out.println("  Email = "      + e.getEmail());    	    	
//    	System.out.println("  Auth = "       + e.isAuth());			
	}

	public void onWorkUserInfo(MetaWorkUserInfoEvent e) {
		System.out.println("Work User Info: ");
    	System.out.println("  Work City = "  + e.getWorkCity());
    	System.out.println("  Work State = "  + e.getWorkState());
    	System.out.println("  Work Phone = "  + e.getWorkPhone());
    	System.out.println("  Work Fax = "  + e.getWorkFax());
    	System.out.println("  Work Address = "  + e.getWorkAddress());
    	System.out.println("  Work Zip = "  + e.getWorkZip());
    	System.out.println("  Work Country = "  + e.getWorkCountry());
    	System.out.println("  Work Company = "  + e.getWorkCompany());
    	System.out.println("  Work Department = "  + e.getWorkDepartment());
    	System.out.println("  Work Position = "  + e.getWorkPosition());
    	System.out.println("  Work WebPage = "  + e.getWorkWebPage());
    	System.out.println("  Work Occupation = "  + e.getWorkOccupation());
		
	}

    /**
     * Слушатель команд управления протоколом
     */

    /**
     * Установить статус
     *
     * @param id
     * @param text
     */
    public void onChangeStatus(int id, String text) {
        srv.getProps().setIntProperty("icq.status", id);
        OscarInterface.changeStatus(con, new StatusModeEnum(srv.getProps().getIntProperty("icq.status")));
    }

    /**
     * Установить Х-статус
     *
     * @param id
     * @param text1
     */
    public void onChangeXStatus(int id, String text1, String text2) {
        status = id;
        statustxt1 = text1;
        statustxt2 = text2;

        OscarInterface.changeXStatus(con, new XStatusModeEnum(status));
    }

    /**
     * Отправить текстовое сообщение
     *
     * @param in   - от кого
     * @param out  - кому
     * @param text - сообщение
     */
    public void sendMessage(String in, String out, String text) {
        if(screenName.equalsIgnoreCase(in)) sendMsg(out,text);
    }

    /**
     * Подключиться к серверу
     */
    public void logOn() {
        connect();
    }

    /**
     * Отключиться от сервера
     */
    public void logOut() {
        disconnect();
    }
}
