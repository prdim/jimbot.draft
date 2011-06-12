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

/**
 * @author spec
 *
 */
public class ProtocolStateEventHandler implements EventHandler {
	private String serviceName;
	private ProtocolStateListener lis;

	/**
	 * @param sn
	 * @param lis
	 */
	public ProtocolStateEventHandler(String serviceName, ProtocolStateListener lis) {
		this.serviceName = serviceName;
		this.lis = lis;
	}

	/**
	 * Топик, по которому будут фильтроваться события
	 * @return
	 */
	public Dictionary<String, Object> getHandlerServiceProperties() {
		Dictionary<String, Object> result = new Hashtable<String, Object>();
		result.put(EventConstants.EVENT_TOPIC, "ru/jimbot/core/default/protocol_state/" + serviceName);
		return result;
	}

	@Override
	public void handleEvent(Event event) {
		int c = (Integer)event.getProperty("command");
		String sn = (String)event.getProperty("sreenname");
		if(c==EventProxy.STATE_LOGON) {
			lis.onLogon(sn);
		} else if(c==EventProxy.STATE_LOGOFF) {
			lis.onLogout(sn);
		} else if(c==EventProxy.STATE_ERROR) {
			Message m = (Message)event.getProperty("message");
			lis.onError(m);
		}
	}

}
