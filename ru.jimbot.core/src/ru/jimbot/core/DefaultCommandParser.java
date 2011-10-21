/**
 * 
 */
package ru.jimbot.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import ru.jimbot.core.services.BotService;

/**
 * Стандартные процедуры для любого парсера команд
 * - Собирает список доступных команд
 * - Список доступных объектов полномочий
 *
 * @author Prolubnikov Dmitry
 */
public abstract class DefaultCommandParser implements Parser {
    protected HashMap<String, Command> commands;
    protected HashMap<String, String> autority;
    protected BotService srv;
    protected ContextManager cm;
    
    public DefaultCommandParser(BotService srv) {
        this.srv = srv;
        commands = new HashMap<String, Command>();
        autority = new HashMap<String, String>();
        cm = new ContextManager();
    }

    /**
     * Освобождение всех ресурсов, связанных с командами
     */
    public void destroyCommands() {
        for(Command c : commands.values()) {
            c.destroy();
        }
    }

    public ContextManager getContextManager() {
        return cm;
    }

    /**
     * Возвращает сервис, в котором зарегистрирован этот парсер
     *
     * @return
     */
    public BotService getService() {
        return srv;
    }

    /**
     * Добавить новую команду в реестр
     *
     * @param pattern
     * @param cmd
     */
    public void addCommand(String pattern, Command cmd) {
        if(commands.containsKey(pattern)) try {commands.get(pattern).destroy(); } catch (Exception e) {}
        commands.put(pattern, cmd);
        autority.putAll(cmd.getAutorityList());
    }

    /**
     * Добавить новую команду в реестр с паттерном по умолчанию
     *
     * @param cmd
     */
    public void addCommand(Command cmd) {
//        for(String s:cmd.getCommandPatterns()) {
//            addCommand(s, cmd);
//        }
    	addCommand(cmd.getName(), cmd);
    }

    /**
     * Список объектов полномочий зарегистрированных команд (для работы остальных модулей и админки)
     *
     * @return
     */
    public Map<String, String> getAuthList() {
        return autority;
    }

    /**
     * Возвращает список зарегистрированных команд
     *
     * @return
     */
    public Set<String> getCommands() {
        return commands.keySet();
    }

    /**
     * Созвращает команду по ее названию
     *
     * @param cmd
     * @return
     */
    public Command getCommand(String cmd) {
        return commands.get(cmd);
    }

    /**
     * От кого пришла команда
     *
     * @param m
     * @return
     */
    public String getScreenName(Message m) {
        return m.getSnIn();
    }

    /**
     * Название команды
     *
     * @param m
     * @return
     */
    public String getCommand(Message m) {
        String s = "";
        try {
            s = m.getMsg().trim().split(" ")[0];
            s = s.toLowerCase();
        } catch (Exception e) {}
        return s;
    }
    
    /**
     * Разбирает аргументы команды и присваивает их внутренним переменным
     * @param cmd
     * @param m
     */
    public void parseArgs(Command cmd, Message m) {
    	if(cmd==null) return;
    	Collection<Variable> args = new ArrayList<Variable>();
    	cmd.publishParameters(args);
    	if(args.size()==0) return; // команда не имеет аргументов
    	String tokens[] = m.getMsg().split("\\s+", args.size()+1);
    	int i=1;
    	for(Variable v : args) {
    		if(i>=tokens.length) break;
    		v.parse(tokens[i]);
    	}
    }

//    /**
//     * Массив аргументов по шаблону
//     * $n - число
//     * $c - слово без пробелов
//     * $s - произвольная строка до конца сообщения
//     *
//     * @param m
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//	public Vector getArgs(Message m, String arg) {
//        Vector v = new Vector();
//        if(arg.equals("")) return v;
//        for(int i=0;i<arg.split(" ").length;i++){
//            if(i>=(m.getMsg().split(" ").length-1)){
//                if(arg.split(" ")[i].equals("$s") ||
//                        arg.split(" ")[i].equals("$c"))
//                    v.add("");
//                else
//                    v.add(0);
//
//            } else {
//                if(arg.split(" ")[i].equals("$c"))
//                    v.add(m.getMsg().split(" ")[i+1]);
//                else if(arg.split(" ")[i].equals("$n"))
//                    v.add(parseN(m.getMsg().split(" ")[i+1]));
//                else
//                    v.add(parseS(i+1,m.getMsg()));
//            }
//        }
//        return v;
//
//    }
//
//    /**
//     * Обработка строкового параметра команды
//     * @param c
//     * @param s
//     * @return
//     */
//    private String parseS(int c, String s){
//        int k=0,i=0;
//        for(i=0;i<s.length();i++){
//            if(s.charAt(i)==' ') k++;
//            if(k>=c) break;
//        }
//        return s.substring(i+1);
//    }
//
//    /**
//     * Обработка числового параметра команды
//     * @param s
//     * @return
//     */
//    private int parseN(String s){
//        try{
//            return Integer.parseInt(s);
//        } catch (Exception ex) {}
//        return 0;
//    }
}
