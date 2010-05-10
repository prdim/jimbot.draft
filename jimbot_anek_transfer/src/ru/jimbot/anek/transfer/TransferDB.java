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

package ru.jimbot.anek.transfer;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazon.carbonado.ConfigurationException;
import com.amazon.carbonado.RepositoryException;

import ru.jimbot.MainConfig;
import ru.jimbot.Manager;

/**
 * Преобразует базу данных бота из MySQL в Berkeley DB
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class TransferDB extends HttpServlet {
	static final String HTML_HEAD =
        "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\"><HTML><HEAD>" +
        "<meta content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\" />";
	static final String BODY = "<BODY BGCOLOR=\"#c0c0c0\">";

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setLocale(Locale.getDefault());
		print(resp, HTML_HEAD + "<TITLE>JimBot " + MainConfig.VERSION + " </TITLE></HEAD>" + BODY +
        	"<H2>Конвертация базы данных анекдотного бота</H2>");
		String s = "<FORM METHOD=POST ACTION=\"transferdb\">" +
				"<TABLE>" +
				"<TR><TH ALIGN=LEFT>Сервер MySQL</TD><TD><INPUT size=\"70\" TYPE=text NAME=\"host\" VALUE=\"\"></TD></TR>" +
				"<TR><TH ALIGN=LEFT>Имя базы данных</TD><TD><INPUT size=\"70\" TYPE=text NAME=\"dbname\" VALUE=\"\"></TD></TR>" +
				"<TR><TH ALIGN=LEFT>Пользователь</TD><TD><INPUT size=\"70\" TYPE=text NAME=\"user\" VALUE=\"\"></TD></TR>" +
				"<TR><TH ALIGN=LEFT>Пароль</TD><TD><INPUT size=\"70\" TYPE=text NAME=\"pass\" VALUE=\"\"></TD></TR>" +
				"</TABLE>";
		for(String i : Manager.getInstance().getServiceNames()) {
			s += "<input type=radio name=\"service\" value=\"" + i + "\"> " + i + "<br>";
		}
		s += "<P><INPUT TYPE=submit VALUE=\"Выполнить\"></FORM>";
		s += "<P><A HREF=\"main\">Назад</A><br></FONT></BODY></HTML>";
		print(resp, s);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Если не выбран сервис для работы
		if("".equals(req.getParameter("service"))) {
			getServletContext().getRequestDispatcher("/main?page=error&id=5&ret=index").forward(req, resp);
		}
		DBConverter dbc = new DBConverter();
		try {
			dbc.openMysql(req.getParameter("host"), req.getParameter("dbname"), req.getParameter("user"), 
					req.getParameter("pass"));
			String name = req.getParameter("service");
			dbc.convertAneks("./services/" + name + "/db/aneks");
			dbc.convertAneksTemp("./services/" + name + "/db/aneks");
			dbc.convertAds("./services/" + name + "/db/aneks");
		} catch (Exception e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/main?page=error&id=5&ret=index").forward(req, resp);
		}
	}

    public void print(HttpServletResponse response, String output) throws IOException {
        new PrintStream(response.getOutputStream(), false, "UTF-8").println(output);
    }
}
