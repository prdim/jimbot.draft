/**
 * 
 */
package ru.jimbot.http.admin;

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
 * @author spec
 *
 */
public class HttpProps implements AbstractProperties {
	private static final String FILE_NAME = "http_config";
	private static transient HttpProps me = null;
	
	private String adminUserName = "admin";
	private String adminPassword = "admin";
	private int failLoginCount = 5;
	private int blockTime = 10;

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.AbstractProperties#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Веб-настройки";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.AbstractProperties#getExtendInfo()
	 */
	@Override
	public String getExtendInfo() {
		return "Настройки панели администрирования бота";
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
			w.write(gson.toJson(this, new TypeToken<HttpProps>() {}.getType()));
			w.close();
		} catch (Exception e) {
			System.err.println("Error saving configuration " + e.getMessage());
		}
	}

	private static synchronized HttpProps load(String f) {
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
			o = gson.fromJson(sb.toString(), new TypeToken<HttpProps>() {}.getType());
		} catch (Exception e) {
			System.err.println("Error loading configuration " + e.getMessage());
		}
		return (HttpProps)o;
	}
	
	public static HttpProps getInstance() {
		if(me == null) {
			try {
				me = load(FILE_NAME);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(me == null) me = new HttpProps();
		}
		return me;	
	}

	/**
	 * @param adminUserName the adminUserName to set
	 */
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	/**
	 * @return the adminUserName
	 */
	public String getAdminUserName() {
		return adminUserName;
	}

	/**
	 * @param adminPassword the adminPassword to set
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	/**
	 * @return the adminPassword
	 */
	public String getAdminPassword() {
		return adminPassword;
	}

	/**
	 * @param failLoginCount the failLoginCount to set
	 */
	public void setFailLoginCount(int failLoginCount) {
		this.failLoginCount = failLoginCount;
	}

	/**
	 * @return the failLoginCount
	 */
	public int getFailLoginCount() {
		return failLoginCount;
	}

	/**
	 * @param blockTime the blockTime to set
	 */
	public void setBlockTime(int blockTime) {
		this.blockTime = blockTime;
	}

	/**
	 * @return the blockTime
	 */
	public int getBlockTime() {
		return blockTime;
	}
}
