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

package ru.jimbot.modules.anek;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author Prolubnikov Dmitry
 */
public class AnekConfigBeanInfo extends SimpleBeanInfo {
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor name = new PropertyDescriptor("name", AnekConfig.class);
            name.setDisplayName("");
            name.setHidden(true);
            PropertyDescriptor uins = new PropertyDescriptor("uins", AnekConfig.class);
            uins.setDisplayName("");
            uins.setHidden(true);
            PropertyDescriptor status = new PropertyDescriptor("status", AnekConfig.class);
            status.setDisplayName("ICQ статус");
            PropertyDescriptor statustxt = new PropertyDescriptor("statustxt", AnekConfig.class);
            statustxt.setDisplayName("Текст ICQ статуса");
            PropertyDescriptor xstatus = new PropertyDescriptor("xstatus", AnekConfig.class);
            xstatus.setDisplayName("x-статус (0-34)");
            PropertyDescriptor xstatustxt1 = new PropertyDescriptor("xstatustxt1", AnekConfig.class);
            xstatustxt1.setDisplayName("Заголовок x-статуса");
            PropertyDescriptor xstatustxt2 = new PropertyDescriptor("xstatustxt2", AnekConfig.class);
            xstatustxt2.setDisplayName("Текст  x-статуса");
            PropertyDescriptor pauseIn = new PropertyDescriptor("pauseIn", AnekConfig.class);
            pauseIn.setDisplayName("Пауза для входящих сообщений");
            PropertyDescriptor pauseOut = new PropertyDescriptor("pauseOut", AnekConfig.class);
            pauseOut.setDisplayName("Пауза для исходящих сообщений");
            PropertyDescriptor msgOutLimit = new PropertyDescriptor("msgOutLimit", AnekConfig.class);
            msgOutLimit.setDisplayName("Ограничение очереди исходящих");
            PropertyDescriptor pauseRestart = new PropertyDescriptor("pauseRestart", AnekConfig.class);
            pauseRestart.setDisplayName("Пауза перед перезапуском коннекта");
            PropertyDescriptor adminUin = new PropertyDescriptor("adminUin", AnekConfig.class);
            adminUin.setDisplayName("Админские UIN");
            PropertyDescriptor useAds = new PropertyDescriptor("useAds", AnekConfig.class);
            useAds.setDisplayName("Использовать рекламу в боте");
            PropertyDescriptor adsRate = new PropertyDescriptor("adsRate", AnekConfig.class);
            adsRate.setDisplayName("Частота рекламы");
            PropertyDescriptor autoStart = new PropertyDescriptor("autoStart", AnekConfig.class);
            autoStart.setDisplayName("Автоматически запускать анекдотный бот");
            PropertyDescriptor maxOutMsgSize = new PropertyDescriptor("maxOutMsgSize", AnekConfig.class);
            maxOutMsgSize.setDisplayName("Максимальный размер одного исходящего сообщения");
            PropertyDescriptor maxOutMsgCount = new PropertyDescriptor("maxOutMsgCount", AnekConfig.class);
            maxOutMsgCount.setDisplayName("Максимальное число частей исходящего сообщения");
//            PropertyDescriptor db = new PropertyDescriptor("db", AnekConfig.class);
//            db.setDisplayName("Настройки mySQL");
            return new PropertyDescriptor[] {name, uins, status, statustxt, xstatus, xstatustxt1,
                xstatustxt2, pauseIn, pauseOut, msgOutLimit, pauseRestart, adminUin, useAds,
                adsRate, autoStart, maxOutMsgSize, maxOutMsgCount, /*db*/};
        } catch (IntrospectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
