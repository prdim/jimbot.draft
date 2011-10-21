/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.util.Collection;
import ru.jimbot.anekbot.AnekBot;
import ru.jimbot.anekbot.AnekBotCommandParser;
import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.Parser;
import ru.jimbot.core.Variable;

/**
 * Случайный анекдот
 * 
 * @author Prolubnikov Dmitry
 */
public class Cmd1 extends DefaultCommand {

	public Cmd1(Parser p) {
        super(p);
    }

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn) {
		((AnekBotCommandParser)p).state++;
        ((AnekBotCommandParser)p).stateInc(sn);
        try {
        	StringBuffer sb = new StringBuffer();
        	sb.append(((AnekBot)p.getService()).getAnekDB().getAnek());
        	if(((AnekBot)p.getService()).getConfig().isUseAds()) {
        		int r = ((AnekBot)p.getService()).getConfig().getAdsRate();
        		sb.append(((AnekBot)p.getService()).getAnekDB().getAds(r));
        	}
			return sb.toString();
		} catch (Exception e) {
			p.getService().err(e.getMessage(), e);
			return "Ошибка получения анекдота :(";
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getHelp()
	 */
	@Override
	public String getHelp() {
		return " - случайный анекдот";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getXHelp()
	 */
	@Override
	public String getXHelp() {
		return getHelp();
	}

	@Override
	public void publishParameters(Collection<Variable> params) {
		// нет параметров
	}

	@Override
	public String getName() {
		return "1";
	}
}
