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

import java.util.Properties;

import ru.jimbot.table.UserPreference;

/**
 * Интерфейс для настроек сервисов
 * @author Prolubnikov Dmitry
 */
public interface AbstractProps {
    /**
     * Установитьнастройки по умолчанию
     */
	public void setDefault();

    /**
     * Получить список настроек
     * @return
     */
	public UserPreference[] getUserPreference();

    /**
     * Получить список УИНов
     * @return
     */
	public UserPreference[] getUINPreference();

    /**
     *
     * @return
     */
	public Properties getProps();

    /**
     * Загрузить настройки из файла
     */
	public void load();

    /**
     * Сохранить настройки в файл
     */
	public void save();

    /**
     *
     * @param _appProps
     */
	public void registerProperties(Properties _appProps);

    /**
     *
     * @param key
     * @return
     */
	public String getProperty(String key);

    /**
     *
     * @param key
     * @return
     */
	public String getStringProperty(String key);

    /**
     *
     * @param key
     * @param val
     */
	public void setStringProperty(String key, String val);

    /**
     *
     * @param key
     * @param val
     */
	public void setIntProperty(String key, int val);

    /**
     *
     * @param key
     * @param val
     */
	public void setBooleanProperty(String key, boolean val);

    /**
     *
     * @param key
     * @return
     */
	public int getIntProperty(String key);

    /**
     *
     * @param key
     * @return
     */
	public boolean getBooleanProperty(String key);

    /**
     * Получить число зарегистрированных УИНов
     * @return
     */
	public int uinCount();

    /**
     * Получить УИН
     * @param i
     * @return
     */
	public String getUin(int i);

    /**
     * Получить пароль
     * @param i
     * @return
     */
	public String getPass(int i);

    /**
     * Добавить УИН
     * @param uin
     * @param pass
     * @return
     */
	public int addUin(String uin, String pass);

    /**
     * Удалить УИН
     * @param c
     */
	public void delUin(int c);

    /**
     * Изменить параметры УИНа
     * @param i
     * @param uin
     * @param pass
     */
	public void setUin(int i, String uin, String pass);

    /**
     * Автоматически запускать сервис при старте приложения?
     * @return
     */
	public boolean isAutoStart();

    /**
     * Список админов
     * @return
     */
	public String[] getAdmins();

    /**
     * Проверка на админа
     * @param screenName
     * @return
     */
    public boolean testAdmin(String screenName);
}
