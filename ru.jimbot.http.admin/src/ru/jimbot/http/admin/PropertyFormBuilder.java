/**
 * 
 */
package ru.jimbot.http.admin;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.PasswordField;

import ru.jimbot.core.services.AbstractProperties;

/**
 * @author spec
 *
 */
public class PropertyFormBuilder{
	private AbstractProperties props;
	
	public PropertyFormBuilder(AbstractProperties props) {
		this.props = props;
	}
	
	public Form build() {
		Form form = new Form();
		form.setCaption(props.getExtendInfo());
		form.setWriteThrough(false); // we want explicit 'apply'
        form.setInvalidCommitted(false); // no invalid values in datamodel
        BeanItem<AbstractProperties> propsItems = new BeanItem<AbstractProperties>(props);
        form.setFormFieldFactory(new PropertiesFieldFactory());
        form.setItemDataSource(propsItems);
        PropertyDescriptor[] pr = PropertyUtils.getPropertyDescriptors(props);
        List<String> pl = new ArrayList<String>();
        for(PropertyDescriptor i : pr) {
        	pl.add(i.getName());
        }
        form.setVisibleItemProperties(pl);
        
		return form;
	}
	
	private class PropertiesFieldFactory extends DefaultFieldFactory {
		private Map<String, PropertyDescriptor> mp;
		
		public PropertiesFieldFactory() {
			mp = new HashMap<String, PropertyDescriptor>();
			PropertyDescriptor[] pr = PropertyUtils.getPropertyDescriptors(props);
			for(PropertyDescriptor i : pr) {
				mp.put(i.getName(), i);
			}
		}

		/* (non-Javadoc)
		 * @see com.vaadin.ui.DefaultFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
		 */
		@Override
		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			Field f;
			if(propertyId.toString().toLowerCase().indexOf("password")>0) {
				f = createPasswordField(propertyId);
			} else {
//				f = super.createField(item, propertyId, uiContext);
				Class<?> type = item.getItemProperty(propertyId).getType();
				f = createFieldByPropertyType(type);
				f.setCaption(mp.get(propertyId).getDisplayName());
			}
			return f;
		}
		
		private PasswordField createPasswordField(Object propertyId) {
            PasswordField pf = new PasswordField();
//            pf.setCaption(DefaultFieldFactory
//                    .createCaptionByPropertyId(propertyId));
            pf.setCaption(mp.get(propertyId).getDisplayName());
            return pf;
        }
	}
}
