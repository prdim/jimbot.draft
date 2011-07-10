/**
 * 
 */
package ru.jimbot.anekbot.dbadmin;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.jimbot.anekbot.AdsBean;
import ru.jimbot.anekbot.AneksBean;
import ru.jimbot.anekbot.IAnekBotDB;

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
				AdsBean value = (AdsBean)event.getProperty().getValue();
                if (null == value/* || value.size() == 0*/) {
                    editor1.setValue("");
                    selectedAds = null;
                } else {
                	editor1.setValue(value.getTxt());
                	editor1.setCaption("Id=" + value.getId());
                	selectedAds = value;
                }
			}
        	
        });
        addComponent(table);
        editor1 = new TextArea();
        editor1.setHeight("50px");
        editor1.setWidth("100%");
        addComponent(editor1);
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
