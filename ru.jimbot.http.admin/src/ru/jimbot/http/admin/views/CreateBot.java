/**
 * 
 */
package ru.jimbot.http.admin.views;

import ru.jimbot.core.ExtendPoint;
import ru.jimbot.core.MainProps;
import ru.jimbot.core.services.IBotServiceBuilder;
import ru.jimbot.http.admin.AbstractView;
import ru.jimbot.http.admin.internal.ActivatorHttpAdmin;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

/**
 * @author spec
 *
 */
public class CreateBot extends AbstractView<VerticalLayout> {
	private String fragment;

	/**
	 * @param fragment
	 */
	public CreateBot(String fragment) {
		super(new VerticalLayout());
		this.fragment = fragment;
		getContent().setMargin(true);
		Button b = new Button("Создать новый сервис бота");
		b.addListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getWindow().addWindow(new NewServiceWindow());
			}
		});
		getContent().addComponent(b);
	}

	@Override
	public void activated(Object... params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivated(Object... params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFragment() {
		return fragment;
	}
	
	private class NewServiceWindow extends Window {
		private VerticalLayout v;
		TextField t;
		ComboBox c;
		NewServiceWindow me;

		public NewServiceWindow() {
			super();
			setModal(true);
			setCaption("Создание нового бота");
			v = (VerticalLayout)this.getContent();
			v.setMargin(true);
			v.setSpacing(true);
			v.setSizeUndefined();
			HorizontalLayout h = new HorizontalLayout();
			t = new TextField("Имя сервиса");
			h.addComponent(t);
			v.addComponent(h);
			h = new HorizontalLayout();
			c = new ComboBox("Тип сервиса");
			c.removeAllItems();
			for(IBotServiceBuilder i : ActivatorHttpAdmin.getExtendPointRegistry().getBotServiceBuilders()) {
				c.addItem(((ExtendPoint)i).getPointName());
			}
			c.setNewItemsAllowed(false);
			h.addComponent(c);
			v.addComponent(h);
			Button b = new Button("Создать");
			b.addListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					if(c.getValue() == null) {
						getWindow().showNotification("Выберите один из типов бота", Notification.TYPE_WARNING_MESSAGE);
						return;
					}
					String s1 = t.getValue().toString();
					String s2 = c.getValue().toString();
					System.out.println(s1);
					if(!s1.matches("\\w+")) {
						getWindow().showNotification("В названии сервиса допустимы только латинский символы и цифры", Notification.TYPE_WARNING_MESSAGE);
						return;
					}
					for(String i : MainProps.getInstance().getServiceNames()) {
						if(i.equals(s1)) {
							getWindow().showNotification("Сервис с таким именем уже существует!", Notification.TYPE_WARNING_MESSAGE);
							return;
						}
					}
					ActivatorHttpAdmin.getExtendPointRegistry().getBuilderForService(s2).createServiceData(s1);
//					MainProps.getInstance().getServiceNames().add(s1);
//					MainProps.getInstance().getServiceTypes().add(s2);
					MainProps.getInstance().addService(s1, s2);
					MainProps.getInstance().save();
//					System.out.println(MainProps.getInstance().getServiceNames());
					me.getParent().getWindow().showNotification("Сераис " + s1 + " создан успешно");
					me.getParent().removeWindow(me);
				}
				
			});
			v.addComponent(b);
			v.setComponentAlignment(b, Alignment.BOTTOM_RIGHT);
			me = this;
		}
		
		
	}
}
