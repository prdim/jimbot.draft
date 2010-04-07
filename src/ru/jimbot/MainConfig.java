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

package ru.jimbot;

import ru.jimbot.core.Password;
import ru.jimbot.util.FileUtils;

import java.util.Vector;

/**
 * Общие настройки для бота
 * @author Prolubnikov Dmitry
 */

public class MainConfig {
    private static MainConfig config = null;

    public static final String VERSION = "v.0.5.0 alpha 2 (16/02/2010)";
    private static final String PROG_TITLE = "jImBot";
    public static final int VER_INT = 18;
    private static final String FILE_NAME = "jimbot-config.xml";

    private boolean autoStart = false;
    private boolean startHTTP = true;
    private String httpUser = "admin";
    private Password httpPass = new Password("admin");
    private int httpPort = 8888;
    private int httpDelay = 10;
    private boolean checkNewVer = true;
    private Vector<String> serviceNames = new Vector<String>();
    private Vector<String> serviceTypes = new Vector<String>();
    private String defaultPath = "./";

    public MainConfig() {
    }

    public static MainConfig getInstance() {
        if(config==null) {
            try {
                config = (MainConfig) FileUtils.XmlToBean(FILE_NAME);
                System.out.println("Loaded JimBot Configuration successfully");
            } catch (Exception e) {
                System.err.println("Error loading JimBot Configuration " + e.getMessage());
                System.out.println("Use JimBot Default Configuration.");
                config = new MainConfig();
            }
        }
        return config;
    }

    public void save() {
        try {
            FileUtils.beanToXML(config, FILE_NAME);
        } catch (Exception e) {
            System.err.println("Error saving JimBot Configuration " + e.getMessage());
        }
        System.out.println("Saved JimBot Configuration successfully");
    }

    public void delService(String name) {
        for(String s : serviceNames) {
            if(name.equals(s)) {
            	serviceNames.remove(s);
            	break;
            }
        }
    }

    public void addService(String name, String type) {
        serviceNames.add(name);
        serviceTypes.add(type);
    }

    public boolean isAutoStart() {
        return autoStart;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    public boolean isStartHTTP() {
        return startHTTP;
    }

    public void setStartHTTP(boolean startHTTP) {
        this.startHTTP = startHTTP;
    }

    public String getHttpUser() {
        return httpUser;
    }

    public void setHttpUser(String httpUser) {
        this.httpUser = httpUser;
    }

    public Password getHttpPass() {
        return httpPass;
    }

    public void setHttpPass(Password httpPass) {
        this.httpPass = httpPass;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public boolean isCheckNewVer() {
        return checkNewVer;
    }

    public void setCheckNewVer(boolean checkNewVer) {
        this.checkNewVer = checkNewVer;
    }

    public Vector<String> getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(Vector<String> serviceNames) {
        this.serviceNames = serviceNames;
    }

    public Vector<String> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(Vector<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public int getHttpDelay() {
        return httpDelay;
    }

    public void setHttpDelay(int httpDelay) {
        this.httpDelay = httpDelay;
    }

    /**
	 * @return the defaultPath
	 */
	public String getDefaultPath() {
		return defaultPath;
	}

	/**
	 * @param defaultPath the defaultPath to set
	 */
	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
	}

	public static String getAbout(){
        return PROG_TITLE + " " + VERSION + "\n(c) Spec, 2006-2010\n" +
                "Поддержка проекта: http://jimbot.ru"/* +
                "\nIcqLib version: " + OscarInterface.getVersion()*/;
    }
}
