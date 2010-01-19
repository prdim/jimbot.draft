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
 * Форма редактирования полномочий пользователей
 *
 * @author Prolubnikov Dmitry
 */
public class UserGroupPropertiesAction extends MainPageServletActions {
    public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ns = request.getParameter("ns"); // Имя сервиса
        if (!Manager.getInstance().getServiceNames().contains(ns)) {
            return "/main?page=error&id=0&ret=index";
        }
        String save = request.getParameter("save");
        String cmd = request.getParameter("cmd");
        if(save == null) {
            print(response, HTML_HEAD + "<TITLE>JimBot "+ MainProps.VERSION+" </TITLE></HEAD>" + BODY +
                            "<H2>Панель управления ботом</H2>" +
                            "<H3>Управление группами пользователей</H3>");
                    print(response, "<FORM METHOD=POST ACTION=\"main\">" +
                            "<INPUT TYPE=hidden NAME=\"page\" VALUE=\"user_group_props_add\">" +
                            "<INPUT TYPE=hidden NAME=\"save\" VALUE=\"1\">" +
                            "<INPUT TYPE=hidden NAME=\"cmd\" VALUE=\"save\">" +
                            "<INPUT TYPE=hidden NAME=\"ns\" VALUE=\"" + ns + "\">" +
                            "Имя группы: <INPUT TYPE=text NAME=\"gr\" size=\"20\"> " +
                            "<INPUT TYPE=submit VALUE=\"Создать новую группу\"></FORM>");
                    String s = "<TABLE>";
                    String[] gr = Manager.getInstance().getService(ns).getProps().getStringProperty("auth.groups").split(";");
                    for(int i=0; i<gr.length; i++){
                        s += "<TR><TH ALIGN=LEFT>"+gr[i]+"</TD>";
                        s += i==0 ? "" : "<TD><A HREF=\"?page=user_group_props&cmd=del&ns="+ns+"&gr=" + gr[i] + "\">(Удалить)</A></TD>";
                        s += "</TR>";
                    }
                    s += "</TABLE>";
                    print(response, s);
                    print(response, "<P><A HREF=\"?page=user_auth_props&ns="+ns+"\">" +
                            "Редактировать полномочия</A><br>");
                    print(response, "<P><A HREF=\"?page=index\">" +
                            "Назад</A><br>");
                    print(response, "</FONT></BODY></HTML>");
        } else {

        }

        return null;
    }
}
