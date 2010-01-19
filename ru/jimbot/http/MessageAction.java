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

import ru.jimbot.util.MainProps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Выводит сообщение и возвращается на заданную страницу
 *
 * @author Prolubnikov Dmitry
 */
public class MessageAction extends MainPageServletActions {
    public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ret = request.getParameter("ret");
        if(ret==null) ret = "index";
        String ns = request.getParameter("ns");
        ret += ns == null ? "" : ("&ns=" + ns);
        int id = Integer.parseInt(request.getParameter("id"));
        String msg = (new String[]{"Данные сохранены.",
                "Сервис запущен.",
                "Операция выполнена.",
                "Сервис остановлен."})[id];
        print(response, HTML_HEAD + "<meta http-equiv=\"Refresh\" content=\"3; url=" +
                        "?page="+ ret + "\" />" +
                        "<TITLE>JimBot "+ MainProps.VERSION+" </TITLE></HEAD><BODY><H3><FONT COLOR=\"#004000\">" +
                        msg + " </FONT></H3>");
        print(response, "<P><A HREF=\"?page=" +
                        ret + "\">" + "Назад</A><br>");
        print(response, "</FONT></BODY></HTML>");
        
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
