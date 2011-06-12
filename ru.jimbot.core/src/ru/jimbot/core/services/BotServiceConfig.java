/**
 * 
 */
package ru.jimbot.core.services;

import java.util.List;

/**
 * @author spec
 *
 */
public interface BotServiceConfig {

	/**
     * Сохранить настройки в файл
     */
	public void save();

    /**
     * Автоматически запускать сервис при старте приложения?
     * @return
     */
	public boolean isAutoStart();
	
	/**
	 * Проверка прав админа
	 * @param screenName
	 * @return
	 */
	public boolean testAdmin(String screenName);
	
	/**
	 * Уины админов, используются для рассылок 
	 * @return
	 */
	public String[] getAdminUins();
	
	/**
	 * Пауза исходящих сообщений
	 * @return
	 */
	public int getPauseOut();
	
	/**
	 * Максимальный размер очереди исходящих сообщений
	 * @return
	 */
    public int getMsgOutLimit();
    
    /**
     * Максимальный размер исходящего сообщения
     * @return
     */
    public int getMaxOutMsgSize();
    
    /**
     * Максимальное число частей, на которые будет разбито большое исходящее сообщение
     * @return
     */
    public int getMaxOutMsgCount();
    
    /**
     * Пауза между входящими сообщениями от одного пользователя, частые сообщения будут считаться флудом
     * @return
     */
    public int getPauseIn();
    
	/**
	 * Полный список уинов сервиса
	 * @return
	 */
	public List<UinConfig> getUins();
	
	/**
	 * Добавить новые настройки уина
	 * @param uin
	 */
//	public void addUin(UinConfig uin);
	
	/**
	 * Удалить настройки уина
	 * @param uin
	 */
	public void removeUin(UinConfig uin);
	
	/**
	 * Обновить/добавить настройки уина
	 * @param uin
	 */
	public void setUin(UinConfig uin);
	
	/**
	 * Получить настройки УИНа по его имени (для возможности редактирования)
	 * @param screenName
	 * @return
	 */
	public UinConfig getUin(String screenName);
}
