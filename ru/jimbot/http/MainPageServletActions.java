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

import org.eclipse.jetty.server.HttpConnection;
import ru.jimbot.table.UserPreference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Общие методы для действий сервлета
 *
 * @author Prolubnikov Dmitry
 */
abstract class MainPageServletActions implements Action {
    static final String HTML_HEAD =
            "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\"><HTML><HEAD>" +
            "<meta content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\" />";
    static final String BODY = "<BODY BGCOLOR=\"#c0c0c0\">";

    /**
     * Вывод данных на веб-страницу
     * @param response
     * @param output
     * @throws IOException
     */
    public void print(HttpServletResponse response, String output) throws IOException {
        new PrintStream(response.getOutputStream(), false, "UTF-8").println(output);
    }

    /**
         * Формирует форму для редактирования настроек бота
         * @param p
         * @return
         */
        protected String prefToHtml(UserPreference[] p) {
            String s = "<TABLE>";
            for(int i=0;i<p.length;i++){
                if(p[i].getType()==UserPreference.CATEGORY_TYPE){
                    s += "<TR><TH ALIGN=LEFT><u>" + p[i].getDisplayedKey() + "</u></TD></TR>";
                } else if(p[i].getType()== UserPreference.BOOLEAN_TYPE) {
                    s += "<TR><TH ALIGN=LEFT>"+p[i].getDisplayedKey()+ "</TD> " +
                    "<TD><INPUT TYPE=CHECKBOX NAME=\"" + p[i].getKey() +
                    "\" VALUE=\"true\" " + ((Boolean)p[i].getValue() ? "CHECKED" : "") + "></TD></TR>";
                } else {
                    s += "<TR><TH ALIGN=LEFT>"+p[i].getDisplayedKey()+ "</TD> " +
                            "<TD><INPUT size=\"70\" TYPE=text NAME=\"" + p[i].getKey() +
                            "\" VALUE=\"" + p[i].getValue() + "\"></TD></TR>";
                }
            }
            s += "</TABLE>";
            return s;
        }    

    protected String encodeURL(String str) {
    	System.out.println("Encode URL: "+str);
        StringBuffer buf = new StringBuffer();
        for (int i = 0, n = str.length(); i < n; i++) {
            char ch = str.charAt(i);
            if (ch == ' ') {
                buf.append("+");
            } else if (!((ch >= '0' && ch <= '9') || (ch >= 'a' || ch <= 'z') ||
                         (ch >= 'A' || ch <= 'Z'))) {
                buf.append("%");
                buf.append(((ch >> 4) & 0xF) >= 10 ?
                           ((ch >> 4) & 0xF) + 'A' - 10 :
                           ((ch >> 4) & 0xF) + '0');
                buf.append((ch & 0xF) >= 10 ? (ch & 0xF) + 'A' - 10 :
                           (ch & 0xF) + '0');
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    protected String encodeHTML(String str) {
        if (str == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0, n = str.length(); i < n; i++) {
            char ch = str.charAt(i);
            switch (ch) {
            case '<':
                buf.append("&lt;");
                break;
            case '>':
                buf.append("&gt;");
                break;
            case '&':
                buf.append("&amp;");
                break;
            case '"':
                buf.append("&quot;");
                break;
            default:
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    protected String getStringVal(HttpServletRequest request, String name) throws IOException {
        return (name) == null ? "" : request.getParameter(name);
    }

    protected boolean getBoolVal(HttpServletRequest request, String name) throws IOException {
        return request.getParameter(name) == null ? false : true;
    }

}
