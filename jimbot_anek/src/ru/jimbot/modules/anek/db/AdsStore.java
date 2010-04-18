/**
 * 
 */
package ru.jimbot.modules.anek.db;

import com.amazon.carbonado.Automatic;
import com.amazon.carbonado.PrimaryKey;
import com.amazon.carbonado.Sequence;
import com.amazon.carbonado.Storable;

/**
 * @author spec
 *
 */
@PrimaryKey("id")
public abstract class AdsStore implements Storable {

	@Sequence("ADS_ID_SEQ")
	public abstract long getId();
	public abstract void setId(long id);
	
	public abstract String getTxt();
	public abstract void setTxt(String txt);
	
	public abstract boolean isEnable();
	public abstract void setEnable(boolean enable);
	
	public abstract String getNote();
	public abstract void setNote(String note);
	
	public abstract String getClientId();
	public abstract void setClientId(String clientId);
	
	public abstract long getExpDate();
	public abstract void setExpDate(long expDate);
	
	public abstract long getMaxCount();
	public abstract void setMaxCount(long maxCount);
}
