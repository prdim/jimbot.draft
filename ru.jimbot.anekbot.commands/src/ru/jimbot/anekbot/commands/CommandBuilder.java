/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.util.ArrayList;
import java.util.List;

import ru.jimbot.anekbot.AnekCommandBuilder;
import ru.jimbot.core.AliasCommand;
import ru.jimbot.core.Command;
import ru.jimbot.core.Parser;

/**
 * @author spec
 *
 */
public class CommandBuilder extends AnekCommandBuilder {

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.AnekCommandBuilder#build(ru.jimbot.core.Parser)
	 */
	@Override
	public List<Command> build(Parser p) {
		Command t;
		List<Command> lc = new ArrayList<Command>();
		lc.add(new Cmd1(p));
		lc.add(new CmdAbout(p));
		lc.add(new CmdAdd(p));
		lc.add(new CmdAdsstat(p));
		lc.add(new CmdAnek(p));
		lc.add(new CmdFree(p));
		t = new CmdHelp(p);
		lc.add(t);
		lc.add(new AliasCommand(p, t, "?"));
//		lc.add(new CmdRefresh(p));
		lc.add(new CmdStat(p));
		lc.add(new CmdAds(p));
		return lc;
	}

	@Override
	public void destroy() {
		// Не создаю динамические команды, поэтому тут нечего делать
		
	}

}
