package ru.jimbot.core;

import java.util.List;
import java.util.Vector;
import java.util.HashMap;

/**
 * Определение общих для всех сервисов функций - добавление и удаление слушателей
 *
 * @author Prolubnikov Dmitry
 */
public abstract class DefaultService implements Service {
    private List<ProtocolListener> protList = new Vector<ProtocolListener>();
    private List<CommandProtocolListener> comProtList = new Vector<CommandProtocolListener>();
    private List<QueueListener> inQueueList = new Vector<QueueListener>();
    private List<QueueListener> outQueueList = new Vector<QueueListener>();
    private List<QueueListener> parserList = new Vector<QueueListener>();
    private List<DbStatusListener> dbList = new Vector<DbStatusListener>();
    private HashMap<String, Protocol> protocols = new HashMap<String, Protocol>();
    private HashMap<String, Object> storage = new HashMap<String, Object>();

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
        comProtList.add(e);
    }

    /**
     * Удаляет слушатель команд для управления протоколом IM
     * @param e
     * @return
     */
    public boolean removeCommandProtocolListener(CommandProtocolListener e) {
        return comProtList.remove(e);
    }

    /**
     * Возвращает список слушателей
     * @return
     */
    public List<CommandProtocolListener> getCommandProtocolListeners() {
        return comProtList;
    }

    /**
     * 
     * @param e
     */
    public void addInQueueListener(QueueListener e) {
        inQueueList.add(e);
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
    public boolean removeInQueueListener(QueueListener e) {
        return inQueueList.remove(e);
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
    public List<QueueListener> getInQueueListeners() {
        return inQueueList;
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