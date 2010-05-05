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
import ru.jimbot.Manager;
import ru.jimbot.core.api.IHTTPService;
import ru.jimbot.util.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Выводит базовую страницу сервлета
 *
 * @author Prolubnikov Dmitry
 */
public class BootstrapAction extends MainPageServletActions {

    public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setLocale(Locale.getDefault());
        print(response, HTML_HEAD + "<TITLE>JimBot " + MainConfig.VERSION + " </TITLE></HEAD>" + BODY +
                        "<H2>Панель управления ботом</H2>");
        if(MainConfig.getInstance().getHttpUser().equals("admin") &&
                        MainConfig.getInstance().getHttpPass().getPass().equals("admin"))
                    print(response, "<H3><FONT COLOR=\"#FF0000\">В целях безопасности как можно скорее измените " +
                            "стандартный логин и пароль для доступа к этой странице! Рекомендуется также изменить порт.</FONT></H3>");
                if(HttpUtils.checkNewVersion()){
                    print(response, "<p>На сайте <A HREF=\"http://jimbot.ru\">jimbot.ru</A> Доступна новая версия!<br>");
                    print(response, HttpUtils.getNewVerDesc().replaceAll("\n", "<BR>"));
                    print(response, "</p>");
                }
                print(response, "<H3>Главное меню</H3>");
                print(response, "<A HREF=\"?page=main_props\">" +
                        "Основные настройки</A><br>");
                print(response, "<A HREF=\"?page=srvs_manager\">" +
                        "Управление сервисами</A><br>");
                String s = "<TABLE>";
                for(String n: Manager.getInstance().getServiceNames()){
                    s += "<TR><TH ALIGN=LEFT>"+n+"</TD>";
                    s += "<TD><A HREF=\"?page=srvs_props&ns="+n+"\">Настройки сервиса</A></TD>";
                    s += "<TD><A HREF=\"?page=srvs_props_uin&ns="+n+"\">Настройки UIN</A></TD>";
                    /*if(Manager.getInstance().getService(n) instanceof ChatServer){
                        s += "<TD><A HREF=\"?page=user_group_props&ns=" + n + "\">Полномочия</A></TD>";
                    } else*/
                        s += "<TD> </TD>";
                    s += "<TD><A HREF=\"?page=srvs_stats&ns="+n+"\">Статистика</A></TD>";
                    if(Manager.getInstance().getService(n).isRun()){
                        s += "<TD><A HREF=\"?page=srvs_stop&ns="+n+"\">Stop</A></TD>";
                    } else {
                        s += "<TD><A HREF=\"?page=srvs_start&ns="+n+"\">Start</A></TD>";
                    }
                    s += "</TR>";
                }
                s += "</TABLE>";
                print(response, s);
                for(IHTTPService t : HandlerFactory.getListHTTP()) {
                	print(response, "<br><A HREF=\"" + t.getPath() + "\">" + t.getName() + "</A>");
                }
                print(response, "<br><A HREF=\"?page=stop_bot\">" + "Отключить бота</A>");
                print(response, "<br><A HREF=\"?page=restart_bot\">" + "Перезапустить бота</A>");
                print(response, "</FONT></BODY></HTML>");
        return null;
    }
}
