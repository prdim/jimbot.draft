/**
 * 
 */
package ru.jimbot.script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.jimbot.core.services.AbstractProperties;

/**
 * @author spec
 *
 */
public class ScriptConfig implements AbstractProperties {
	private static transient final String FILE_NAME = "script_config";
	private static transient ScriptConfig me = null;
	private String code = "UTF8";

	@Override
	public String getTitle() {
		return "Настройки скриптов";
	}

	@Override
	public String getExtendInfo() {
		return "Общие настройки скриптов";
	}

	@Override
	public void save() {
		try {
			File t = new File("./config");
			if(!t.exists()) t.mkdir();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./config/" + FILE_NAME), "UTF8"));
			w.write(gson.toJson(this));
			w.close();
		} catch (Exception e) {
			System.err.println("Error saving configuration " + e.getMessage());
		}		
	}

	private static synchronized ScriptConfig load(String f) {
		Object o = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./config/" + FILE_NAME),"UTF8")); 
			StringBuilder sb = new StringBuilder();
			while(br.ready()) {
				sb.append(br.readLine());
			}
			br.close();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			o = gson.fromJson(sb.toString(), ScriptConfig.class);
		} catch (Exception e) {
			System.err.println("Error loading configuration " + e.getMessage());
		}
		return (ScriptConfig)o;		
	}
	
	public static ScriptConfig getInstance() {
		if(me == null) {
			try {
				me = load(FILE_NAME);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(me == null) me = new ScriptConfig();
		}
		return me;	
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
