/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.util.Collection;
import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.MainProps;
import ru.jimbot.core.Parser;
import ru.jimbot.core.Variable;

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
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn) {
		return "JimBot v." + MainProps.VERSION + "\n(c) Spec, 2006-2010\n" +
        "Поддержка проекта: http://jimbot.ru";
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

	@Override
	public void publishParameters(Collection<Variable> params) {
		// нет аргументов
	}

	@Override
	public String getName() {
		return "!about";
	}

}
