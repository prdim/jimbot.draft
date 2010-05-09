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

import ru.jimbot.MainConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Выводит страницу с сообщением об ошибке
 *
 * @author Prolubnikov Dmitry
 */
public class ErrorMessageAction extends MainPageServletActions {
    public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ret = request.getParameter("ret");
        if(ret==null) ret = "index";
        int id = Integer.parseInt(request.getParameter("id"));
        String msg = (new String[]{"Отсутствует сервис с таким именем!",
                        "Пустое имя сервиса!",
                        "Сервис с таким именем уже существует!",
                        "Необходимо выбрать тип сервиса!",
                        "Произошла ошибка при создании сервиса.",
                        "Произошла ошибка!"})[id];
        print(response, HTML_HEAD + "<meta http-equiv=\"Refresh\" content=\"3; url=" +
                        "?page="+ ret + "\" />" +
                        "<TITLE>JimBot "+ MainConfig.VERSION+" </TITLE></HEAD><BODY><H3><FONT COLOR=\"#FF0000\">" +
                        msg + " </FONT></H3>");
        print(response, "<P><A HREF=\"?page=" +
                        ret + "\">" + "Назад</A><br>");
        print(response, "</FONT></BODY></HTML>");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
