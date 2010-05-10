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

package ru.jimbot.modules.anek;

import ru.jimbot.core.api.Service;
import ru.jimbot.modules.anek.db.AdsLogStore;
import ru.jimbot.modules.anek.db.AdsStore;
import ru.jimbot.modules.anek.db.AneksStore;
import ru.jimbot.modules.anek.db.AneksTempStore;
import ru.jimbot.modules.anek.db.DbManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import com.amazon.carbonado.ConfigurationException;
import com.amazon.carbonado.Cursor;
import com.amazon.carbonado.PersistException;
import com.amazon.carbonado.RepositoryException;
import com.amazon.carbonado.Storage;
import com.amazon.carbonado.SupportException;


/**
 * Работа с анекдотами
 * 
 * @author Prolubnikov Dmitry
 */
public class AnekWork {
//    public int maxAnek=0;
//    public DBAneks db;
    private Random r = new Random();
    private Vector<Long> adsKey; //= new Vector<Integer>();
    private Vector<Long> aneksKey;
//    private String serviceName = "";
//    private String host, name, user, pass;
    private Service srv;
    private DbManager db_log;
    private DbManager db_aneks;
    
    /** Creates a new instance of AnekWork */
    public AnekWork(String name, Service srv) {
//    	serviceName = name;
        this.srv = srv;
//    	host = ((AnekConfig)srv.getConfig()).getDb().getHost();
//    	this.name = ((AnekConfig)srv.getConfig()).getDb().getBase();
//    	user = ((AnekConfig)srv.getConfig()).getDb().getUser();
//    	pass = ((AnekConfig)srv.getConfig()).getDb().getPass().getPass();
    }
    
    public void initDB() throws ConfigurationException, RepositoryException {
    	db_aneks = new DbManager("aneks", new File("./services/" + srv.getName() + "/db/aneks"));
        db_log = new DbManager("ads_log", new File("./services/" + srv.getName() + "/db/ads_log"));
        readKey();
    }
    
    public void closeDB(){
    	db_aneks.close();
    	db_log.close();
    }
    
    /**
     * Обновление данных при изменении БД
     */
    public void refreshData(){
    	readKey();
    }
    
