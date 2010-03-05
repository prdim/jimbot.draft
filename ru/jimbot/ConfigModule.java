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

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import ru.jimbot.util.FileUtils;

import java.util.Properties;

/**
 *
 * @author Prolubnikov Dmitry
 */
public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        try {
            Properties p = FileUtils.loadProperties("jimbot_config.xml");
            Names.bindProperties(binder(), p);
            System.out.println("Loaded JimBot Configuration successfully");
        } catch (Exception e) {
            System.out.println("Error loading JimBot Configuration " + e.getMessage());
            System.out.println("Use JimBot Default Configuration.");
        }
    }
}
