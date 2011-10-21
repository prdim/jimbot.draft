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
import ru.jimbot.core.VariableString;

/**
 * Добавление анекдота
 * 
 * @author Prolubnikov Dmitry
 */
public class CmdAdd extends DefaultCommand {
	VariableString anek = new VariableString();

	public CmdAdd(Parser p) {
        super(p);
    }

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn) {
        if (anek.getValue().equals("")) return "Пустой анекдот.";
        if (anek.getValue().length() < 20) return "";
        if (anek.getValue().length() > 500) return "";
        try {
        	((AnekBot)p.getService()).getAnekDB().addTempAnek(anek.getValue(), sn);
        	p.getService().log("Add anek <" + sn + ">: " + anek.getValue());
	        ((AnekBotCommandParser) p).state_add++;
	        return "Анекдот сохранен. После рассмотрения администрацией он будет добавлен в базу.";
		} catch (Exception e) {
			p.getService().err(e.getMessage(), e);
			return "Ошибка сохранения анекдота :(";
		}
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

	@Override
	public void publishParameters(Collection<Variable> params) {
		params.add(anek);
	}

	@Override
	public String getName() {
		return "!add";
	}

}
