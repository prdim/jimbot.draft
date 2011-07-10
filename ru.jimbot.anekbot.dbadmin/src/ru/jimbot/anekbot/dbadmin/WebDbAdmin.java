/**
 * 
 */
package ru.jimbot.anekbot.dbadmin;

import java.util.Set;

import ru.jimbot.anekbot.dbadmin.internal.ActivatorAnekDbadmin;
import ru.jimbot.http.admin.AbstractView;
import ru.jimbot.http.admin.ViewAddon;
import ru.jimbot.anekbot.AneksBean;
import ru.jimbot.anekbot.IAnekBotDB;
import ru.jimbot.core.exceptions.DbException;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;
import com.vaadin.data.Property.ValueChangeListener;

/**
 * @author spec
 *
 */
public class WebDbAdmin extends AbstractView<VerticalLayout> implements ViewAddon {
	private TabSheet t;
	private Table table;
	private IAnekBotDB db;
	private boolean flagInit = false; // Таблица с данными инициализирована?
	private TextField tf1;
	private Label lb1;
	private long start1 = 0;
	private long count1 = 100;
	private TextArea editor1;
	private AneksBean selectedAnek;
	
	/**
	 * @param layout
	 */
	public WebDbAdmin() {
		super(new VerticalLayout());
		getContent().setMargin(true);
		HorizontalLayout refreshState = new HorizontalLayout();
		if(ActivatorAnekDbadmin.getAnekDB() == null) {
			getContent().addComponent(new Label("База данных не подключена!"));
		}
		t = new TabSheet();
        t.setHeight("600px");
        t.setWidth("800px");
        
        VerticalLayout l1 = new VerticalLayout();
        VerticalLayout l2 = new VerticalLayout();
        VerticalLayout l3 = new VerticalLayout();
        t.addTab(l1, "AneksStore", null);
        t.addTab(l2, "AdsStore", null);
        t.addTab(l3, "AneksTempStore", null);
        getContent().addComponent(t);
        
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
				start1 = 0;
				refreshAneksData();
			}
		});
        h11.addComponent(bFirst);
        Button bPrev = new Button("<<", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				count1 = getCount(tf1);
				start1 = (start1-count1)>0 ? (start1-count1) : 0;
				refreshAneksData();
			}
		});
        h11.addComponent(bPrev);
        Button bNext = new Button(">>", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				count1 = getCount(tf1);
				long max = db.d_aneksCount();
				start1 = (start1+count1)>=max ? (max-1) : (start1+count1);
				refreshAneksData();
			}
		});
        h11.addComponent(bNext);
        Button bLast = new Button(">>|", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				count1 = getCount(tf1);
				long max = db.d_aneksCount();
				start1 = max-count1;
				refreshAneksData();
			}
		});
        h11.addComponent(bLast);
        l1.addComponent(h11);
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
				AneksBean value = (AneksBean)event.getProperty().getValue();
                if (null == value/* || value.size() == 0*/) {
                    editor1.setValue("");
                    selectedAnek = null;
                } else {
                	editor1.setValue(value.getText());
                	editor1.setCaption("Id=" + value.getId());
                	selectedAnek = value;
                }
			}
        	
        });
        l1.addComponent(table);
        editor1 = new TextArea();
        editor1.setHeight("100px");
        editor1.setWidth("100%");
        l1.addComponent(editor1);
        HorizontalLayout h12 = new HorizontalLayout();
        Button bSave = new Button("Сохранить", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(null == selectedAnek) {
					getContent().getWindow().showNotification("Нужно выделить один элемент", Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				selectedAnek.setText(editor1.getValue().toString());
				try {
					db.d_saveAnek(selectedAnek);
				} catch (DbException e) {
					e.printStackTrace();
					getContent().getWindow().showNotification("При сохранении произошла ошибка", e.getMessage(), 
							Notification.TYPE_ERROR_MESSAGE);
				}
				getContent().getWindow().showNotification("Сохранено успешно!");
			}
		});
        h12.addComponent(bSave);
        Button bDelete = new Button("Удалить", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(null == selectedAnek) {
					getContent().getWindow().showNotification("Нужно выделить один элемент", Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				try {
					db.d_removeAnek(selectedAnek);
					db.refreshCash();
				} catch (DbException e) {
					e.printStackTrace();
					getContent().getWindow().showNotification("При удалении произошла ошибка", e.getMessage(),
							Notification.TYPE_ERROR_MESSAGE);
				}
				getContent().getWindow().showNotification("Анекдот с ID=" + selectedAnek.getId() + " успешно удален из базы!");
				refreshAneksData();
			}
		});
        h12.addComponent(bDelete);
        l1.addComponent(h12);
        Button bAdd = new Button("Добавить новый анекдот", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					db.addAnek(editor1.getValue().toString());
//					db.refreshCash();
				} catch (DbException e) {
					e.printStackTrace();
					getContent().getWindow().showNotification("При сохранении произошла ошибка", e.getMessage(), 
							Notification.TYPE_ERROR_MESSAGE);
				}
				getContent().getWindow().showNotification("Сохранено успешно!");
				refreshAneksData();
			}
		});
        l1.addComponent(bAdd);
	}
	
	private long getCount(TextField f) {
		long c = 100;
		try {c = Long.parseLong(f.getValue().toString());} catch (Exception e) {};
		return c;
	}
	
	private void refreshAneksData() {
		table.setContainerDataSource(getTableData());
        lb1.setCaption("с " + start1 + " по " + (start1 + count1) + " из " + db.d_aneksCount());
	}

	@Override
	public void activated(Object... params) {
		if(flagInit) return;
		try {
			db = ActivatorAnekDbadmin.getAnekDB().initDB("test_anek");
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        table.setContainerDataSource(getTableData());
//        lb1.setCaption("с " + start1 + " по " + (start1 + count1) + " из " + db.d_aneksCount());
		refreshAneksData();
        flagInit = true;
	}

	@Override
	public void deactivated(Object... params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFragment() {
		return "addon-anek-dbadmin";
	}

	@Override
	public String getName() {
		return "Управление базой анекбота";
	}
	
	public IndexedContainer getTableData() {
		IndexedContainer c = new IndexedContainer();
		c.addContainerProperty("id", Long.class, 0);
		c.addContainerProperty("length", Integer.class, 0);
		c.addContainerProperty("text", String.class, "");
		try {
			for (AneksBean i : db.d_getAneks(start1, count1)) {
				Item item = c.addItem(i);
				item.getItemProperty("id").setValue(i.getId());
				item.getItemProperty("length").setValue(i.getText().length());
				item.getItemProperty("text").setValue(i.getText());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return c;
	}
}
