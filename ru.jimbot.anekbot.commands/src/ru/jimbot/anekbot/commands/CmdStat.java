/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import ru.jimbot.anekbot.AnekBot;
import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.Parser;
import ru.jimbot.core.Variable;
import ru.jimbot.anekbot.AnekBotCommandParser;

/**
 * Статистика
 * 
 * @author Prolubnikov Dmitry
 */
public class CmdStat extends DefaultCommand {

	public CmdStat(Parser p) {
        super(p);
    }

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(java.lang.String, java.util.Vector)
	 */
	@Override
	public String exec(String sn) {
		String s = "Всего в базе анекдотов: " + ((AnekBot)p.getService()).getAnekDB().count();
        s += "\nОтправлено анекдотов: " + ((AnekBotCommandParser)p).state;
        s += "\nДобавлено анекдотов: " + ((AnekBotCommandParser)p).state_add;
        s += "\nУникальных UIN: " + ((AnekBotCommandParser)p).uq.size();
        s += "\nАктивных в данный момент: " + p.getContextManager().getAllContexts().size();
        s += "\nБот запущен: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(((AnekBotCommandParser)p).getTimeStart()));
        s += "\nВремя работы: " + ((AnekBotCommandParser)p).getTime(((AnekBotCommandParser)p).getUpTime());
        s += "\nВ среднем анекдотов в час: " + ((AnekBotCommandParser)p).getHourStat();
        s += "\nВ среднем анекдотов в сутки: " + ((AnekBotCommandParser)p).getDayStat();
        s += "\nПрочитано вами анекдотов: " + ((AnekBotCommandParser)p).uq.get(sn).cnt;
        if(p.getService().getConfig().testAdmin(sn)){
            s += "\nИспользовано памяти: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        }
        return s;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getHelp()
	 */
	@Override
	public String getHelp() {
		return " - статистика работы бота";
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
		return "!stat";
	}

}
