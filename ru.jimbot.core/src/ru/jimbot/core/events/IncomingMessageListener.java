/**
 * 
 */
package ru.jimbot.core.events;

import ru.jimbot.core.Message;

/**
 * @author spec
 *
 */
public interface IncomingMessageListener {
	public void onTextMessage(Message m);
	public void onStatusMessage(Message m);
	public void onOtherMessage(Message m);
}
