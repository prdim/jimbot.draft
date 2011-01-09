/**
 * 
 */
package ru.jimbot.core.services;

/**
 * @author spec
 *
 */
public abstract class Log {
	public static final int ERROR = 3;
	public static final int INFO = 2;
	public static final int DEBUG = 1;
	
	public abstract void setLevel(int level);
	public abstract int getLevel();
	
	public abstract void print(String type, String name, Object msg);
	public abstract void debug(String name, Object msg);
	public abstract void debug(String name, Object msg, Throwable throwable);
	public abstract void error(String name, Object msg);
	public abstract void error(String name, Object msg, Throwable throwable);
}
