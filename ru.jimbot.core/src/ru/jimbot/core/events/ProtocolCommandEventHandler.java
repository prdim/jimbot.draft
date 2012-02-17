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
		result.put(EventConstants.EVENT_TOPIC, "ru/jimbot/core/default/protocol_command");
//		System.out.println(">>>REG " + "ru/jimbot/core/default/protocol_command/" + sn);
		return result;
	}
	
	@Override
	public void handleEvent(Event event) {
		if(!sn.equals(event.getProperty("screenname"))) return;
		int c = (Integer)event.getProperty("command");
		if(c==EventProxy.STATE_LOGON) {
			Thread t = new Thread() {
				@Override
				public void run() {
					lis.logon(sn); // Если логон зависнет, то события не будут обрабатываться до таймаута соединения
				}
			};
			t.start(); // Поэтому запустим его в отдельном потоке
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
