/**
 * 
 */
package ru.jimbot.core;

/**
 * Интерфейс точка расширения
 * @author spec
 *
 */
public interface ExtendPoint {
	
	/**
	 * Тип точки расширения (имя интерфейса, который реализует данное расширение)
	 * @return
	 */
	public String getType();
	
	/**
	 * Уникальное символьное имя расширения
	 * @return
	 */
	public String getName();
	
	/**
	 * Вызывается при удалении расширения из системы
	 */
	public void unreg();
}
