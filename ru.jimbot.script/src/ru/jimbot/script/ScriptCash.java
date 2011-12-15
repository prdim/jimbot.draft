/**
 * 
 */
package ru.jimbot.script;

import java.io.File;
import java.util.HashMap;

/**
 * Кеш для макросов. Читает макрос при первом обращении. Наблюдает изменения файла.
 * При необходимости обновляет кеш.
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class ScriptCash {
	private HashMap<String,ScriptCashElement> scr;
	
	public ScriptCash(){
		scr = new HashMap<String,ScriptCashElement>();
	}
	
	/**
	 * Возвращает текст скрипта из кеша, если он там есть.
	 * При необходимости обновляет и пополняет кеш.
	 * Возвращает "" если файл отсутствует.
	 * @param fname
	 * @return
	 */
	public String getScript(String fname){
		String s = "";
		try {
			if(!new File(fname).exists()){
//				Log.getDefault().error("Скрипт не найден: " + fname);
				return "";
			}
			if(scr.containsKey(fname)){
				s = scr.get(fname).getScript();
			} else {
				scr.put(fname, new ScriptCashElement(fname));
				s = scr.get(fname).getScript();
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return s;
	}
}
