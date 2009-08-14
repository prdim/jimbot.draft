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

import ru.jimbot.core.Service;
import ru.jimbot.core.Protocol;
import ru.jimbot.modules.AbstractProps;
import ru.jimbot.modules.MsgInQueue;
import ru.jimbot.modules.MsgOutQueue;
import ru.jimbot.db.DBAdaptor;

import java.util.HashMap;

/**
 * Реализация сервиса анекдотного бота
 * 
 * @author Prolubnikov Dmitry
 */
public class AnekService implements Service {
    private String name = ""; // Имя сервиса
    private HashMap<String, Protocol> prots = new HashMap<String, Protocol>(); // Ссылки на протоколы
    private MsgInQueue inq = new MsgInQueue(new AnekCommandProc());
    private MsgOutQueue outq = new MsgOutQueue();
    private AnekCommandProc cmd = new AnekCommandProc();
    public AnekService(String name) {
        this.name = name;
    }

    /**
     * Запуск сервиса
     */
    public void start() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Остановка сервиса
     */
    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Возвращает екземпляр протокола по УИНу
     *
     * @param screenName
     * @return
     */
    public Protocol getProtocol(String screenName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Возвращает имя данного сервиса
     *
     * @return
     */
    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Набор настроек сервиса
     *
     * @return
     */
    public AbstractProps getProps() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Экземпляр класса для работы с БД
     *
     * @return
     */
    public DBAdaptor getDB() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
