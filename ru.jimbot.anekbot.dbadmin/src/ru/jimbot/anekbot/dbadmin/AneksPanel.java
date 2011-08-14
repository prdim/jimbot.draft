/**
 * 
 */
package ru.jimbot.anekbot.dbadmin;

import ru.jimbot.anekbot.AneksBean;
import ru.jimbot.anekbot.IAnekBotDB;
import ru.jimbot.core.exceptions.DbException;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
public class AneksPanel extends VerticalLayout{
	private IAnekBotDB db;
	private Table table;
	private TextField tf1;
	private Label lb1;
	private long start1 = 0;
	private long count1 = 100;
	private TextArea editor1;
	private AneksBean selectedAnek;

	/**
	 * @param db
	 */
	public AneksPanel() {
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
				long max = db.d_aneksCount();
				start1 = (start1+count1)>=max ? (max-1) : (start1+count1);
				refreshData();
			}
		});
        h11.addComponent(bNext);
        Button bLast = new Button(">>|", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				count1 = getCount(tf1);
				long max = db.d_aneksCount();
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
        addComponent(table);
        editor1 = new TextArea();
        editor1.setHeight("100px");
        editor1.setWidth("100%");
        addComponent(editor1);
        HorizontalLayout h12 = new HorizontalLayout();
        Button bSave = new Button("Сохранить", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(null == selectedAnek) {
					getParent().getWindow().showNotification("Нужно выделить один элемент", Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				selectedAnek.setText(editor1.getValue().toString());
				try {
					db.d_saveAnek(selectedAnek);
				} catch (DbException e) {
					e.printStackTrace();
					getParent().getWindow().showNotification("При сохранении произошла ошибка", e.getMessage(), 
							Notification.TYPE_ERROR_MESSAGE);
				}
				getParent().getWindow().showNotification("Сохранено успешно!");
			}
		});
        h12.addComponent(bSave);
        Button bDelete = new Button("Удалить", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(null == selectedAnek) {
					getParent().getWindow().showNotification("Нужно выделить один элемент", Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				try {
					db.d_removeAnek(selectedAnek);
					db.refreshCash();
				} catch (DbException e) {
					e.printStackTrace();
					getParent().getWindow().showNotification("При удалении произошла ошибка", e.getMessage(),
							Notification.TYPE_ERROR_MESSAGE);
				}
				getParent().getWindow().showNotification("Анекдот с ID=" + selectedAnek.getId() + " успешно удален из базы!");
				refreshData();
			}
		});
        h12.addComponent(bDelete);
        addComponent(h12);
        Button bAdd = new Button("Добавить новый анекдот", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					db.addAnek(editor1.getValue().toString());
//					db.refreshCash();
				} catch (DbException e) {
					e.printStackTrace();
					getParent().getWindow().showNotification("При сохранении произошла ошибка", e.getMessage(), 
							Notification.TYPE_ERROR_MESSAGE);
				}
				getParent().getWindow().showNotification("Сохранено успешно!");
				editor1.setValue("");
				refreshData();
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
        lb1.setCaption("с " + start1 + " по " + (start1 + count1) + " из " + db.d_aneksCount());
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
