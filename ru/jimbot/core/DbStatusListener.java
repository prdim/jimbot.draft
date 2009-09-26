package ru.jimbot.core;

import ru.jimbot.db.DBAdaptor;

/**
 * Сообщает слушателю об изменении состояния подключения к базе данных
 * @author Prolubnikov Dmitry
 */
public interface DbStatusListener {
    /**
     * Произошло соединение с БД
     * @param db
     */
    public void onConnect(DBAdaptor db);

    /**
     * Произошла ошибка БД и разрыв соединения
     * @param e
     */
    public void onError(String e);
}
