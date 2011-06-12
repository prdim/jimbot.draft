/**
 * 
 */
package ru.jimbot.http.admin.views;

import java.util.Arrays;
import java.util.List;

import ru.jimbot.core.services.AbstractProperties;
import ru.jimbot.core.services.BotServiceConfig;
import ru.jimbot.core.services.UinConfig;
import ru.jimbot.http.admin.AbstractView;
import ru.jimbot.http.admin.BotConfigFormBuilder;
import ru.jimbot.http.admin.ConfirmWindow;
import ru.jimbot.http.admin.PropertyFormBuilder;
import ru.jimbot.http.admin.internal.Activator;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

/**
 * @author spec
 *
 */
public class EditBotPropertyView extends AbstractView<VerticalLayout> {
	private String serviceName = "";
	private String fragment;
	private final Table table;
	
	public EditBotPropertyView(String fragment, String service) {
		super(new VerticalLayout());
		this.fragment = fragment;
		this.serviceName = service;
		BotServiceConfig p = Activator.getExtendPointRegistry().getBotService(serviceName).getConfig();
		Form f = new BotConfigFormBuilder(p).build();
		getContent().addComponent(f);
		getContent().addComponent(new Button("Сохранить", new Clicker(p, f)));
		Label lb = new Label("<span>Настройки УИНов</span>", Label.CONTENT_XHTML);
		getContent().addComponent(lb);
		Button newUin = new Button("Добавить уин", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getWindow().addWindow(new UinEditWindow(new UinConfig("", "", "test")));
			}
		});
		getContent().addComponent(newUin);
		table = new Table("Список УИНов бота:");
		getContent().addComponent(table);
//		table.setWidth("100%");
		table.setWidth("350px");
        table.setHeight("200px");
        table.setSelectable(true);
        table.setMultiSelect(false);
        table.setImmediate(true); // react at once when something is selected
        table.setContainerDataSource(getTableData());
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        table.setColumnHeaders(new String[] { "УИН", "Пароль", "Протокол" });
        table.setColumnCollapsed("password", true);
        table.addActionHandler(new Action.Handler() {
        	final Action action_edit = new Action("Изменить");
        	final Action action_delete = new Action("Удалить");

			@Override
			public Action[] getActions(Object target, Object sender) {
				return new Action[] {action_edit, action_delete};
			}

			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if(action_edit == action) {
					Item item = table.getItem(target);
					String name = (String) item.getItemProperty("screenName").getValue();
					UinConfig uin = Activator.getExtendPointRegistry().getBotService(serviceName).getConfig().getUin(name);
					getWindow().addWindow(new UinEditWindow(uin));
				} else if(action_delete == action) {
					Item item = table.getItem(target);
					String name = (String) item.getItemProperty("screenName").getValue();
					final UinConfig uin = Activator.getExtendPointRegistry().getBotService(serviceName).getConfig().getUin(name);
					Window w = new ConfirmWindow("Удалить УИН <" + name + ">?") {
						
						@Override
						public void confirm() {
							Activator.getExtendPointRegistry().getBotService(serviceName).getConfig().removeUin(uin);
							Activator.getExtendPointRegistry().getBotService(serviceName).getConfig().save();
							getContent().getWindow().showNotification("Информация сохранена");
							table.setContainerDataSource(getTableData());
							table.setColumnCollapsed("password", true);
							table.requestRepaint();
						}
					};
					getWindow().addWindow(w);
				}
			}
        	
        });
	}

	@Override
	public void activated(Object... params) {
//		if(params.length>0)
//			serviceName = (String)params[0];
	}

	@Override
	public void deactivated(Object... params) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.http.admin.View#getFragment()
	 */
	@Override
	public String getFragment() {
		return fragment;
	}
	
	public IndexedContainer getTableData() {
		IndexedContainer c = new IndexedContainer();
		c.addContainerProperty("screenName", String.class, null);
		c.addContainerProperty("password", String.class, null);
		c.addContainerProperty("protocol", String.class, null);
		List<UinConfig> us = Activator.getExtendPointRegistry().getBotService(serviceName).getConfig().getUins();
		for(UinConfig i : us) {
			Item item = c.addItem(i);
			item.getItemProperty("screenName").setValue(i.getScreenName());
			item.getItemProperty("password").setValue(i.getPassword());
			item.getItemProperty("protocol").setValue(i.getProtocol());
		}
		return c;
	}

	private class Clicker implements Button.ClickListener {
		private BotServiceConfig props;
		private Form form;
		
		public Clicker(BotServiceConfig p, Form f) {
			props = p;
			form = f;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			form.commit();
			props.save();
			getContent().getWindow().showNotification("Информация сохранена");
		}
		
	}
	
	private class UinEditWindow extends Window {
		UinConfig uin;
		private VerticalLayout v;
		private Window me;
		Form uinForm;

		/**
		 * @param uin
		 */
		public UinEditWindow(UinConfig _uin) {
			super();
			this.uin = _uin;
			me = this;
			setModal(true);
			v = (VerticalLayout)me.getContent();
			v.setMargin(true);
			v.setSpacing(true);
			v.setSizeUndefined();
			uinForm = new Form();
			uinForm.setCaption("Данные для УИНа");
			uinForm.setWriteThrough(false); // we want explicit 'apply'
			uinForm.setInvalidCommitted(false); // no invalid values in datamodel
			uinForm.setFormFieldFactory(new PropertiesFieldFactory());
			BeanItem<UinConfig> items = new BeanItem<UinConfig>(uin);
			uinForm.setItemDataSource(items);
			uinForm.setVisibleItemProperties(Arrays.asList(new String[] {"screenName", "password", "protocol"}));
			addComponent(uinForm);
			Button apply = new Button("Сохранить", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					uinForm.commit();
					Activator.getExtendPointRegistry().getBotService(serviceName).getConfig().setUin(uin);
					Activator.getExtendPointRegistry().getBotService(serviceName).getConfig().save();
					me.getParent().getWindow().showNotification("Данные сохранены");
					me.getParent().removeWindow(me);
					table.setContainerDataSource(getTableData());
					table.setColumnCollapsed("password", true);
					table.requestRepaint();
				}
			});
			addComponent(apply);
			v.setComponentAlignment(apply, Alignment.BOTTOM_RIGHT);
		}
	}
	
	private class PropertiesFieldFactory extends DefaultFieldFactory {
		ComboBox protocols = new ComboBox("Протокол");
		
		public PropertiesFieldFactory() {
			for(String i : Activator.getExtendPointRegistry().getProtocols().keySet()) {
				protocols.addItem(i);
			}
			protocols.setNewItemsAllowed(false);
//			protocols.setRequired(true);
//			protocols.setRequiredError("Это поле обязательно к заполнению");
		}

		/* (non-Javadoc)
		 * @see com.vaadin.ui.DefaultFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
		 */
		@Override
		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			Field f;
			f = super.createField(item, propertyId, uiContext);
			if("screenName".equals(propertyId)) {
				TextField tf = (TextField) f;
				tf.setCaption("UIN");
				tf.setRequired(true);
				tf.setRequiredError("Это поле обязательно к заполнению");
			} else if("password".equals(propertyId)) {
				f.setCaption("Пароль");
			} else if("protocol".equals(propertyId)) {
				return protocols;
			}
			return f;
		}
	}
}
