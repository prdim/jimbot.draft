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

import ru.jimbot.core.DefaultCommandParser;
import ru.jimbot.core.QueueListener;
import ru.jimbot.core.Service;
import ru.jimbot.core.Message;

import java.util.Set;

/**
 * Парсер команд для чата
 * @author Prolubnikov Dmitry
 */
public class ChatCommandParser extends DefaultCommandParser implements QueueListener {
    public ChatCommandParser(Service srv) {
        super(srv);
    }

    /**
     * Обработка команды
     *
     * @param m
     */
    public void parse(Message m) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Возвращает список разрешенных полномочий для пользователя с заданным УИНом
     *
     * @param screenName
     * @return
     */
    public Set<String> getAuthList(String screenName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onMessage(Message m) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
