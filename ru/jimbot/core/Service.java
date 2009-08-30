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

package ru.jimbot.core;

import ru.jimbot.core.Protocol;
import ru.jimbot.db.DBAdaptor;
import ru.jimbot.core.AbstractProps;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Интерфейс для всех сервисов бота
 * 
 * @author Prolubnikov Dmitry
 */
public interface Service {
    /**
     * Запуск сервиса
     */
    public void start();

    /**
     * Остановка сервиса
     */
    public void stop();

    /**
     * Сервис запущен?
     * @return
     */
    public boolean isRun();

    /**
     * Возвращает екземпляр протокола по УИНу
     * @param screenName
     * @return
     */
    public Protocol getProtocol(String screenName);

    /**
     * Список УИНов ко всем установленным протоколам
     * @return
     */
    public Set<String> getAllProtocols();

    /**
     * Возвращает имя данного сервиса
     * @return
     */
    public String getName();

    /**
     * Набор настроек сервиса
     * @return
     */
    public AbstractProps getProps();

    /**
     * Экземпляр класса для работы с БД
     * @return
     */
    public DBAdaptor getDB();

    /**
     * Засунуть объект в хранилище данных
     * @param key
     * @param o
     */
    public void addDataStorage(String key, Object o);

    /**
     * Получить объект из хранилища
     * @param key
     * @return
     */
    public Object getDataStorage(String key);

    /**
     * Возвращает очередб входящих
     * @return
     */
    public MsgInQueue getInQueue();

    /**
     * Возвращает очередь исходящих для заданного уина
     * @return
     */
    public ConcurrentLinkedQueue<Message> getOutQueue(String sn);

    /**
     * возвращает очередь исходящих
     * @return
     */
    public MsgOutQueue getOutQueue();

    public List<DbStatusListener> getDbStatusListeners();
    void addDbStatusListener(DbStatusListener e);
    boolean removeDbStatusListener(DbStatusListener e);
    public void addProtocolListener(ProtocolListener e);
    public boolean removeProtocolListener(ProtocolListener e);
    public List<ProtocolListener> getProtocolListeners();
    public void addParserListener(QueueListener e);
    public boolean removeParserListener(QueueListener e);
    public List<QueueListener> getParserListeners();
    public void addCommandProtocolListener(CommandProtocolListener e);
    public void removeCommandProtocolListener(CommandProtocolListener e);
    public Collection<CommandProtocolListener> getCommandProtocolListeners();
    public CommandProtocolListener getCommandProtocolListener(String screenName);
}
