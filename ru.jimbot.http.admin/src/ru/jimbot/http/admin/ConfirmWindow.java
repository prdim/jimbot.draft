/**
 * 
 */
package ru.jimbot.http.admin;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

/**
 * @author spec
 *
 */
public abstract class ConfirmWindow extends Window {
	private VerticalLayout v;
	private Window me;
	
	public ConfirmWindow(String s) {
		super();
		me = this;
		setModal(true);
		setCaption("Подтверждение");
		v = (VerticalLayout)me.getContent();
		v.setMargin(true);
		v.setSpacing(true);
		v.setSizeUndefined();
		Label l = new Label(s);
		l.setContentMode(Label.CONTENT_PREFORMATTED);
		addComponent(l);
		HorizontalLayout h = new HorizontalLayout();
		Button ok = new Button("Ok", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				confirm();
				((Window)me.getParent()).removeWindow(me);
			}
		});
		Button cancel = new Button("Cancel", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				((Window)me.getParent()).removeWindow(me);
			}
		});
		h.addComponent(ok);
		h.addComponent(cancel);
		addComponent(h);
		v.setComponentAlignment(h, Alignment.BOTTOM_RIGHT);
		this.center();
	}
	
	public abstract void confirm();
}
