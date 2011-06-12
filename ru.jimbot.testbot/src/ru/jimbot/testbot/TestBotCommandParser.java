/**
 * 
 */
package ru.jimbot.testbot;

import java.util.HashSet;
import java.util.Set;

import ru.jimbot.core.Command;
import ru.jimbot.core.DefaultCommandParser;
import ru.jimbot.core.ICommandBuilder;
import ru.jimbot.core.Message;
import ru.jimbot.core.events.EventProxy;
import ru.jimbot.core.events.IncomingMessageEventHandler;
import ru.jimbot.core.events.IncomingMessageListener;
import ru.jimbot.core.services.BotService;
import ru.jimbot.testbot.internal.Activator;

/**
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class TestBotCommandParser extends DefaultCommandParser /*implements IncomingMessageListener*/ {
    private boolean firstStartMsg = false;
//    private IncomingMessageEventHandler h;
    private EventProxy eva;
    
	public TestBotCommandParser(BotService srv) {
		super(srv);
//        srv.addParserListener(this);
        cm.setDefaultAge(60000); // Время жизни одной сессии по умолчанию
        initCommands();	
//        h = new IncomingMessageEventHandler(srv.getServiceName(), this);
//        Activator.regEventHandler(h, h.getHandlerServiceProperties());
        eva = new EventProxy(Activator.getEventAdmin(), srv.getServiceName());
	}

    /**
     * Создаем реестр всех команд
     */
    public void initCommands() {
    	for(ICommandBuilder i : ((TestBot)srv).getCommandConnector().getAllCommandBuilders()) {
    		for(Command j : i.build(this)) {
    			addCommand(j);
    		}
    	}
        for(Command i:commands.values()) {
            i.init();
        }
    }
    
//    public void onMessage(Message m) {
//    	parse(m);
//	}

	public Set<String> getAuthList(String screenName) {
        HashSet<String> h = new HashSet<String>();
        if(srv.getConfig().testAdmin(screenName)){
            h.add("admin");
        }
        return h;
	}

	public void parse(Message m) {
		if(m.getType()!=Message.TYPE_TEXT) return;
        firstMsg(m);
        Command cmd;
        if(cm.getContext(m.getSnIn()).getCounter()==0) // это новая сессия? Повторим приветствие
    		notify(new Message(m.getSnOut(), m.getSnIn(), ((TestBotConfig)srv.getConfig()).getHelloMsg()));
        cm.getContext(m.getSnIn()).update(); // Обновим сессию
        // Обработка интерактивных команд
        if("".equals(cm.getContext(m.getSnIn()).getLastCommand())) {
            cmd = this.getCommand(this.getCommand(m));
        } else {
            cmd = this.getCommand(cm.getContext(m.getSnIn()).getLastCommand());
        }
        if(cmd==null){ // Если сообщение - не команда    	
        	// Просто запишем его в лог
        	srv.log(m.getSnIn() + ">>> " + m.getMsg());
        } else {
        	// Проверим полномочия и выполним команду
            if(cmd.authorityCheck(m.getSnIn())) notify(cmd.exec(m));
        }
	}

    private void notify(Message m) {
//    	System.out.println(m);
//        for(QueueListener i:srv.getOutQueueListeners()) {
//            i.onMessage(m);
//        }
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

//	@Override
//	public void onTextMessage(Message m) {
//		parse(m);
//	}
//
//	@Override
//	public void onStatusMessage(Message m) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onOtherMessage(Message m) {
//		// TODO Auto-generated method stub
//		
//	}
}
