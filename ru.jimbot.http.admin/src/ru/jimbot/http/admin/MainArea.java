/**
 * 
 */
package ru.jimbot.http.admin;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 * @author spec
 *
 */
public class MainArea extends AbstractView<Panel> implements ViewContainer {

    private static final long serialVersionUID = 9010669373711637452L;

    private View currentView;

    public MainArea() {
        super(new Panel());
        getContent().setSizeFull();
//        getContent().addComponent(ViewHandler.getUriFragmentUtil());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activated(Object... params) {
        // Do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactivated(Object... params) {
        // Do nothing
    }

    /**
     * {@inheritDoc}
     */
    public void activate(View view) {
        if (currentView == null) {
        	view.activated(null);
            getContent().addComponent((Component) view);
        } else {
        	currentView.deactivated(null);
        	view.activated(null);
            getContent().replaceComponent((Component) currentView,
                    (Component) view);
        }
        currentView = view;
    }

    /**
     * {@inheritDoc}
     */
    public void deactivate(View view) {
        if (currentView != null) {
            getContent().removeComponent((Component) view);
        }
        view.deactivated(null);
        currentView = null;
    }

    @Override
    public String getFragment() {
    	return null;
    }
}