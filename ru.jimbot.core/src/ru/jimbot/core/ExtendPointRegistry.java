/**
 * 
 */
package ru.jimbot.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ru.jimbot.core.services.Log;

/**
 * Реестр точек расширения
 * @author spec
 *
 */
public class ExtendPointRegistry {
	private ConcurrentHashMap<String, ExtendPoint> exts;
	private LoggerProxy log;
	
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
			((Log)p).debug(null, "Activate logger");
		}
	}
	
	public void addExtend(ExtendPoint p) {
		addExtend(p.getName(), p);
	}
	
	/**
	 * Удалить из системы расширение с заданным именем
	 * @param name
	 */
	public void removeExtend(String name) {
		ExtendPoint p = exts.get(name);
		if("ru.jimbot.core.services.Log".equals(p.getType())) {
			log.removeLogger((Log)p);
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
}
