/**
 * 
 */
package ru.jimbot.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import ru.jimbot.core.Command;
import ru.jimbot.core.Parser;
import ru.jimbot.core.services.Log;
import ru.jimbot.script.internal.ActivatorScript;

import bsh.Interpreter;

/**
 * Управление скриптами
 * @author Prolubnikov Dmitry
 */
public class ScriptServer {
    private Parser p;
    private HashMap<String, Long> scrm; // Тут будем хранить имя файла и время его изменения
//    private String code = "windows-1251";
    private String code = "UTF8";
    private String path = "";
    private Log log;

    public ScriptServer(Parser p) {
        this.p = p;
        scrm = new HashMap<String, Long>();
        path = "./services/" + p.getService().getServiceName() + "/scripts";
        log = ActivatorScript.getExtendPointRegistry().getLogger();
        code = ScriptConfig.getInstance().getCode();
    }
    
    public ScriptServer setCode(String s) {
    	code = s;
    	return this;
    }
    
    public ScriptServer setPath(String s) {
    	path = s;
    	return this;
    }

    /**
     * Читает все скрипты с командами и возвращает список полученных из скриптов команд
     */
    public List<Command> readAllCommandScripts() {
        Vector<String> v = getListScriptFiles(path);
        List<Command> l = new ArrayList<Command>();
    	try {
    		for(int i=0; i<v.size(); i++){
//    			Log.getLogger(p.getService().getServiceName()).info("Устанавливаю скрипт: " + v.get(i));
    			log.debug(p.getService().getServiceName(), "Устанавливаю скрипт: " + v.get(i));
    			scrm.put(v.get(i), new File(v.get(i)).lastModified());
                Command c = getCommandScript(v.get(i));
                if(c==null){
//                    Log.getLogger(p.getService().getServiceName()).info("Ошибка установки, команда игнорируется.");
                	log.debug(p.getService().getServiceName(), "Ошибка установки, команда игнорируется.");
                } else {
//                    c.init();
//                    p.addCommand(c);
                	l.add(c);
                }
    		}
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}
    	return l;
    }

    /**
     * Обновляет только новые скрипты в заданном каталоге. Плюс - проводит инициализацию новых скриптов.
     */
    public void refreshAllCommandScripts() {
        Vector<String> v = getListScriptFiles(path);
    	try {
            for (int i = 0; i < v.size(); i++) {
                if (!scrm.containsKey(v.get(i)) || (scrm.get(v.get(i)) != (new File(v.get(i)).lastModified()))) {
//                    Log.getLogger(p.getService().getServiceName()).info("Обновляю скрипт: " + v.get(i));
                	log.debug(p.getService().getServiceName(), "Обновляю скрипт: " + v.get(i));
                	scrm.put(v.get(i), new File(v.get(i)).lastModified());
                    Command c = getCommandScript(v.get(i));
                    if (c == null) {
//                        Log.getLogger(p.getService().getServiceName()).info("Ошибка установки, команда игнорируется.");
                    	log.debug(p.getService().getServiceName(), "Ошибка установки, команда игнорируется.");
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
    				   new FileInputStream(fileName), code));
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
