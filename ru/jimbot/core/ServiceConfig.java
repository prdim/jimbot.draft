/*
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2010 JimBot project
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
 * Интерфейс для конфига сервисов
 * @author Prolubnikov Dmitry
 */
public interface ServiceConfig {

    /**
     * Сохранить настройки в файл
     */
	public void save();

    /**
     * Автоматически запускать сервис при старте приложения?
     * @return
     */
	public boolean isAutoStart();

    /**
     * Проверка на админа
     * @param screenName
     * @return
     */
    public boolean testAdmin(String screenName);

    public String[] getAdminUin();
    public UinConfig[] getUins();
    public void setUin(int i, String sn, String pass);
    public void addUin(String sn, String pass);
    public void delUin(int i);
    public int getPauseOut();
    public int getMsgOutLimit();
    public int getMaxOutMsgSize();
    public int getMaxOutMsgCount();
    public int getPauseIn();
}
