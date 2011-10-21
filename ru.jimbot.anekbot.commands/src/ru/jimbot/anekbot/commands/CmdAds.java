/**
 * 
 */
package ru.jimbot.anekbot.commands;

import java.util.Collection;
import ru.jimbot.anekbot.AnekBot;
import ru.jimbot.anekbot.IAnekBotDB;
import ru.jimbot.core.DefaultCommand;
import ru.jimbot.core.Message;
import ru.jimbot.core.Parser;
import ru.jimbot.core.UserContext;
import ru.jimbot.core.Variable;

/**
 * Работа с рекламой
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class CmdAds extends DefaultCommand {

	protected CmdAds(Parser p) {
		super(p);
	}
	
	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#exec(ru.jimbot.core.Message)
	 */
	@Override
	public Message exec(Message m) {
		UserContext c = p.getContextManager().getContext(m.getSnIn());
		IAnekBotDB db = ((AnekBot)p.getService()).getAnekDB();
		String s = "Активных рекламных объявлений: " + db.adsCount() + 
			"\nВыберите действие: \n0 - выход\n1 - список активных объявлений" +
			"\n2 - удалить\n3 - добавить\n4 - продлить";
		String k = c.getData("cmd")==null ? "" : (String)c.getData("cmd");
		if("".equals(c.getLastCommand())) {
			c.setLastCommand("!ads");
		} else if("2".equals(k)) {
			try {
				s = db.delAds(Long.parseLong(m.getMsg())) ? "Выполнено." : "Произошла ошибка.";
			} catch (Exception e) {
				p.getService().err(e.getMessage(), e);
				s = "Произошла ошибка.";
			}
			c.getData().remove("cmd");
		} else if("3".equals(k)) {
			c.getData().put("txt", m.getMsg());
			c.getData().put("cmd", "31");
			s = "Примечание: ";
		} else if("31".equals(k)) {
			long id = -1;
			try {
				id = db.addAds((String)c.getData().get("txt"), m.getMsg(), m.getSnIn());
			} catch (Exception e) {
				p.getService().err(e.getMessage(), e);
			}
			s = id<0 ? "Ошибка добавления" : "Добавлено новое объявление " + id;
			c.getData().remove("txt");
			c.getData().remove("cmd");
		}else if("4".equals(k)) {
			try {
				s = db.extendAds(Long.parseLong(m.getMsg()), 7*24*3600*1000) ? "Выполнено." : "Произошла ошибка.";
			} catch (Exception e) {
				p.getService().err(e.getMessage(), e);
				s = "Произошла ошибка.";
			}
			c.getData().remove("cmd");
		} else if("0".equals(m.getMsg())) {
			c.setLastCommand("");
			s = "Сеанс работы с рекламными сообщениями окончен";
		} else if("1".equals(m.getMsg())) {
			s = db.adsList();
		} else if("2".equals(m.getMsg())) {
			s = "Номер удаляемого объявления: ";
			c.getData().put("cmd", "2");
		} else if("3".equals(m.getMsg())) {
			s = "Текст объявления: ";
			c.getData().put("cmd", "3");
		} else if("4".equals(m.getMsg())) {
			s = "Номер продляемого объявления: ";
			c.getData().put("cmd", "4");
		} else {

		}
		c.update();
		return new Message(m.getSnOut(), m.getSnIn(), s);
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
		return " - работа с рекламными сообщениями";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Command#getXHelp()
	 */
	@Override
	public String getXHelp() {
		return getHelp();
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.DefaultCommand#authorityCheck(java.lang.String)
	 */
	@Override
	public boolean authorityCheck(String screenName) {
		return p.getService().getConfig().testAdmin(screenName);
	}

	@Override
	public void publishParameters(Collection<Variable> params) {
		
	}

	@Override
	public String getName() {
		return "!ads";
	}
}
