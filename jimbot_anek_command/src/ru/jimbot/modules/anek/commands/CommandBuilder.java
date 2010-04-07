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

package ru.jimbot.modules.anek.commands;

import java.util.ArrayList;
import java.util.List;

import ru.jimbot.core.api.Command;
import ru.jimbot.core.api.Parser;
import ru.jimbot.modules.anek.AnekCommandBuilder;

/**
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class CommandBuilder extends AnekCommandBuilder {

	public List<Command> build(Parser p) {
		List<Command> lc = new ArrayList<Command>();
		lc.add(new Cmd1(p));
		lc.add(new CmdAbout(p));
		lc.add(new CmdAdd(p));
		lc.add(new CmdAdsstat(p));
		lc.add(new CmdAnek(p));
		lc.add(new CmdFree(p));
		lc.add(new CmdHelp(p));
		lc.add(new CmdRefresh(p));
		lc.add(new CmdStat(p));
		return lc;
	}

}
