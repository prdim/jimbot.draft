/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import ru.jimbot.anekbot.AnekBot;
import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.Message;
import ru.jimbot.core.Parser;

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
	 * @see ru.jimbot.core.Command#exec(ru.jimbot.core.Message)
	 */
	@Override
	public Message exec(Message m) {
		return new Message(m.getSnOut(), m.getSnIn(), exec(m.getSnIn(), null));
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn, Vector param) {
		return ((AnekBot)p.getService()).getAnekDB().adsStat();
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getCommandPatterns()
	 */
	@Override
	public List<String> getCommandPatterns() {
		return Arrays.asList(new String[] {"!adsstat"});
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

}
