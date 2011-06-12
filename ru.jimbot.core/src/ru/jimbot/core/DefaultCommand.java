/**
 * 
 */
package ru.jimbot.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
}
