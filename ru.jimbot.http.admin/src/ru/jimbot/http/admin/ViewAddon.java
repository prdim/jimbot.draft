/**
 * 
 */
package ru.jimbot.http.admin;

/**
 * Описывает страницу-дополнение в веб-админку
 * @author spec
 *
 */
public interface ViewAddon extends View {

	/**
	 * Имя, которое должно отображаться в меню
	 * @return
	 */
	public String getName();
}
