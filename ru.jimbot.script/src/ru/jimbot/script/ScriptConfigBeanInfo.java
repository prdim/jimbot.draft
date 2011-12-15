/**
 * 
 */
package ru.jimbot.script;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;

/**
 * @author spec
 *
 */
public class ScriptConfigBeanInfo extends SimpleBeanInfo {

	/* (non-Javadoc)
	 * @see java.beans.SimpleBeanInfo#getPropertyDescriptors()
	 */
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			ArrayList<PropertyDescriptor> pr = new ArrayList<PropertyDescriptor>();
			PropertyDescriptor p;
		
		
			p = new PropertyDescriptor("code", ScriptConfig.class);
		
			p.setDisplayName("Кодировка файла скрипта (UTF8, windows-1251,...)");
			pr.add(p);
		
			return pr.toArray(new PropertyDescriptor[] {});
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
