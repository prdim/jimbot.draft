/**
 * 
 */
package ru.jimbot.http.admin.views;

import ru.jimbot.http.admin.AbstractView;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author spec
 *
 */
public class EmptyView extends AbstractView<VerticalLayout> {
	private String fragment;
	private static final long serialVersionUID = -3211990479652125650L;

	public EmptyView(String fragment, String t) {
        super(new VerticalLayout());
        this.fragment = fragment;
        Label l = new Label(t);
        getContent().addComponent(l);
    }

	@Override
	public void activated(Object... params) {
		// TODO Auto-generated method stub
		System.out.println("!!!");
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

}