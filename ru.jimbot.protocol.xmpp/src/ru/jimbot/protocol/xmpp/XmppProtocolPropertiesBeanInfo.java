/**
 * 
 */
package ru.jimbot.protocol.xmpp;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;

/**
 * @author Prolubnikov Dmitry
 * @author Black_Kot
 *
 */
public class XmppProtocolPropertiesBeanInfo extends SimpleBeanInfo {
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			ArrayList<PropertyDescriptor> pr = new ArrayList<PropertyDescriptor>(8);
			PropertyDescriptor p;
			
			p = new PropertyDescriptor("status", XmppProtocolProperties.class);
			p.setDisplayName("Статус (1-5)");
			pr.add(p);
			
			p = new PropertyDescriptor("statustxt", XmppProtocolProperties.class);
			p.setDisplayName("Текст статуса");
			pr.add(p);
			
			p = new PropertyDescriptor("pauseOut", XmppProtocolProperties.class);
			p.setDisplayName("Пауза для исходящих сообщений");
			pr.add(p);
			
			p = new PropertyDescriptor("msgOutLimit", XmppProtocolProperties.class);
			p.setDisplayName("Ограничение очереди исходящих");
			pr.add(p);
			
			p = new PropertyDescriptor("autoRestart", XmppProtocolProperties.class);
			p.setDisplayName("Автоматически перезапускать коннект");
			pr.add(p);
			
			p = new PropertyDescriptor("maxOutMsgSize", XmppProtocolProperties.class);
			p.setDisplayName("Максимальный размер одного исходящего сообщения");
			pr.add(p);
			
			p = new PropertyDescriptor("maxOutMsgCount", XmppProtocolProperties.class);
			p.setDisplayName("Максимальное число частей исходящего сообщения");
			pr.add(p);

                        p =null;

			return pr.toArray(new PropertyDescriptor[pr.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
