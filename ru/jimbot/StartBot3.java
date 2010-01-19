/**
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2009 JimBot project
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

package ru.jimbot;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import ru.jimbot.modules.WorkScript;
import ru.jimbot.util.Log;
import ru.jimbot.util.MainProps;
import ru.jimbot.util.SystemErrLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Запуск бота
 * @author Prolubnikov Dmitriy
 *
 */
public class StartBot3 {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
            	System.setErr(new PrintStream(new SystemErrLogger(), true));
                MainProps.load();
                Manager.getInstance();
        if (MainProps.getBooleanProperty("main.StartHTTP")) {
            Manager.getInstance().startHTTPServer();
//            Handler handler = new AbstractHandler() {
//                public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
//                    response.setContentType("text/html");
//                    response.setStatus(HttpServletResponse.SC_OK);
//                    response.getWriter().println("<h1>Hello</h1>");
//                    ((Request) request).setHandled(true);
//                    System.out.println(request.getRemoteAddr() + ":" + request.getRemotePort());
//                }
//            };
//
//            org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(8080);
//            server.setHandler(handler);
//            try {
//                server.start();
//                server.join();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }

//            try {
//                Vector<String> v = WorkScript.getInstance("").listHTTPScripts();
//                String[] s = new String[2 + v.size() * 2];
//                s[0] = "/";
//                s[1] = "ru.jimbot.modules.http.MainPage";
//                for (int i = 0; i < v.size(); i++) {
//                    s[i * 2 + 2] = v.get(i);
//                    s[i * 2 + 3] = "ru.jimbot.modules.http.HTTPScriptRequest";
//                }
//                Server.startServer(s);
//            } catch (Exception ex) {
//                Log.getDefault().error(ex.getMessage(), ex);
//            }
//            try {
//                Manager.getInstance().startAll();
//            } catch (Exception ex) {
//                Log.getDefault().error(ex.getMessage(), ex);
//            }
        }
    }
}
