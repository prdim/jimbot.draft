/**
 * 
 */
package ru.jimbot.anekbot.dbadmin;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.soap.Text;

import ru.jimbot.anekbot.AdsBean;
import ru.jimbot.anekbot.AneksBean;
import ru.jimbot.anekbot.IAnekBotDB;
import ru.jimbot.core.exceptions.DbException;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

/**
 * @author spec
 *
 */
public class AdsPanel extends VerticalLayout{
	private IAnekBotDB db;
	private Table table;
	private TextField tf1;
	private Label lb1;
	private long start1 = 0;
	private long count1 = 100;
	private TextArea editor1;
	private AdsBean selectedAds;
	private CheckBox cb;
	private PopupDateField dt;
	private TextField tf2, tf3, tf4;
	private CheckBox cbConfirm;
	
	/**
	 * 
	 */
	public AdsPanel() {
		HorizontalLayout h11 = new HorizontalLayout();
        h11.setMargin(true);
        tf1 = new TextField("Число записей");
        tf1.setValue(count1);
        h11.addComponent(tf1);
        lb1 = new Label();
        h11.addComponent(lb1);
        Button bFirst = new Button("|<<", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				count1 = getCount(tf1);
				start1 = 0;
				refreshData();
			}
		});
        h11.addComponent(bFirst);
        Button bPrev = new Button("<<", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				count1 = getCount(tf1);
				start1 = (start1-count1)>0 ? (start1-count1) : 0;
				refreshData();
			}
		});
        h11.addComponent(bPrev);
        Button bNext = new Button(">>", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				count1 = getCount(tf1);
				long max = db.d_adsCount();
				start1 = (start1+count1)>=max ? (max-1) : (start1+count1);
				refreshData();
			}
		});
        h11.addComponent(bNext);
        Button bLast = new Button(">>|", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				count1 = getCount(tf1);
				long max = db.d_adsCount();
				start1 = max-count1;
				refreshData();
			}
		});
        h11.addComponent(bLast);
        addComponent(h11);
        table = new Table();
        table.setWidth("100%");
        table.setHeight("300px");
        table.setSelectable(true);
        table.setMultiSelect(false);
        table.setImmediate(true); // react at once when something is selected
        
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        table.addListener(new Table.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				// in multiselect mode, a Set of itemIds is returned,
                // in singleselect mode the itemId is returned directly
//                Set<?> value = (Set<?>) event.getProperty().getValue();
				AdsBean value = (AdsBean)event.getProperty().getValue();
                if (null == value/* || value.size() == 0*/) {
                    editor1.setValue("");
                    selectedAds = null;
                } else {
                	editor1.setValue(value.getTxt());
                	editor1.setCaption("Id=" + value.getId());
                	selectedAds = value;
                	cb.setValue(value.isEnable());
                	dt.setValue(new Date(value.getExpDate()));
                	tf2.setValue(value.getNote());
                	tf3.setValue(value.getClientId());
                	tf4.setValue(value.getMaxCount());
                }
			}
        	
        });
        addComponent(table);
        HorizontalLayout h2 = new HorizontalLayout();
        cb = new CheckBox("Включить");
        h2.addComponent(cb);
        dt = new PopupDateField();
        dt.setResolution(PopupDateField.RESOLUTION_MIN);
        dt.setCaption("Активно до");
        h2.addComponent(dt);
        tf2 = new TextField("Примечание");
        tf3 = new TextField("Кто");
        tf4 = new TextField("Ограничение показов");
        h2.addComponent(tf2);
        h2.addComponent(tf3);
        h2.addComponent(tf4);
        addComponent(h2);
        editor1 = new TextArea();
        editor1.setHeight("50px");
        editor1.setWidth("100%");
        addComponent(editor1);
        
        HorizontalLayout h12 = new HorizontalLayout();
        Button bSave = new Button("Сохранить", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				boolean f = false;
				if(null == selectedAds) {
					getParent().getWindow().showNotification("Нужно выделить один элемент", Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				selectedAds.setTxt(editor1.getValue().toString());
				if((Boolean)cb.getValue() != selectedAds.isEnable()) {
					f = true; // нужно обновить кеш ключей после сохранения изменений
				}
				selectedAds.setEnable((Boolean)cb.getValue());
				selectedAds.setExpDate(((Date)dt.getValue()).getTime());
				selectedAds.setNote(tf2.getValue().toString());
				selectedAds.setClientId(tf3.getValue().toString());
				selectedAds.setMaxCount(Long.parseLong(tf3.getValue().toString()));
				try {
					db.d_saveAds(selectedAds);
				} catch (DbException e) {
					e.printStackTrace();
					getParent().getWindow().showNotification("При сохранении произошла ошибка", e.getMessage(), 
							Notification.TYPE_ERROR_MESSAGE);
				}
				if(f) db.refreshCash();
				getParent().getWindow().showNotification("Сохранено успешно!");
			}
		});
        h12.addComponent(bSave);
        Button bExtend = new Button("Продлить", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(null == selectedAds) {
					getParent().getWindow().showNotification("Нужно выделить один элемент", Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				try {
					if(!db.extendAds(selectedAds.getId(), 7*24*3600*1000)) {
						getParent().getWindow().showNotification("Упс... неудачно :(", 
								"Объявление активно? Продлять можно только активные объявления", Notification.TYPE_WARNING_MESSAGE);
					}
				} catch (DbException e) {
					e.printStackTrace();
					getParent().getWindow().showNotification("При сохранении произошла ошибка", e.getMessage(), 
							Notification.TYPE_ERROR_MESSAGE);
				}
				refreshData();
				getParent().getWindow().showNotification("Сохранено успешно!");
			}
		});
        h12.addComponent(bExtend);
        Button bDelete = new Button("Удалить", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(null == selectedAds) {
					getParent().getWindow().showNotification("Нужно выделить один элемент", Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				if(!cbConfirm.booleanValue()) {
					cbConfirm.setVisible(true);
					return;
				}
				try {
					cbConfirm.setValue(false);
					cbConfirm.setVisible(false);
					db.d_removeAds(selectedAds);
				} catch (DbException e) {
					e.printStackTrace();
					getParent().getWindow().showNotification("При сохранении произошла ошибка", e.getMessage(), 
							Notification.TYPE_ERROR_MESSAGE);
				}
				db.refreshCash();
				refreshData();
				getParent().getWindow().showNotification("Сохранено успешно!");
			}
		});
        h12.addComponent(bDelete);
        cbConfirm = new CheckBox("Подтверждение удаления", false);
        cbConfirm.setVisible(false);
        h12.addComponent(cbConfirm);
        addComponent(h12);
        Button bAdd = new Button("Добавить новое объявление", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					db.addAds(editor1.getValue().toString(), tf2.getValue().toString(), tf3.getValue().toString());
				} catch (DbException e) {
					e.printStackTrace();
					getParent().getWindow().showNotification("При сохранении произошла ошибка", e.getMessage(), 
							Notification.TYPE_ERROR_MESSAGE);
				}
				refreshData();
				getParent().getWindow().showNotification("Сохранено успешно!");
			}
		});
        addComponent(bAdd);
	}
	
	public void setDB(IAnekBotDB d) {
		db = d;
	}
	
	private long getCount(TextField f) {
		long c = 100;
		try {c = Long.parseLong(f.getValue().toString());} catch (Exception e) {};
		return c;
	}
	
	public void refreshData() {
		table.setContainerDataSource(getTableData());
        lb1.setCaption("с " + start1 + " по " + (start1 + count1) + " из " + db.d_adsCount());
	}
	
	public IndexedContainer getTableData() {
		IndexedContainer c = new IndexedContainer();
		c.addContainerProperty("id", Long.class, 0);
		c.addContainerProperty("e", String.class, 0);
		c.addContainerProperty("exp date", String.class, 0);
		c.addContainerProperty("text", String.class, "");
		c.addContainerProperty("note", String.class, "");
		c.addContainerProperty("client", String.class, "");
		c.addContainerProperty("max count", Long.class, 0);
		
		try {
			for (AdsBean i : db.d_getAds(start1, count1)) {
				Item item = c.addItem(i);
				item.getItemProperty("id").setValue(i.getId());
				item.getItemProperty("e").setValue(i.isEnable() ? "Y" : "N");
				item.getItemProperty("exp date").setValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date(i.getExpDate())));
				item.getItemProperty("text").setValue(i.getTxt());
				item.getItemProperty("note").setValue(i.getNote());
				item.getItemProperty("client").setValue(i.getClientId());
				item.getItemProperty("max count").setValue(i.getMaxCount());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
}
