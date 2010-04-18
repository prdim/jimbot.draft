/**
 * 
 */
package ru.jimbot.modules.anek.db;

import com.amazon.carbonado.PrimaryKey;
import com.amazon.carbonado.Storable;

/**
 * @author spec
 *
 */
@PrimaryKey("id")
public abstract class AdsLogStore implements Storable {

	public abstract long getId();
	public abstract void setId(long id);
	
	public abstract long getAdsId();
	public abstract void setAdsId(long adsId);
	
	public abstract String getUin();
	public abstract void setUin(String uin);
}
