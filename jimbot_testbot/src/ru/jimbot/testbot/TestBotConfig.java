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

package ru.jimbot.testbot;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Vector;

import ru.jimbot.core.Password;
import ru.jimbot.core.UinConfig;
import ru.jimbot.core.api.ServiceConfig;
import ru.jimbot.util.FileUtils;

/**
 * Настройки тестового бота
 * @author Prolubnikov Dmitry
 *
 */
public class TestBotConfig implements ServiceConfig {
	public static final String FILE_NAME = "testbot-config.xml";
    private String name = "TestBot";
    private Vector<UinConfig> uins = new Vector<UinConfig>();
    private int status = 0;
    private String statustxt = "";
    private int xstatus = 0;
    private String xstatustxt1 = "";
    private String xstatustxt2 = "";
    private int pauseIn = 3000;
    private int pauseOut = 500;
    private int msgOutLimit = 20;
    private long pauseRestart = 11*60*1000;
    private String adminUin = "111111;222222";
    private boolean autoStart = false;
    private int maxOutMsgSize = 500;
    private int maxOutMsgCount = 5;
    private String helloMsg = "Вас приветствует автоответчик, оставте свое сообщение...";
	
    public TestBotConfig() {
	}

	public TestBotConfig(String name) {
		this.name = name;
	}
	
	private String patch() {
    	return "services/" + name + "/" + FILE_NAME;
    }

	public void save() {
		try {
			beanToXML(this, patch());
			System.out.println("Saved " + name + " Configuration successfully");
		} catch (Exception e) {
			System.err.println("Error saving " + name + " Configuration "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	private void beanToXML(Object bean, String fn) throws FileNotFoundException {
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
				new FileOutputStream(fn)));
		encoder.writeObject(bean);
		encoder.close();
	}

	public static TestBotConfig load(String name) {
		TestBotConfig a;
		try {
			a = (TestBotConfig) XmlToBean("services/" + name + "/" + FILE_NAME);
			System.out
					.println("Loaded " + name + " Configuration successfully");
		} catch (Exception e) {
			System.err.println("Error loading " + name + " Configuration "
					+ e.getMessage());
			System.out.println("Use " + name + " Default Configuration.");
			a = new TestBotConfig(name);
		}
		return a;
	}

	private static Object XmlToBean(String fn) throws FileNotFoundException {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(
				new FileInputStream(fn)));
		Object o = decoder.readObject();
		decoder.close();
		return o;
	}

    public boolean testAdmin(String screenName) {
        boolean f = false;
        for(String s : adminUin.split(";")) {
            if(screenName.equals(s)) f = true;
        }
        return f;
    }
    
	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.ServiceConfig#addUin(java.lang.String, java.lang.String)
	 */
	public void addUin(String sn, String pass, String protocol) {
		uins.add(new UinConfig(sn, pass, protocol));
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.ServiceConfig#delUin(int)
	 */
	public void delUin(int i) {
		uins.remove(i);
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.ServiceConfig#getAdminUins()
	 */
	public String[] getAdminUins() {
		return adminUin.split(";");
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.api.ServiceConfig#setUin(int, java.lang.String, java.lang.String)
	 */
	public void setUin(int i, String sn, String pass) {
        this.uins.get(i).setScreenName(sn);
        this.uins.get(i).setPass(new Password(pass));
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the uins
	 */
	public Vector<UinConfig>  getUins() {
		return uins;
	}

	/**
	 * @param uins the uins to set
	 */
	public void setUins(Vector<UinConfig>  uins) {
		this.uins = uins;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the statustxt
	 */
	public String getStatustxt() {
		return statustxt;
	}

	/**
	 * @param statustxt the statustxt to set
	 */
	public void setStatustxt(String statustxt) {
		this.statustxt = statustxt;
	}

	/**
	 * @return the xstatus
	 */
	public int getXstatus() {
		return xstatus;
	}

	/**
	 * @param xstatus the xstatus to set
	 */
	public void setXstatus(int xstatus) {
		this.xstatus = xstatus;
	}

	/**
	 * @return the xstatustxt1
	 */
	public String getXstatustxt1() {
		return xstatustxt1;
	}

	/**
	 * @param xstatustxt1 the xstatustxt1 to set
	 */
	public void setXstatustxt1(String xstatustxt1) {
		this.xstatustxt1 = xstatustxt1;
	}

	/**
	 * @return the xstatustxt2
	 */
	public String getXstatustxt2() {
		return xstatustxt2;
	}

	/**
	 * @param xstatustxt2 the xstatustxt2 to set
	 */
	public void setXstatustxt2(String xstatustxt2) {
		this.xstatustxt2 = xstatustxt2;
	}

	/**
	 * @return the pauseIn
	 */
	public int getPauseIn() {
		return pauseIn;
	}

	/**
	 * @param pauseIn the pauseIn to set
	 */
	public void setPauseIn(int pauseIn) {
		this.pauseIn = pauseIn;
	}

	/**
	 * @return the pauseOut
	 */
	public int getPauseOut() {
		return pauseOut;
	}

	/**
	 * @param pauseOut the pauseOut to set
	 */
	public void setPauseOut(int pauseOut) {
		this.pauseOut = pauseOut;
	}

	/**
	 * @return the msgOutLimit
	 */
	public int getMsgOutLimit() {
		return msgOutLimit;
	}

	/**
	 * @param msgOutLimit the msgOutLimit to set
	 */
	public void setMsgOutLimit(int msgOutLimit) {
		this.msgOutLimit = msgOutLimit;
	}

	/**
	 * @return the pauseRestart
	 */
	public long getPauseRestart() {
		return pauseRestart;
	}

	/**
	 * @param pauseRestart the pauseRestart to set
	 */
	public void setPauseRestart(long pauseRestart) {
		this.pauseRestart = pauseRestart;
	}

	/**
	 * @return the adminUin
	 */
	public String getAdminUin() {
		return adminUin;
	}

	/**
	 * @param adminUin the adminUin to set
	 */
	public void setAdminUin(String adminUin) {
		this.adminUin = adminUin;
	}

	/**
	 * @return the autoStart
	 */
	public boolean isAutoStart() {
		return autoStart;
	}

	/**
	 * @param autoStart the autoStart to set
	 */
	public void setAutoStart(boolean autoStart) {
		this.autoStart = autoStart;
	}

	/**
	 * @return the maxOutMsgSize
	 */
	public int getMaxOutMsgSize() {
		return maxOutMsgSize;
	}

	/**
	 * @param maxOutMsgSize the maxOutMsgSize to set
	 */
	public void setMaxOutMsgSize(int maxOutMsgSize) {
		this.maxOutMsgSize = maxOutMsgSize;
	}

	/**
	 * @return the maxOutMsgCount
	 */
	public int getMaxOutMsgCount() {
		return maxOutMsgCount;
	}

	/**
	 * @param maxOutMsgCount the maxOutMsgCount to set
	 */
	public void setMaxOutMsgCount(int maxOutMsgCount) {
		this.maxOutMsgCount = maxOutMsgCount;
	}

	/**
	 * @return the helloMsg
	 */
	public String getHelloMsg() {
		return helloMsg;
	}

	/**
	 * @param helloMsg the helloMsg to set
	 */
	public void setHelloMsg(String helloMsg) {
		this.helloMsg = helloMsg;
	}


}
