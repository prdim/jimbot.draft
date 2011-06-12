/**
 * 
 */
package ru.jimbot.http.admin;

import java.util.HashMap;
import java.util.Map;

import ru.jimbot.core.MainProps;
import ru.jimbot.http.admin.views.ControlView;
import ru.jimbot.http.admin.views.CreateBot;
import ru.jimbot.http.admin.views.EditBotPropertyView;
import ru.jimbot.http.admin.views.EditPropertyView;
import ru.jimbot.http.admin.views.EmptyView;
import ru.jimbot.http.admin.views.StateView;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.AbstractSplitPanel;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UriFragmentUtility;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

/**
 * @author spec
 *
 */
public class MainWindow extends VerticalLayout/*Window*/ implements ViewContainer {
	public static final Object PROPERTY_NAME = "name";
//    public static final Object PROPERTY_DESCRIPTION = "description";
	private HorizontalSplitPanel splitPanel;
	private MainArea mainArea;
//	private Accordion menu;
	private Tree controlTree;
	private Tree propertyTree;
	private Tree stateTree;
	private Tree navigationTree;
//	private Map<Object, Tree> viewToTree = new HashMap<Object, Tree>();
	private UriFragmentUtility uriFragmentUtility = new UriFragmentUtility();
	private Map<String, View> allViews = new HashMap<String, View>();
	private HierarchicalContainer container;
	
	public MainWindow() {
		setCaption("JimBot " + MainProps.VERSION);
		
		buildMainLayout();
		setView(allViews.get(""));
	}
	
	private Tree createMenuTree() {
		final Tree tree = new Tree();
        tree.setImmediate(true);
        tree.setStyleName("menu");
        tree.setImmediate(true);
        container = new HierarchicalContainer();
        container.addContainerProperty(PROPERTY_NAME, String.class, "");
//        container.addContainerProperty(PROPERTY_DESCRIPTION, String.class, "");
        tree.setItemCaptionPropertyId(PROPERTY_NAME);
        
        View ve1 = new EmptyView("", "Добро пожаловать в панель управления ботом!"); 
        allViews.put(ve1.getFragment(), ve1);
        addChildrenItem(container, "Управление", ve1, null, false);
        View v = new ControlView("control-main");
        allViews.put(v.getFragment(), v);
        addChildrenItem(container, "Главное", v, ve1, true);
        View ve2 = new EmptyView("", "Добро пожаловать в панель управления ботом!");
//        allViews.put(ve.getFragment(), ve);
        addChildrenItem(container, "Настройки", ve2, null, false);
        v = new EditPropertyView("property-main");
        allViews.put(v.getFragment(), v);
        addChildrenItem(container, "Основные настройки", v, ve2, true);
        View vv = new CreateBot("property-bot");
        allViews.put(vv.getFragment(), vv);
        addChildrenItem(container, "Настройки ботов", vv, ve2, false);
        for(String i : MainProps.getInstance().getServiceNames()) {
        	v = new EditBotPropertyView("property-bot-" + i, i);
        	allViews.put(v.getFragment(), v);
        	addChildrenItem(container, "Настройки " + i, v, vv, true);
        }
        View ve3 = new EmptyView("", "Добро пожаловать в панель управления ботом!");
        addChildrenItem(container, "Информация", ve3, null, false);
        v = new StateView("state-main");
        allViews.put(v.getFragment(), v);
        addChildrenItem(container, "Состояние", v, ve3, true);
        
        tree.setContainerDataSource(container);
//        Item item = tree.addItem(v);
//        item.getItemProperty("name").setValue("Главное");
        
        tree.addListener(new Tree.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					View v = (View)event.getProperty().getValue();
					setView(v);
				}
			}
        	
        });
        tree.expandItemsRecursively(ve1);
        tree.expandItemsRecursively(ve2);
        tree.expandItemsRecursively(ve3);
//        tree.expandItemsRecursively(vv);
        return tree;
	}
	
	private void addChildrenItem(HierarchicalContainer c, String name, View v, View parent, boolean leaf) {
		Item item = c.addItem(v);
		item.getItemProperty(PROPERTY_NAME).setValue(name);
//		item.getItemProperty(PROPERTY_DESCRIPTION).setValue(description);
		if(parent != null) c.setParent(v, parent);
		if(leaf) c.setChildrenAllowed(v, false);
	}
	
	public void setView(View v) {
		uriFragmentUtility.setFragment(v.getFragment(), false);
		mainArea.activate(v);
	}
	
	public void setView(String path) {
		View v = allViews.get(path);
		if(v == null) v = allViews.get("");
		setView(v);
	}
	
	private void buildMainLayout() {
//		addComponent(uriFragmentUtility);
		uriFragmentUtility.addListener(new FragmentChangedListener() {

			@Override
			public void fragmentChanged(FragmentChangedEvent source) {
//				System.out.println(">>>" + source.getUriFragmentUtility().getFragment());
				String frag = source.getUriFragmentUtility().getFragment();
                if (frag != null && frag.contains("/")) {
                    String[] parts = frag.split("/");
                    frag = parts[parts.length - 1];
                }
                setView(frag);
			}
			
		});
        splitPanel = new HorizontalSplitPanel();

//        menu = new Accordion();
        mainArea = new MainArea();

        splitPanel.addComponent(createMenuTree());
        splitPanel.addComponent(mainArea);
        splitPanel.setSizeFull();
        splitPanel.setSplitPosition(250, Sizeable.UNITS_PIXELS);
//        setContent(splitPanel);
        addComponent(splitPanel);
        setExpandRatio(splitPanel, 1);
        addComponent(uriFragmentUtility);
        setExpandRatio(uriFragmentUtility, 0);
    }

	@Override
	public void activate(View view) {
//		mainArea.activate(view);
//        Tree tree = viewToTree.get(view.getClass());
//        if (tree != null) {
//            menu.setSelectedTab(tree);
//            tree.select(view.getClass());
//        }
	}

	@Override
	public void deactivate(View view) {
		mainArea.deactivate(view);
	}

}
