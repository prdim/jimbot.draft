/**
 * 
 */
package ru.jimbot.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;


/**
 * Элемент для таблицы кешеа скрипта
 * @author Prolubnikov Dmitry
 *
 */
public class ScriptCashElement {
	private String fname="";
	private Date dt = new Date(0);
	private String script="";
	
	public ScriptCashElement(String fname){
		dt = new Date(new File(fname).lastModified());
		this.fname = fname;
		script = readScript(fname);
	}
	
	/**
	 * Возвращает текст скрипта, при необходимости заново перечитывает файл
	 * @return
	 */
	public String getScript(){
		long d = new File(fname).lastModified();
		if(d != dt.getTime()){
			script = readScript(fname);
			dt.setTime(d);
		}
		return script;
	}
	
    /**
     * Читаем текст макроса в текстовую переменную
     * @param fileName
     * @return
     */
    private String readScript(String fileName){
    	String s="";
    	try {
//    		Log.getDefault().info("Reading script: "+fileName);
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
}
