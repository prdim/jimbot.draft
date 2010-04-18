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

import ru.jimbot.Manager;
import ru.jimbot.core.MsgInQueue;
import ru.jimbot.core.MsgOutQueue;
import ru.jimbot.core.api.CommandProtocolListener;
import ru.jimbot.core.api.DbStatusListener;
import ru.jimbot.core.api.DefaultService;
import ru.jimbot.core.api.IProtocolManager;
import ru.jimbot.core.api.Protocol;
import ru.jimbot.core.api.ServiceConfig;
import ru.jimbot.util.Log;

/**
 * Пример реализации тестового бота в виде плагина
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class TestBot extends DefaultService {
	private String name = "";
	private TestBotConfig config;
	private boolean start = false;
	private TestBotCommandParser cmd;
	private CommandConnector con = null;
		
	/**
	 * @param name
	 */
	public TestBot(String name) {
		this.name = name;
		config = TestBotConfig.load(name);
	}

	/**
	 * @param name
	 * @param con
	 */
	public TestBot(String name, CommandConnector con) {
		this.name = name;
		this.con = con;
		config = TestBotConfig.load(name);
	}

	public CommandConnector getCommandConnector() {
    	return con;
    }

	public ServiceConfig getConfig() {
		return config;
	}

	public String getName() {
		return name;
	}

	public boolean isRun() {
		return start;
	}

	public void start() {
        getCron().clear();
        getCron().start();
        for(int i=0;i<config.getUins().size();i++) {
            IProtocolManager pm = Manager.getInstance().getAllProtocolManagers().get(config.getUins().get(i).getProtocol());
            
        	Protocol p = pm.addProtocol(pm.getBuilder(config.getUins().get(i).getScreenName())
        			.pass(config.getUins().get(i).getPass().getPass())
        			.status(config.getStatus())
        			.statustxt(config.getStatustxt())
        			.xstatus(config.getXstatus())
        			.xstatustxt1(config.getXstatustxt1())
        			.xstatustxt2(config.getXstatustxt2())
        			.build());
        	p.setLogger(Log.getLogger(name));
        	addCommandProtocolListener((CommandProtocolListener)p);
            protocols.put(config.getUins().get(i).getScreenName(), p);
            System.out.println("Create protocol " + p.getScreenName());
        }        
        qe.start();
        inq = new MsgInQueue(this);
        outq = new MsgOutQueue(this);
        inq.start();
        outq.start();
        cmd = new TestBotCommandParser(this);
        getCron().addTask(new CheckSessionTask(cmd, 60000));
        for(CommandProtocolListener i:getCommandProtocolListeners()) {
            i.logOn();
        }
        start = true;
	}

	public void stop() {
        getCron().stop();
        for(CommandProtocolListener i:getCommandProtocolListeners()) {
            try{
                i.logOut();
            } catch (Exception e) {}
        }
        cmd.destroyCommands();
        qe.stop();
        inq.stop();
        inq = null;
        outq.stop();
        outq = null;
        start = false;
        removeAllListeners();
	}

}
