/**
 * 
 */
package ru.jimbot.core.events;

import ru.jimbot.core.Message;

/**
 * @author spec
 *
 */
public interface OutgoingMessageListener {
	public void onMessage(Message m);
}
