/**
 * 
 */
package ru.jimbot.anekbot.db;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import com.amazon.carbonado.Cursor;
import com.amazon.carbonado.RepositoryException;
import com.amazon.carbonado.Storage;
import com.amazon.carbonado.SupportException;

import ru.jimbot.anekbot.AdsBean;
import ru.jimbot.anekbot.AneksBean;
import ru.jimbot.anekbot.IAnekBotDB;
import ru.jimbot.core.exceptions.DbException;
import ru.jimbot.modules.anek.db.AdsLogStore;
import ru.jimbot.modules.anek.db.AdsStore;
import ru.jimbot.modules.anek.db.AneksStore;
import ru.jimbot.modules.anek.db.AneksTempStore;

/**
 * @author spec
 *
 */
public class AnekDB implements IAnekBotDB {
	private String name = "";
	private Random r = new Random();
    private List<Long> adsKey;
    private List<Long> aneksKey;
    private DbManager db_log;
    private DbManager db_aneks;
    private Map<String, AnekDB> dbs = new HashMap<String, AnekDB>();
	
	/**
	 * @param name
	 */
	public AnekDB(String name) {
		super();
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#initDB()
	 */
	@Override
	public IAnekBotDB initDB(String name) throws DbException {
		if(this.name.equals(name)) {
			initDB();
			return this;
		}
		if(dbs.containsKey(name)) return dbs.get(name);
		AnekDB t = new AnekDB(name);
		t.initDB();
		dbs.put(name, t);
		return t;
	}
	
	public void initDB() throws DbException {
		try {
			db_aneks = new DbManager("aneks", new File("./services/" + name + "/db/aneks"));
			db_log = new DbManager("ads_log", new File("./services/" + name + "/db/ads_log"));
			readKey();
		} catch (Exception e) {
			throw new DbException(e.getMessage(), e);
		}
	}
	
	/**
     * Читает ключи к активным рекламным объявлениям
	 * @throws RepositoryException 
	 * @throws SupportException 
     */
	private void readKey() throws SupportException, RepositoryException {
		adsKey = new ArrayList<Long>();
		aneksKey = new ArrayList<Long>();
		Storage<AdsStore> str1 = db_aneks.getRepository().storageFor(
				AdsStore.class);
		Cursor<AdsStore> c = str1.query("enable=?").with(true).fetch();
		while (c.hasNext()) {
			adsKey.add(c.next().getId());
		}
		Cursor<AneksStore> k = db_aneks.getRepository()
				.storageFor(AneksStore.class).query().fetch();
		while (k.hasNext()) {
			AneksStore s = k.next();
			aneksKey.add(s.getId());
		}
		System.out.println("-->" + aneksKey.size());
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#closeDB()
	 */
	@Override
	public void closeDB() {
		db_aneks.close();
    	db_log.close();
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#getAds(long)
	 */
	@Override
	public String getAds(long id) throws DbException {
		String s = "";
    	try {
    		s = db_aneks.getRepository().storageFor(AdsStore.class).query("id=?").with(id).loadOne().getTxt();
    		addLogAds(id);
    	} catch (Exception e) {
    		throw new DbException(e.getMessage(), e);
    	}
    	return s;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#addLogAds(long)
	 */
	@Override
	public void addLogAds(long id) throws DbException {
		try{
            AdsLogStore t = db_log.getRepository().storageFor(AdsLogStore.class).prepare();
            t.setId(System.currentTimeMillis());
            t.setAdsId(id);
            t.setUin("");
            if(!t.tryInsert())
            	t.update();
        } catch (Exception e){
        	throw new DbException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#adsStat()
	 */
	@Override
	public String adsStat() {
		StringBuffer sb = new StringBuffer("Статистика показа рекламы:\n");
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
    		sb.append(i + " - " + m.get(i).getData() + "\n");
    	}
        return sb.toString();
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#getAds()
	 */
	@Override
	public String getAds(int rate) throws DbException {
//		if(!((AnekConfig)srv.getConfig()).isUseAds())
//    		return "";
        if(!testRnd(rate))
            return "";
    	String s = getAds(adsKey.get(r.nextInt(adsKey.size())));
    	if(s.equals(""))
    		return "";
    	else
    		return "\n***\n" + s;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#getAnek(long)
	 */
	@Override
	public String getAnek(long id) throws DbException {
		String s = "";
        if(!aneksKey.contains(id)) return "Нет такого анека";
        try {
    		s = db_aneks.getRepository().storageFor(AneksStore.class).query("id=?").with(id).loadOne().getText();
    	} catch (Exception e) {
    		throw new DbException(e.getMessage(), e);
    	}
        return "Анекдот №" + id + "\n" + s; //+ getAds();
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#adsList()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#delAds(long)
	 */
	@Override
	public boolean delAds(long id) throws DbException {
		boolean f = false;
    	if(!adsKey.contains(id)) return false;
    	try {
    		AdsStore t = db_aneks.getRepository().storageFor(AdsStore.class).query("id=?").with(id).loadOne();
    		t.setEnable(false);
    		t.update();
    		adsKey.remove(id);
    		f = true;
    	} catch (Exception e) {
    		throw new DbException(e.getMessage(), e);
    	}
    	return f;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#addAds(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public long addAds(String txt, String note, String uin) throws DbException {
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
    		throw new DbException(e.getMessage(), e);
    	}
    	return r;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#extendAds(long, long)
	 */
	@Override
	public boolean extendAds(long id, long time) throws DbException {
		boolean f = false;
    	if(!adsKey.contains(id)) return false;
    	try {
    		AdsStore t = db_aneks.getRepository().storageFor(AdsStore.class).query("id=?").with(id).loadOne();
    		t.setExpDate(t.getExpDate() + time/*7*24*3600*1000*/);
    		t.update();
    		f = true;
    	} catch (Exception e) {
    		throw new DbException(e.getMessage(), e);
    	}
    	return f;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#getAnek()
	 */
	@Override
	public String getAnek() throws DbException {
		return getAnek(aneksKey.get(r.nextInt(aneksKey.size())));
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#addAnek(java.lang.String)
	 */
	@Override
	public void addAnek(String s) throws DbException {
		try {
			AneksStore t = db_aneks.getRepository().storageFor(AneksStore.class).prepare();
	    	t.setText(s);
	    	t.insert();
	    	aneksKey.add(t.getId());
		} catch (Exception e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#addTempAnek(java.lang.String, java.lang.String)
	 */
	@Override
	public void addTempAnek(String s, String uin) throws DbException {
		try {
			AneksTempStore t = db_aneks.getRepository().storageFor(AneksTempStore.class).prepare();
	    	t.setId(System.currentTimeMillis());
	    	t.setText(s);
	    	t.setUin(uin);
	    	t.insert();
		} catch (Exception e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#count()
	 */
	@Override
	public int count() {
		return aneksKey.size();
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#adsCount()
	 */
	@Override
	public int adsCount() {
		return adsKey.size();
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
    
	private class Inc {
    	int i = 1;
    	
    	public void inc() {
    		i++;
    	}
    	
    	public int getData() {
    		return i;
    	}
    }
	
	/*****************************************************************
	 * 
	 */
	
	private AneksBean aneksToBean(AneksStore a) {
		AneksBean b = new AneksBean();
		b.setId(a.getId());
		b.setText(a.getText());
		return b;
	}
	
	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#d_getAneks(long, int)
	 */
	@Override
	public List<AneksBean> d_getAneks(long start, long count) throws DbException {
		List<AneksBean> t = new ArrayList<AneksBean>();
		int i = 0;
		try {
			Cursor<AneksStore> c = db_aneks.getRepository().storageFor(AneksStore.class).query().fetchSlice(start, start + count);
			while (c.hasNext()) {
				t.add(aneksToBean(c.next()));
				if(i++ >= count) break;
			}
		} catch (Exception e) {
			throw new DbException(e.getMessage(), e);
		}
		return t;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#d_saveAnek(ru.jimbot.anekbot.AneksBean)
	 */
	@Override
	public void d_saveAnek(AneksBean a) throws DbException {
		try {
			AneksStore t = db_aneks.getRepository().storageFor(AneksStore.class).prepare();
	    	t.setText(a.getText());
	    	t.setId(a.getId());
	    	t.update();
		} catch (Exception e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#d_removeAnek(ru.jimbot.anekbot.AneksBean)
	 */
	@Override
	public void d_removeAnek(AneksBean a) throws DbException {
		try {
			AneksStore t = db_aneks.getRepository().storageFor(AneksStore.class).prepare();
	    	t.setText(a.getText());
	    	t.setId(a.getId());
	    	t.delete();
		} catch (Exception e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#d_aneksCount()
	 */
	@Override
	public long d_aneksCount(){
		try {
			return db_aneks.getRepository().storageFor(AneksStore.class).query().count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#refreshCash()
	 */
	@Override
	public void refreshCash() {
		try {
			readKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private AdsBean adsToBean(AdsStore a) {
		AdsBean b = new AdsBean();
		b.setId(a.getId());
		b.setClientId(a.getClientId());
		b.setEnable(a.isEnable());
		b.setExpDate(a.getExpDate());
		b.setMaxCount(a.getMaxCount());
		b.setNote(a.getNote());
		b.setTxt(a.getTxt());
		return b;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#d_getAds(long, long)
	 */
	@Override
	public List<AdsBean> d_getAds(long start, long count) throws DbException {
		List<AdsBean> t = new ArrayList<AdsBean>();
		int i = 0;
		try {
			Cursor<AdsStore> c = db_aneks.getRepository().storageFor(AdsStore.class).query().fetchSlice(start, start + count);
			while (c.hasNext()) {
				t.add(adsToBean(c.next()));
				if(i++ >= count) break;
			}
		} catch (Exception e) {
			throw new DbException(e.getMessage(), e);
		}
		return t;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#d_saveAds(ru.jimbot.anekbot.AdsBean)
	 */
	@Override
	public void d_saveAds(AdsBean a) throws DbException {
		try {
			AdsStore t = db_aneks.getRepository().storageFor(AdsStore.class).prepare();
			t.setId(a.getId());
			t.setClientId(a.getClientId());
			t.setEnable(a.isEnable());
			t.setExpDate(a.getExpDate());
			t.setMaxCount(a.getMaxCount());
			t.setNote(a.getNote());
			t.setTxt(a.getTxt());
	    	t.update();
		} catch (Exception e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#d_removeAds(ru.jimbot.anekbot.AdsBean)
	 */
	@Override
	public void d_removeAds(AdsBean a) throws DbException {
		try {
			AdsStore t = db_aneks.getRepository().storageFor(AdsStore.class).prepare();
	    	t.setId(a.getId());
	    	t.delete();
		} catch (Exception e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.anekbot.IAnekBotDB#d_adsCount()
	 */
	@Override
	public long d_adsCount() {
		try {
			return db_aneks.getRepository().storageFor(AdsStore.class).query().count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