    /**
     * Читает ключи к активным рекламным объявлениям
     */
    private void readKey(){
    	adsKey = new Vector<Long>();
    	aneksKey = new Vector<Long>();
    	try {
			Storage<AdsStore> str1 = db_aneks.getRepository().storageFor(AdsStore.class);
			Cursor<AdsStore> c = str1.query("enable=?").with(true).fetch();
			while(c.hasNext()) {
				adsKey.add(c.next().getId());
			}
//			System.out.println(db_aneks.getRepository().storageFor(AneksStore.class).query().count());
			Cursor<AneksStore> k = db_aneks.getRepository().storageFor(AneksStore.class).query().fetch();
			while(k.hasNext()) {
				AneksStore s = k.next();
				aneksKey.add(s.getId());
			}
		} catch (SupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    }
    
    public String getAds(long id){
    	String s = "";
    	try {
    		s = db_aneks.getRepository().storageFor(AdsStore.class).query("id=?").with(id).loadOne().getTxt();
    		addLogAds(id);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return s;
    }
    
    public void addLogAds(long id){
    	try{
            AdsLogStore t = db_log.getRepository().storageFor(AdsLogStore.class).prepare();
            t.setId(System.currentTimeMillis());
            t.setAdsId(id);
            t.setUin("");
            if(!t.tryInsert())
            	t.update();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    /**
     * Статистика показа рекламы за сутки
     * @return
     */
    public String adsStat(){
    	String s = "Статистика показа рекламы:\n";
    	HashMap<Long, Inc> m = new HashMap<Long, Inc>();
    	try {
    		Cursor<AdsLogStore> c = db_log.getRepository()
    			.storageFor(AdsLogStore.class)
    			.query("id>=?").with(System.currentTimeMillis()-24*3600*1000).fetch();
    		while(c.hasNext()) {
    			long i = c.next().getAdsId();
    			if(m.containsKey(i))
    				m.get(i).inc();
    			else
    				m.put(i, new Inc());
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	for(long i : m.keySet()) {
    		s += i + " - " + m.get(i).getData() + "\n";
    	}
        return s;
    }
    
    /**
     * Возвращает случайное объявление, или ""
     * @return
     */
    public String getAds(){
    	if(!((AnekConfig)srv.getConfig()).isUseAds())
    		return "";
        if(!testRnd(((AnekConfig)srv.getConfig()).getAdsRate()))
            return "";
    	String s = getAds(adsKey.get(r.nextInt(adsKey.size())));
    	if(s.equals(""))
    		return "";
    	else
    		return "\n***\n" + s;
    }

    public String getAdsForStatus() {
        if(!((AnekConfig)srv.getConfig()).isUseAds())
    		return "";
    	String s = getAds(adsKey.get(r.nextInt(adsKey.size())));
        return s;
    }
    
    /**
     * Событие с вероятностью 1/i
     */
    public boolean testRnd(int i){
        if(i<1)
            return false;
        else if(i==1)
            return true;
        else
            return r.nextInt(i)==1;
    }
    
    public String getAnek(long id) {
        String s = "";
        if(!aneksKey.contains(id)) return "Нет такого анека";
        try {
    		s = db_aneks.getRepository().storageFor(AneksStore.class).query("id=?").with(id).loadOne().getText();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
        return "Анекдот №" + id + "\n" + s + getAds();
    }
    
    public int count() {
        return aneksKey.size();
    }
    
    public int adsCount() {
    	return adsKey.size();
    }
    
    public String adsList() {
    	String s = "";
    	for(long i : adsKey) {
    		try {
    			AdsStore t = db_aneks.getRepository().storageFor(AdsStore.class).query("id=?").with(i).loadOne();
    			s += "[" + i + ", " + t.getClientId() + ", " + t.getNote() + ", " + 
    				new SimpleDateFormat("dd.MM.yyyy").format(new Date(t.getExpDate())) +
    				"] - " + t.getTxt() + "\n";
    		} catch (Exception e) {
    			e.printStackTrace();
    			s += e.getMessage();
    		}
    	}
    	return s;
    }
    
    public boolean delAds(long id) {
    	boolean f = false;
    	if(!adsKey.contains(id)) return false;
    	try {
    		AdsStore t = db_aneks.getRepository().storageFor(AdsStore.class).query("id=?").with(id).loadOne();
    		t.setEnable(false);
    		t.update();
    		adsKey.remove(id);
    		f = true;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return f;
    }
    
    public long addAds(String txt, String note, String uin) {
    	long r = -1;
    	try {
    		AdsStore t = db_aneks.getRepository().storageFor(AdsStore.class).prepare();
    		t.setTxt(txt);
    		t.setClientId(uin);
    		t.setEnable(true);
    		t.setExpDate(System.currentTimeMillis() + 7*24*3600*1000);
    		t.setMaxCount(0);
    		t.setNote(note);
    		t.insert();
    		r = t.getId();
    		adsKey.add(r);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return r;
    }
    
    public boolean extendAds(long id) {
    	boolean f = false;
    	if(!adsKey.contains(id)) return false;
    	try {
    		AdsStore t = db_aneks.getRepository().storageFor(AdsStore.class).query("id=?").with(id).loadOne();
    		t.setExpDate(t.getExpDate() + 7*24*3600*1000);
    		t.update();
    		f = true;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return f;
    }
    
    public String getAnek(){
        return getAnek(aneksKey.get(r.nextInt(aneksKey.size())));
    }
    
    public void addAnek(String s) throws SupportException, RepositoryException {
    	AneksStore t = db_aneks.getRepository().storageFor(AneksStore.class).prepare();
    	t.setText(s);
    	t.insert();
    }
    
    /**
     * Добавление анекдота во временную таблицу
     * @param s
     * @throws RepositoryException 
     * @throws SupportException 
     */
    public void addTempAnek(String s, String uin) throws SupportException, RepositoryException{
    	AneksTempStore t = db_aneks.getRepository().storageFor(AneksTempStore.class).prepare();
    	t.setId(System.currentTimeMillis());
    	t.setText(s);
    	t.setUin(uin);
    	t.insert();
    }
    
    private class Inc {
    	int i = 1;
    	
    	public void inc() {
    		i++;
    	}
    	
    	public int getData() {
    		return i;
    	}
    }
}
