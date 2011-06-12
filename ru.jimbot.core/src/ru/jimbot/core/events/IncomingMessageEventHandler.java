/**
 * 
 */
package ru.jimbot.core.events;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import ru.jimbot.core.Message;
import ru.jimbot.core.MsgInQueue;

/**
 * Обработчик события входящих сообщений. Должен быть зарегистрирован в качестве сервиса чтобы перехватывать события.
 * Помещает входящее сообщение в заданную очередь входящих.
 * @author spec
 *
 */
public class IncomingMessageEventHandler implements EventHandler {
	private String serviceName;
	private IncomingMessageListener lis;

	/**
	 * 
	 * @param serviceName - имя сервиса бота, по нему настраивается фильтр топика событий
	 * @param l - очередь входящих, в которую будут помещаться сообщения
	 */
	public IncomingMessageEventHandler(String serviceName, IncomingMessageListener l) {
		super();
		this.serviceName = serviceName;
		this.lis = l;
	}

	/**
	 * Топик, по которому будут фильтроваться события
	 * @return
	 */
	public Dictionary<String, Object> getHandlerServiceProperties() {
		Dictionary<String, Object> result = new Hashtable<String, Object>();
		result.put(EventConstants.EVENT_TOPIC, "ru/jimbot/core/default/incoming_message/" + serviceName);
		return result;
	}

	@Override
	public void handleEvent(Event event) {
		Message m = (Message)event.getProperty("message");
		if(m.getType() == Message.TYPE_TEXT) {
			lis.onTextMessage(m);
		} else if(m.getType() == Message.TYPE_STATUS) {
			lis.onStatusMessage(m);
		} else {
			lis.onOtherMessage(m);
		}
	}

}
