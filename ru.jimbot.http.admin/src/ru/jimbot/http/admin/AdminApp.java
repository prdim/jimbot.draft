/**
 * 
 */
package ru.jimbot.http.admin;

import com.vaadin.Application;
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
	            	String n = HttpProps.getInstance().getAdminUserName();
	            	String p = HttpProps.getInstance().getAdminPassword();
	            	if(n.equals(event.getLoginParameter("username")) &&
	            			p.equals(event.getLoginParameter("password"))) {
//	            		main.setContent(new MainView());
	            		MainWindow mainWindow = new MainWindow();
	            		mainWindow.setSizeFull();
	            		main.setContent(mainWindow);
	            	} else {
	            		getWindow().showNotification("Ошибка", "Неправильный логин или пароль", Notification.TYPE_ERROR_MESSAGE);
	            	}
	            }
	        });
	        addComponent(login);
		}
	}
}
