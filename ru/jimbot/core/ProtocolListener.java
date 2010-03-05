/**
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2009 JimBot project
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package ru.jimbot.core;

/**
 * Интерфейс для слушателя событий протокола IM
 *
 * @author Prolubnikov Dmitry
 */
public interface ProtocolListener {

    /**
     * Входящее текстовое сообщение
     * @param m
     */
    public void onTextMessage(Message m);

    /**
     * Изменение статуса одного из номеров в КЛ
     * @param m
     */
    public void onStatusMessage(Message m);

    /**
     * Произошла ошибка
     * @param m
     */
    public void onError(Message m);

    /**
     * Соединение с сервером
     * @param sn
     */
    public void logOn(String sn);

    /**
     * Разрыв соединения с сервером
     * @param sn
     */
    public void logOut(String sn);

    /**
     * Другой тип входящего сообщения
     * @param m
     */
    public void onOtherMessage(Message m);
}
