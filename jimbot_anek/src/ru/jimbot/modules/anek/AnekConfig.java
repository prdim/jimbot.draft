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

package ru.jimbot.modules.anek;

//import ru.jimbot.core.DbConfig;
import ru.jimbot.core.Password;
import ru.jimbot.core.UinConfig;
import ru.jimbot.core.api.ServiceConfig;
import ru.jimbot.util.FileUtils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Vector;

/**
 * @author Prolubnikov Dmitry
 */
public class AnekConfig implements ServiceConfig {
    public static final String FILE_NAME = "anek-config.xml";
    private String name = "AnekBot";
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
    private boolean useAds = false;
    private int adsRate = 3;
    private boolean autoStart = false;
    private int maxOutMsgSize = 500;
    private int maxOutMsgCount = 5;
//    private DbConfig db = new DbConfig();

    public AnekConfig() {
    }

    public AnekConfig(String name) {
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
            System.err.println("Error saving " + name + " Configuration " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void beanToXML(Object bean, String fn) throws FileNotFoundException {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fn)));
        encoder.writeObject(bean);
        encoder.close();
    }

    public static AnekConfig load(String name) {
        AnekConfig a;
            try {
                a = (AnekConfig) XmlToBean("services/" + name + "/" + FILE_NAME);
                System.out.println("Loaded " + name + " Configuration successfully");
            } catch (Exception e) {
                System.err.println("Error loading " + name + " Configuration " + e.getMessage());
                System.out.println("Use " + name + " Default Configuration.");
                a = new AnekConfig(name);
            }
        return a;
    }
    
    private static Object XmlToBean(String fn) throws FileNotFoundException {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fn)));
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

    public void setUin(int i, String sn, String pass) {
        this.uins.get(i).setScreenName(sn);
        this.uins.get(i).setPass(new Password(pass));
    }

    public void addUin(String sn, String pass, String protocol) {
        uins.add(new UinConfig(sn, pass, protocol));
    }

    public void delUin(int i) {
        uins.remove(i);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<UinConfig> getUins() {
        return uins;
    }

    public void setUins(Vector<UinConfig> uins) {
        this.uins = uins;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatustxt() {
        return statustxt;
    }

    public void setStatustxt(String statustxt) {
        this.statustxt = statustxt;
    }

    public int getXstatus() {
        return xstatus;
    }

    public void setXstatus(int xstatus) {
        this.xstatus = xstatus;
    }

    public String getXstatustxt1() {
        return xstatustxt1;
    }

    public void setXstatustxt1(String xstatustxt1) {
        this.xstatustxt1 = xstatustxt1;
    }

    public String getXstatustxt2() {
        return xstatustxt2;
    }

    public void setXstatustxt2(String xstatustxt2) {
        this.xstatustxt2 = xstatustxt2;
    }

    public int getPauseIn() {
        return pauseIn;
    }

    public void setPauseIn(int pauseIn) {
        this.pauseIn = pauseIn;
    }

    public int getPauseOut() {
        return pauseOut;
    }

    public void setPauseOut(int pauseOut) {
        this.pauseOut = pauseOut;
    }

    public int getMsgOutLimit() {
        return msgOutLimit;
    }

    public void setMsgOutLimit(int msgOutLimit) {
        this.msgOutLimit = msgOutLimit;
    }

    public long getPauseRestart() {
        return pauseRestart;
    }

    public void setPauseRestart(long pauseRestart) {
        this.pauseRestart = pauseRestart;
    }

    public String getAdminUin() {
        return adminUin;
    }
    
    public String[] getAdminUins() {
        return adminUin.split(";");
    }

    public void setAdminUin(String adminUin) {
        this.adminUin = adminUin;
    }

    public boolean isUseAds() {
        return useAds;
    }

    public void setUseAds(boolean useAds) {
        this.useAds = useAds;
    }

    public int getAdsRate() {
        return adsRate;
    }

    public void setAdsRate(int adsRate) {
        this.adsRate = adsRate;
    }

    public boolean isAutoStart() {
        return autoStart;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    public int getMaxOutMsgSize() {
        return maxOutMsgSize;
    }

    public void setMaxOutMsgSize(int maxOutMsgSize) {
        this.maxOutMsgSize = maxOutMsgSize;
    }

    public int getMaxOutMsgCount() {
        return maxOutMsgCount;
    }

    public void setMaxOutMsgCount(int maxOutMsgCount) {
        this.maxOutMsgCount = maxOutMsgCount;
    }

//    public DbConfig getDb() {
//        return db;
//    }
//
//    public void setDb(DbConfig db) {
//        this.db = db;
//    }
}
