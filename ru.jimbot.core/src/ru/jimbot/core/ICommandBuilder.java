/**
 * 
 */
package ru.jimbot.core;

import java.util.List;

/**
 * Интерфейс для билдера команд
 * @author Prolubnikov Dmitry
 *
 */
public interface ICommandBuilder {
	
	/**
	 * Возвращает список созданных команд
	 * @param p - парсер команд, в котором потом будут зарегистрированы созданные команды, 
	 * может использоваться для динамического изменения команд после их создания
	 * @return
	 */
	public List<Command> build(Parser p);
	
	/**
	 * Если билдер занимается динамическим изменением команд на лету, то тут он должен прекратить этим заниматься
	 */
	public void destroy();
}
