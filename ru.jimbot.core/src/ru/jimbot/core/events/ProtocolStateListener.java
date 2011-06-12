/**
 * 
 */
package ru.jimbot.core.events;

import ru.jimbot.core.Message;

/**
 * @author spec
 *
 */
public interface ProtocolStateListener {
	public void onLogon(String sn);
	public void onLogout(String sn);
	public void onError(Message m);
}
