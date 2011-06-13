/**
 * 
 */
package ru.jimbot.http.admin;

import java.util.ArrayList;
import java.util.List;

/**
 * Реестр страниц-расширений веб-админки
 * @author spec
 *
 */
public class ViewAddonRegistry {
	private List<ViewAddon> ad = new ArrayList<ViewAddon>();

	public synchronized void add(ViewAddon a) {
		ad.add(a);
	}
	
	public synchronized void remove(ViewAddon a) {
		ad.remove(a);
	}
	
	public synchronized void removeAll() {
		ad.clear();
	}
	
	public synchronized List<ViewAddon> getAddons() {
		return ad;
	}
}
