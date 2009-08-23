/**
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2009 JimBot project
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

import ru.jimbot.core.*;
import ru.jimbot.db.DBAdaptor;
import ru.jimbot.util.Log;

import java.util.HashMap;

/**
 * Реализация сервиса анекдотного бота
 * 
 * @author Prolubnikov Dmitry
 */
public class AnekService extends DefaultService implements DbStatusListener {
    private String name = ""; // Имя сервиса
    private HashMap<String, Protocol> prots = new HashMap<String, Protocol>(); // Ссылки на протоколы
    private MsgInQueue inq;
    private MsgOutQueue outq;
    private AnekProps props;
    private DBAneks db;
    private AnekWork aw;
//    private AnekCommandProc cmd = new AnekCommandProc();

    public AnekService(String name) {
        this.name = name;
        aw = new AnekWork(name, this);
    }

    /**
     * Запуск сервиса
     */
    public void start() {
        inq = new MsgInQueue(this);
        props = AnekProps.getInstance(name);
        // TODO ...
        aw.initDB();
        db = aw.db;
    }

    /**
     * Остановка сервиса
     */
    public void stop() {
        inq.stop();
        inq = null;
        aw.closeDB();
        // TODO ...
    }

    /**
     * Возвращает екземпляр протокола по УИНу
     *
     * @param screenName
     * @return
     */
    public Protocol getProtocol(String screenName) {
        return prots.get(screenName);
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
    public AbstractProps getProps() {
        return props;
    }

    /**
     * Экземпляр класса для работы с БД
     *
     * @return
     */
    public DBAdaptor getDB() {
        return db;
    }

    /**
     * Соединение с базой произошло, можно запускать УИНы
     * @param db - ссылка на базу
     */
    public void onConnect(DBAdaptor db) {
        // TODO подключение номеров
    }

    /**
     * При подключении к базе произошла ошибка
     * @param e
     */
    public void onError(String e) {
        Log.error("Ошибка соединения с базой данных. Отключаю сервис " + name);
        stop();
    }
}
