package ru.jimbot.core;

import ru.jimbot.db.DBAdaptor;

/**
 * Сообщает слушателю об изменении состояния подключения к базе данных
 * @author Prolubnikov Dmitry
 */
public interface DbStatusListener {
    public void onConnect(DBAdaptor db);
    public void onError(String e);
}
