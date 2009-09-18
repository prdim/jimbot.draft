package ru.jimbot.core;

import ru.jimbot.core.events.QueueEvents;
import ru.jimbot.core.events.Event;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Определение общих для всех сервисов функций - добавление и удаление слушателей
 *
 * @author Prolubnikov Dmitry
 */
public abstract class DefaultService implements Service {
    private HashMap<String, CommandProtocolListener> comProtList = new HashMap<String, CommandProtocolListener>();
    private List<ProtocolListener> protList = new Vector<ProtocolListener>();
//    private List<QueueListener> inQueueList = new Vector<QueueListener>();
    private List<QueueListener> outQueueList = new Vector<QueueListener>();
    private List<QueueListener> parserList = new Vector<QueueListener>();
    private List<DbStatusListener> dbList = new Vector<DbStatusListener>();
    protected HashMap<String, Protocol> protocols = new HashMap<String, Protocol>();
    private HashMap<String, Object> storage = new HashMap<String, Object>();
    protected MsgInQueue inq;
    protected MsgOutQueue outq;
    protected QueueEvents qe = new QueueEvents();

    /**
     * Добавлене в очередь нового события
     *
     * @param e
     */
    public void createEvent(Event e) {
        qe.addEvent(e);
    }

    /**
     * Удалить ссылки на все слушатели (при завершении работы сервиса)
     */
    public void removeAllListeners() {
        comProtList.clear();
        protList.clear();
        outQueueList.clear();
        parserList.clear();
        dbList.clear();
    }

    /**
     * Засунуть объект в хранилище данных
     *
     * @param key
     * @param o
     */
    public void addDataStorage(String key, Object o) {
        storage.put(key, o);
    }

    /**
     * Получить объект из хранилища
     *
     * @param key
     * @return
     */
    public Object getDataStorage(String key) {
        return storage.get(key);
    }

    /**
     * Возвращает очередб входящих
     *
     * @return
     */
    public MsgInQueue getInQueue() {
        return inq;
    }

    /**
     * Возвращает очередь исходящих для заданного уина
     *
     * @return
     */
    public ConcurrentLinkedQueue<Message> getOutQueue(String sn) {
        return outq.getUinQueue(sn);
    }

    /**
     * возвращает очередь исходящих
     *
     * @return
     */
    public MsgOutQueue getOutQueue() {
        return outq;
    }

    /**
     * Добавляет новый уин и объект протокола
     * @param screenName
     * @param p
     */
    public void addProtocol(String screenName, Protocol p) {
        protocols.put(screenName, p);
    }

    /**
     * Возвращает по уину объект протокола
     * @param screenName
     * @return
     */
    public Protocol getProtocol(String screenName) {
        return protocols.get(screenName);
    }

    /**
     * Список УИНов ко всем установленным протоколам
     *
     * @return
     */
    public Set<String> getAllProtocols() {
        return protocols.keySet();
    }

    /**
     * Добавляем новый слушатель протокола IM
     * @param e
     */
    public void addProtocolListener(ProtocolListener e){
        protList.add(e);
    }

    /**
     * Удаляем ненужный слушатель
     * @param e
     * @return
     */
    public boolean removeProtocolListener(ProtocolListener e){
        return protList.remove(e);
    }

    /**
     * Возвращает список слушателей событий протокола IM
     * @return
     */
    public List<ProtocolListener> getProtocolListeners() {
        return protList;
    }

    /**
     * Добавить слушатель команд для управления протоколом IM
     * @param e
     */
    public void addCommandProtocolListener(CommandProtocolListener e) {
        comProtList.put(e.getScreenName(), e);
    }

    /**
     * Удаляет слушатель команд для управления протоколом IM
     * @param e
     * @return
     */
    public void removeCommandProtocolListener(CommandProtocolListener e) {
        comProtList.remove(e.getScreenName());
    }

    /**
     * Возвращает список слушателей
     * @return
     */
    public Collection<CommandProtocolListener> getCommandProtocolListeners() {
        return comProtList.values();
    }

    /**
     * Возвращает слушателя с нужным УИНом
     * @param screenName
     * @return
     */
    public CommandProtocolListener getCommandProtocolListener(String screenName) {
        return comProtList.get(screenName);
    }

    /**
     *
     * @param e
     */
    public void addOutQueueListener(QueueListener e) {
        outQueueList.add(e);
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean removeOutQueueListener(QueueListener e) {
        return outQueueList.remove(e);
    }

    /**
     *
     * @return
     */
    public List<QueueListener> getOutQueueListeners() {
        return outQueueList;
    }

    /**
     *
     * @param e
     */
    public void addParserListener(QueueListener e) {
        parserList.add(e);
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean removeParserListener(QueueListener e) {
        return parserList.remove(e);
    }

    /**
     *
     * @return
     */
    public List<QueueListener> getParserListeners() {
        return parserList;
    }

    public void addDbStatusListener(DbStatusListener e) {
        dbList.add(e);
    }

    public boolean removeDbStatusListener(DbStatusListener e) {
        return dbList.remove(e);
    }

    public List<DbStatusListener> getDbStatusListeners() {
        return dbList;
    }
}
