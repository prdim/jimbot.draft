/**
 * 
 */
package ru.jimbot.core.services;

/**
 * Общий интерфейс для настроек
 * @author spec
 *
 */
public interface AbstractProperties {

	/**
	 * Название настроек
	 * @return
	 */
	public abstract String getTitle();
	
	/**
	 * Расширеное описание настроек
	 * @return
	 */
	public abstract String getExtendInfo();
	
	/**
	 * Сохранить настройки
	 */
	public abstract void save();	
}
