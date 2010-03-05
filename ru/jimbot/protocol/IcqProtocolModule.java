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

import com.google.inject.Binder;
import com.google.inject.Module;
import ru.jimbot.core.Protocol;
import ru.jimbot.core.Service;
import ru.jimbot.modules.anek.AnekService;

/**
 *
 * @author Prolubnikov Dmitry
 */
public class IcqProtocolModule implements Module {
    public void configure(Binder binder) {
        binder.bind(Protocol.class).to(IcqProtocol.class);
        binder.bind(Service.class).to(AnekService.class);
    }
}
