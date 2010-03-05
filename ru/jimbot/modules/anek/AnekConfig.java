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

import ru.jimbot.core.DbConfig;
import ru.jimbot.core.Password;
import ru.jimbot.core.ServiceConfig;
import ru.jimbot.core.UinConfig;
import ru.jimbot.util.FileUtils;

import java.io.File;

/**
 * @author Prolubnikov Dmitry
 */
public class AnekConfig implements ServiceConfig {
    public static final String FILE_NAME = "anek-config.xml";
    private String name = "Anek";
    private UinConfig[] uins = new UinConfig[] {};
    private int status = 0;
    private String statustxt = "";
    private int xstatus = 0;
    private String xstatustxt1 = "";
    private String xstatustxt2 = "";
    private int pauseIn = 3000;
    private int pauseOut = 500;
    private int msgOutLimit = 20;
    private long pauseRestart = 11*60*1000;
    private String[] adminUin = new String[] {"111111", "222222"};
    private boolean useAds = false;
    private int adsRate = 3;
    private boolean autoStart = false;
    private int maxOutMsgSize = 500;
    private int maxOutMsgCount = 5;
    private DbConfig db = new DbConfig();

    public AnekConfig() {
    }

    public AnekConfig(String name) {
        this.name = name;
    }

    public void save() {
        try {
            FileUtils.beanToXML(this, name + File.pathSeparator + FILE_NAME);
        } catch (Exception e) {
            System.err.println("Error saving " + name + " Configuration " + e.getMessage());
        }
        System.out.println("Saved " + name + " Configuration successfully");
    }

    public static AnekConfig load(String name) {
        AnekConfig a;
            try {
                a = (AnekConfig) FileUtils.XmlToBean(name + File.pathSeparator + FILE_NAME);
                System.out.println("Loaded " + name + " Configuration successfully");
            } catch (Exception e) {
                System.err.println("Error loading " + name + " Configuration " + e.getMessage());
                System.out.println("Use " + name + " Default Configuration.");
                a = new AnekConfig();
            }
        return a;
    }

    public boolean testAdmin(String screenName) {
        boolean f = false;
        for(String s : adminUin) {
            if(screenName.equals(s)) f = true;
        }
        return f;
    }

    public void setUin(int i, String sn, String pass) {
        this.uins[i].setScreenName(sn);
        this.uins[i].setPass(new Password(pass));
    }

    public void addUin(String sn, String pass) {
        uins = FileUtils.addItem(uins, new UinConfig(sn, pass, "icq"));
    }

    public void delUin(int i) {
        uins = FileUtils.removeItem(uins, i);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UinConfig[] getUins() {
        return uins;
    }

    public void setUins(UinConfig[] uins) {
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

    public String[] getAdminUin() {
        return adminUin;
    }

    public void setAdminUin(String[] adminUin) {
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

    public DbConfig getDb() {
        return db;
    }

    public void setDb(DbConfig db) {
        this.db = db;
    }
}
