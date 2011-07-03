/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.Message;
import ru.jimbot.core.Parser;

/**
 * Помощь по боту
 * @author Prolubnikov Dmitry
 */
public class CmdHelp extends DefaultCommand {

	public CmdHelp(Parser p) {
        super(p);
    }
	
	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(ru.jimbot.core.Message)
	 */
	@Override
	public Message exec(Message m) {
		return new Message(m.getSnOut(), m.getSnIn(), exec(m.getSnIn(), p.getArgs(m, "$c")));
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn, Vector param) {
		String s = "";
        String c = "";
        try {
            c = (String)param.get(0);
        } catch (Exception e) {}
        try {
            if(c.equals("")){
                s += "Список доступных команд бота:\nво всех командах символы <> указывать не нужно\n";
                for(String i:p.getCommands()) {
                    if(p.getCommand(i).authorityCheck(sn)) {
                        String s1 = p.getCommand(i).getHelp();
                        s += s1.equals("") ? "" : (i + s1 + '\n');
                    }
                }
            } else {
                if(p.getCommands().contains(c)) {
                    s += c + p.getCommand(c).getXHelp();
                } else {
                    s += "Команда " + c + " не найдена.";
                }
            }
        } catch (Exception e) {
        	p.getService().err(e.getMessage(), e);
        }
        return s;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getCommandPatterns()
	 */
	@Override
	public List<String> getCommandPatterns() {
		return Arrays.asList(new String[] {"!help"});
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getHelp()
	 */
	@Override
	public String getHelp() {
		return " - помощь по командам\n!help <команда> - подробности использования команды";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getXHelp()
	 */
	@Override
	public String getXHelp() {
		return " <команда> - подробная информация по использованию команды.";
	}

}
