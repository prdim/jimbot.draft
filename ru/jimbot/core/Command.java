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

import java.util.List;

/**
 * Интерфейс команды бота.
 *
 * @author Prolubnikov Dmitry
 */
public interface Command {

    /**
     * Инициализация. Вызывается при создании экземпляра класса.
     */
    public void init();

    /**
     * Выполнение команды
     * @return - результат выполнения для потока исходящих сообщений
     */
    public String execute();

    /**
     * Если результат выполнения - пуская строка, получить результат здесь. Сообщение для конкретного получателя.
     * @return - сообщение
     */
    public Message getOutMessage();

    /**
     * Указание сообщения, над которым должна быть выполнена данная команда.
     * @param m
     */
    public void setInMessage(Message m);

    /**
     * Список ключевых слов, по которым можно вызвать эту команду
     * @return
     */
    public List getCommandPatterns();
}
