/**
 * 
 */
package ru.jimbot.core.events;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

/**
 * @author spec
 *
 */
public class ProtocolCommandEventHandler implements EventHandler {
	private String sn;
	private ProtocolCommandListener lis;	
	
	/**
	 * @param sn
	 * @param lis
	 */
	public ProtocolCommandEventHandler(String sn, ProtocolCommandListener lis) {
		this.sn = sn;
		this.lis = lis;
	}
	
	/**
	 * Топик, по которому будут фильтроваться события
	 * @return
	 */
	public Dictionary<String, Object> getHandlerServiceProperties() {
		Dictionary<String, Object> result = new Hashtable<String, Object>();
		result.put(EventConstants.EVENT_TOPIC, "ru/jimbot/core/default/protocol_command/" + sn);
//		System.out.println(">>>REG " + "ru/jimbot/core/default/protocol_command/" + sn);
		return result;
	}
	
	@Override
	public void handleEvent(Event event) {
		int c = (Integer)event.getProperty("command");
//		System.out.println(">>>EVENT " + event.getTopic() + " : " + c);
		if(c==EventProxy.STATE_LOGON) {
			lis.logon(sn);
		} else if(c==EventProxy.STATE_LOGOFF) {
			lis.logout(sn);
		} else if(c==EventProxy.CHANGE_STATUS) {
			int i = (Integer)event.getProperty("status");
			String s = (String)event.getProperty("statustxt");
			lis.changeStatus(i, s);
		} else if(c==EventProxy.CHANGE_XSTATUS) {
			int i = (Integer)event.getProperty("status");
			String s1 = (String)event.getProperty("statustxt1");
			String s2 = (String)event.getProperty("statustxt2");
			lis.changeXStatus(i, s1, s2);
		}
	}

}
