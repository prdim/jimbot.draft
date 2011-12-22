/**
 * 
 */
package ru.jimbot.http.admin;

import ru.jimbot.http.admin.internal.ActivatorHttpAdmin;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.Window.Notification;

/**
 * @author spec
 *
 */
public class AdminApp extends Application {
	Window main;
	private int fail = 0;
	private long lastLogon = 0;
	
	@Override
	public void init() {
//		ViewHandler.initialize(this);
//		SessionHandler.initialize(this);
		main = new Window("JimBot - панель администрирования");
		setMainWindow(main);
		main.addComponent(new LoginWindow(this));
//		MainWindow mainWindow = new MainWindow();
//		mainWindow.setSizeFull();
//		setMainWindow(mainWindow);
	}

	public class LoginWindow extends VerticalLayout {
		public LoginWindow(Application app) {
			LoginForm login = new LoginForm();
	    	login.setWidth("100%");
	        login.setHeight("300px");
	        login.addListener(new LoginForm.LoginListener() {
	            public void onLogin(LoginEvent event) {
	            	WebApplicationContext context = ((WebApplicationContext) getApplication().getContext());
	            	if(HttpProps.getInstance().getFailLoginCount() <= fail) {
	            		if((System.currentTimeMillis() - lastLogon) < HttpProps.getInstance().getBlockTime()*60000) {
	            			getWindow().showNotification("Ошибка", "Превышено максимальное число попыток ввода пароля. Доступ заблокирован.", Notification.TYPE_ERROR_MESSAGE);
	            			return;
	            		}
	            		fail = 0;
	            	}
	            	String n = HttpProps.getInstance().getAdminUserName();
	            	String p = HttpProps.getInstance().getAdminPassword();
	            	if(n.equals(event.getLoginParameter("username")) &&
	            			p.equals(event.getLoginParameter("password"))) {
//	            		main.setContent(new MainView());
	            		ActivatorHttpAdmin.getExtendPointRegistry().getLogger().print("http", "logon", "Вход в админку с адреса " + context.getBrowser().getAddress());
	            		fail = 0;
	            		MainWindow mainWindow = new MainWindow();
	            		mainWindow.setSizeFull();
	            		main.setContent(mainWindow);
	            	} else {
	            		ActivatorHttpAdmin.getExtendPointRegistry().getLogger().print("http", "logon", "Неудачная попытка входа в админку с адреса " + context.getBrowser().getAddress());
	            		fail++;
	            		lastLogon = System.currentTimeMillis();
	            		getWindow().showNotification("Ошибка", "Неправильный логин или пароль", Notification.TYPE_ERROR_MESSAGE);
	            	}
	            }
	        });
	        addComponent(login);
		}
	}
}
