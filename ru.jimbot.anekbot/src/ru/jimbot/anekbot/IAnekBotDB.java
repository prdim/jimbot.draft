/**
 * 
 */
package ru.jimbot.anekbot;

import java.util.List;

import ru.jimbot.core.exceptions.DbException;

/**
 * Интерфейс для работы анекбота с БД
 * @author spec
 *
 */
public interface IAnekBotDB {

	/**
	 * Инициализация базы данных
	 */
	public IAnekBotDB initDB(String name) throws DbException;
	
	/**
	 * Закрыть базу данных
	 */
	public void closeDB();
	
    /**
     * Получить рекламное объявление с заданным id
     * @param id
     * @return
     */
    public String getAds(long id) throws DbException;
    
    /**
     * Добавить информацио о показанном объявлении в лог
     * @param id
     */
    public void addLogAds(long id) throws DbException;
    
    /**
     * Статистика показа рекламы за сутки
     * @return
     */
    public String adsStat();
    
    /**
     * Возвращает случайное объявление с заданной вероятностью, или ""
     * @return
     */
    public String getAds(int rate) throws DbException;
    
    /**
     * Возвращает анекдот с заданным ид
     * @param id
     * @return
     */
    public String getAnek(long id) throws DbException;
    
    /**
     * Список активных рекламных объявлений для вывода админу
     * @return
     */
    public String adsList();
    
    /**
     * Удалить рекламное объявление
     * @param id
     * @return
     */
    public boolean delAds(long id) throws DbException;
    
    /**
     * Добавить рекламное объявление
     * @param txt
     * @param note
     * @param uin
     * @return
     */
    public long addAds(String txt, String note, String uin) throws DbException;
    
    /**
     * Продлить рекламное объявление
     * @param id
     * @return
     */
    public boolean extendAds(long id, long time) throws DbException;
    
    /**
     * Получить случайный анекдот
     * @return
     * @throws DbException
     */
    public String getAnek() throws DbException;
    
    /**
     * Добавить новый анекдот
     * @param s
     */
    public void addAnek(String s) throws DbException;
    
    /**
     * Добавление анекдота во временную таблицу
     * @param s
     * @param uin
     */
    public void addTempAnek(String s, String uin) throws DbException;
    
    /**
     * Количество анекдотов в базе
     * @return
     */
    public int count();
    
    /**
     * Количество рекламных объявлений в базе
     * @return
     */
    public int adsCount();
    
    /**
     * Обновить кеш ключей анекдотов, вызывается при изменениях в БД
     */
    public void refreshCash();
    
    /**
     * Методы для работы с БД напрямую. Используются в админке для прямого доступа к данным или импорта-экспорта
     */
    
    /**
     * получить список анекдотов
     * @param start - позиция начало
     * @param count - количество возвращаемых записей
     * @return
     * @throws DbException
     */
    public List<AneksBean> d_getAneks(long start, long count) throws DbException;
    
    /**
     * Сохранить изменненый анекдот. Для создания нового использовать addAnek
     * @param a
     * @throws DbException
     */
    public void d_saveAnek(AneksBean a) throws DbException;
    
    /**
     * Удалить анекдот
     * @param a
     * @throws DbException
     */
    public void d_removeAnek(AneksBean a) throws DbException;
    
    /**
     * Размер таблицы анекдотов
     * @throws DbException
     */
    public long d_aneksCount();
}
