/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.util.Collection;
import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.Parser;
import ru.jimbot.core.Variable;
import ru.jimbot.core.VariableWord;

/**
 * Помощь по боту
 * @author Prolubnikov Dmitry
 */
public class CmdHelp extends DefaultCommand {
	VariableWord c = new VariableWord();

	public CmdHelp(Parser p) {
        super(p);
    }

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn) {
		String s = "";
        try {
            if(c.getValue().equals("")){
                s += "Список доступных команд бота:\nво всех командах символы <> указывать не нужно\n";
                for(String i:p.getCommands()) {
                    if(p.getCommand(i).authorityCheck(sn)) {
                        String s1 = p.getCommand(i).getHelp();
                        s += s1.equals("") ? "" : (i + s1 + '\n');
                    }
                }
            } else {
                if(p.getCommands().contains(c)) {
                    s += c.getValue() + p.getCommand(c.getValue()).getXHelp();
                } else {
                    s += "Команда " + c.getValue() + " не найдена.";
                }
            }
        } catch (Exception e) {
        	p.getService().err(e.getMessage(), e);
        }
        return s;
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

	@Override
	public void publishParameters(Collection<Variable> params) {
		params.add(c);
	}

	@Override
	public String getName() {
		return "!help";
	}

}
