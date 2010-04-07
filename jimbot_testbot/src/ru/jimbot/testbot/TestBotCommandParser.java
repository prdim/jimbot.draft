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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import ru.jimbot.core.Message;
import ru.jimbot.core.api.Command;
import ru.jimbot.core.api.DefaultCommandParser;
import ru.jimbot.core.api.ICommandBuilder;
import ru.jimbot.core.api.QueueListener;
import ru.jimbot.core.api.Service;
import ru.jimbot.util.HttpUtils;
import ru.jimbot.util.Log;

/**
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class TestBotCommandParser extends DefaultCommandParser implements QueueListener {
    private boolean firstStartMsg = false;
    
	public TestBotCommandParser(Service srv) {
		super(srv);
        srv.addParserListener(this);
        cm.setDefaultAge(60000); // Время жизни одной сессии по умолчанию
        initCommands();		
	}

    /**
     * Создаем реестр всех команд
     */
    public void initCommands() {
    	for(ICommandBuilder i : ((TestBot)srv).getCommandConnector().getAllCommandBuilders()) {
    		for(Command j : i.build(this)) {
    			addCommand(j);
    		}
    	}
        for(Command i:commands.values()) {
            i.init();
        }
    }
    
    public void onMessage(Message m) {
    	parse(m);
	}

	public Set<String> getAuthList(String screenName) {
        HashSet<String> h = new HashSet<String>();
        if(srv.getConfig().testAdmin(screenName)){
            h.add("admin");
        }
        return h;
	}

	public void parse(Message m) {
		if(m.getType()!=Message.TYPE_TEXT) return;
        firstMsg(m);
        Command cmd;
        if(cm.getContext(m.getSnIn()).getCounter()==0) // это новая сессия? Повторим приветствие
    		notify(new Message(m.getSnOut(), m.getSnIn(), ((TestBotConfig)srv.getConfig()).getHelloMsg()));
        cm.getContext(m.getSnIn()).update(); // Обновим сессию
        // Обработка интерактивных команд
        if("".equals(cm.getContext(m.getSnIn()).getLastCommand())) {
            cmd = this.getCommand(this.getCommand(m));
        } else {
            cmd = this.getCommand(cm.getContext(m.getSnIn()).getLastCommand());
        }
        if(cmd==null){ // Если сообщение - не команда    	
        	// Просто запишем его в лог
        	Log.getLogger(srv.getName()).talk(m.getSnIn() + ">>> " + m.getMsg());
        } else {
        	// Проверим полномочия и выполним команду
            if(cmd.authorityCheck(m.getSnIn())) notify(cmd.exec(m));
        }
	}

    private void notify(Message m) {
    	System.out.println(m);
        for(QueueListener i:srv.getOutQueueListeners()) {
            i.onMessage(m);
        }
    }
    
    private void firstMsg(Message m){
    	if(!firstStartMsg){
    		String[] s = srv.getConfig().getAdminUins();
    		for(int i=0;i<s.length;i++){
    		    String ss = "Бот успешно запущен!\n";
                if(HttpUtils.checkNewVersion())
                    ss += "На сайте http://jimbot.ru Доступна новая версия!\n" + HttpUtils.getNewVerDesc();
                else
                    ss += "Вся информация о боте из первых рук только на сайте: http://jimbot.ru";
                notify(new Message(m.getSnOut(), s[i], ss));
    		}
    		firstStartMsg=true;
    	}
    }
}
