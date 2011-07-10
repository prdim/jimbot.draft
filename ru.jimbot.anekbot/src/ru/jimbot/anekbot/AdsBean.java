/**
 * 
 */
package ru.jimbot.anekbot;

/**
 * @author spec
 *
 */
public class AdsBean {
	private long id = 0;
	private String Txt = "";
	private boolean enable = false;
	private String note = "";
	private String clientId = "";
	private long expDate = 0;
	private long maxCount = 0;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the txt
	 */
	public String getTxt() {
		return Txt;
	}
	/**
	 * @param txt the txt to set
	 */
	public void setTxt(String txt) {
		Txt = txt;
	}
	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return enable;
	}
	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the expDate
	 */
	public long getExpDate() {
		return expDate;
	}
	/**
	 * @param expDate the expDate to set
	 */
	public void setExpDate(long expDate) {
		this.expDate = expDate;
	}
	/**
	 * @return the maxCount
	 */
	public long getMaxCount() {
		return maxCount;
	}
	/**
	 * @param maxCount the maxCount to set
	 */
	public void setMaxCount(long maxCount) {
		this.maxCount = maxCount;
	}
}
