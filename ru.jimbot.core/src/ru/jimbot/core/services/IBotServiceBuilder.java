/**
 * 
 */
package ru.jimbot.core.services;


/**
 * Создает новый сервис бота с заданным именем
 * @author spec
 *
 */
public interface IBotServiceBuilder {

	/**
	 * Возвращает созданный сервис бота
	 * @param name
	 * @return
	 */
	public BotService build(String name);
	
	/**
	 * Создает область данных для бота (файлы, папки, БД...)
	 * @param name
	 * @return
	 */
	public boolean createServiceData(String name);
	
	/**
	 * Удаляет область данных бота
	 * @param name
	 * @return
	 */
	public boolean deleteServiceData(String name);
}
