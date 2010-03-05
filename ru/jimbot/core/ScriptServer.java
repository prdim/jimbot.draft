/*
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

package ru.jimbot.core;

import bsh.Interpreter;
import ru.jimbot.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Date;
import java.util.Vector;

/**
 * Управление скриптами
 * @author Prolubnikov Dmitry
 */
public class ScriptServer {
    private Parser p;
    private HashMap<String, Long> scrm; // Тут будем хранить имя файла и время его изменения

    public ScriptServer(Parser p) {
        this.p = p;
        scrm = new HashMap<String, Long>();
    }

    /**
     * Читает все скрипты с командами и устанавливает их
     */
    public void readAllCommandScripts() {
        Vector<String> v = getListScriptFiles("./services/" + p.getService().getName() + "/scripts/command");
    	try {
    		for(int i=0; i<v.size(); i++){
    			Log.getLogger(p.getService().getName()).info("Устанавливаю скрипт: " + v.get(i));
                Command c = getCommandScript(v.get(i));
                if(c==null){
                    Log.getLogger(p.getService().getName()).info("Ошибка установки, команда игнорируется.");
                } else {
                    c.init();
                    p.addCommand(c);
                }
    		}
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}
    }

    /**
     * Обновляет только новые скрипты в заданном каталоге. Плюс - проводит инициализацию новых скриптов.
     */
    public void refreshAllCommandScripts() {
        Vector<String> v = getListScriptFiles("./services/" + p.getService().getName() + "/scripts/command");
    	try {
            for (int i = 0; i < v.size(); i++) {
                if (!scrm.containsKey(v.get(i)) || (scrm.get(v.get(i)) != (new File(v.get(i)).lastModified()))) {
                    Log.getLogger(p.getService().getName()).info("Обновляю скрипт: " + v.get(i));
                    Command c = getCommandScript(v.get(i));
                    if (c == null) {
                        Log.getLogger(p.getService().getName()).info("Ошибка установки, команда игнорируется.");
                    } else {
                        p.addCommand(c);
                        c.init();
                    }
                }
            }
        } catch (Exception ex){
    		ex.printStackTrace();
    	}        
    }

    /**
     * Получить список имен файлов скриптов в заданном каталоге
     * @param cat
     * @return
     */
    public Vector<String> getListScriptFiles(String cat) {
        Vector<String> v = new Vector<String>();
        File f = new File(cat);
        if(!f.exists()) return v;
        if(!f.isDirectory()) return v;
        File[] fs = f.listFiles();
        if(fs.length>0)
        	for(int i=0;i<fs.length;i++){
        		if(fs[i].isFile())
        			if(getExt(fs[i].getName()).equals("bsh"))
        				v.add(fs[i].getAbsolutePath());
        	}
        return v;
    }

    /**
     * Получить экземпляр класса команды из скрипта
     * @param fileName
     * @return
     */
    public synchronized Command getCommandScript(String fileName) {
        try {
            Interpreter bsh = new Interpreter();
            bsh.set("parser", p);
            String s = readScript(fileName);
            bsh.eval(s);
            return (Command)bsh.get("out");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Читаем текст макроса в текстовую переменную
     * @param fileName
     * @return
     */
    private String readScript(String fileName){
    	String s="";
    	try {
//    		Log.getLogger(p.getService().getName()).info("Reading script: "+fileName);
            scrm.put(fileName, new File(fileName).lastModified());
    		BufferedReader in
    		   = new BufferedReader(new InputStreamReader(
    				   new FileInputStream(fileName),"windows-1251"));
    		while(in.ready())
    			s += in.readLine() + '\n';
    		in.close();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return s;
    }

    private String getName(String s){
        if(s.indexOf(".")<0)
            return s;
        else
            return s.replace('.', ':').split(":")[0];
    }

    private String getExt(String s){
        if(s.indexOf(".")<0)
            return "";
        else
            return s.replace('.', ':').split(":")[1];
    }}
