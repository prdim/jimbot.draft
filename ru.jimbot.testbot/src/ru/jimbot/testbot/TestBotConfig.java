/**
 * 
 */
package ru.jimbot.testbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ru.jimbot.core.services.BotServiceConfig;
import ru.jimbot.core.services.UinConfig;

/**
 * Настройки тестового бота
 * @author Prolubnikov Dmitry
 *
 */
public class TestBotConfig implements BotServiceConfig {
	public static final String FILE_NAME = "testbot-config";
    private String name = "TestBot";
    private List<UinConfig> uins = new ArrayList<UinConfig>();
    private transient HashMap<String, UinConfig> uinsCopy = new HashMap<String, UinConfig>();
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
			File t = new File("./services/" + name);
			if(!t.exists()) t.mkdir();
			
//			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("./services/" + name + "/" + FILE_NAME)));
//			encoder.writeObject(this);
//			encoder.close();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./services/" + name + "/" + FILE_NAME), "UTF8"));
			w.write(gson.toJson(this, new TypeToken<TestBotConfig>() {}.getType()));
			w.close();
		} catch (Exception e) {
			System.err.println("Error saving configuration " + e.getMessage());
		}
	}

	public static synchronized TestBotConfig load(String name) {
		Object o = new TestBotConfig(name);
		try {
//			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("./services/" + name + "/" + FILE_NAME)));
//			o = decoder.readObject();
//			decoder.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./services/" + name + "/" + FILE_NAME),"UTF8")); 
			StringBuilder sb = new StringBuilder();
			while(br.ready()) {
				sb.append(br.readLine());
			}
			br.close();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			o = gson.fromJson(sb.toString(), new TypeToken<TestBotConfig>() {}.getType());
			((TestBotConfig)o).copyUins();
		} catch (Exception e) {
			System.err.println("Error loading configuration " + e.getMessage());
		}
		return (TestBotConfig)o;
	}
	
	private void copyUins() {
		uinsCopy = new HashMap<String, UinConfig>();
		for(UinConfig i : uins) {
			uinsCopy.put(i.getScreenName(), i);
		}
	}

    public boolean testAdmin(String screenName) {
        boolean f = false;
        for(String s : adminUin.split(";")) {
            if(screenName.equals(s)) f = true;
        }
        return f;
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

	@Override
	public List<UinConfig> getUins() {
		return uins;
	}


	private void addUin(UinConfig uin) {
		uins.add(uin);
		uinsCopy.put(uin.getScreenName(), uin);
	}

	@Override
	public void removeUin(UinConfig uin) {
		if(!uinsCopy.containsKey(uin.getScreenName())) return;
		UinConfig u = uinsCopy.get(uin.getScreenName());
		uins.remove(u);
		uinsCopy.remove(u);
	}

	@Override
	public void setUin(UinConfig uin) {
		if(uinsCopy.containsKey(uin.getScreenName())) {
			removeUin(uin);
			addUin(uin);
		} else {
			addUin(uin);
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.BotServiceConfig#getUin(java.lang.String)
	 */
	@Override
	public UinConfig getUin(String screenName) {
		return uinsCopy.get(screenName);
	}

	@Override
	public String[] getAdminUins() {
		return adminUin.split(";");
	}
}

