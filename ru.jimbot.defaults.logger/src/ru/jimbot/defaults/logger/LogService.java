/**
 * 
 */
package ru.jimbot.defaults.logger;

import java.io.IOException;
import java.util.HashMap;

import ru.jimbot.core.ExtendPoint;
import ru.jimbot.core.services.Log;
import org.apache.log4j.*;

/**
 * Прототип сервиса логирования. Позже нужно доработать
 * @author spec
 *
 */
public class LogService extends Log implements ExtendPoint {
	private int level;
	private HashMap<String, Logger> loggers = new HashMap<String, Logger>();
	public static final String PATTERN = "[%d{dd.MM.yy HH:mm:ss}] %m%n";
	public static final int MAX_BACKUP_INDEX = 5;
	private Logger rootLogger;
	
	public LogService() {
		level = DefaultLogConfig.getInstance().isDebugMode() ? Log.DEBUG : Log.INFO;
		rootLogger = Logger.getRootLogger();
		RollingFileAppender t;
		try {
			t = new RollingFileAppender(new PatternLayout(PATTERN), "./log/system.log", true);
			t.setName("system");
			t.setMaxBackupIndex(MAX_BACKUP_INDEX);
			t.setMaxFileSize("1MB");
			rootLogger.addAppender(t);
			if(DefaultLogConfig.getInstance().isConsoleMode()) {
				rootLogger.addAppender(new ConsoleAppender(new PatternLayout(PATTERN)));
			}
			rootLogger.setLevel(Level.ALL);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
//		System.out.println("LOG: [" + type + "]:[" + name + "]:" + msg);
		String n = name + "_" + type;
		if(!loggers.containsKey(n)) {
			loggers.put(n, initLoggerDaily(name, type));
		}
		loggers.get(n).info(msg);
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#debug(java.lang.String, java.lang.Object)
	 */
	@Override
	public void debug(String name, Object msg) {
		if(Log.DEBUG != level) return;
//		System.out.println("DEBUG: [" + name + "]:" + msg);
//		if(!loggers.containsKey(name)) {
//			loggers.put(name, initLoggerFile(name, ""));
//		}
		rootLogger.debug("DEBUG: [" + name + "]:" + msg);
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#debug(java.lang.String, java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void debug(String name, Object msg, Throwable throwable) {
		if(Log.DEBUG != level) return;
//		System.err.println("DEBUG: [" + name + "]:" + msg);
//		if(!loggers.containsKey(name)) {
//			loggers.put(name, initLoggerFile(name, ""));
//		}
		rootLogger.debug(msg, throwable);
//		throwable.printStackTrace();
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#error(java.lang.String, java.lang.Object)
	 */
	@Override
	public void error(String name, Object msg) {
//		System.err.println("ERROR: [" + name + "]:" + msg);
		String n = name + "_error";
		if(!loggers.containsKey(n)) {
			loggers.put(n, initLoggerFile(name, "error"));
		}
		loggers.get(n).error(msg);
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.Log#error(java.lang.String, java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void error(String name, Object msg, Throwable throwable) {
//		System.err.println("ERROR: [" + name + "]:" + msg);
		String n = name + "_error";
		if(!loggers.containsKey(n)) {
			loggers.put(n, initLoggerFile(name, "error"));
		}
		loggers.get(n).error(msg, throwable);
//		throwable.printStackTrace();
	}

	@Override
	public String getType() {
		return "ru.jimbot.core.services.Log";
	}

	@Override
	public void unreg() {
//		debug("Default Log Service", "Unreg this");
		Logger.shutdown();
	}

	@Override
	public String getPointName() {
		return "DefaultLogService";
	}

	private Logger initLoggerDaily(String name, String type) {
		String n = "".equals(type) ? name : (name + "_" + type);
		Logger l = Logger.getLogger(n);
		
		DailyRollingFileAppender t;
		try {
			t = new DailyRollingFileAppender(new PatternLayout(PATTERN), "./log/" + n + ".log", "'.'yyyy-MM-dd");
			t.setName(n);
			l.addAppender(t);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return l;
	}
	
	private Logger initLoggerFile(String name, String type) {
		String n = "".equals(type) ? name : (name + "_" + type);
		Logger l = Logger.getLogger(n);
		RollingFileAppender t;
		try {
			t = new RollingFileAppender(new PatternLayout(PATTERN), "./log/" + n + ".log", true);
			t.setName("n");
			t.setMaxBackupIndex(MAX_BACKUP_INDEX);
			t.setMaxFileSize("1MB");
			l.addAppender(t);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return l;
	}
}
