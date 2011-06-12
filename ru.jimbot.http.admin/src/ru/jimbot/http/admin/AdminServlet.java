/**
 * 
 */
package ru.jimbot.http.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

/**
 * @author spec
 *
 */
public class AdminServlet extends AbstractApplicationServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4842403849753413076L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		try {
//			resp.setStatus(HttpServletResponse.SC_OK);
//			PrintWriter writer = resp.getWriter();
//			writer.write("<h1>Test!</h1>");
//			writer.flush();
//			writer.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
		return new AdminApp();
	}

	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		return AdminApp.class;
	}

	
}
