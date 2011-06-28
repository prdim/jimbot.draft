/**
 * 
 */
package ru.jimbot.core.exceptions;

/**
 * @author spec
 *
 */
public class DbException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DbException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DbException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public DbException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public DbException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
