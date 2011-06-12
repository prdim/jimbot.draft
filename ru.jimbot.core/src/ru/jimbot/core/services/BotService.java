/**
 * 
 */
package ru.jimbot.core.services;

import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import ru.jimbot.core.Message;
import ru.jimbot.core.MsgInQueue;
import ru.jimbot.core.Parser;

/**
* Интерфейс для всех сервисов бота
* 
* @author Prolubnikov Dmitry
*/
public interface BotService {
	/**
     * Добавлене в очередь нового события
     * @param e
     */
//    public void createEvent(Event e);
    
    /**
     * Запуск сервиса
     */
    public void start();

    /**
     * Остановка сервиса
     */
    public void stop();

    /**
     * Сервис запущен?
     * @return
     */
    public boolean isRun();

    /**
     * Возвращает екземпляр протокола по УИНу
     * @param screenName
     * @return
     */
    public Protocol getProtocol(String screenName);

    /**
     * Список УИНов ко всем установленным протоколам
     * @return
     */
    public Set<String> getAllProtocols();

    /**
     * Возвращает имя данного сервиса
     * @return
     */
    public String getServiceName();

    /**
     * Набор настроек сервиса
     * @return
     */
    public BotServiceConfig getConfig();

    /**
     * Засунуть объект в хранилище данных
     * @param key
     * @param o
     */
    public void addDataStorage(String key, Object o);

    /**
     * Получить объект из хранилища
     * @param key
     * @return
     */
    public Object getDataStorage(String key);

    /**
     * Возвращает очередь входящих
     * @return
     */
    public MsgInQueue getInQueue();

    /**
     * Возвращает очередь исходящих для заданного уина
     * @return
     */
//    public ConcurrentLinkedQueue<Message> getOutQueue(String sn);

    /**
     * возвращает очередь исходящих
     * @return
     */
//    public MsgOutQueue getOutQueue();

//    public List<DbStatusListener> getDbStatusListeners();
//    void addDbStatusListener(DbStatusListener e);
//    boolean removeDbStatusListener(DbStatusListener e);
//    public void addParserListener(QueueListener e);
//    public boolean removeParserListener(QueueListener e);
//    public List<QueueListener> getParserListeners();
//    public void addCommandProtocolListener(CommandProtocolListener e);
//    public void removeCommandProtocolListener(CommandProtocolListener e);
//    public Collection<CommandProtocolListener> getCommandProtocolListeners();
//    public CommandProtocolListener getCommandProtocolListener(String screenName);
//    public void addOutQueueListener(QueueListener e);
//    public boolean removeOutQueueListener(QueueListener e);
//    public List<QueueListener> getOutQueueListeners();
    
    /**
     * Вывод сообщения в лог, связанный с данным сервисом
     * @param s
     */
    public void log(String s);
    
    /**
     * Вывод сообщения об ошибке в лог, связанный с данным сервисом
     * @param s
     * @param throwable
     */
    public void err(String s, Throwable throwable);
    
    /**
     * Возвращает парсер команд для данного бота
     * @return
     */
    public Parser getParser();
}
