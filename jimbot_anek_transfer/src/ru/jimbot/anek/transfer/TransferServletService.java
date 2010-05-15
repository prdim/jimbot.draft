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

import java.util.Map;

import javax.servlet.http.HttpServlet;

import ru.jimbot.core.api.IHTTPService;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class TransferServletService implements IHTTPService {

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.IHTTPService#getName()
	 */
	public String getName() {
		return "Конвертация базы данных";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.IHTTPService#getPath()
	 */
	public String getPath() {
		return "/transferdb";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.IHTTPService#getServlet()
	 */
	public HttpServlet getServlet() {
		return new TransferDB();
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.IHTTPService#isAuth()
	 */
	public boolean isAuth() {
		return true;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.IHTTPService#getInitParams()
	 */
	public Map<String, String> getInitParams() {
		// TODO Auto-generated method stub
		return null;
	}
}
