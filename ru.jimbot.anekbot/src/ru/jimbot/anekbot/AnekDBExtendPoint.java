/**
 * 
 */
package ru.jimbot.anekbot;

/**
 * Точка расширения для регистрации обработчика базы данных анекдотного бота
 * @author spec
 *
 */
public class AnekDBExtendPoint {
	private IAnekBotDB db = null;
	
	public void regDB(IAnekBotDB db) {
		this.db = db;
	}
	
	public IAnekBotDB getDB() {
		return db;
	}
}
