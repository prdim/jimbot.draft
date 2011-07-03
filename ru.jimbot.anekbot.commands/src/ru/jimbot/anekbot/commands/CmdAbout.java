/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.MainProps;
import ru.jimbot.core.Message;
import ru.jimbot.core.Parser;

/**
 * Команда !about
 * 
 * @author Prolubnikov Dmitry
 */
public class CmdAbout extends DefaultCommand {
	
	public CmdAbout(Parser p) {
        super(p);
    }

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(ru.jimbot.core.Message)
	 */
	@Override
	public Message exec(Message m) {
		return new Message(m.getSnOut(), m.getSnIn(), exec(m.getSnIn(),new Vector()));
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn, Vector param) {
		return "JimBot v." + MainProps.VERSION + "\n(c) Spec, 2006-2010\n" +
        "Поддержка проекта: http://jimbot.ru";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getCommandPatterns()
	 */
	@Override
	public List<String> getCommandPatterns() {
		return Arrays.asList(new String[] {"!about"});
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getHelp()
	 */
	@Override
	public String getHelp() {
		return " - информация об авторе программы";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getXHelp()
	 */
	@Override
	public String getXHelp() {
		return getHelp();
	}

}
