/*
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2010 JimBot project
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.Test;
import ru.jimbot.ConfigModule;
import ru.jimbot.MainConfig;
import ru.jimbot.core.Password;
import ru.jimbot.util.FileUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @author Prolubnikov Dmitry
 */
public class Test2 {

//    @Test
//    public void testGuice() {
//        Module cfgMod = new ConfigModule();
//        Injector injector = Guice.createInjector(cfgMod);
//        MainConfig cfg = injector.getInstance(MainConfig.class);
//        p(cfg.getHttpPass());
//        p(cfg.isAutoStart());
//        assert true;
//    }

    @Test
    public void testBean() throws IntrospectionException {
        p(MainConfig.getInstance().getHttpPass().getPass());
        p(MainConfig.getInstance().isAutoStart());
        MainConfig.getInstance().setAutoStart(true);
        MainConfig.getInstance().setHttpPass(new Password("pass"));
//        MainConfig.getInstance().setServiceNames(FileUtils.addItem(MainConfig.getInstance().getServiceNames()));
//        MainConfig.getInstance().setServiceTypes(FileUtils.addItem(MainConfig.getInstance().getServiceTypes()));
        MainConfig.getInstance().save();
        BeanInfo beanInfo = Introspector.getBeanInfo(MainConfig.class);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor p : pds) {
            p(p.getDisplayName() + " : " + p.getName() + " : " + p.getPropertyType().getName());
        }
        assert true;
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
