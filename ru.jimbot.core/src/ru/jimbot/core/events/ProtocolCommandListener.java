/**
 * 
 */
package ru.jimbot.core.events;

/**
 * @author spec
 *
 */
public interface ProtocolCommandListener {
	public void logon(String sn);
	public void logout(String sn);
	public void changeStatus(int id, String txt);
	public void changeXStatus(int id, String txt1, String txt2);
}
