/**
 * 
 */
package ru.jimbot.anekbot;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;

/**
 * @author spec
 *
 */
public class AnekConfigBeanInfo extends SimpleBeanInfo {
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			ArrayList<PropertyDescriptor> pr = new ArrayList<PropertyDescriptor>();
			PropertyDescriptor p;
			
			p = new PropertyDescriptor("pauseIn", AnekConfig.class);
			p.setDisplayName("Пауза для входящих сообщений");
			pr.add(p);
			
			p = new PropertyDescriptor("adminUin", AnekConfig.class);
			p.setDisplayName("Админские UIN");
			pr.add(p);
			
			p = new PropertyDescriptor("useAds", AnekConfig.class);
			p.setDisplayName("Использовать рекламу в боте");
			pr.add(p);
			
			p = new PropertyDescriptor("adsRate", AnekConfig.class);
			p.setDisplayName("Частота рекламы");
			pr.add(p);
			
			p = new PropertyDescriptor("autoStart", AnekConfig.class);
			p.setDisplayName("Автоматически запускать анекдотный бот");
			pr.add(p);
			
			p = new PropertyDescriptor("pauseConnect", AnekConfig.class);
			p.setDisplayName("Пауза подключений уинов при старте бота");
			pr.add(p);
			
			return pr.toArray(new PropertyDescriptor[] {});
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
