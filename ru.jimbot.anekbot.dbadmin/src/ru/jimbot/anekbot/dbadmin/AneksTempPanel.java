/**
 * 
 */
package ru.jimbot.anekbot.dbadmin;

import java.util.Set;

import ru.jimbot.anekbot.AneksBean;
import ru.jimbot.anekbot.AneksTempBean;
import ru.jimbot.anekbot.IAnekBotDB;
import ru.jimbot.core.exceptions.DbException;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
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
public class AneksTempPanel extends VerticalLayout{
	private IAnekBotDB db;
	private Table table;
	private TextField tf1;
	private Label lb1;
	private long start1 = 0;
	private long count1 = 100;
	private TextArea editor1;
	private AneksTempBean selectedAnek;
	private Set<AneksTempBean> selectedAneks;
	private CheckBox cbConfirm;
	
	/**
	 * 
	 */
	public AneksTempPanel() {
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
        table.setMultiSelect(true);
        table.setImmediate(true); // react at once when something is selected
        
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        table.addListener(new Table.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				// in multiselect mode, a Set of itemIds is returned,
                // in singleselect mode the itemId is returned directly
                Set<AneksTempBean> value = (Set<AneksTempBean>) event.getProperty().getValue();
//				AneksTempBean value = (AneksTempBean)event.getProperty().getValue();
                if(cbConfirm.isVisible()) cbConfirm.setVisible(false);
                if (null == value || value.size() == 0) {
                    editor1.setValue("");
                    selectedAnek = null;
                } else {
                	AneksTempBean v = value.iterator().next(); // Берем первую строчку из выделенных
                	editor1.setValue(v.getText());
                	editor1.setCaption("Id=" + v.getId());
                	selectedAnek = v;
                }
                selectedAneks = value; // набор используем потом для массового удаления
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
					db.d_saveAnekTemp(selectedAnek);
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
					getParent().getWindow().showNotification("Нужно выделить одну или несколько строк таблицы", 
							Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				if(!cbConfirm.booleanValue()) {
					cbConfirm.setCaption("подтвердите удаление анекдотов: " + selectedAneks.size());
					cbConfirm.setVisible(true);
					return;
				}
				try {
					cbConfirm.setValue(false);
					cbConfirm.setVisible(false);
					for(AneksTempBean i : selectedAneks) {
						db.d_removeAnekTemp(i);
					}
				} catch (DbException e) {
					e.printStackTrace();
					getParent().getWindow().showNotification("При удалении произошла ошибка", e.getMessage(),
							Notification.TYPE_ERROR_MESSAGE);
				}
				if(selectedAneks.size()>1) {
					getParent().getWindow().showNotification("Удалено анекдотов:" + selectedAneks.size());
				} else {
					getParent().getWindow().showNotification("Анекдот с ID=" + selectedAnek.getId() + " успешно удален из базы!");
				}
				refreshData();
			}
		});
        h12.addComponent(bDelete);
        cbConfirm = new CheckBox("Подтверждение удаления", false);
        cbConfirm.setVisible(false);
        h12.addComponent(cbConfirm);
        addComponent(h12);
        Button bAdd = new Button("Переместить в анекдоты", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(null == selectedAnek) {
					getParent().getWindow().showNotification("Нужно выделить одну или несколько строк таблицы", 
							Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				try {
					for(AneksTempBean i : selectedAneks) {
						db.addAnek(i.getText());
						db.d_removeAnekTemp(i);
					}
				} catch (DbException e) {
					e.printStackTrace();
					getParent().getWindow().showNotification("При сохранении произошла ошибка", e.getMessage(), 
							Notification.TYPE_ERROR_MESSAGE);
				}
				getParent().getWindow().showNotification("Перенесено анекдотов: " + selectedAneks.size());
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
        lb1.setCaption("с " + start1 + " по " + (start1 + count1) + " из " + db.d_anekTempCount());
	}
	
	public IndexedContainer getTableData() {
		IndexedContainer c = new IndexedContainer();
		c.addContainerProperty("id", Long.class, 0);
		c.addContainerProperty("length", Integer.class, 0);
		c.addContainerProperty("text", String.class, "");
		c.addContainerProperty("uin", String.class, "");
		try {
			for (AneksTempBean i : db.d_getAnekTemp(start1, count1)) {
				Item item = c.addItem(i);
				item.getItemProperty("id").setValue(i.getId());
				item.getItemProperty("length").setValue(i.getText().length());
				item.getItemProperty("text").setValue(i.getText());
				item.getItemProperty("uin").setValue(i.getUin());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return c;
	}
}
