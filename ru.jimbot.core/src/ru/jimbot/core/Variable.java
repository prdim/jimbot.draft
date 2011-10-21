/**
 * 
 */
package ru.jimbot.core;

/**
 * Интерфейс для переменной - параметра команды.
 * Должны быть реализованы необходимые типы.
 * 
 * @author spec
 *
 */
public interface Variable {
	String getTypeName();
	boolean parse(String s);
	String format();
}
