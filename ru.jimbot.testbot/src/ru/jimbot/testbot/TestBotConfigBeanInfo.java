/**
 * 
 */
package ru.jimbot.testbot;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author Prolubnikov Dmitry
 * 
 */
public class TestBotConfigBeanInfo extends SimpleBeanInfo {
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			// PropertyDescriptor name = new PropertyDescriptor("name",
			// TestBotConfig.class);
			// name.setDisplayName("");
			// name.setHidden(true);
			// PropertyDescriptor uins = new PropertyDescriptor("uins",
			// TestBotConfig.class);
			// uins.setDisplayName("");
			// uins.setHidden(true);
			PropertyDescriptor status = new PropertyDescriptor("status",
					TestBotConfig.class);
			status.setDisplayName("ICQ статус");
			PropertyDescriptor statustxt = new PropertyDescriptor("statustxt",
					TestBotConfig.class);
			statustxt.setDisplayName("Текст ICQ статуса");
			PropertyDescriptor xstatus = new PropertyDescriptor("xstatus",
					TestBotConfig.class);
			xstatus.setDisplayName("x-статус (0-34)");
			PropertyDescriptor xstatustxt1 = new PropertyDescriptor(
					"xstatustxt1", TestBotConfig.class);
			xstatustxt1.setDisplayName("Заголовок x-статуса");
			PropertyDescriptor xstatustxt2 = new PropertyDescriptor(
					"xstatustxt2", TestBotConfig.class);
			xstatustxt2.setDisplayName("Текст  x-статуса");
			PropertyDescriptor pauseIn = new PropertyDescriptor("pauseIn",
					TestBotConfig.class);
			pauseIn.setDisplayName("Пауза для входящих сообщений");
			PropertyDescriptor pauseOut = new PropertyDescriptor("pauseOut",
					TestBotConfig.class);
			pauseOut.setDisplayName("Пауза для исходящих сообщений");
			PropertyDescriptor msgOutLimit = new PropertyDescriptor(
					"msgOutLimit", TestBotConfig.class);
			msgOutLimit.setDisplayName("Ограничение очереди исходящих");
			PropertyDescriptor pauseRestart = new PropertyDescriptor(
					"pauseRestart", TestBotConfig.class);
			pauseRestart.setDisplayName("Пауза перед перезапуском коннекта");
			PropertyDescriptor adminUin = new PropertyDescriptor("adminUin",
					TestBotConfig.class);
			adminUin.setDisplayName("Админские UIN");
			PropertyDescriptor autoStart = new PropertyDescriptor("autoStart",
					TestBotConfig.class);
			autoStart.setDisplayName("Автоматически запускать бота");
			PropertyDescriptor maxOutMsgSize = new PropertyDescriptor(
					"maxOutMsgSize", TestBotConfig.class);
			maxOutMsgSize
					.setDisplayName("Максимальный размер одного исходящего сообщения");
			PropertyDescriptor maxOutMsgCount = new PropertyDescriptor(
					"maxOutMsgCount", TestBotConfig.class);
			maxOutMsgCount
					.setDisplayName("Максимальное число частей исходящего сообщения");
			PropertyDescriptor helloMsg = new PropertyDescriptor("helloMsg",
					TestBotConfig.class);
			helloMsg.setDisplayName("Приветственное сообщение");
			return new PropertyDescriptor[] { status, statustxt, xstatus,
					xstatustxt1, xstatustxt2, pauseIn, pauseOut, msgOutLimit,
					pauseRestart, adminUin, autoStart, maxOutMsgSize,
					maxOutMsgCount, helloMsg };
		} catch (IntrospectionException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		return null;
	}
}
