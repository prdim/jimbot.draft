/**
 * 
 */
package ru.jimbot.anek.transfer;

import javax.servlet.http.HttpServlet;

import ru.jimbot.core.api.IHTTPService;

/**
 * @author spec
 *
 */
public class TransferServletService implements IHTTPService {

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.IHTTPService#getName()
	 */
	public String getName() {
		return "Конвертация базы данных";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.IHTTPService#getPath()
	 */
	public String getPath() {
		return "/transferdb";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.IHTTPService#getServlet()
	 */
	public HttpServlet getServlet() {
		return new TransferDB();
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.IHTTPService#isAuth()
	 */
	public boolean isAuth() {
		return true;
	}

}
