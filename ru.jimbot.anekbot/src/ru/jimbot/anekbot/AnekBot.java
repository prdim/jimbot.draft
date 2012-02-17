/**
 * 
 */
package ru.jimbot.anekbot;

import java.util.Date;

import ru.jimbot.anekbot.internal.ActivatorAnekBot;
import ru.jimbot.core.Destroyable;
import ru.jimbot.core.MsgInQueue;
import ru.jimbot.core.Parser;
import ru.jimbot.core.events.EventProxy;
import ru.jimbot.core.exceptions.DbException;
import ru.jimbot.core.services.BotServiceConfig;
import ru.jimbot.core.services.DefaultBotService;
import ru.jimbot.core.services.IProtocolManager;
import ru.jimbot.core.services.Log;
import ru.jimbot.core.services.Protocol;

/**
 * @author spec
 *
 */
public class AnekBot extends DefaultBotService {
	private String name = "";
	private AnekConfig config;
	private boolean start = false;
	private AnekBotCommandParser cmd;
	private CommandConnector con = null;
	private Log logger;
	private EventProxy eva;
	private IAnekBotDB db;
	private Thread t;
	private AnekBot me;
	
	/**
	 * @param name
	 * @param con
	 */
	public AnekBot(String name, CommandConnector con) {
		super();
		this.name = name;
		this.con = con;
		config = AnekConfig.load(name);
		logger = ActivatorAnekBot.getExtendPointRegistry().getLogger();
		eva = new EventProxy(ActivatorAnekBot.getEventAdmin(), name);
		me = this;
		if(config.isAutoStart()) {
			Thread t = new Thread() {

				/* (non-Javadoc)
				 * @see java.lang.Thread#run()
				 */
				@Override
				public void run() {
					try {
						Thread.sleep(config.getPauseStart());
						me.start();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			};
			t.start();
		}
	}
	
	public IAnekBotDB getAnekDB() {
		return db;
	}
	
	public CommandConnector getCommandConnector() {
		return con;
	}

	@Override
	public void start() {
		db = ActivatorAnekBot.getAnekDB();
		if(db == null) {
			logger.error(name, "Отсутствует база данных!");
			return;
		}
		try {
			db = db.initDB(name);
		} catch (DbException e) {
			logger.error(name, e.getMessage(), e);
			return;
		}
		for(int i=0;i<config.getUins().size();i++) {
			IProtocolManager pm = ActivatorAnekBot.getExtendPointRegistry().getProtocols().get(config.getUins().get(i).getProtocol());
			if(!protocols.containsKey(config.getUins().get(i).getScreenName())) {
				Protocol p = pm.addProtocol(pm.getBuilder(config.getUins().get(i).getScreenName())
						.pass(config.getUins().get(i).getPassword())
						.build(name));
				p.setLogger(logger);
				protocols.put(config.getUins().get(i).getScreenName(), p);
				logger.debug(name, "Create protocol " + p.getScreenName());
			}
		}
		inq = new MsgInQueue(this);
		inq.start();
		cmd = new AnekBotCommandParser(this);
		t = new Thread() {

			/* (non-Javadoc)
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				for(int i=0;i<config.getUins().size();i++) {
					eva.protocolCommand(config.getUins().get(i).getScreenName(), EventProxy.STATE_LOGON);
					try {sleep(config.getPauseConnect());} catch (InterruptedException e) {}
//        	System.out.println(">>>" + new Date(System.currentTimeMillis()));
				}
			}
			
		};
		t.start();
        start = true;
	}

	@Override
	public void stop() {
//		if(db != null) {
//			db.closeDB();
//		}
		if (t != null) t.stop();
		for(int i=0;i<config.getUins().size();i++) {
        	eva.protocolCommand(config.getUins().get(i).getScreenName(), EventProxy.STATE_LOGOFF);
        }
        cmd.destroyCommands();
        inq.stop();
        inq = null;
        start = false;
	}

	@Override
	public boolean isRun() {
		return start;
	}

	@Override
	public String getServiceName() {
		return name;
	}

	@Override
	public AnekConfig getConfig() {
		return config;
	}

	@Override
	public void log(String s) {
		logger.print("talk", name, s);
	}

	@Override
	public void err(String s, Throwable throwable) {
		logger.error(name, s, throwable);
	}

	@Override
	public Parser getParser() {
		return cmd;
	}

	@Override
	public void destroy() {
		for(Protocol p : protocols.values()) {
			((Destroyable)p).destroy();
		}
	}

}
