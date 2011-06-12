/**
 * 
 */
package ru.jimbot.core;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Интерфейс команды бота.
 *
 * @author Prolubnikov Dmitry
 */
public interface Command {

    /**
     * Инициализация. Вызывается после создания экземпляра класса.
     */
    public void init();

    /**
     * Уничтожение. Вызывается перед удалением команды. Необходимо освободить все ресурсы.
     */
    public void destroy();

    /**
     * Выполнение команды
     * @param m - обрабатываемое сообщение с командой
     * @return - результат (если нужен)
     */
    public Message exec(Message m);

    /**
     * Выполнение команды
     * @param sn - от кого?
     * @param param - вектор параметров (могут быть как строки, так и числа)
     * @return - результат (если нужен)
     */
    @SuppressWarnings("unchecked")
	public String exec(String sn, Vector param);

    /**
     * Список ключевых слов, по которым можно вызвать эту команду
     * @return
     */
    public List<String> getCommandPatterns();

    /**
     * Выводит короткую помощь по команде (1 строка)
     * @return
     */
    public String getHelp();

    /**
     * Выводит подробную помощь по команде
     * @return
     */
    public String getXHelp();

    /**
     * Список проверяемых командой объектов полномочий с их описанием
     * @return
     */
    public Map<String, String> getAutorityList();

    /**
     * Проверка полномочий
     * @param s - список полномочий юзера
     * @return  - истина, если команда доступна
     */
    public boolean authorityCheck(Set<String> s);

    /**
     * Проверка полномочий по уину
     * @param screenName
     * @return
     */
    public boolean authorityCheck(String screenName);
}
