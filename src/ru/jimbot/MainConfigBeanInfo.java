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

package ru.jimbot;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Описание свойств для общих настроек
 * @author Prolubnikov Dmitry
 */
public class MainConfigBeanInfo extends SimpleBeanInfo {
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor autoStart = new PropertyDescriptor("autoStart",MainConfig.class);
            autoStart.setDisplayName("Автозапуск сервисов бота");
            PropertyDescriptor startHTTP = new PropertyDescriptor("startHTTP",MainConfig.class);
            startHTTP.setDisplayName("Запуск HTTP-сервера");
            PropertyDescriptor httpUser = new PropertyDescriptor("httpUser",MainConfig.class);
            httpUser.setDisplayName("Пользователь HTTP-админки");
            PropertyDescriptor httpPass = new PropertyDescriptor("httpPass",MainConfig.class);
            httpPass.setDisplayName("Пароль для входа в HTTP-админку");
            PropertyDescriptor httpPort = new PropertyDescriptor("httpPort",MainConfig.class);
            httpPort.setDisplayName("Порт HTTP");
            PropertyDescriptor checkNewVer = new PropertyDescriptor("checkNewVer",MainConfig.class);
            checkNewVer.setDisplayName("Проверять наличие новой версии?");
            PropertyDescriptor serviceNames = new PropertyDescriptor("serviceNames",MainConfig.class);
            serviceNames.setDisplayName("Названия сервисов");
            serviceNames.setHidden(true);
            PropertyDescriptor serviceTypes = new PropertyDescriptor("serviceTypes",MainConfig.class);
            serviceTypes.setDisplayName("Типы сервисов");
            serviceTypes.setHidden(true);
            PropertyDescriptor httpDelay = new PropertyDescriptor("httpDelay",MainConfig.class);
            httpDelay.setDisplayName("Время жизни http-сессии (минут)");
            return new PropertyDescriptor[] {autoStart, startHTTP, httpUser, httpPass,
                httpPort, checkNewVer, serviceNames, serviceTypes, httpDelay};
        } catch (IntrospectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

}
