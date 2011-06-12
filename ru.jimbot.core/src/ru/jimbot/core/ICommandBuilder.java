/**
 * 
 */
package ru.jimbot.core;

import java.util.List;

/**
 * @author Prolubnikov Dmitry
 *
 */
public interface ICommandBuilder {
	public List<Command> build(Parser p);
}
