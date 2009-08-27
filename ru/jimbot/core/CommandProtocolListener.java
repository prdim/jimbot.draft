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
    public void setChangeStatus(int id, String text);
    public void setChangeXStatus(int id, String text);
    public void sendMessage(String in, String out, String text);
    public void logOn();
    public void logOff();
    public String getScreenName();
}
