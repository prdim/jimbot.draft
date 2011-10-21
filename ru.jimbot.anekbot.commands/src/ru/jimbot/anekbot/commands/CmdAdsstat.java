/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.util.Collection;
import ru.jimbot.anekbot.AnekBot;
import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.Parser;
import ru.jimbot.core.Variable;

/**
 * Статистика показа рекламы
 * 
 * @author Prolubnikov Dmitry
 */
public class CmdAdsstat extends DefaultCommand {

	public CmdAdsstat(Parser p) {
        super(p);
    }

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn) {
		return ((AnekBot)p.getService()).getAnekDB().adsStat();
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getHelp()
	 */
	@Override
	public String getHelp() {
		return ""; // В обычном хелпе выводить не нужно
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getXHelp()
	 */
	@Override
	public String getXHelp() {
		return " - статистика показа рекламы";
	}

	@Override
	public void publishParameters(Collection<Variable> params) {
		
	}

	@Override
	public String getName() {
		return "!adsstat";
	}

}
