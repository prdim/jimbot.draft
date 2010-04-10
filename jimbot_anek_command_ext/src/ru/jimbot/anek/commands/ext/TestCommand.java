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

package ru.jimbot.anek.commands.ext;

import ru.jimbot.core.Message;
import ru.jimbot.core.api.DefaultCommand;
import ru.jimbot.core.api.Parser;
import ru.jimbot.util.Log;

import java.util.Arrays;
import java.util.Vector;
import java.util.List;

/**
 * Тестовая команда, для демонстрации создания плагина
 * @author Prolubnikov Dmitry
 */
public class TestCommand extends DefaultCommand {

    public TestCommand(Parser p) {
        super(p);
    }

    /* (non-Javadoc)
	 * @see ru.jimbot.core.api.DefaultCommand#destroy()
	 */
	@Override
	public void destroy() {
		System.out.println("Destroy test command");
	}



	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.DefaultCommand#init()
	 */
	@Override
	public void init() {
		System.out.println("Init test command");
	}



	/**
     * Выполнение команды
     *
     * @param m - обрабатываемое сообщение с командой
     * @return - результат (если нужен)
     */
    public Message exec(Message m) {
    	return new Message(m.getSnOut(), m.getSnIn(), exec(m.getSnIn(),new Vector()));
    }

    /**
     * Выполнение команды
     *
     * @param sn    - от кого?
     * @param param - вектор параметров (могут быть как строки, так и числа)
     * @return - результат (если нужен)
     */
    public String exec(String sn, Vector param) {
        return "Привет! Я новая тестовая команда!";
    }

    /**
     * Список ключевых слов, по которым можно вызвать эту команду
     *
     * @return
     */
    public List<String> getCommandPatterns() {
        return Arrays.asList(new String[] {"!ctest"});
    }

    /**
     * Выводит короткую помощь по команде (1 строка)
     *
     * @return
     */
    public String getHelp() {
        return " - Это тестовая команда. Позволяет увидеть способ добавления новых команд плагинами.";
    }

    /**
     * Выводит подробную помощь по команде
     *
     * @return
     */
    public String getXHelp() {
        return getHelp();
    }
}
