/**
 * 
 */
package ru.jimbot.defaults.logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ru.jimbot.core.services.AbstractProperties;

/**
 * Нстройки логгера
 * @author spec
 *
 */
public class DefaultLogConfig implements AbstractProperties {
	private static transient DefaultLogConfig me = null;
	private static final String FILE_NAME = "default_log_config";

	private boolean debugMode = false;
	
	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.AbstractProperties#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Default Logger Config";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.AbstractProperties#getExtendInfo()
	 */
	@Override
	public String getExtendInfo() {
		return "Настройки логгера по умолчанию";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.AbstractProperties#save()
	 */
	@Override
	public void save() {
		try {
			File t = new File("./config");
			if(!t.exists()) t.mkdir();
			
//			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("./config/" + FILE_NAME)));
//			encoder.writeObject(this);
//			encoder.close();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./config/" + FILE_NAME), "UTF8"));
			w.write(gson.toJson(this, new TypeToken<DefaultLogConfig>() {}.getType()));
			w.close();
		} catch (Exception e) {
			System.err.println("Error saving configuration " + e.getMessage());
		}
	}

	private static synchronized DefaultLogConfig load(String f) {
		Object o = null;
		try {
//			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("./config/" + f)));
//			o = decoder.readObject();
//			decoder.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./config/" + FILE_NAME),"UTF8")); 
			StringBuilder sb = new StringBuilder();
			while(br.ready()) {
				sb.append(br.readLine());
			}
			br.close();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			o = gson.fromJson(sb.toString(), new TypeToken<DefaultLogConfig>() {}.getType());
		} catch (Exception e) {
			System.err.println("Error loading configuration " + e.getMessage());
		}
		return (DefaultLogConfig)o;
	}
	
	public static DefaultLogConfig getInstance() {
		if(me == null) {
			try {
				me = load(FILE_NAME);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(me == null) me = new DefaultLogConfig();
		}
		return me;
	}

	/**
	 * @return the debugMode
	 */
	public boolean isDebugMode() {
		return debugMode;
	}

	/**
	 * @param debugMode the debugMode to set
	 */
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
}
