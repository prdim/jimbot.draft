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

package ru.jimbot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.channels.FileLock;

import ru.jimbot.util.AddOnsLoader;
import ru.jimbot.util.Log;
import ru.jimbot.util.SystemErrLogger;

/**
 * Запуск бота
 * @author Prolubnikov Dmitriy
 * Использована информация статьи http://itfreak.ru/development/java-single-application-instance/
 */
public class StartBot3 {
    private static final String LOCK_FILE_NAME = ".lock"; // имя lock-файла
    private static File rootDir; // директория, в которой находится lock-файл        	

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {
        rootDir = new File("./"); // инициализация директории, в которой находится lock-файл
        // проверка на присутсвие единственного выполняемого экземпляра приложения;
        // естественно должна выполняться перед основной инициализацией приложения
        // и реализацией его бизнесс-логики
        singleAppInstanceCheck();

        // Добавление jar-файлов из lib в classpath
        try {
            AddOnsLoader.loadAddOns();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        System.setErr(new PrintStream(new SystemErrLogger(), true));
//        MainProps.load();
        Manager.getInstance();
        if (MainConfig.getInstance().isStartHTTP())
//            Manager.getInstance().startHTTPServer();
        
//            try {
//                Vector<String> v = WorkScript.getInstance("").listHTTPScripts();
//                String[] s = new String[2 + v.size() * 2];
//                s[0] = "/";
//                s[1] = "ru.jimbot.modules.http.MainPage";
//                for (int i = 0; i < v.size(); i++) {
//                    s[i * 2 + 2] = v.get(i);
//                    s[i * 2 + 3] = "ru.jimbot.modules.http.HTTPScriptRequest";
//                }
//                Server.startServer(s);
//            } catch (Exception ex) {
//                Log.getDefault().error(ex.getMessage(), ex);
//            }
        try {
            Manager.getInstance().startAll();
        } catch (Exception ex) {
            Log.getDefault().error(ex.getMessage(), ex);
        }
    }

    private static void singleAppInstanceCheck() throws Throwable {
        // проверка: запущен ли другой экземпляр приложения?
        if (!lock()) { // если да, то...
            // ... информируем об этом пользователя...
            System.err.println("JimBot is already running!");
            // ... и прекращаем работу
            System.exit(1);
        }
    }

    private static boolean lock() {
        try {
            // создаем блокировку
            final FileLock lock = new FileOutputStream(
                                           new File(rootDir, LOCK_FILE_NAME))
                                              .getChannel().tryLock();
            if (lock != null) {
                // а вот и сам "фокус":
                // создаем поток...
                new Thread(new Runnable(){
                    public void run() {
                        while (true) {
                            try {
                                // ... и проверяем валидность блокировки
                                //     внутри него...
                                if (lock.isValid()) {};
                                // ... а затем засыпаем "навечно"
                                Thread.sleep(Long.MAX_VALUE);
                            } catch (InterruptedException e) {
                                // игнорируем
                            }
                        }
                    }
                }).start();
            }
            return lock != null;
        } catch (Exception ex) {
            // игнорируем, если мы ничего не в силах поделать -
            // пользователь должен сам позаботиться о том,
            // чтобы не запускать на выполнение более одного экземпляра приложения
        }
        return true;
    }
}
