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
 * Интерфейс слушателя команд для управления IM протоколом
 * @author Prolubnikov Dmitry
 */
public interface CommandProtocolListener {

    /**
     * Установить статус
     * @param id
     * @param text
     */
    public void setChangeStatus(int id, String text);

    /**
     * Установить Х-статус
     * @param id
     * @param text
     */
    public void setChangeXStatus(int id, String text);

    /**
     * Отправить текстовое сообщение
     * @param in - от кого
     * @param out - кому
     * @param text - сообщение
     */
    public void sendMessage(String in, String out, String text);

    /**
     * Подключиться к серверу
     */
    public void logOn();

    /**
     * Отключиться от сервера
     */
    public void logOff();

    /**
     * Возвращает УИН слушателя
     * @return
     */
    public String getScreenName();
}
