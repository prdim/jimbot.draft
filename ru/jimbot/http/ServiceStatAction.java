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
import ru.jimbot.modules.MsgStatCounter;
import ru.jimbot.util.MainProps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Выводит страницу со статистикой работы сервиса
 *
 * @author Prolubnikov Dmitry
 */
public class ServiceStatAction extends MainPageServletActions {
    public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ns = request.getParameter("ns"); // Имя сервиса
    	if(!Manager.getInstance().getServiceNames().contains(ns)){
    		return "/main?page=error&id=0&ret=index";
    	}
    	print(response, HTML_HEAD + "<meta http-equiv=\"Refresh\" content=\"3; url=" +
    			"?page=srvs_stats&ns="+ ns + "\" />" +
    			"<TITLE>JimBot "+ MainProps.VERSION+" </TITLE></HEAD>" + BODY +
                "<H3>Статистика работы " + ns + "</H3>");
        if(Manager.getInstance().getService(ns).isRun()){
    	print(response, "Очередь входящих сообщений: " + Manager.getInstance().getService(ns).getInQueue().size() + "<br>");
    	print(response, "Очередь исходящих сообщений: <br>");
    	for(int i=0;i<Manager.getInstance().getService(ns).getProps().uinCount();i++){
            String sn = Manager.getInstance().getService(ns).getProps().getUin(i);
    		print(response, ">> " + sn +
    				(Manager.getInstance().getService(ns).getProtocol(sn).isOnLine() ? "  [ ON]  " : "  [OFF]  ") +
    				Manager.getInstance().getService(ns).getOutQueue(sn).size() +
    				", потери:" + Manager.getInstance().getService(ns).getOutQueue().getLostMsgCount(sn) + "<br>");
    	}
    	print(response, "<br>Статистика принятых сообщений по номерам:<br>");
    	String s = "<TABLE BORDER=\"1\"><TR><TD>UIN</TD><TD>1 минута</TD><TD>5 митут</TD><TD>60 минут</TD><TD>24 часа</TD><TD>Всего</TD></TR>";
    	int c = Manager.getInstance().getService(ns).getProps().uinCount();
    	for(int i=0;i<c;i++){
    		String u = Manager.getInstance().getService(ns).getProps().getUin(i);
    		s += "<TR><TD>" + u +
    			"</TD><TD>" + MsgStatCounter.getElement(u).getMsgCount(MsgStatCounter.M1) +
    			"</TD><TD>" + MsgStatCounter.getElement(u).getMsgCount(MsgStatCounter.M5) +
    			"</TD><TD>" + MsgStatCounter.getElement(u).getMsgCount(MsgStatCounter.M60) +
    			"</TD><TD>" + MsgStatCounter.getElement(u).getMsgCount(MsgStatCounter.H24) +
    			"</TD><TD>" + MsgStatCounter.getElement(u).getMsgCount(MsgStatCounter.ALL) +
    			"</TD></TR>";
    	}
    	s += "</TABLE>";
        print(response, s);
        } else {
            print(response, "Сервис не запущен.");
        }
    	print(response, "<P><A HREF=\"?page=index\">" +
				"Назад</A><br>");
    	print(response, "</FONT></BODY></HTML>");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
