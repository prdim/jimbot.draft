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

import ru.jimbot.Manager;
import ru.jimbot.util.MainProps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Создание нового сервиса
 *
 * @author Prolubnikov Dmitry
 */
public class ServiceCreateAction extends MainPageServletActions {
    public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String save = request.getParameter("save");
        if (save == null) {
            print(response, HTML_HEAD + "<TITLE>JimBot " + MainProps.VERSION + " </TITLE></HEAD>" + BODY +
                    "<H2>Панель управления ботом</H2>" +
                    "<H3>Создание нового сервиса</H3>");
            print(response, "<FORM METHOD=POST ACTION=\"main\">" +
                    "<INPUT TYPE=hidden NAME=\"page\" VALUE=\"srvs_create\">" +
                    "<INPUT TYPE=hidden NAME=\"save\" VALUE=\"1\">" +
                    "Имя сервиса: <INPUT TYPE=text NAME=\"ns\" size=\"40\"> <br>" +
                    "Тип сервиса: chat <input type=radio name=\"type\" value=\"chat\"> " +
                    "anek <input type=radio name=\"type\" value=\"anek\">" +
                    "<P><INPUT TYPE=submit VALUE=\"Сохранить\"></FORM>");

            print(response, "<P><A HREF=\"?page=srvs_manager\">" +
                    "Назад</A><br>");
            print(response, "</FONT></BODY></HTML>");
        } else {
            String ns = request.getParameter("ns");
            String type = request.getParameter("type");
            if (ns.equals("")) {
                return "/main?page=error&id=1&ret=srvs_create";
            }
            if (Manager.getInstance().getServiceNames().contains(ns)) {
                return "/main?page=error&id=2&ret=srvs_create";
            }
            if (type == null) {
                return "/main?page=error&id=3&ret=srvs_create";
            }
            Manager.getInstance().addService(ns, type);
            MainProps.addService(ns, type);
            MainProps.save();
            return "/main?page=message&id=0&ret=srvs_manager";
        }
        return null;
    }
}
