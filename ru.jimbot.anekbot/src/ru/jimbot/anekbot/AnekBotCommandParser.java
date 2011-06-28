/**
 * 
 */
package ru.jimbot.anekbot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import ru.jimbot.anekbot.internal.ActivatorAnekBot;
import ru.jimbot.core.Command;
import ru.jimbot.core.DefaultCommandParser;
import ru.jimbot.core.ICommandBuilder;
import ru.jimbot.core.Message;
import ru.jimbot.core.MsgStatCounter;
import ru.jimbot.core.events.EventProxy;
import ru.jimbot.core.services.BotService;

/**
 * @author spec
 *
 */
public class AnekBotCommandParser extends DefaultCommandParser {
	private EventProxy eva;
	private boolean firstStartMsg = false;
	public ConcurrentHashMap <String,StateUin> uq;
    public long state=0; //Статистика запросов
    public long state_add = 0;
    private Timer timer;
    private TimerTask qt;
	
	public AnekBotCommandParser(BotService srv) {
		super(srv);
		eva = new EventProxy(ActivatorAnekBot.getEventAdmin(), srv.getServiceName());
		cm.setDefaultAge(60000); // Время жизни одной сессии по умолчанию
        initCommands();	
        uq = new ConcurrentHashMap<String,StateUin>();
        timer = new Timer("session clear " + srv.getServiceName());
        qt = new TimerTask() {

			@Override
			public void run() {
				cm.clearExpired();
			}
        	
        };
        timer.schedule(qt, cm.getDefaultAge(), cm.getDefaultAge());
	}
	
	/**
     * Создаем реестр всех команд
     */
    public void initCommands() {
    	for(ICommandBuilder i : ((AnekBot)srv).getCommandConnector().getAllCommandBuilders()) {
    		for(Command j : i.build(this)) {
    			addCommand(j);
    		}
    	}
        for(Command i:commands.values()) {
            i.init();
        }
    }

	@Override
	public void parse(Message m) {
		try {
			// Игнорируем все лишнее
			if (m.getType() != Message.TYPE_TEXT)
				return;
			firstMsg(m);
			addState(m.getSnIn());
			Command cmd;
			if ("".equals(cm.getContext(m.getSnIn()).getLastCommand())) {
				cmd = this.getCommand(this.getCommand(m));
			} else {
				cmd = this.getCommand(cm.getContext(m.getSnIn())
						.getLastCommand());
			}
			if (cmd == null) {
				notify(new Message(m.getSnOut(), m.getSnIn(),
						"Неверная команда! Для справки отправте !help"));
			} else {
				if (cmd.authorityCheck(m.getSnIn()))
					notify(cmd.exec(m));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Set<String> getAuthList(String screenName) {
		HashSet<String> h = new HashSet<String>();
        if(srv.getConfig().testAdmin(screenName)){
            h.add("admin");
        }
        return h;
	}
	
	private void notify(Message m) {
    	eva.outgoingMessage(m);
    }
    
    private void firstMsg(Message m){
    	if(!firstStartMsg){
    		String[] s = srv.getConfig().getAdminUins();
    		for(int i=0;i<s.length;i++){
    		    String ss = "Бот успешно запущен!\n";
//                if(HttpUtils.checkNewVersion())
//                    ss += "На сайте http://jimbot.ru Доступна новая версия!\n" + HttpUtils.getNewVerDesc();
//                else
//                    ss += "Вся информация о боте из первых рук только на сайте: http://jimbot.ru";
                notify(new Message(m.getSnOut(), s[i], ss));
    		}
    		firstStartMsg=true;
    	}
    }
    
    /**
     * Определение времени запуска бота
     */
    public long getTimeStart(){
        long t = 0;
        try{
            File f = new File("./state");
            t = f.lastModified();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return t;
    }

    public long getUpTime(){
        return System.currentTimeMillis()-getTimeStart();
    }

    public long getHourStat(){
        if(getUpTime()>1000*60*60){
            return state/(getUpTime()/3600000);
        }
        return 0;
    }

    public long getDayStat(){
        if(getUpTime()>1000*60*60*24){
            return state/(getUpTime()/86400000);
        }
        return 0;
    }

    public String getTime(long t){
        Date dt = new Date(t);
        SimpleDateFormat df = new SimpleDateFormat("HH часов mm минут");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return (t/86400000) + " дней " + df.format(dt);
//        DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
//        df.setTimeZone(TimeZone.getTimeZone("GMT"));
//        return (t/86400000) + " дней " + df.format(dt);
    }

    /**
     * Возвращает наименее загруженный номер
     * @return
     */
    public String getFreeUin(){
    	String u = "";
    	int k = 99;
    	int c = 0;
    	for(int i=0;i<srv.getConfig().getUins().size();i++){
            String s = srv.getConfig().getUins().get(i).getScreenName();
    		if(srv.getProtocol(s).isOnLine()){
                c = MsgStatCounter.getElement(s).getMsgCount(MsgStatCounter.M1);
    			if(k>c){
    				k = c;
    				u = s;
    			}
    		}
    	}
    	return u;
    }

    public void addState(String uin){
        if(!uq.containsKey(uin)){
            StateUin u = new StateUin(uin,0);
            uq.put(uin,u);
        }
    }

    public void stateInc(String uin){
        StateUin u = uq.get(uin);
        u.cnt++;
        uq.put(uin,u);
    }

    public class StateUin {
        public String uin="";
        public int cnt=0;

        public StateUin(String u, int c){
            uin = u;
            cnt = c;
        }
    }
}
