/**
 * 
 */
package ru.jimbot.defaults.logger;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;

/**
 * @author spec
 *
 */
public class DefaultLogConfigBeanInfo extends SimpleBeanInfo {

	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			ArrayList<PropertyDescriptor> pr = new ArrayList<PropertyDescriptor>();
			PropertyDescriptor p;
			
			p = new PropertyDescriptor("debugMode", DefaultLogConfig.class);
			p.setDisplayName("Режим отладки в логе");
			pr.add(p);
			
			return pr.toArray(new PropertyDescriptor[] {});
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
