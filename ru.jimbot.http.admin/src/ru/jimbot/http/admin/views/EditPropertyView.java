/**
 * 
 */
package ru.jimbot.http.admin.views;

import ru.jimbot.core.ExtendPoint;
import ru.jimbot.core.MainProps;
import ru.jimbot.core.services.AbstractProperties;
import ru.jimbot.core.services.IBotServiceBuilder;
import ru.jimbot.http.admin.AbstractView;
import ru.jimbot.http.admin.HttpProps;
import ru.jimbot.http.admin.PropertyFormBuilder;
import ru.jimbot.http.admin.internal.Activator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

/**
 * @author spec
 *
 */
public class EditPropertyView extends AbstractView<VerticalLayout> {
	private String fragment;

	public EditPropertyView(String fragment) {
		super(new VerticalLayout());
		this.fragment = fragment;
		getContent().setMargin(true);
		for(AbstractProperties p : Activator.getProps()) {
			Form f = new PropertyFormBuilder(p).build();
			getContent().addComponent(f);
			getContent().addComponent(new Button("Сохранить", new Clicker(p, f)));
		}
	}

	@Override
	public void activated(Object... params) {
		// TODO Auto-generated method stub
		
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

	private class Clicker implements Button.ClickListener {
		private AbstractProperties props;
		private Form form;
		
		public Clicker(AbstractProperties p, Form f) {
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
}
