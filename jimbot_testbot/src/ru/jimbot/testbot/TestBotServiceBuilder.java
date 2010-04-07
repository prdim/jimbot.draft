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

package ru.jimbot.testbot;

import java.io.File;

import ru.jimbot.core.api.IServiceBuilder;
import ru.jimbot.core.api.Service;
import ru.jimbot.util.FileUtils;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class TestBotServiceBuilder implements IServiceBuilder {
	private CommandConnector con = null;

	/**
	 * @param con
	 */
	public TestBotServiceBuilder(CommandConnector con) {
		this.con = con;
	}

	public Service build(String name) {
		return new TestBot(name,con);
	}

	public String getServiceType() {
		return "TestBot";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.IServiceBuilder#createServiceData(java.lang.String)
	 */
	public boolean createServiceData(String name) {
		boolean b = false;
		try {
			b = new File("./services/" + name).mkdir();
			if(!b) return b;
			b = new File("./log/" + name).mkdir();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.IServiceBuilder#deleteServiceData(java.lang.String)
	 */
	public boolean deleteServiceData(String name) {
		boolean b = false;
		try {
			b = deleteDirectory(new File("./services/ + name"));
			if(!b) return b;
			b = deleteDirectory(new File("./log/" + name));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/**
     * Удаление папки, содержащей файлы
     * @param path
     * @return
     */
    private boolean deleteDirectory(File path) {
        if( path.exists() ) {
          File[] files = path.listFiles();
          for(int i=0; i<files.length; i++) {
             if(files[i].isDirectory()) {
               deleteDirectory(files[i]);
             }
             else {
               files[i].delete();
             }
          }
        }
        return( path.delete() );
      }
}
