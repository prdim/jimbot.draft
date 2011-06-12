/**
 * 
 */
package ru.jimbot.http.admin;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;

/**
 * @author spec
 *
 */
public class HttpPropsBeanInfo extends SimpleBeanInfo {
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			ArrayList<PropertyDescriptor> pr = new ArrayList<PropertyDescriptor>();
			PropertyDescriptor p;
			
			p = new PropertyDescriptor("adminUserName", HttpProps.class);
			p.setDisplayName("Имя пользователя");
			pr.add(p);
			p = new PropertyDescriptor("adminPassword", HttpProps.class);
			p.setDisplayName("Пароль");
			pr.add(p);
			p = new PropertyDescriptor("failLoginCount", HttpProps.class);
			p.setDisplayName("Число неудачных попыток входа до блокировки");
			pr.add(p);
			p = new PropertyDescriptor("blockTime", HttpProps.class);
			p.setDisplayName("Время блокировки (минут)");
			pr.add(p);
			
			return pr.toArray(new PropertyDescriptor[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
