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

package ru.jimbot.modules;

import ru.jimbot.modules.chat.Users;
import ru.jimbot.protocol.IcqProtocol;
import ru.jimbot.protocol.ProtocolListener;
import ru.jimbot.protocol.AbstractProtocol;


/**
 * Слушатель входящих сообщений. Помещает их в очередь
 * @author Prolubnikov Dmitry
 */
public class MsgReceiver implements ProtocolListener {
    MsgInQueue iq;
    AbstractProtocol iprot;
    
    /** Creates a new instance of MsgReciver */
    public MsgReceiver(MsgInQueue q, AbstractProtocol ip) {
        iq=q;
        iprot = ip;
        ip.addListener(this);
    }
    
    public void getMsg(String sendSN, String recivSN, String msg, boolean isOffline){
        iq.addMsg(iprot, sendSN, msg, isOffline);
    }
    
    public void getStatus(String sn, int status){
//        Log.info(">>Status " + sn + " changed to "+ status);
        iq.addStatus(iprot,sn,String.valueOf(status));
    }    
    
    public void getInfo(Users u, int type) {
        iq.addInfo(u, type);
    }
}
