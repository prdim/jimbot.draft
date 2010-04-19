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

package ru.jimbot.modules.anek;

import ru.jimbot.Manager;
import ru.jimbot.core.*;
import ru.jimbot.core.api.CommandProtocolListener;
import ru.jimbot.core.api.DbStatusListener;
import ru.jimbot.core.api.DefaultService;
import ru.jimbot.core.api.IProtocolManager;
import ru.jimbot.core.api.Protocol;
import ru.jimbot.modules.anek.db.DBConverter;
import ru.jimbot.util.Log;

/**
 * Реализация сервиса анекдотного бота
 * 
 * @author Prolubnikov Dmitry
 */
public class AnekService extends DefaultService implements DbStatusListener {
    private String name = ""; // Имя сервиса
//    private HashMap<String, Protocol> prots = new HashMap<String, Protocol>(); // Ссылки на протоколы
    private AnekConfig config;
//    private DBAneks db;
    private AnekWork aw;
    private boolean start = false;
    private AnekCommandParser cmd;
//    private ChronoMaster cron = new ChronoMaster();
    private AnekCommandConnector con = null;

    public AnekService(String name) {
        this.name = name;
        config = AnekConfig.load(name);
        aw = new AnekWork(name, this);
    }
    
    public AnekService(String name, AnekCommandConnector con) {
    	this.con = con;
    	this.name = name;
        config = AnekConfig.load(name);
        aw = new AnekWork(name, this);
    }
    
    public AnekCommandConnector getCommandConnector() {
    	return con;
    }

    public AnekWork getAnekWork() {
        return aw;
    }
    
    /**
     * Запуск сервиса
     */
    public void start() {
    	DBConverter dbc = new DBConverter();
    	try {
    		dbc.openMysql(config.getDb().getHost(), config.getDb().getBase(), config.getDb().getUser(), config.getDb().getPass().getPass());
    		dbc.convertAneks("./services/" + name + "/db/aneks");
    		dbc.convertAneksTemp("./services/" + name + "/db/aneks");
    		dbc.convertAds("./services/" + name + "/db/aneks");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	if(true) return;
        getCron().clear();
        getCron().start();
        for(int i=0;i<config.getUins().size();i++) {
//            Protocol p = new IcqProtocol();
//            p.setConnectionData("login.icq.com", 5980, config.getUins()[i].getScreenName(),
//                    config.getUins()[i].getPass().getPass());
//            p.setLogger(Log.getLogger(name));
//            p.setStatusData(config.getStatus(), config.getStatustxt());
//            p.setXStatusData(config.getXstatus(), config.getXstatustxt1(), config.getXstatustxt2());
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
        cmd = new AnekCommandParser(this);
        getCron().addTask(new CheckSessionTask(cmd, 60000));
        // TODO ...
        addDbStatusListener(this);
        start = true;
        try {
        	aw.initDB();
        	onConnect();
        } catch (Exception ex) {
        	ex.printStackTrace();
        	onError(ex.getMessage());
        }
//        db = aw.db;
    }

    /**
     * Остановка сервиса
     */
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
        aw.closeDB();
        start = false;
        removeAllListeners();
        // TODO Подумать как лучше убрать ссылки и слушатели очередей, парсеров команд, протоколом и т.п.
    }

    /**
     * Сервис запущен?
     *
     * @return
     */
    public boolean isRun() {
        return start;
    }

    /**
     * Возвращает имя данного сервиса
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Набор настроек сервиса
     *
     * @return
     */
    public AnekConfig getConfig() {
        return config;
    }

//    /**
//     * Экземпляр класса для работы с БД
//     *
//     * @return
//     */
//    public DBAdaptor getDB() {
//        return db;
//    }

    /**
     * Соединение с базой произошло, можно запускать УИНы
     * @param db - ссылка на базу
     */
    public void onConnect() {
    	System.out.println("Connect DB!");
        for(CommandProtocolListener i:getCommandProtocolListeners()) {
            i.logOn();
        }
//        cron.clear();
//        ChangeStatusTask t = new ChangeStatusTask(this, 120000, aw);
////        t.addStatus(1, "Статус1", "Текст статуса 1");
////        t.addStatus(2, "Статус2", "Текст статуса 2");
////        t.addStatus(3, "Статус3", "Текст статуса 3");
//        cron.addTask(t);
//        cron.start();
    }

    /**
     * При подключении к базе произошла ошибка
     * @param e
     */
    public void onError(String e) {
        Log.getLogger(getName()).error("Ошибка соединения с базой данных. Отключаю сервис " + name);
        stop();
    }
}
