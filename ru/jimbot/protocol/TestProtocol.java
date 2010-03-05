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

package ru.jimbot.protocol;

import ru.jimbot.core.CommandProtocolListener;
import ru.jimbot.core.Protocol;
import ru.jimbot.core.ProtocolListener;
import ru.jimbot.util.Log;

import java.util.List;

/**
 * @author Prolubnikov Dmitry
 */
public class TestProtocol implements Protocol, CommandProtocolListener {

    public void setConnectionData(String server, int port, String sn, String pass) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLogger(Log logger) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setStatusData(int status, String text) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setXStatusData(int status, String text1, String text2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addProtocolListener(ProtocolListener e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean removeProtocolListener(ProtocolListener e) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<ProtocolListener> getProtocolListeners() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void connect() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onChangeStatus(int id, String text) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void disconnect() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setStatus(int status) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onChangeXStatus(int id, String text1, String text2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isOnLine() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void sendMsg(String sn, String msg) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void sendMessage(String in, String out, String text) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void getStatus(String sn, int status) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void logOn() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void logOut() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addContactList(String sn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void RemoveContactList(String sn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setConnectData(String server, int port, String screenName, String pass) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getScreenName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getLastError() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
