/**
 * 
 */
package ru.jimbot.core;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;


/**
 * @author spec
 *
 */
public class MainPropsBeanInfo extends SimpleBeanInfo {
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			ArrayList<PropertyDescriptor> pr = new ArrayList<PropertyDescriptor>();
			PropertyDescriptor p;
			
			p = new PropertyDescriptor("autoStart", MainProps.class);
			p.setDisplayName("Автоматически запускать сервисы при старте бота");
			pr.add(p);
			
			p = new PropertyDescriptor("startHTTP", MainProps.class);
			p.setDisplayName("Запускать встроенный HTTP-сервер (требуется для работы веб-админки)");
			pr.add(p);
			
			p = new PropertyDescriptor("checkNewVer", MainProps.class);
			p.setDisplayName("Проверять наличие новой версии при запуске");
			pr.add(p);
			
			return pr.toArray(new PropertyDescriptor[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
