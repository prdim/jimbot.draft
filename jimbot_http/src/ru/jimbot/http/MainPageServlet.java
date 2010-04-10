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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет основной страницы веб-админки
 *
 * @author Prolubnikov Dmitry
 */
public class MainPageServlet extends HttpServlet {
    protected ActionFactory factory = new ActionFactory();

    public MainPageServlet() {
        super();
    }

    protected String getActionName(HttpServletRequest request) {
        String page = request.getParameter("page");
        if(page==null || "".equals(page)) return "index";
        else return page;
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Action action = factory.create(getActionName(request));
        String url = action.perform(request, response);
        if (url != null) getServletContext().getRequestDispatcher(url).forward(request, response);
    }
}
