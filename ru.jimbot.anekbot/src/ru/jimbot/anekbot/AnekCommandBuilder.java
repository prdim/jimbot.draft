/**
 * 
 */
package ru.jimbot.anekbot;

import java.util.List;

import ru.jimbot.core.Command;
import ru.jimbot.core.ICommandBuilder;
import ru.jimbot.core.Parser;

/**
 * Чтобы не путать все плагины с командами между разными ботами - создадим свое расширение интерфейса
 * Именно его и будем ловить при поиске сервисов.
 * 
 * @author Prolubnikov Dmitry
 *
 */
public abstract class AnekCommandBuilder implements ICommandBuilder {

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.ICommandBuilder#build(ru.jimbot.core.api.Parser)
	 */
	public abstract List<Command> build(Parser p);

}
