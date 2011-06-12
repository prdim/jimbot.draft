/**
 * 
 */
package ru.jimbot.core.services;

/**
 * Интерфейс для работы с протоколами в боте
 * 
 * @author Prolubnikov Dmitry
 */
public interface Protocol {

    /**
     * Внешние данные для работы протокола
     */

    /**
     * Основные данные о соединении
     * @param server
     * @param port
     * @param sn
     * @param pass
     */
    public void setConnectionData(String server, int port, String sn, String pass);

    /**
     * Куда протокол будет писать логи?
     * @param logger
     */
    public void setLogger(Log logger);

    /**
     * Обычный статус
     * @param status
     * @param text
     */
    public void setStatusData(int status, String text);

    /**
     * Расширенный статус
     * @param status
     * @param text1
     * @param text2
     */
    public void setXStatusData(int status, String text1, String text2);

    /**
     * Методы для работы с протоколом
     */

    /**
     * Установить соединение
     */
    public void connect();

    /**
     * Разорвать соединение
     */
    public void disconnect();

    /**
     * Соединение установлено?
     * @return
     */
    public boolean isOnLine();

    /**
     * Отправить сообщение
     * @param sn - кому
     * @param msg - текст
     */
    public void sendMsg(String sn, String msg);

    /**
     * Добавить в контакт-лист
     * @param sn
     */
    public void addContactList(String sn);

    /**
     * Удалить из контакт-листа
     * @param sn
     */
    public void RemoveContactList(String sn);

    /**
     * Возвращает УИН
     * @return
     */
    public String getScreenName();

    /**
     * Возвращает последнюю ошибку соединения
     * @return
     */
    public String getLastError();

//    /**
//     * Добавляем новый слушатель протокола IM
//     * @param e
//     */
//    public void addProtocolListener(ProtocolListener e);
//
//    /**
//     * Удаляем ненужный слушатель
//     * @param e
//     * @return
//     */
//    public boolean removeProtocolListener(ProtocolListener e);
//
//    /**
//     * Возвращает список слушателей событий протокола IM
//     * @return
//     */
//    public List<ProtocolListener> getProtocolListeners();
}
