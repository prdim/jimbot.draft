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

import java.util.List;

import ru.jimbot.core.api.Command;
import ru.jimbot.core.api.ICommandBuilder;
import ru.jimbot.core.api.Parser;

/**
 * Чтобы не путать все плагины с командами между разными ботами - создадим свое расширение интерфейса
 * Именно его и будем ловить при поиске сервисов.
 * @author Prolubnikov Dmitry
 *
 */
public abstract class AnekCommandBuilder implements ICommandBuilder {

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.ICommandBuilder#build(ru.jimbot.core.api.Parser)
	 */
	public abstract List<Command> build(Parser p);

}
