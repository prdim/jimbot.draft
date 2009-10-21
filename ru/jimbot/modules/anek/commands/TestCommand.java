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

package ru.jimbot.modules.anek.commands;

import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.Parser;
import ru.jimbot.core.Message;

import java.util.Vector;
import java.util.List;

/**
 * Для тестирования и последующего переноса команды в скрипт
 * @author Prolubnikov Dmitry
 */
public class TestCommand extends DefaultCommand {

    public TestCommand(Parser p) {
        super(p);
    }

    /**
     * Выполнение команды
     *
     * @param m - обрабатываемое сообщение с командой
     * @return - результат (если нужен)
     */
    public Message exec(Message m) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Выполнение команды
     *
     * @param sn    - от кого?
     * @param param - вектор параметров (могут быть как строки, так и числа)
     * @return - результат (если нужен)
     */
    public String exec(String sn, Vector param) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Список ключевых слов, по которым можно вызвать эту команду
     *
     * @return
     */
    public List<String> getCommandPatterns() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Выводит короткую помощь по команде (1 строка)
     *
     * @return
     */
    public String getHelp() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Выводит подробную помощь по команде
     *
     * @return
     */
    public String getXHelp() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
