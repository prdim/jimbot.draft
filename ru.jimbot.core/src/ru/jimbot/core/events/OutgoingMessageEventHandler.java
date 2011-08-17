package ru.jimbot.core.events;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import ru.jimbot.core.Message;

public class OutgoingMessageEventHandler implements EventHandler {
	private String sn;
	private OutgoingMessageListener lis;

	/**
	 * @param sn
	 * @param lis
	 */
	public OutgoingMessageEventHandler(String sn, OutgoingMessageListener lis) {
		this.sn = sn;
		this.lis = lis;
	}

	/**
	 * Топик, по которому будут фильтроваться события
	 * @return
	 */
	public Dictionary<String, Object> getHandlerServiceProperties() {
		Dictionary<String, Object> result = new Hashtable<String, Object>();
		result.put(EventConstants.EVENT_TOPIC, "ru/jimbot/core/default/outgoing_message");
		return result;
	}

	@Override
	public void handleEvent(Event event) {
		if(!sn.equals(event.getProperty("screenname"))) return;
		Message m = (Message)event.getProperty("message");
		lis.onMessage(m);
	}

}
