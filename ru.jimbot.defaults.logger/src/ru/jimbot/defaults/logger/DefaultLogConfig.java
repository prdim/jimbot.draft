/**
 * 
 */
package ru.jimbot.defaults.logger;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import ru.jimbot.core.services.AbstractProperties;

/**
 * Нстройки логгера
 * @author spec
 *
 */
public class DefaultLogConfig implements AbstractProperties {
	private static transient DefaultLogConfig me = null;
	private static final String FILE_NAME = "default_log_config.xml";

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
			
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("./config/" + FILE_NAME)));
			encoder.writeObject(this);
			encoder.close();
		} catch (Exception e) {
			System.err.println("Error saving configuration " + e.getMessage());
		}
	}

	private static synchronized DefaultLogConfig load(String f) {
		Object o = null;
		try {
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("./config/" + f)));
			o = decoder.readObject();
			decoder.close();
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
