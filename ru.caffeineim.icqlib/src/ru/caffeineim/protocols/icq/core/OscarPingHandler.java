/**
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package ru.caffeineim.protocols.icq.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.caffeineim.protocols.icq.packet.sent.generic.KeepAlive;


/**
 * <p>Created by 22.06.2008
 *   @author Samolisov Pavel
 */
// TODO найти другое решение, без пингующих пакетов. ICQ этого не любит
public class OscarPingHandler implements Runnable {

	private static Log log = LogFactory.getLog(OscarPacketHandler.class);

	public static final String THREAD_NAME = "OscarPingHandlerThread";

	private static long uin = 300300;

	private Thread thread;
	private long interval;
	private OscarConnection connection;
	private boolean running;

	public OscarPingHandler(OscarConnection connection, long interval) {
		this.connection = connection;
		this.interval = interval;
		this.running = true;

		thread = new Thread(this, THREAD_NAME);
		thread.start();
	}

	public void run() {
		try {
			while (connection.getClient().isRuning() && running) {
				if(connection.isAuthorized()) {
//					log.debug("PING!!!");
					connection.sendFlap(new KeepAlive());
				}
				Thread.sleep(interval);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void stop() {
		log.debug("OscarPingHandler thread has been stoped");
    	running = false;
    }
}
