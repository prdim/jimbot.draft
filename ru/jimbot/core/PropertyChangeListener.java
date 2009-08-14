package ru.jimbot.core;

/**
 * Интерфейс. Заинтересованная сторона получает сообщение об изменении свойства объекта (например - статуса)
 *
 * @author Prolubnikov Dmitry
 */
public interface PropertyChangeListener {
    public void changeEvent(String id, String value);
}
