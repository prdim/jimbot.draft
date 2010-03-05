/*
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

package test;

import junit.framework.TestCase;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.Test;
import ru.jimbot.util.Log;
import org.apache.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prolubnikov Dmitry
 */
public class Test1 {
    public Test1() {
    }

    /**
     * Проверяем работу логов
     */
    @Test
    public void testLog() {
        Log.getDefault().info("Проверка 1");
        Log.getDefault().talk("Проверка 2");
        Log.getLogger("AnekBot").info("Проверка 3");
        Log.getLogger("AnekBot").talk("Проверка 4");
        Log.getDefault().http("test http");
        Log.getDefault().info("Проверка 5");
        Log.getDefault().talk("Проверка 6");
//        PropertyConfigurator.configure("lib/log4j.properties");
//        Logger l1 = Logger.getRootLogger();
//        l1.info("test1");
//        PropertyConfigurator.configure("services/AnekBot/log4j.properties");
//        Logger l2 = Logger.getLogger("AnekBot.system");
//        l2.info("test2");
//        l1.info("test3");
    }

    @Test
    public void testServer() throws Exception {
        Handler handler=new AbstractHandler()
        {
//            public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
//                throws IOException, ServletException
//            {
//                response.setContentType("text/html");
//                response.setStatus(HttpServletResponse.SC_OK);
//                response.getWriter().println("<h1>Hello</h1>");
//                ((Request)request).setHandled(true);
//            }

            public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
                response.setContentType("text/html");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("<h1>Hello</h1>");
                ((Request)request).setHandled(true);
            }
        };

        Server server = new Server(8080);
        server.setHandler(handler);
        server.start();

    }
}
