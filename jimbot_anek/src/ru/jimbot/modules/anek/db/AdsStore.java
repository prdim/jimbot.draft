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

package ru.jimbot.modules.anek.db;

import com.amazon.carbonado.Automatic;
import com.amazon.carbonado.PrimaryKey;
import com.amazon.carbonado.Sequence;
import com.amazon.carbonado.Storable;

/**
 * @author Prolubnikov Dmitry
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
