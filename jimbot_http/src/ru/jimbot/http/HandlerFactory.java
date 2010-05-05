/*
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2010 JimBot project
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package ru.jimbot.http;

import org.eclipse.jetty.http.security.Constraint;
import org.eclipse.jetty.http.security.Credential;
import org.eclipse.jetty.http.security.Password;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.jimbot.MainConfig;
import ru.jimbot.core.api.IHTTPService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Определяет из доступных скриптов и создает новые обработчики http-запросов
 *
 * @author Prolubnikov Dmitry
 */
public class HandlerFactory {
	private static List<IHTTPService> lst;
	
	/**
	 * Возвращает список зарегистрированных http-сервисов, чтобы нарисовать ссылки в админке
	 * @return
	 */
	public static List<IHTTPService> getListHTTP() {
		return lst;
	}
	
    /**
     * Создает и возвращает список доступных (установленных в боте) обработчиков
     * @return
     */
    public static HandlerList getAvailableHandlers(List<IHTTPService> slist) {
    	lst = slist;
        HandlerList handlers = new HandlerList();
        // Добавляем обработчик файлов
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
        resource_handler.setResourceBase("./htdocs");
        // Авторизация
        Constraint constraint = new Constraint();
        constraint.setName("user");
        constraint.setRoles(new String[]{"user","admin"});
        constraint.setAuthenticate(true);
        ConstraintMapping cm = new ConstraintMapping();
        cm.setConstraint(constraint);
        cm.setPathSpec("/*");

        ConstraintSecurityHandler sh = new ConstraintSecurityHandler();
        HashLoginService ls = new HashLoginService("JimBotRealm");
        ls.putUser(MainConfig.getInstance().getHttpUser(),
                new Password(MainConfig.getInstance().getHttpPass().getPass()),
                new String[]{"admin","user"});
        FormAuthenticator auth = new FormAuthenticator("/logon.html", "/logonError.html", false);
        sh.setAuthenticator(auth);
        sh.setAuthMethod("FORM");
        sh.setLoginService(ls);
        sh.setConstraintMappings(new ConstraintMapping[]{cm});
        //Обработчики сервлетов
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
//        context.addServlet(new ServletHolder(new TestServlet()),"/test");
//        context.addServlet(new ServletHolder(new TestServlet2()),"/test2");
        context.addServlet(new ServletHolder(new MainPageServlet()),"/main");
        context.addServlet(new ServletHolder(new MainPageServlet()),"/j_security_check");
        for(IHTTPService t : slist) {
        	context.addServlet(new ServletHolder(t.getServlet()), t.getPath());
        	//TODO Сделать добавление сервлетов без авторизации
        }
        context.setSecurityHandler(sh);
        context.getSessionHandler().getSessionManager().setMaxInactiveInterval(MainConfig.getInstance().getHttpDelay()*60);
        handlers.setHandlers(new Handler[] { resource_handler, context, new DefaultHandler() });
        return handlers;
    }

//    public static void setAvailableHandlers(Server server) {
////        ContextHandler context;
////        ServletHandler servletHandler;
////        SessionHandler sessionHandler;
////        ConstraintSecurityHandler securityHandler;
////
////        context = new ContextHandler();
////        context.setContextPath("");
////        context.setResourceBase("./htdocs");
////        context.setWelcomeFiles(new String[]{ "index.html" });
////
////        ResourceHandler resource_handler = new ResourceHandler();
////        resource_handler.setDirectoriesListed(true);
////        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
////        resource_handler.setResourceBase("./htdocs");
////
////        HandlerList handlers = new HandlerList();
////        handlers.setHandlers(new Handler[] {context, resource_handler});
////        server.setHandler(handlers);
////
////        servletHandler = new ServletHandler();
////        sessionHandler = new SessionHandler();
////        securityHandler = new ConstraintSecurityHandler();
////        securityHandler.setHandler(servletHandler);
////        sessionHandler.setHandler(securityHandler);
////        context.setHandler(sessionHandler);
////        sessionHandler.getSessionManager().setMaxInactiveInterval(30*60);
////        servletHandler.addServlet(new ServletHolder(new TestServlet()));
////
////        HashLoginService ls = new HashLoginService("TestRealm");
////        ls.putUser("user",new Password("user"), new String[]{"user"});
////        ls.putUser("admin",new Password("admin"), new String[]{"admin","user"});
////        securityHandler.setLoginService(ls);
////        Constraint constraint = new Constraint();
////        constraint.setName("user");
////        constraint.setRoles(new String[]{"user","admin"});
////        constraint.setAuthenticate(true);
////        ConstraintMapping cm = new ConstraintMapping();
////        cm.setConstraint(constraint);
////        cm.setPathSpec("/*");
////        securityHandler.setConstraintMappings(new ConstraintMapping[]{cm});
////
////        FormAuthenticator authenticator = new FormAuthenticator("/logon.html", "/logonError.html", false);
////        securityHandler.setAuthenticator(authenticator);
////        securityHandler.setAuthMethod(Constraint.__FORM_AUTH);
//
//
//
//        HandlerList handlers = new HandlerList();
//        // Добавляем обработчик файлов
//        ResourceHandler resource_handler = new ResourceHandler();
//        resource_handler.setDirectoriesListed(true);
//        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
//        resource_handler.setResourceBase("./htdocs");
//        // Авторизация
//        Constraint constraint = new Constraint();
//        constraint.setName("user");
//        constraint.setRoles(new String[]{"user","admin"});
//        constraint.setAuthenticate(true);
//        ConstraintMapping cm = new ConstraintMapping();
//        cm.setConstraint(constraint);
//        cm.setPathSpec("/*");
//
//        ConstraintSecurityHandler sh = new ConstraintSecurityHandler();
//        HashLoginService ls = new HashLoginService("Testrealm");
//        ls.putUser("user",new Password("user"), new String[]{"user"});
//        ls.putUser("admin",new Password("admin"), new String[]{"admin","user"});
//        sh.setAuthenticator(new FormAuthenticator("logon", "logonError", false));
//        sh.setStrict(false);
//        sh.setAuthMethod("FORM");
////        sh.setInitParameter(FormAuthenticator.__FORM_LOGIN_PAGE, "/logon");
////        sh.setInitParameter(FormAuthenticator.__FORM_ERROR_PAGE, "/logonError");
////        sh.setAuthMethod("BASIC");
//        sh.setLoginService(ls);
//        sh.setConstraintMappings(new ConstraintMapping[]{cm});
//        //Обработчики сервлетов
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.setContextPath("/");
//        context.addServlet(new ServletHolder(new TestServlet()),"/test");
//        context.addServlet(new ServletHolder(new TestServlet2()),"/test2");
//        context.setSecurityHandler(sh);
//        context.getSessionHandler().getSessionManager().setMaxInactiveInterval(60);
//        context.getSessionHandler().getSessionManager().setMaxCookieAge(60);
//        sh.setHandler(context);
//        ServletContextHandler logonContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        logonContext.setContextPath("/");
//        logonContext.addServlet(new ServletHolder(new LogonServlet()),"/logon");
//        logonContext.addServlet(new ServletHolder(new LogonErrorServlet()),"/logonError");
//
//        handlers.setHandlers(new Handler[] { resource_handler, logonContext, context, new DefaultHandler() });
//        server.setHandler(handlers);
//        server.addBean(ls);
//    }

//    public void setAvailableHandlers2(Server server) throws Exception {
////        LocalConnector _connector = new LocalConnector();
//        ContextHandler _context = new ContextHandler(); //new ServletContextHandler();
//        SessionHandler _session = new SessionHandler();
//        ConstraintSecurityHandler _security = new ConstraintSecurityHandler();
//        HashLoginService _loginService = new HashLoginService("TestRealm");
//
//        RequestHandler _handler = new RequestHandler();
//
////        ServletHandler _handler = new ServletHandler();
////        _handler.addServletWithMapping(TestServlet.class,"/test");
//
////        ServletContextHandler _handler = new ServletContextHandler();
////        _handler.setContextPath("/");
////        _handler.addServlet(new ServletHolder(new TestServlet()),"/test");
////        _handler.addServlet(new ServletHolder(new TestServlet2()),"/test2");
////        _handler.addServlet(new ServletHolder(new LogonServlet()),"/logon");
////        _handler.addServlet(new ServletHolder(new LogonErrorServlet()),"/logonError");
//
////        server.setConnectors(new Connector[]{_connector});
//        _context.setContextPath("/");
//
//        server.setHandler(_context);
//        _context.setHandler(_session);
//        _session.setHandler(_security);
//        _security.setHandler(_handler);
//
//        _loginService.putUser("user",new Password("password"), new String[] {"user"});
//        _loginService.putUser("admin",new Password("password"), new String[] {"user","administrator"});
//        server.addBean(_loginService);
//
//        Constraint constraint1 = new Constraint();
//        constraint1.setAuthenticate(true);
//        constraint1.setName("auth");
//        constraint1.setRoles(new String[]{Constraint.ANY_ROLE});
//        ConstraintMapping mapping1 = new ConstraintMapping();
//        mapping1.setPathSpec("/test");
//        mapping1.setConstraint(constraint1);
//
//        Set<String> knownRoles=new HashSet<String>();
//        knownRoles.add("user");
//        knownRoles.add("administrator");
//
//        _security.setConstraintMappings(new ConstraintMapping[]
//                {
//                        mapping1
//                },knownRoles);
//
//        _security.setAuthenticator(new FormAuthenticator("/logon","/logonError",false));
//        _security.setStrict(false);
//        server.start();
//
//
//    }
//
//    class RequestHandler extends AbstractHandler
//    {
//        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException
//        {
//            baseRequest.setHandled(true);
//            if (request.getAuthType()==null || "user".equals(request.getRemoteUser()) || request.isUserInRole("user"))
//            {
//                response.setStatus(200);
//                response.setContentType("text/plain; charset=UTF-8");
//                response.getWriter().println("URI="+request.getRequestURI());
//                String user = request.getRemoteUser();
//                response.getWriter().println("user="+user);
//            }
//            else
//                response.sendError(500);
//        }
//    }
}
