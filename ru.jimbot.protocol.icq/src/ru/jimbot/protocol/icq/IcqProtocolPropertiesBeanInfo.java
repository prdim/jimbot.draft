/**
 * 
 */
package ru.jimbot.protocol.icq;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;

/**
 * @author spec
 *
 */
public class IcqProtocolPropertiesBeanInfo extends SimpleBeanInfo {
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			ArrayList<PropertyDescriptor> pr = new ArrayList<PropertyDescriptor>();
			PropertyDescriptor p;
			
			p = new PropertyDescriptor("server", IcqProtocolProperties.class);
			p.setDisplayName("Сервер");
			pr.add(p);
			
			p = new PropertyDescriptor("port", IcqProtocolProperties.class);
			p.setDisplayName("Порт");
			pr.add(p);
			
			p = new PropertyDescriptor("status", IcqProtocolProperties.class);
			p.setDisplayName("ICQ статус");
			pr.add(p);
			
			p = new PropertyDescriptor("statustxt", IcqProtocolProperties.class);
			p.setDisplayName("Текст ICQ статуса");
			pr.add(p);
			
			p = new PropertyDescriptor("xstatus", IcqProtocolProperties.class);
			p.setDisplayName("x-статус (0-34)");
			pr.add(p);
			
			p = new PropertyDescriptor("xstatustxt1", IcqProtocolProperties.class);
			p.setDisplayName("Заголовок x-статуса");
			pr.add(p);
			
			p = new PropertyDescriptor("xstatustxt2", IcqProtocolProperties.class);
			p.setDisplayName("Текст  x-статуса");
			pr.add(p);
			
			p = new PropertyDescriptor("pauseOut", IcqProtocolProperties.class);
			p.setDisplayName("Пауза для исходящих сообщений");
			pr.add(p);
			
			p = new PropertyDescriptor("msgOutLimit", IcqProtocolProperties.class);
			p.setDisplayName("Ограничение очереди исходящих");
			pr.add(p);
			
			p = new PropertyDescriptor("pauseRestart", IcqProtocolProperties.class);
			p.setDisplayName("Пауза перед перезапуском коннекта");
			pr.add(p);
			
			p = new PropertyDescriptor("maxOutMsgSize", IcqProtocolProperties.class);
			p.setDisplayName("Максимальный размер одного исходящего сообщения");
			pr.add(p);
			
			p = new PropertyDescriptor("maxOutMsgCount", IcqProtocolProperties.class);
			p.setDisplayName("Максимальное число частей исходящего сообщения");
			pr.add(p);
			
			return pr.toArray(new PropertyDescriptor[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
