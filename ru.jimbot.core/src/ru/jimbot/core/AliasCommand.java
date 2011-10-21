/**
 * 
 */
package ru.jimbot.core;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Создание псевдонима для команды
 * @author spec
 *
 */
public class AliasCommand implements Command {
	private Parser p;
	private Command cmd;
	private String alias;
	
	/**
	 * @param p
	 * @param cmd
	 * @param alias
	 */
	public AliasCommand(Parser p, Command cmd, String alias) {
		super();
		this.p = p;
		this.cmd = cmd;
		this.alias = alias;
	}
	
	public AliasCommand(Parser p, String cmd, String alias) {
		super();
		this.p = p;
		this.cmd = p.getCommand(cmd);
		this.alias = alias;
	}
	
	@Override
	public void init() {
//		cmd.init();
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Message exec(Message m) {
		return cmd.exec(m);
	}

	@Override
	public String getName() {
		return alias;
	}

	@Override
	public String getHelp() {
		return cmd.getHelp();
	}
	
	@Override
	public String getXHelp() {
		return cmd.getXHelp();
	}
	
	@Override
	public Map<String, String> getAutorityList() {
		return cmd.getAutorityList();
	}
	
	@Override
	public boolean authorityCheck(Set<String> s) {
		return cmd.authorityCheck(s);
	}
	
	@Override
	public boolean authorityCheck(String screenName) {
		return cmd.authorityCheck(screenName);
	}

	@Override
	public void publishParameters(Collection<Variable> params) {
		cmd.publishParameters(params);
	}
}
