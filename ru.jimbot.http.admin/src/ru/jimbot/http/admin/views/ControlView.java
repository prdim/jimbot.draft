/**
 * 
 */
package ru.jimbot.http.admin.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleException;

import ru.jimbot.core.MainProps;
import ru.jimbot.http.admin.AbstractView;
import ru.jimbot.http.admin.ConfirmWindow;
import ru.jimbot.http.admin.internal.Activator;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

/**
 * Страница управления ботом
 * 
 * @author spec
 *
 */
public class ControlView extends AbstractView<VerticalLayout> {
	private String fragment;
	private Map<String, Button> bStops = new HashMap<String, Button>();
	private Map<String, Button> bStarts = new HashMap<String, Button>();

	public ControlView(String fragment) {
		super(new VerticalLayout());
		this.fragment = fragment;
		getContent().setMargin(true);
		getContent().addComponent(new Label("Основные команды управления ботом"));
		Button stopBot = new Button("Остановить JimBot", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getWindow().addWindow(new ConfirmWindow("Остановка бота приведет к закрытию административной панели.\nВы уверены?") {

					@Override
					public void confirm() {
						try {
							// TODO Это сообщение не видно...
							getWindow().showNotification("Останавливаю JimBot...", "", Notification.TYPE_WARNING_MESSAGE);
							Activator.getContext().getBundle(0).stop();
						} catch (BundleException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				});
//				try {
//					getWindow().showNotification("Останавливаю бота...", "", Notification.TYPE_WARNING_MESSAGE);
//					Activator.getContext().getBundle(0).stop();
//				} catch (BundleException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
        });
		getContent().addComponent(stopBot);
		for(final String i : MainProps.getInstance().getServiceNames()) {
			HorizontalLayout h = new HorizontalLayout();
			h.setMargin(true);
			// TODO Подумать, что сделать со статусом сервиса
//			String state = Activator.getExtendPointRegistry().getBotService(i).isRun() ? "[ONLINE]" : "[OFFLINE] ";
			h.addComponent(new Label(i /*+ " " + state + " "*/));
			Button b1 = new Button("Остановить сервис", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					Activator.getExtendPointRegistry().getBotService(i).stop();
					getWindow().showNotification("Сервис остановлен");
					bStops.get(i).setVisible(false);
					bStarts.get(i).setVisible(true);
				}
			});
			Button b2 = new Button("Запустить сервис", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					Activator.getExtendPointRegistry().getBotService(i).start();
					getWindow().showNotification("Сервис запущен");
					bStops.get(i).setVisible(true);
					bStarts.get(i).setVisible(false);
				}
			});
			if(Activator.getExtendPointRegistry().getBotService(i).isRun()) {
				b2.setVisible(false);
			} else {
				b1.setVisible(false);
			}
			h.addComponent(b1);
			h.addComponent(b2);
			bStarts.put(i, b2);
			bStops.put(i, b1);
			getContent().addComponent(h);
		}
	}

	@Override
	public void activated(Object... params) {
//		System.out.println(">>>Activate<<<");
	}

	@Override
	public void deactivated(Object... params) {
//		System.out.println(">>>Deactivate<<<");
	}

	public String getFragment() {
		return fragment;
	}
}
