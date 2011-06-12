/**
 * 
 */
package ru.jimbot.core;

import java.util.Map;
import java.util.Set;
import java.util.Vector;

import ru.jimbot.core.services.BotService;

/**
 * Интерфейс для парсера команд
 *
 * @author Prolubnikov Dmitry
 */
public interface Parser {

    /**
     * Возвращает сервис, в котором зарегистрирован этот парсер
     * @return
     */
    public BotService getService();

    /**
     * Обработка команды
     * @param m
     */
    public void parse(Message m);

    /**
     * Название команды
     * @param m
     * @return
     */
    public String getCommand(Message m);

    /**
     * Массив аргументов по шаблону
     * @param m
     * @return
     */
    @SuppressWarnings("unchecked")
	public Vector getArgs(Message m, String pattern);

    /**
     * От кого пришла команда
     * @param m
     * @return
     */
    public String getScreenName(Message m);

    /**
     * Добавить новую команду в реестр
     * @param pattern
     * @param cmd
     */
    public void addCommand(String pattern, Command cmd);

    /**
     * Добавить новую команду в реестр с паттерном по умолчанию
     * @param cmd
     */
    public void addCommand(Command cmd);

    /**
     * Список объектов полномочий зарегистрированных команд (для работы остальных модулей и админки)
     * @return
     */
    public Map<String, String> getAuthList();

    /**
     * Возвращает список зарегистрированных команд
     * @return
     */
    public Set<String> getCommands();

    /**
     * Созвращает команду по ее названию
     * @param cmd
     * @return
     */
    public Command getCommand(String cmd);

    /**
     * Возвращает список разрешенных полномочий для пользователя с заданным УИНом
     * @param screenName
     * @return
     */
    public Set<String> getAuthList(String screenName);

    /**
     * Возвращает экземпляр менеджера сессий пользователей
     * @return
     */
    public ContextManager getContextManager();
}
