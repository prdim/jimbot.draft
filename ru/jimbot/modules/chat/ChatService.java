/*
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

package ru.jimbot.modules.chat;

import ru.jimbot.core.DefaultService;
import ru.jimbot.core.DbStatusListener;
import ru.jimbot.core.AbstractProps;
import ru.jimbot.db.DBAdaptor;

/**
 * Реализация сервиса чата
 * @author Prolubnikov Dmitry
 */
public class ChatService extends DefaultService implements DbStatusListener {
    private String name = ""; // Имя сервиса
    private ChatProps props;

    public ChatService(String name) {
        this.name = name;
        props = ChatProps.getInstance(name);
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
     * Сервис запущен?
     *
     * @return
     */
    public boolean isRun() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Произошло соединение с БД
     *
     * @param db
     */
    public void onConnect(DBAdaptor db) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Произошла ошибка БД и разрыв соединения
     *
     * @param e
     */
    public void onError(String e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
