/**
 * 
 */
package ru.jimbot.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ru.jimbot.core.services.BotService;
import ru.jimbot.core.services.IBotServiceBuilder;
import ru.jimbot.core.services.IProtocolManager;
import ru.jimbot.core.services.Log;

/**
 * Реестр точек расширения
 * @author spec
 *
 */
public class ExtendPointRegistry {
	private ConcurrentHashMap<String, ExtendPoint> exts;
	private LoggerProxy log;
	// Управление сервисами ботов
	private HashMap<String, BotService> services = new HashMap<String, BotService>();
	private Map<String, IBotServiceBuilder> sbs = new HashMap<String, IBotServiceBuilder>();
	private Map<String, IProtocolManager> ipm = new HashMap<String, IProtocolManager>();
	
	public ExtendPointRegistry() {
		exts = new ConcurrentHashMap<String, ExtendPoint>();
		log = new LoggerProxy();
	}
	
	/**
	 * Возвращает прокси для зарегистрированных в системе логгеров
	 * @return
	 */
	public Log getLogger() {
		return log;
	}
	
	/**
	 * Добавить новое или заменить существующее расширение
	 * @param name
	 * @param p
	 */
	public void addExtend(String name, ExtendPoint p) {
		exts.put(name, p);
		if("ru.jimbot.core.services.Log".equals(p.getType())) {
			log.addLogger((Log)p);
//			((Log)p).debug("core", "Activate logger");
		} else if("ru.jimbot.core.services.BotServiceBuilder".equals(p.getType())) {
			sbs.put(p.getPointName(), (IBotServiceBuilder)p);
			createAllBotServicesForBuilder(p.getPointName());
		} else if("ru.jimbot.core.services.IProtocolManager".equals(p.getType())) {
			ipm.put(p.getPointName(), (IProtocolManager)p);
		}
	}
	
	public void addExtend(ExtendPoint p) {
		addExtend(p.getPointName(), p);
	}
	
	/**
	 * Удалить из системы расширение с заданным именем
	 * @param name
	 */
	public void removeExtend(String name) {
		ExtendPoint p = exts.get(name);
		if("ru.jimbot.core.services.Log".equals(p.getType())) {
			log.removeLogger((Log)p);
		} else if("ru.jimbot.core.services.BotServiceBuilder".equals(p.getType())) {
			removeAllBotServicesForBuilder(((ExtendPoint)sbs.get(name)).getPointName());
			sbs.remove(name);
		} else if("ru.jimbot.core.services.IProtocolManager".equals(p.getType())) {
			ipm.remove(name);
		}
		p.unreg();
		exts.remove(name);
	}
	
	/**
	 * Возвращает все зарегистрированные расширения
	 * @return
	 */
	public Map<String, ExtendPoint> getAllExtends() {
		return exts;
	}
	
	/**
	 * Возвращает список расширений заданного типа
	 * @param type
	 * @return
	 */
	public List<ExtendPoint> getExtendsType(String type) {
		List<ExtendPoint> ls = new ArrayList<ExtendPoint>();
		for(ExtendPoint i : exts.values()) {
			if(type.equals(i.getType())) {
				ls.add(i);
			}
		}
		return ls;
	}
	
	/**
	 * Удалиить все зарегистрированные расширения
	 */
	public void unregAll() {
		for(ExtendPoint i : exts.values()) {
			i.unreg();
		}
		exts = new ConcurrentHashMap<String, ExtendPoint>();
	}
	
	/**
	 * Возвращает список зарегистрированных в системе протоколов
	 * @return
	 */
	public Map<String, IProtocolManager> getProtocols() {
		Map<String, IProtocolManager> lp = new HashMap<String, IProtocolManager>();
		for(ExtendPoint i : exts.values()) {
			if("ru.jimbot.core.services.IProtocolManager".equals(i.getType())) {
				lp.put(((IProtocolManager)i).getProptocolName(), (IProtocolManager)i);
			}
		}
		return lp;
	}
	
	/**
	 * Возвращает список зарегистрированных сервисов бота
	 */
	public List<IBotServiceBuilder> getBotServiceBuilders() {
		List<IBotServiceBuilder> lp = new ArrayList<IBotServiceBuilder>();
		for(ExtendPoint i : exts.values()) {
			if("ru.jimbot.core.services.BotServiceBuilder".equals(i.getType())) {
				lp.add((IBotServiceBuilder)i);
			}
		}
		return lp;
	}
	
	/**
	 * Возвращает билдер сервиса по его имени
	 * @param name
	 * @return
	 */
	public IBotServiceBuilder getBuilderForService(String name) {
		return sbs.get(name);
	}
	
	public BotService getBotService(String serviceName) {
		return services.get(serviceName);
	}
	
	public void createBotService(String serviceName) {
		if(services.containsKey(serviceName)) return;
		if(!MainProps.getInstance().getServiceNames().contains(serviceName)) return;
		IBotServiceBuilder b = sbs.get(MainProps.getInstance().getTypeForService(serviceName));
		BotService s = b.build(serviceName);
		services.put(serviceName, s);
	}
	
	public void removeBotService(String serviceName) {
		if(!services.containsKey(serviceName)) return;
		BotService s = services.get(serviceName);
		((Destroyable)s).destroy();
		services.remove(s);
	}
	
	private void createAllBotServicesForBuilder(String typeName) {
		for(ServiceConfig i : MainProps.getInstance().getServices()) {
			if(typeName.equals(i.getServiceType()))
				createBotService(i.getServiceName());
		}
	}
	
	private void removeAllBotServicesForBuilder(String typeName) {
		for(ServiceConfig i : MainProps.getInstance().getServices()) {
			if(typeName.equals(i.getServiceType()))
				removeBotService(i.getServiceName());
		}
	}
}
