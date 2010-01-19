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
 * Страница настроек уинов для сервиса
 *
 * @author Prolubnikov Dmitry
 */
public class UinPropertiesAction extends MainPageServletActions {
    public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ns = request.getParameter("ns"); // Имя сервиса
        if (!Manager.getInstance().getServiceNames().contains(ns)) {
            return "/main?page=error&id=0&ret=index";
        }
        String save = request.getParameter("save");
        String cmd = request.getParameter("cmd");
        String cnt = request.getParameter("cnt");
        if (save == null) {
            print(response, HTML_HEAD + "<TITLE>JimBot " + MainProps.VERSION + " </TITLE></HEAD>" + BODY +
                    "<H2>Панель управления ботом</H2>" +
                    "<H3>Настройки UIN для сервиса " + ns + "</H3>");
            print(response, "<P><A HREF=\"?page=srvs_props_uin&cmd=add&save=1&ns=" + ns + "\">" +
                    "Добавить новый UIN</A><br>");
            String s = "<FORM METHOD=POST ACTION=\"main\">" +
                    "<INPUT TYPE=hidden NAME=\"page\" VALUE=\"srvs_props_uin\">" +
                    "<INPUT TYPE=hidden NAME=\"ns\" VALUE=\"" + ns + "\">" +
                    "<INPUT TYPE=hidden NAME=\"save\" VALUE=\"1\">" +
                    "<INPUT TYPE=hidden NAME=\"cmd\" VALUE=\"save\">";
            for (int i = 0; i < Manager.getInstance().getService(ns).getProps().uinCount(); i++) {
                s += "UIN" + i + ": " +
                        "<INPUT TYPE=text NAME=\"uin_" + i + "\" VALUE=\"" +
                        Manager.getInstance().getService(ns).getProps().getUin(i) + "\"> : " +
                        "<INPUT TYPE=text NAME=\"pass_" + i + "\" VALUE=\"" +
                        "\"> " +
                        "<A HREF=\"?page=srvs_props_uin&cmd=del&save=1&ns=" + ns + "&cnt=" + i + "\">" +
                        "Удалить</A><br>";
            }
            s += "<P><INPUT TYPE=submit VALUE=\"Сохранить\"></FORM>";
            print(response, s);
            print(response, "<P><A HREF=\"?page=index\">" +
                    "Назад</A><br>");
            print(response, "</FONT></BODY></HTML>");
        } else if("1".equals(save)) {
            if ("save".equals(cmd)) {
                for (int i = 0; i < Manager.getInstance().getService(ns).getProps().uinCount(); i++) {
                    if (!"".equals(request.getParameter("pass_" + i)))
                        Manager.getInstance().getService(ns).getProps().setUin(i,
                                request.getParameter("uin_" + i), request.getParameter("pass_" + i));
                }
                Manager.getInstance().getService(ns).getProps().save();
                return "/main?page=message&id=0&ret=index";
            } else if ("add".equals(cmd)) {
                Manager.getInstance().getService(ns).getProps().addUin("111", "pass");
                //TODO Разобраться
                return "/main?page=message&id=2&ret=srvs_props_uin&ns="+ns;
            } else if ("del".equals(cmd)) {
                int i = Integer.parseInt(cnt);
                Manager.getInstance().getService(ns).getProps().delUin(i);
                //TODO Разобраться
                return "/main?page=message&id=2&ret=srvs_props_uin&ns="+ns;
            }
        }
        return null;
    }
}
