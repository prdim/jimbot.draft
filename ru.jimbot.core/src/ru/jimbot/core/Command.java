/**
 * 
 */
package ru.jimbot.core;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

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
     * Выставляет переменные для парсера. Парсер интерпретирует значения и записывает их в эти переменные.
     * @param params
     */
    public void publishParameters(Collection<Variable> params);

//    /**
//     * Выполнение команды
//     * @param sn - от кого?
//     * @param param - вектор параметров (могут быть как строки, так и числа)
//     * @return - результат (если нужен)
//     */
//    @SuppressWarnings("unchecked")
//	public String exec(String sn, Vector param);

    /**
     * Имя, по которому можно вызвать эту команду
     * @return
     */
    public String getName();

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
