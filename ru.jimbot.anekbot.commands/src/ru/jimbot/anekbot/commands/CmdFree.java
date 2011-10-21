/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.util.Collection;
import ru.jimbot.anekbot.AnekBotCommandParser;
import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.Message;
import ru.jimbot.core.Parser;
import ru.jimbot.core.Variable;

/**
 * Поиск свободнго УИНа
 * 
 * @author Prolubnikov Dmitry
 */
public class CmdFree extends DefaultCommand {

	public CmdFree(Parser p) {
        super(p);
    }
	
	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(ru.jimbot.core.Message)
	 */
	@Override
	public Message exec(Message m) {
		String sn2 = ((AnekBotCommandParser)p).getFreeUin(m.getSnOut());
		((AnekBotCommandParser)p).notify(new Message(sn2, m.getSnIn(), "Привет! Я самый свободный УИН ;)"));
		return new Message(m.getSnOut(), m.getSnIn(), "Номер самого свободного УИНа - " + sn2);
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn) {
		return null;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getHelp()
	 */
	@Override
	public String getHelp() {
		return " - переход на самый свободный уин";
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
		
	}

	@Override
	public String getName() {
		return "!free";
	}

}
