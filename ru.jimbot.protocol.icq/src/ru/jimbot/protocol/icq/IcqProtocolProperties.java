/**
 * 
 */
package ru.jimbot.protocol.icq;

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
public class IcqProtocolProperties implements AbstractProperties {
	public static final String FILE_NAME = "icq-config";
    private transient String serviceName = ""; // TODO Зачем он мне?
    private String server = "login.icq.com";
    private int port = 5980;
    private int status = 0;
    private String statustxt = "";
    private int xstatus = 0;
    private String xstatustxt1 = "";
    private String xstatustxt2 = "";
//    private int pauseIn = 3000;
    private int pauseOut = 500;
    private int msgOutLimit = 20;
    private long pauseRestart = 11*60*1000;
    private int maxOutMsgSize = 500;
    private int maxOutMsgCount = 5;
    
    public IcqProtocolProperties() {
    	// конструктор без параметра нужен для восстановления сереализованных данных
    }
	
	/**
	 * @param serviceName
	 */
	public IcqProtocolProperties(String serviceName) {
		super();
		this.serviceName = serviceName;
	}

	public static synchronized IcqProtocolProperties load(String name) {
		Object o = new IcqProtocolProperties(name);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./services/" + name + "/" + FILE_NAME),"UTF8")); 
			StringBuilder sb = new StringBuilder();
			while(br.ready()) {
				sb.append(br.readLine());
			}
			br.close();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			o = gson.fromJson(sb.toString(), new TypeToken<IcqProtocolProperties>() {}.getType());
		} catch (Exception e) {
			System.err.println("Error loading configuration " + e.getMessage());
		}
		((IcqProtocolProperties)o).setServiceName(name);
		return (IcqProtocolProperties)o;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.AbstractProperties#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Настройки протокола ICQ";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.AbstractProperties#getExtendInfo()
	 */
	@Override
	public String getExtendInfo() {
		return "Настройки протокола ICQ";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.services.AbstractProperties#save()
	 */
	@Override
	public void save() {
		try {
			File t = new File("./services/" + serviceName);
			if(!t.exists()) t.mkdir();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./services/" +
					serviceName + "/" + FILE_NAME), "UTF8"));
			w.write(gson.toJson(this, new TypeToken<IcqProtocolProperties>() {}.getType()));
			w.close();
		} catch (Exception e) {
			System.err.println("Error saving configuration " + e.getMessage());
		}
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
	 * @param xstatustxt2 the xstatustxt2 to set
	 */
	public void setXstatustxt2(String xstatustxt2) {
		this.xstatustxt2 = xstatustxt2;
	}

	/**
	 * @return the xstatustxt2
	 */
	public String getXstatustxt2() {
		return xstatustxt2;
	}

//	/**
//	 * @return the pauseIn
//	 */
//	public int getPauseIn() {
//		return pauseIn;
//	}
//
//	/**
//	 * @param pauseIn the pauseIn to set
//	 */
//	public void setPauseIn(int pauseIn) {
//		this.pauseIn = pauseIn;
//	}

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
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
