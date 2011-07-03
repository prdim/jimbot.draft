/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import ru.jimbot.anekbot.AnekBot;
import ru.jimbot.anekbot.AnekBotCommandParser;
import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.Message;
import ru.jimbot.core.Parser;
import ru.jimbot.core.exceptions.DbException;

/**
 * Получение анекдота по номеру
 * 
 * @author Prolubnikov Dmitry
 */
public class CmdAnek extends DefaultCommand {

	public CmdAnek(Parser p) {
        super(p);
    }
	
	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(ru.jimbot.core.Message)
	 */
	@Override
	public Message exec(Message m) {
		return new Message(m.getSnOut(), m.getSnIn(), exec(m.getSnIn(), p.getArgs(m, "$n")));
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn, Vector param) {
		((AnekBotCommandParser)p).state++;
        ((AnekBotCommandParser)p).stateInc(sn);
        try {
			return ((AnekBot)p.getService()).getAnekDB().getAnek((Integer)param.get(0));
		} catch (Exception e) {
			p.getService().err(e.getMessage(), e);
			return "Ошибка получения анекдота :(";
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getCommandPatterns()
	 */
	@Override
	public List<String> getCommandPatterns() {
		return Arrays.asList(new String[] {"!anek"});
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getHelp()
	 */
	@Override
	public String getHelp() {
		return " <id> - Получить анекдот с заданным id";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getXHelp()
	 */
	@Override
	public String getXHelp() {
		return getHelp();
	}

}
