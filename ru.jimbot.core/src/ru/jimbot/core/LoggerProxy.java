/**
 * 
 */
package ru.jimbot.core;

import java.util.ArrayList;
import java.util.List;

import ru.jimbot.core.services.Log;

/**
 * Выводит данные на все зарегистрированные сервисы логирования
 * @author spec
 *
 */
public class LoggerProxy extends Log {
	private List<Log> loggers;
	private int level = Log.INFO;
	
	public LoggerProxy() {
		loggers = new ArrayList<Log>();
	}
	
	public void addLogger(Log logger) {
		logger.setLevel(level);
		loggers.add(logger);
	}
	
	public void removeLogger(Log logger) {
		loggers.remove(logger);
	}
	
	public void crear() {
		loggers = new ArrayList<Log>();
	}
	

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#setLevel(int)
	 */
	@Override
	public void setLevel(int level) {
		this.level = level;
		for(Log i : loggers) {
			try {
				i.setLevel(level);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
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
		for(Log i : loggers) {
			try {
				i.print(type, name, msg);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#debug(java.lang.String, java.lang.Object)
	 */
	@Override
	public void debug(String name, Object msg) {
		if(level>Log.DEBUG) return;
		for(Log i : loggers) {
			try {
				i.debug(name, msg);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#debug(java.lang.String, java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void debug(String name, Object msg, Throwable throwable) {
		if(level>Log.DEBUG) return;
		for(Log i : loggers) {
			try {
				i.debug(name, msg, throwable);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#error(java.lang.String, java.lang.Object)
	 */
	@Override
	public void error(String name, Object msg) {
		for(Log i : loggers) {
			try {
				i.error(name, msg);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#error(java.lang.String, java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void error(String name, Object msg, Throwable throwable) {
		for(Log i : loggers) {
			try {
				i.error(name, msg, throwable);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
