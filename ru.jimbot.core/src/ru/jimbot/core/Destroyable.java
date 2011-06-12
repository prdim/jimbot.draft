/**
 * 
 */
package ru.jimbot.core;

/**
 * Объект должен перед уничтожением освободить ресурсы
 * @author spec
 *
 */
public abstract class Destroyable {

	/**
	 * Выполняется перед уничтожением объекта, здесь нужно освободить ресурсы
	 */
	public abstract void destroy();
}
