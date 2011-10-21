/**
 * 
 */
package ru.jimbot.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Общие функции для команд. Чтобы не реализовывать их в простых командах.
 *
 * @author Prolubnikov Dmitry
 */
public abstract class DefaultCommand implements Command {
    protected Parser p;

    protected DefaultCommand(Parser p) {
        this.p = p;
    }
    
//    /**
//     * Шаблон для анализа аргументов команды
//     * @return
//     */
//    public String getArgsPattern() {
//    	return "";
//    }

    /**
     * Инициализация. Вызывается после создания экземпляра класса.
     */
    public void init() {
        // ничего не делаем
    }

    public void destroy() {
        // ничего не делаем
    }

    /**
     * Список проверяемых командой объектов полномочий с их описанием
     *
     * @return
     */
    public Map<String, String> getAutorityList() {
        // Пустой список
        return new HashMap<String, String>();
    }

    /**
     * Проверка полномочий
     *
     * @param s - список полномочий юзера
     * @return - истина, если команда доступна
     */
    public boolean authorityCheck(Set<String> s) {
        return true;
    }

    /**
     * Проверка полномочий по уину
     *
     * @param screenName
     * @return
     */
    public boolean authorityCheck(String screenName) {
        return authorityCheck(p.getAuthList(screenName));
    }
    
    /**
     * По умолчанию, ничего особого с отправляемым сообщением делать не нужно - просто отсылаем текст, 
     * создание которого нужно реализовать в нужной команде
     */
    public Message exec(Message m) {
		return new Message(m.getSnOut(), m.getSnIn(), exec(m.getSnIn()));
	}
    
    /**
     * В простейших командах нужно реализовать только возврат текста в ответ
     * @param sn
     * @return
     */
    public abstract String exec(String sn);
}
