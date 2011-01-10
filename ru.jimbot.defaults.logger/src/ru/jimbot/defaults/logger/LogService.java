/**
 * 
 */
package ru.jimbot.defaults.logger;

import ru.jimbot.core.ExtendPoint;
import ru.jimbot.core.services.Log;

/**
 * Прототип сервиса логирования. Позже нужно доработать
 * @author spec
 *
 */
public class LogService extends Log implements ExtendPoint {
	private int level;
	
	public LogService() {
		level = DefaultLogConfig.getInstance().isDebugMode() ? Log.DEBUG : Log.INFO;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#setLevel(int)
	 */
	@Override
	public void setLevel(int level) {
		this.level = DefaultLogConfig.getInstance().isDebugMode() ? Log.DEBUG : level;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#getLevel()
	 */
	@Override
	public int getLevel() {
		return level;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#print(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void print(String type, String name, Object msg) {
		System.out.println("LOG: [" + type + "]:[" + name + "]:" + msg);
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#debug(java.lang.String, java.lang.Object)
	 */
	@Override
	public void debug(String name, Object msg) {
		System.out.println("DEBUG: [" + name + "]:" + msg);
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#debug(java.lang.String, java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void debug(String name, Object msg, Throwable throwable) {
		System.err.println("DEBUG: [" + name + "]:" + msg);
		throwable.printStackTrace();
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#error(java.lang.String, java.lang.Object)
	 */
	@Override
	public void error(String name, Object msg) {
		System.err.println("ERROR: [" + name + "]:" + msg);
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#error(java.lang.String, java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void error(String name, Object msg, Throwable throwable) {
		System.err.println("ERROR: [" + name + "]:" + msg);
		throwable.printStackTrace();
	}

	@Override
	public String getType() {
		return "ru.jimbot.core.services.Log";
	}

	@Override
	public void unreg() {
		debug("Default Log Service", "Unreg this");
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "DefaultLogService";
	}

}
