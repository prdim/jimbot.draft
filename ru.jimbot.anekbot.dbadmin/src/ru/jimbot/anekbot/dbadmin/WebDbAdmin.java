/**
 * 
 */
package ru.jimbot.anekbot.dbadmin;

import ru.jimbot.anekbot.dbadmin.internal.ActivatorAnekDbadmin;
import ru.jimbot.http.admin.AbstractView;
import ru.jimbot.http.admin.ViewAddon;
import ru.jimbot.anekbot.IAnekBotDB;
import ru.jimbot.core.exceptions.DbException;

import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * @author spec
 *
 */
public class WebDbAdmin extends AbstractView<VerticalLayout> implements ViewAddon {
	private TabSheet t;
	private IAnekBotDB db;
	private boolean flagInit = false; // Таблица с данными инициализирована?
	private AneksPanel aneks;
	private AdsPanel ads;
	private AneksTempPanel aneksTemp;
	
	/**
	 * @param layout
	 */
	public WebDbAdmin() {
		super(new VerticalLayout());
		getContent().setMargin(true);
//		HorizontalLayout refreshState = new HorizontalLayout();
		if(ActivatorAnekDbadmin.getAnekDB() == null) {
			getContent().addComponent(new Label("База данных не подключена!"));
		}
		t = new TabSheet();
        t.setHeight("600px");
        t.setWidth("800px");
        
        aneks = new AneksPanel();
        ads = new AdsPanel();
        aneksTemp = new AneksTempPanel();
        t.addTab(aneks, "Анекдоты", null);
        t.addTab(ads, "Реклама", null);
        t.addTab(aneksTemp, "Присланные анекдоты", null);
        getContent().addComponent(t);
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
		aneks.setDB(db);
		aneks.refreshData();
		ads.setDB(db);
		ads.refreshData();
		aneksTemp.setDB(db);
		aneksTemp.refreshData();
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
}
