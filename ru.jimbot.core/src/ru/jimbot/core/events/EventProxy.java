/**
 * 
 */
package ru.jimbot.core.events;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import ru.jimbot.core.Message;

/**
 * Класс помогает генерировать общие события бота в OSGi
 * Данный клас содержит мои собственные соглашения о событиях в боте, придерживаться их необязательно
 * @author spec
 *
 */
public class EventProxy {
	public static final int STATE_LOGON = 1;
	public static final int STATE_LOGOFF = 0;
	public static final int STATE_ERROR = 2;
	public static final int CHANGE_STATUS = 3;
	public static final int CHANGE_XSTATUS = 4;
	
	private EventAdmin eva;
	private String serviceName;

	public EventProxy(EventAdmin e, String serviceName) {
		eva = e;
		this.serviceName = serviceName;
		System.out.println(">>>" + serviceName + ":" + eva);
	}

	/**
	 * Создать событие входящего сообщения. В топик помещается имя сервиса, который должен это сообщение обработать
	 * @param m
	 */
	public void incomingMessage(Message m) {
		Dictionary<String, Object> p = new Hashtable<String, Object>();
		p.put("message", m);
		p.put("service", serviceName);
		eva.postEvent(new Event("ru/jimbot/core/default/incoming_message/" + serviceName, p));
	}
	
	/**
	 * Сгенерировать событие исходящего сообщение, в топик помещается screen name протокола, который должен это сообщение отправить
	 * @param m
	 */
	public void outgoingMessage(Message m) {
		Dictionary<String, Object> p = new Hashtable<String, Object>();
		String sn = m.getSnIn();
		p.put("message", m);
		p.put("service", serviceName);
		eva.postEvent(new Event("ru/jimbot/core/default/outgoing_message/" + sn, p));
	}
	
	/**
	 * Сгенерировать событие управления протоколом
	 * @param sn
	 * @param state
	 */
	public void protocolCommand(String sn, int state) {
		Dictionary<String, Object> p = new Hashtable<String, Object>();
		p.put("service", serviceName);
		p.put("sreenname", sn);
		p.put("command", state);
		eva.postEvent(new Event("ru/jimbot/core/default/protocol_command/" + sn, p));
	}
	
	/**
	 * Сгенерировать событие изменения статуса
	 * @param sn
	 * @param i
	 * @param s
	 */
	public void changeStatus(String sn, int i, String s) {
		Dictionary<String, Object> p = new Hashtable<String, Object>();
		p.put("service", serviceName);
		p.put("sreenname", sn);
		p.put("command", CHANGE_STATUS);
		p.put("status", i);
		p.put("statustxt", s);
		eva.postEvent(new Event("ru/jimbot/core/default/protocol_command/" + sn, p));
	}
	
	/**
	 * Сгенерировать событие изменения X-статуса
	 * @param sn
	 * @param i
	 * @param s1
	 * @param s2
	 */
	public void changeXStatus(String sn, int i, String s1, String s2) {
		Dictionary<String, Object> p = new Hashtable<String, Object>();
		p.put("service", serviceName);
		p.put("sreenname", sn);
		p.put("command", CHANGE_XSTATUS);
		p.put("status", i);
		p.put("statustxt1", s1);
		p.put("statustxt2", s2);
		eva.postEvent(new Event("ru/jimbot/core/default/protocol_command/" + sn, p));
	}
	
	/**
	 * Сгенерировать событие изменения состояния протокола
	 * @param sn
	 * @param state
	 * @param m
	 */
	public void protocolChangeState(String sn, int state, Message m) {
		Dictionary<String, Object> p = new Hashtable<String, Object>();
		p.put("service", serviceName);
		p.put("sreenname", sn);
		p.put("state", state);
		if(state == STATE_ERROR) p.put("message", m);
		eva.postEvent(new Event("ru/jimbot/core/default/protocol_state/" + serviceName, p));
	}
}
