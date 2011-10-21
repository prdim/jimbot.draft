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
import ru.jimbot.core.VariableNumber;

/**
 * Получение анекдота по номеру
 * 
 * @author Prolubnikov Dmitry
 */
public class CmdAnek extends DefaultCommand {
	VariableNumber anek = new VariableNumber();

	public CmdAnek(Parser p) {
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
			return ((AnekBot)p.getService()).getAnekDB().getAnek(anek.getValue());
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
		return " <id> - Получить анекдот с заданным id";
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
		return "!anek";
	}
}
