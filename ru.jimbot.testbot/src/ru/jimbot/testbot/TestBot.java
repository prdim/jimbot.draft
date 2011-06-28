/**
 * 
 */
package ru.jimbot.testbot;

import ru.jimbot.core.Destroyable;
import ru.jimbot.core.ExtendPointRegistry;
import ru.jimbot.core.MsgInQueue;
import ru.jimbot.core.Parser;
import ru.jimbot.core.events.EventProxy;
import ru.jimbot.core.services.BotServiceConfig;
import ru.jimbot.core.services.DefaultBotService;
import ru.jimbot.core.services.IProtocolManager;
import ru.jimbot.core.services.Log;
import ru.jimbot.core.services.Protocol;
import ru.jimbot.testbot.internal.ActivatorTestBot;

/**
 * Пример реализации тестового бота в виде плагина
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class TestBot extends DefaultBotService {
	private String name = "";
	private TestBotConfig config;
	private boolean start = false;
	private TestBotCommandParser cmd;
	private CommandConnector con = null;
	private Log logger;
	EventProxy eva;
		
	/**
	 * @param name
	 */
	public TestBot(String name) {
		this.name = name;
		config = TestBotConfig.load(name);
		logger = ActivatorTestBot.getExtendPointRegistry().getLogger();
		eva = new EventProxy(ActivatorTestBot.getEventAdmin(), name);
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		for(Protocol p : protocols.values()) {
			((Destroyable)p).destroy();
		}
	}

	/**
	 * @param name
	 * @param con
	 */
	public TestBot(String name, CommandConnector con) {
		this.name = name;
		this.con = con;
		config = TestBotConfig.load(name);
		logger = ActivatorTestBot.getExtendPointRegistry().getLogger();
		eva = new EventProxy(ActivatorTestBot.getEventAdmin(), name);
	}

	public CommandConnector getCommandConnector() {
    	return con;
    }

	public TestBotConfig getConfig() {
		return config;
	}

	public String getServiceName() {
		return name;
	}

	public boolean isRun() {
		return start;
	}

	public void start() {
//        getCron().clear();
//        getCron().start();
        for(int i=0;i<config.getUins().size();i++) {
//            IProtocolManager pm = Manager.getInstance().getAllProtocolManagers().get(config.getUins().get(i).getProtocol());
        	IProtocolManager pm = ActivatorTestBot.getExtendPointRegistry().getProtocols().get(config.getUins().get(i).getProtocol());
            
        	Protocol p = pm.addProtocol(pm.getBuilder(config.getUins().get(i).getScreenName())
        			.pass(config.getUins().get(i).getPassword())
        			.status(config.getStatus())
        			.statustxt(config.getStatustxt())
        			.xstatus(config.getXstatus())
        			.xstatustxt1(config.getXstatustxt1())
        			.xstatustxt2(config.getXstatustxt2())
        			.build(name));
        	p.setLogger(logger);
//        	addCommandProtocolListener((CommandProtocolListener)p);
            protocols.put(config.getUins().get(i).getScreenName(), p);
            logger.debug(name, "Create protocol " + p.getScreenName());
        }        
//        qe.start();
        inq = new MsgInQueue(this);
//        outq = new MsgOutQueue(this);
        inq.start();
//        outq.start();
        cmd = new TestBotCommandParser(this);
//        getCron().addTask(new CheckSessionTask(cmd, 60000));
//        for(CommandProtocolListener i:getCommandProtocolListeners()) {
//            i.logOn();
//        }
        for(int i=0;i<config.getUins().size();i++) {
        	eva.protocolCommand(config.getUins().get(i).getScreenName(), EventProxy.STATE_LOGON);
        }
        start = true;
	}

	public void stop() {
//        getCron().stop();
//        for(CommandProtocolListener i:getCommandProtocolListeners()) {
//            try{
//                i.logOut();
//            } catch (Exception e) {}
//        }
        for(int i=0;i<config.getUins().size();i++) {
        	eva.protocolCommand(config.getUins().get(i).getScreenName(), EventProxy.STATE_LOGOFF);
        }
        cmd.destroyCommands();
//        qe.stop();
        inq.stop();
        inq = null;
//        outq.stop();
//        outq = null;
        start = false;
//        removeAllListeners();
	}

	@Override
	public void log(String s) {
		logger.print("talk", name, s);
	}

	@Override
	public void err(String s, Throwable throwable) {
		logger.error("error", s, throwable);
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.BotService#getParser()
	 */
	@Override
	public Parser getParser() {
		return cmd;
	}
}
