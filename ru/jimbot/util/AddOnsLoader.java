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

package ru.jimbot.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author kion
 * @author Prolubnikov Dmitry
 * Добавление jar-файлов в classpath прямо во время выполнения
 * Взято: http://itfreak.ru/development/java-runtime-classpath-management/
 */
public class AddOnsLoader {
    // директория, содержащая аддоны
    private static final File ADDONS_DIR = new File("./lib");

    // префикс для URL локальных файлов
    private static final String FILE_PROTOCOL_PREFIX = "file:";

    public static void loadAddOns() throws Throwable {
        // проверка существования директории с аддонами
        if (ADDONS_DIR.isDirectory()) {
            for (File file : ADDONS_DIR.listFiles()) {
                // добавление каждого из найденых файлов к classpath
                addClassPathURL(FILE_PROTOCOL_PREFIX + file.getAbsolutePath());
            }
        }
    }

    private static void addClassPathURL(String path) throws Throwable {
        // URL файла для добавления к classpath
        URL u = new URL(path);
        // достаем системный загрузчик классов
        URLClassLoader urlClassLoader =
            (URLClassLoader) ClassLoader.getSystemClassLoader();
        // используя механизм отражения,
        // достаем метод для добавления URL к classpath
        Class urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod(
                "addURL",
                new Class[]{ URL.class });
        // делаем метод доступным для вызова
        method.setAccessible(true);
        // вызываем метод системного загрузчика,
        // передавая в качестве параметра
        // URL файла для добавления к classpath
        System.out.println("Load library: " + path);
        method.invoke(urlClassLoader, new Object[]{ u });
    }
}
