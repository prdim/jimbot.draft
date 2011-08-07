/**
 * 
 */
package ru.jimbot.anekbot;

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
 * @author spec
 *
 */
public class AnekConfig implements BotServiceConfig {
	public static final String FILE_NAME = "anekbot-config";
    private String name = "AnekBot";
    private List<UinConfig> uins = new ArrayList<UinConfig>();
    private transient HashMap<String, UinConfig> uinsCopy = new HashMap<String, UinConfig>();
//    private int status = 0;
//    private String statustxt = "";
//    private int xstatus = 0;
//    private String xstatustxt1 = "";
//    private String xstatustxt2 = "";
    private int pauseIn = 3000;
//    private int pauseOut = 500;
//    private int msgOutLimit = 20;
//    private long pauseRestart = 11*60*1000;
    private String adminUin = "111111;222222";
    private boolean useAds = false;
    private int adsRate = 3;
    private boolean autoStart = false;
//    private int maxOutMsgSize = 500;
//    private int maxOutMsgCount = 5;
    private long pauseConnect = 1000;
    private long pauseStart = 10000;
    
	/**
	 * 
	 */
	public AnekConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 */
	public AnekConfig(String name) {
		super();
		this.name = name;
	}
	
	private String patch() {
    	return "services/" + name + "/" + FILE_NAME;
    }
	
	public void save() {
		try {
			File t = new File("./services/" + name);
			if(!t.exists()) t.mkdir();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(patch()), "UTF8"));
			w.write(gson.toJson(this, new TypeToken<AnekConfig>() {}.getType()));
			w.close();
		} catch (Exception e) {
			System.err.println("Error saving configuration " + e.getMessage());
		}
	}
	
	public static synchronized AnekConfig load(String name) {
		Object o = new AnekConfig(name);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./services/" + name + "/" + FILE_NAME),"UTF8")); 
			StringBuilder sb = new StringBuilder();
			while(br.ready()) {
				sb.append(br.readLine());
			}
			br.close();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			o = gson.fromJson(sb.toString(), new TypeToken<AnekConfig>() {}.getType());
			((AnekConfig)o).copyUins();
		} catch (Exception e) {
			System.err.println("Error loading configuration " + e.getMessage());
		}
		return (AnekConfig)o;
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
	
	@Override
	public UinConfig getUin(String screenName) {
		return uinsCopy.get(screenName);
	}

	@Override
	public String[] getAdminUins() {
		return adminUin.split(";");
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
	 * @return the useAds
	 */
	public boolean isUseAds() {
		return useAds;
	}

	/**
	 * @param useAds the useAds to set
	 */
	public void setUseAds(boolean useAds) {
		this.useAds = useAds;
	}

	/**
	 * @return the adsRate
	 */
	public int getAdsRate() {
		return adsRate;
	}

	/**
	 * @param adsRate the adsRate to set
	 */
	public void setAdsRate(int adsRate) {
		this.adsRate = adsRate;
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
	 * @param pauseIn the pauseIn to set
	 */
	public void setPauseIn(int pauseIn) {
		this.pauseIn = pauseIn;
	}

	/**
	 * @return the pauseIn
	 */
	public int getPauseIn() {
		return pauseIn;
	}

	/**
	 * @param pauseConnect the pauseConnect to set
	 */
	public void setPauseConnect(long pauseConnect) {
		this.pauseConnect = pauseConnect;
	}

	/**
	 * @return the pauseConnect
	 */
	public long getPauseConnect() {
		return pauseConnect;
	}

	/**
	 * @param pauseStart the pauseStart to set
	 */
	public void setPauseStart(long pauseStart) {
		this.pauseStart = pauseStart;
	}

	/**
	 * @return the pauseStart
	 */
	public long getPauseStart() {
		return pauseStart;
	}
    
    
}
