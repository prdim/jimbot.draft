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
            PropertyDescriptor uins = new PropertyDescriptor("uins", AnekConfig.class);
            uins.setDisplayName("");
            PropertyDescriptor status = new PropertyDescriptor("status", AnekConfig.class);
            status.setDisplayName("");
            PropertyDescriptor statustxt = new PropertyDescriptor("statustxt", AnekConfig.class);
            statustxt.setDisplayName("");
            PropertyDescriptor xstatus = new PropertyDescriptor("xstatus", AnekConfig.class);
            xstatus.setDisplayName("");
            PropertyDescriptor xstatustxt1 = new PropertyDescriptor("xstatustxt1", AnekConfig.class);
            xstatustxt1.setDisplayName("");
            PropertyDescriptor xstatustxt2 = new PropertyDescriptor("xstatustxt2", AnekConfig.class);
            xstatustxt2.setDisplayName("");
            PropertyDescriptor pauseIn = new PropertyDescriptor("pauseIn", AnekConfig.class);
            pauseIn.setDisplayName("");
            PropertyDescriptor pauseOut = new PropertyDescriptor("pauseOut", AnekConfig.class);
            pauseOut.setDisplayName("");
            PropertyDescriptor msgOutLimit = new PropertyDescriptor("msgOutLimit", AnekConfig.class);
            msgOutLimit.setDisplayName("");
            PropertyDescriptor pauseRestart = new PropertyDescriptor("pauseRestart", AnekConfig.class);
            pauseRestart.setDisplayName("");
            PropertyDescriptor adminUin = new PropertyDescriptor("adminUin", AnekConfig.class);
            adminUin.setDisplayName("");
            PropertyDescriptor useAds = new PropertyDescriptor("useAds", AnekConfig.class);
            useAds.setDisplayName("");
            PropertyDescriptor adsRate = new PropertyDescriptor("adsRate", AnekConfig.class);
            adsRate.setDisplayName("");
            PropertyDescriptor autoStart = new PropertyDescriptor("autoStart", AnekConfig.class);
            autoStart.setDisplayName("");
            PropertyDescriptor maxOutMsgSize = new PropertyDescriptor("maxOutMsgSize", AnekConfig.class);
            maxOutMsgSize.setDisplayName("");
            PropertyDescriptor maxOutMsgCount = new PropertyDescriptor("maxOutMsgCount", AnekConfig.class);
            maxOutMsgCount.setDisplayName("");
            PropertyDescriptor db = new PropertyDescriptor("db", AnekConfig.class);
            db.setDisplayName("");
            return new PropertyDescriptor[] {name, uins, status, statustxt, xstatus, xstatustxt1,
                xstatustxt2, pauseIn, pauseOut, msgOutLimit, pauseRestart, adminUin, useAds,
                adsRate, autoStart, maxOutMsgSize, maxOutMsgCount, db};
        } catch (IntrospectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
