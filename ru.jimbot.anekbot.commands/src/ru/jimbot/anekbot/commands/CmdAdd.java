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

/**
 * Добавление анекдота
 * 
 * @author Prolubnikov Dmitry
 */
public class CmdAdd extends DefaultCommand {

	public CmdAdd(Parser p) {
        super(p);
    }
	
	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(ru.jimbot.core.Message)
	 */
	@Override
	public Message exec(Message m) {
		return new Message(m.getSnOut(), m.getSnIn(), exec(m.getSnIn(), p.getArgs(m, "$s")));
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn, Vector param) {
		String s = (String) param.get(0);
        if (s.equals("")) return "Пустой анекдот.";
        if (s.length() < 20) return "";
        if (s.length() > 500) return "";
        try {
        	((AnekBot)p.getService()).getAnekDB().addTempAnek(s, sn);
        	p.getService().log("Add anek <" + sn + ">: " + s);
	        ((AnekBotCommandParser) p).state_add++;
	        return "Анекдот сохранен. После рассмотрения администрацией он будет добавлен в базу.";
		} catch (Exception e) {
			p.getService().err(e.getMessage(), e);
			return "Ошибка сохранения анекдота :(";
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getCommandPatterns()
	 */
	@Override
	public List<String> getCommandPatterns() {
		return Arrays.asList(new String[] {"!add"});
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getHelp()
	 */
	@Override
	public String getHelp() {
		return " <анекдот> - Добавить новый анекдот";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getXHelp()
	 */
	@Override
	public String getXHelp() {
		return getHelp();
	}

}
