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

package ru.jimbot.http;

import java.util.HashMap;
import java.util.Map;

/**
 * По имени страницы определяет обработчик действия
 *
 * @author Prolubnikov Dmitry
 */
public class ActionFactory {
    protected Map map = defaultMap();

    public ActionFactory() {
        super();
    }

    public Action create(String actionName) {
        Class clazz = (Class) map.get(actionName);
        if (clazz == null) throw new RuntimeException(getClass() + " was unable to find an action named '" + actionName + "'.");
        Action actionInstance = null;
        try {
            actionInstance = (Action) clazz.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return actionInstance;
    }

    protected Map defaultMap() {
        Map map = new HashMap();
        map.put("index", BootstrapAction.class);
        map.put("srvs_manager", ServiceManagerAction.class);
        map.put("srvs_delete", ServiceDeleteAction.class);
        map.put("srvs_create", ServiceCreateAction.class);
        map.put("srvs_props_uin", UinPropertiesAction.class);
        map.put("srvs_props", ServicePropertiesAction.class);
        map.put("main_props", MainPropertiesAction.class);
        map.put("user_group_props", UserGroupPropertiesAction.class);
        map.put("srvs_stats", ServiceStatAction.class);
        map.put("srvs_stop", ServiceStopAction.class);
        map.put("srvs_start", ServiceStartAction.class);
        map.put("stop_bot", StopBotAction.class);
        map.put("restart_bot", RestartBotAction.class);
        map.put("message", MessageAction.class);
        map.put("error", ErrorMessageAction.class);
        return map;
    }

}
