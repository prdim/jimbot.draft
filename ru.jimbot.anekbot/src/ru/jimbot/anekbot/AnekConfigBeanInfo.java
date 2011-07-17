/**
 * 
 */
package ru.jimbot.anekbot;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author spec
 *
 */
public class AnekConfigBeanInfo extends SimpleBeanInfo {
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
//			PropertyDescriptor status = new PropertyDescriptor("status", AnekConfig.class);
//            status.setDisplayName("ICQ статус");
//            PropertyDescriptor statustxt = new PropertyDescriptor("statustxt", AnekConfig.class);
//            statustxt.setDisplayName("Текст ICQ статуса");
//            PropertyDescriptor xstatus = new PropertyDescriptor("xstatus", AnekConfig.class);
//            xstatus.setDisplayName("x-статус (0-34)");
//            PropertyDescriptor xstatustxt1 = new PropertyDescriptor("xstatustxt1", AnekConfig.class);
//            xstatustxt1.setDisplayName("Заголовок x-статуса");
//            PropertyDescriptor xstatustxt2 = new PropertyDescriptor("xstatustxt2", AnekConfig.class);
//            xstatustxt2.setDisplayName("Текст  x-статуса");
            PropertyDescriptor pauseIn = new PropertyDescriptor("pauseIn", AnekConfig.class);
            pauseIn.setDisplayName("Пауза для входящих сообщений");
//            PropertyDescriptor pauseOut = new PropertyDescriptor("pauseOut", AnekConfig.class);
//            pauseOut.setDisplayName("Пауза для исходящих сообщений");
//            PropertyDescriptor msgOutLimit = new PropertyDescriptor("msgOutLimit", AnekConfig.class);
//            msgOutLimit.setDisplayName("Ограничение очереди исходящих");
//            PropertyDescriptor pauseRestart = new PropertyDescriptor("pauseRestart", AnekConfig.class);
//            pauseRestart.setDisplayName("Пауза перед перезапуском коннекта");
            PropertyDescriptor adminUin = new PropertyDescriptor("adminUin", AnekConfig.class);
            adminUin.setDisplayName("Админские UIN");
            PropertyDescriptor useAds = new PropertyDescriptor("useAds", AnekConfig.class);
            useAds.setDisplayName("Использовать рекламу в боте");
            PropertyDescriptor adsRate = new PropertyDescriptor("adsRate", AnekConfig.class);
            adsRate.setDisplayName("Частота рекламы");
            PropertyDescriptor autoStart = new PropertyDescriptor("autoStart", AnekConfig.class);
            autoStart.setDisplayName("Автоматически запускать анекдотный бот");
//            PropertyDescriptor maxOutMsgSize = new PropertyDescriptor("maxOutMsgSize", AnekConfig.class);
//            maxOutMsgSize.setDisplayName("Максимальный размер одного исходящего сообщения");
//            PropertyDescriptor maxOutMsgCount = new PropertyDescriptor("maxOutMsgCount", AnekConfig.class);
//            maxOutMsgCount.setDisplayName("Максимальное число частей исходящего сообщения");
            return new PropertyDescriptor[] {pauseIn, adminUin, useAds,
                adsRate, autoStart};
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
