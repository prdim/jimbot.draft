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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author Prolubnikov Dmitry
 */
public class DbConfigBeanInfo extends SimpleBeanInfo {
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor type = new PropertyDescriptor("type",DbConfig.class);
            type.setDisplayName("Тип базы данных");
            PropertyDescriptor host = new PropertyDescriptor("host",DbConfig.class);
            host.setDisplayName("Сервер базы данных");
            PropertyDescriptor user = new PropertyDescriptor("user",DbConfig.class);
            user.setDisplayName("Пользователь базы данных");
            PropertyDescriptor pass = new PropertyDescriptor("pass",DbConfig.class);
            pass.setDisplayName("Пароль");
            PropertyDescriptor base = new PropertyDescriptor("base",DbConfig.class);
            base.setDisplayName("Название базы");
            return new PropertyDescriptor[] {type, host, user, pass, base};
        } catch (IntrospectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
