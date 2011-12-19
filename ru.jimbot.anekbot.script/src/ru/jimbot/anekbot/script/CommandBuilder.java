/**
 * 
 */
package ru.jimbot.anekbot.script;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bsh.Interpreter;

import ru.jimbot.anekbot.AnekCommandBuilder;
import ru.jimbot.core.Command;
import ru.jimbot.core.Parser;
import ru.jimbot.script.ScriptServer;
import ru.jimbot.anekbot.*;

/**
 * @author spec
 *
 */
public class CommandBuilder extends AnekCommandBuilder {
	private Parser p;
	private ScriptServer ss;
	// TODO создать поток в котором будут проверяться обновления скриптов и обновляться команды
	TimerTask task;
	Timer timer;
	final int TIMER_PAUSE = 5000;

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.AnekCommandBuilder#build(ru.jimbot.core.Parser)
	 */
	@Override
	public List<Command> build(Parser p) {
		this.p = p;
		Interpreter b = new Interpreter();
		// Установим наш загрузчик классов, чтобы скрипт имел доступ к классам в этом бандле
		b.setClassLoader(this.getClass().getClassLoader());
		ss = new ScriptServer(p, b);
		task = new TimerTask() {
			
			@Override
			public void run() {
				ss.refreshAllCommandScripts();
			}
		};
		timer = new Timer("Refresh Script Timer");
		timer.schedule(task, TIMER_PAUSE, TIMER_PAUSE);
		return ss.readAllCommandScripts();
	}

	@Override
	public void destroy() {
		timer.cancel();
		timer.purge();
		timer = null;
		task = null;
	}

}
