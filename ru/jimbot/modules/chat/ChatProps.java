/**
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2009 JimBot project
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

package ru.jimbot.modules.chat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

import ru.jimbot.core.AbstractProps;
import ru.jimbot.table.UserPreference;
import ru.jimbot.util.Log;

/**
 *
 * @author Prolubnikov Dmitry
 */
public class ChatProps implements AbstractProps {
	public static HashMap<String,ChatProps> props = new HashMap<String,ChatProps>();
    public String PROPS_FILE = "";
    private String PROPS_FOLDER = "";
    public String ENCODING = "windows-1251";
    public Properties appProps;
    public Properties langProps;
    public boolean isLoaded = false;
    
    /** Creates a new instance of ChatProps */
    public ChatProps() {
    }
    
    public static ChatProps getInstance(String name){
    	if(props.containsKey(name))
    		return props.get(name);
    	else {
    		ChatProps p = new ChatProps();
    		p.PROPS_FILE = "./services/"+name+"/"+name+".xml";
    		p.PROPS_FOLDER = "./services/"+name;
    		p.setDefault();
    		p.load();
    		props.put(name, p);
    		return p;
    	}
    }
    
    public void setDefault() {
        appProps = new Properties();
        
        setIntProperty("conn.uinCount",1);
        setStringProperty("conn.uin0","111");
        setStringProperty("conn.pass0","Password");
        
        setIntProperty("chat.pauseOut",5000);
        setBooleanProperty("chat.IgnoreOfflineMsg",true);
        setIntProperty("chat.TempKick",10); //Временный кик, минут
        setIntProperty("chat.ChangeStatusTime",60000);
        setIntProperty("chat.ChangeStatusCount",5);
        setBooleanProperty("chat.FreeReg",true); //регистрация новых пользователей без ограничений
        setIntProperty("chat.MaxMsgSize",150); //Максимальный размер одного сообщения от пользователя
        setIntProperty("chat.MaxOutMsgSize",500);
        setIntProperty("chat.MaxOutMsgCount",5);
        setIntProperty("icq.status",0/*Icq.STATUS_ONLINE*/);
        setIntProperty("icq.xstatus",0);
//        setIntProperty("icq.statusFlag",0);
        setBooleanProperty("main.StartBot",false);
        setIntProperty("bot.pauseIn",3000); //Пауза входящих сообщений
        setIntProperty("bot.pauseOut",500); //Пауза исходящих сообщений
        setIntProperty("bot.msgOutLimit",20); //Ограничение очереди исходящих сообщений
        setIntProperty("bot.pauseRestart",11*60*1000); //Пауза перед запуском упавшего коннекта
        setStringProperty("bot.adminUIN","111111;222222");
        setIntProperty("chat.autoKickTime",60);
        setIntProperty("chat.autoKickTimeWarn",58);
        setIntProperty("icq.AUTORETRY_COUNT",5);
        setStringProperty("icq.STATUS_MESSAGE1","");
        setStringProperty("icq.STATUS_MESSAGE2","");
        setBooleanProperty("chat.ignoreMyMessage", true);
        setBooleanProperty("chat.isAuthRequest", false);
        setStringProperty("chat.badNicks","admin;админ");
        setIntProperty("chat.defaultKickTime",5);
        setIntProperty("chat.maxKickTime",300);
        setIntProperty("chat.maxNickLenght",10);
        setBooleanProperty("chat.showChangeUserStatus",true);
        setBooleanProperty("chat.writeInMsgs",false);
        setBooleanProperty("chat.writeAllMsgs",true);
        setBooleanProperty("adm.useAdmin",true);
        setBooleanProperty("adm.useMatFilter",true);
        setBooleanProperty("adm.useSayAdmin",true);
        setStringProperty("adm.matString","бля;хуй;хуя;хуе;хуё;хуи;хули;пизд;сук;суч;ублюд;сволоч;гандон;ебат;ебет;ибат;ебан;ебал;ибал;пидар;пидор;залуп;муда;муди");
        setStringProperty("adm.noMatString","рубл;нибал;абля;обля;оскорбля;шибал;гибал;хулига;требля;скреба;скребе;страх;стеб;хлеб;скипидар;любля;барсук");
        setIntProperty("adm.getStatTimeout",15);
        setIntProperty("adm.maxSayAdminCount",5);
        setIntProperty("adm.maxSayAdminTimeout",10);
        setIntProperty("adm.sayAloneTime",15);
        setIntProperty("adm.sayAloneProbability",20);
        setStringProperty("auth.groups","user;poweruser;moder;admin");
        setStringProperty("auth.group_user","pmsg;reg;invite;adminsay;adminstat;room;anyroom");
        setStringProperty("auth.group_poweruser","pmsg;reg;invite;adminsay;adminstat;room;anyroom");
        setStringProperty("auth.group_moder","pmsg;reg;invite;adminsay;adminstat;kickone;settheme;exthelp;whouser;room;dblnick;anyroom;wroom");
        setStringProperty("auth.group_admin","pmsg;reg;invite;adminsay;adminstat;kickone;kickall;ban;settheme;info;exthelp;authread;whouser;room;kickhist;whoinv;chgkick;dblnick;anyroom;wroom");
//        setBooleanProperty("main.checkNewVer",true);
//        setStringProperty("main.newVerInfo","455227096");
//        setStringProperty("main.newVerReport","111");
        setIntProperty("chat.MaxInviteTime",24);
        setBooleanProperty("chat.NoDelContactList",false);
        setIntProperty("chat.maxUserOnUin",7);
        setStringProperty("chat.badSymNicks","");
        setStringProperty("chat.goodSymNicks","");
        setStringProperty("chat.delimiter",":");
        setStringProperty("chat.inviteDescription","Для регистрации в чате вам необходимо получить приглашение одного из пользователей.");
        setIntProperty("chat.floodCountLimit",5);
        setIntProperty("chat.floodTimeLimit",10);
        setIntProperty("chat.floodTimeLimitNoReg",20);
        setStringProperty("db.host","localhost:3306");
        setStringProperty("db.user","root");
        setStringProperty("db.pass","");
        setStringProperty("db.dbname","botdb");
        setBooleanProperty("chat.useCaptcha", false);
        setBooleanProperty("chat.isUniqueNick", false);
        setIntProperty("chat.maxNickChanged",99);
        setBooleanProperty("chat.isShowKickReason", false);
    }

    public UserPreference[] getUserPreference(){
        UserPreference[] p = {
            new UserPreference(UserPreference.CATEGORY_TYPE,"main", "Основные настройки",""),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"main.StartBot","Запускать чат-бот",getBooleanProperty("main.StartBot")),
//            new UserPreference(UserPreference.BOOLEAN_TYPE,"main.checkNewVer","Запрашивать новую версию чата",getBooleanProperty("main.checkNewVer")),
//            new UserPreference(UserPreference.STRING_TYPE,"main.newVerInfo","Где запрашивать",getStringProperty("main.newVerInfo")),
//            new UserPreference(UserPreference.STRING_TYPE,"main.newVerReport","UIN который извещать о новой версии",getStringProperty("main.newVerReport")),
            new UserPreference(UserPreference.CATEGORY_TYPE,"bot", "Настройки бота",""),
            new UserPreference(UserPreference.INTEGER_TYPE,"icq.status","ICQ статус",getIntProperty("icq.status")),
//            new UserPreference(UserPreference.INTEGER_TYPE,"icq.statusFlag","Дополнительный статус",getIntProperty("icq.statusFlag")),
            new UserPreference(UserPreference.INTEGER_TYPE,"icq.xstatus","x-статус (0-34)",getIntProperty("icq.xstatus")),
            new UserPreference(UserPreference.STRING_TYPE,"icq.STATUS_MESSAGE1","Сообщение x-статуса 1",getStringProperty("icq.STATUS_MESSAGE1")),
            new UserPreference(UserPreference.STRING_TYPE,"icq.STATUS_MESSAGE2","Сообщение x-статуса 2",getStringProperty("icq.STATUS_MESSAGE2")),
            new UserPreference(UserPreference.INTEGER_TYPE,"icq.AUTORETRY_COUNT","Число переподключений движка при обрыве",getIntProperty("icq.AUTORETRY_COUNT")),
            new UserPreference(UserPreference.INTEGER_TYPE,"bot.pauseIn","Пауза для входящих сообщений",getIntProperty("bot.pauseIn")),
            new UserPreference(UserPreference.INTEGER_TYPE,"bot.pauseOut","Пауза для исходящих сообщений",getIntProperty("bot.pauseOut")),
            new UserPreference(UserPreference.INTEGER_TYPE,"bot.msgOutLimit","Ограничение очереди исходящих",getIntProperty("bot.msgOutLimit")),
            new UserPreference(UserPreference.INTEGER_TYPE,"bot.pauseRestart","Пауза перед перезапуском коннекта",getIntProperty("bot.pauseRestart")),
            new UserPreference(UserPreference.STRING_TYPE,"bot.adminUIN","Админские UIN",getStringProperty("bot.adminUIN")),
            new UserPreference(UserPreference.CATEGORY_TYPE,"chat", "Настройки чата",""),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.floodCountLimit","Число повторов флуда",getIntProperty("chat.floodCountLimit")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.floodTimeLimit","Период флуда (сек)",getIntProperty("chat.floodTimeLimit")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.floodTimeLimitNoReg","Пауза сообщений для незареганых (сек)",getIntProperty("chat.floodTimeLimitNoReg")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.pauseOut","Задержка очереди чата",getIntProperty("chat.pauseOut")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"chat.IgnoreOfflineMsg","Игнорировать оффлайн сообщения",getBooleanProperty("chat.IgnoreOfflineMsg")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"chat.ignoreMyMessage","Игнорировать собственные сообщения в чате",getBooleanProperty("chat.ignoreMyMessage")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.TempKick","Временный кик (минут)",getIntProperty("chat.TempKick")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.ChangeStatusTime","Период переподключения юзера",getIntProperty("chat.ChangeStatusTime")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.ChangeStatusCount","Количество переподключений для блокировки юзера",getIntProperty("chat.ChangeStatusCount")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"chat.FreeReg","Свободная регистрация",getBooleanProperty("chat.FreeReg")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"chat.useCaptcha","Использовать CAPTCHA при регистрации",getBooleanProperty("chat.useCaptcha")),
            new UserPreference(UserPreference.STRING_TYPE,"chat.inviteDescription","Пояснения по поводу приглашений в чат",getStringProperty("chat.inviteDescription")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.MaxInviteTime","Время действия приглашения (часов)",getIntProperty("chat.MaxInviteTime")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.MaxMsgSize","Максимальный размер одного сообщения",getIntProperty("chat.MaxMsgSize")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.MaxOutMsgSize","Максимальный размер одного исходящего сообщения",getIntProperty("chat.MaxOutMsgSize")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.MaxOutMsgCount","Максимальное число частей исходящего сообщения",getIntProperty("chat.MaxOutMsgCount")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.autoKickTime","Время автокика при молчании (минут)",getIntProperty("chat.autoKickTime")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.autoKickTimeWarn","Время предупреждения перед автокиком",getIntProperty("chat.autoKickTimeWarn")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"chat.isAuthRequest","Запрашивать авторизацию у пользователей",getBooleanProperty("chat.isAuthRequest")),
            new UserPreference(UserPreference.STRING_TYPE,"chat.badNicks","Запрещенные ники",getStringProperty("chat.badNicks")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.maxNickChanged","Число смен ника за сутки",getIntProperty("chat.maxNickChanged")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.defaultKickTime","Время кика по умолчанию",getIntProperty("chat.defaultKickTime")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.maxKickTime","Максимальное время кика",getIntProperty("chat.maxKickTime")),
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.maxNickLenght","Максимальная длина ника в чате",getIntProperty("chat.maxNickLenght")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"chat.isUniqueNick","Уникальные ники в чате",getBooleanProperty("chat.isUniqueNick")),            
            new UserPreference(UserPreference.BOOLEAN_TYPE,"chat.showChangeUserStatus","Показывать вход-выход при падении юзеров",getBooleanProperty("chat.showChangeUserStatus")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"chat.writeInMsgs","Записывать все входящие сообщения в БД",getBooleanProperty("chat.writeInMsgs")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"chat.writeAllMsgs","Записывать сообщения в БД (отключит статистику и т.п.)",getBooleanProperty("chat.writeAllMsgs")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"chat.NoDelContactList","Не очищать контакт-лист",getBooleanProperty("chat.NoDelContactList")),            
            new UserPreference(UserPreference.INTEGER_TYPE,"chat.maxUserOnUin","Максимум юзеров на 1 уин",getIntProperty("chat.maxUserOnUin")),
            new UserPreference(UserPreference.STRING_TYPE,"chat.badSymNicks","Запрещенные символы в никах",getStringProperty("chat.badSymNicks")),
            new UserPreference(UserPreference.STRING_TYPE,"chat.goodSymNicks","Разрешенные символы в никах",getStringProperty("chat.goodSymNicks")),
            new UserPreference(UserPreference.STRING_TYPE,"chat.delimiter","Разделитель после ника",getStringProperty("chat.delimiter")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"chat.isShowKickReason","Выводить нарушителю причину кика",getBooleanProperty("chat.isShowKickReason")),
            new UserPreference(UserPreference.CATEGORY_TYPE,"adm", "Настройки Админа",""),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"adm.useAdmin","Использовать Админа в чате",getBooleanProperty("adm.useAdmin")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"adm.useMatFilter","Разрешить реакцию на мат",getBooleanProperty("adm.useMatFilter")),
            new UserPreference(UserPreference.BOOLEAN_TYPE,"adm.useSayAdmin","Разрешить админу разговаривать",getBooleanProperty("adm.useSayAdmin")),
            new UserPreference(UserPreference.STRING_TYPE,"adm.matString","Слова для мата",getStringProperty("adm.matString")),
            new UserPreference(UserPreference.STRING_TYPE,"adm.noMatString","Слова исключения",getStringProperty("adm.noMatString")),
            new UserPreference(UserPreference.INTEGER_TYPE,"adm.getStatTimeout","Пауза между показами статистики",getIntProperty("adm.getStatTimeout")),
            new UserPreference(UserPreference.INTEGER_TYPE,"adm.maxSayAdminCount","Максимум обращений к админу для одного человека",getIntProperty("adm.maxSayAdminCount")),
            new UserPreference(UserPreference.INTEGER_TYPE,"adm.maxSayAdminTimeout","Время сброса статистики обращений",getIntProperty("adm.maxSayAdminTimeout")),
            new UserPreference(UserPreference.INTEGER_TYPE,"adm.sayAloneTime","Время молчания, через которое админ заговорит",getIntProperty("adm.sayAloneTime")),
            new UserPreference(UserPreference.INTEGER_TYPE,"adm.sayAloneProbability","Вероятность разговора админа в тишине (1 к ...)",getIntProperty("adm.sayAloneProbability")),
//            new UserPreference(UserPreference.CATEGORY_TYPE,"auth", "Группы пользователей",""),
//            new UserPreference(UserPreference.STRING_TYPE,"auth.groups","Группы пользователей",getStringProperty("auth.groups")),
//            new UserPreference(UserPreference.STRING_TYPE,"auth.group_user","Полномочия пользователей",getStringProperty("auth.group_user")),
//            new UserPreference(UserPreference.STRING_TYPE,"auth.group_poweruser","Полномочия продвинутых пользователей",getStringProperty("auth.group_poweruser")),
//            new UserPreference(UserPreference.STRING_TYPE,"auth.group_moder","Полномочия модераторов",getStringProperty("auth.group_moder")),
//            new UserPreference(UserPreference.STRING_TYPE,"auth.group_admin","Полномочия админов",getStringProperty("auth.group_admin")),
            new UserPreference(UserPreference.CATEGORY_TYPE,"db", "Настройки mySQL",""),
            new UserPreference(UserPreference.STRING_TYPE,"db.host","Хост БД",getStringProperty("db.host")),
            new UserPreference(UserPreference.STRING_TYPE,"db.user","Пользователь",getStringProperty("db.user")),
            new UserPreference(UserPreference.PASS_TYPE,"db.pass","Пароль",getStringProperty("db.pass")),
            new UserPreference(UserPreference.STRING_TYPE,"db.dbname","Название базы данных",getStringProperty("db.dbname"))
        };
        return p;
    }

    public UserPreference[] getUINPreference(){
        UserPreference[] p = new UserPreference[uinCount()*2+1];
        p[0] = new UserPreference(UserPreference.CATEGORY_TYPE,"conn", "Настройки UINов для подключения","");
        for(int i=0;i<uinCount();i++){
            p[i*2+1] = new UserPreference(UserPreference.STRING_TYPE,"conn.uin" + i,"UIN" + i,getProperty("conn.uin" + i,""));
            p[i*2+2] = new UserPreference(UserPreference.PASS_TYPE,"conn.pass" + i,"Password" + i,getProperty("conn.pass" + i, ""));
        }
        return p;
    }
    
    public boolean isAutoStart(){
    	return getBooleanProperty("main.StartBot");
    }
    
    public boolean testAdmin(String uin) {
        if(uin.equals("0")) return true; //Выртуальный админ
        String s = getStringProperty("bot.adminUIN");
        if(s.equals("")) return false;
        String[] ss = s.split(";");
        try{
            for(int i=0;i<ss.length;i++){
                if(ss[i].equalsIgnoreCase(uin)) return true;
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    public String[] getAdmins(){
    	return getStringProperty("bot.adminUIN").split(";");
    }
    
    public String getChatRules(){
        return loadText("./text/rules.txt");
    }
    
    public String getHelp1(){
        return loadText("./text/help1.txt");
    }
    
    public String getHelp2(){
        return loadText("./text/help2.txt");
    }

    public String loadText(String fname){
        String s = "";
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(fname),"windows-1251"));
            while (r.ready()) {
                s += r.readLine() + "\n";
            }
            r.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }

    public final void load() {
        File file = new File(PROPS_FILE);
        setDefault();
        try {
            FileInputStream fi = new FileInputStream(file);
//            appProps.load(fi);
            appProps.loadFromXML(fi);
            fi.close();
            Log.info("Load preferences ok");
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.error("Error opening preferences: ");
        }
    }
    
    public final void save() {
        File file = new File(PROPS_FILE);
        File dir = new File(this.PROPS_FOLDER);
        try {
        	if(!dir.exists())
        		dir.mkdirs();
            FileOutputStream fo = new FileOutputStream(file);
//            appProps.store(fo,"jImBot properties");
            appProps.storeToXML(fo, "jImBot properties");
            fo.close();
            Log.info("Save preferences ok");
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.error("Error saving preferences: ");
        }
    }
    
    public int uinCount(){
        return getIntProperty("conn.uinCount");
    }
    
    public String getUin(int i){
        return getStringProperty("conn.uin"+i);
    }
    
    public String getPass(int i){
        return getStringProperty("conn.pass"+i);
    }    
    
    /**
     * Изменение уина
     * @param i
     * @param uin
     * @param pass
     */
    public void setUin(int i, String uin, String pass){
    	setStringProperty("conn.uin"+i, uin);
    	if(!pass.equals("")) setStringProperty("conn.pass"+i, pass);
    }
    
    /**
     * Добавление нового уина в настройки
     * @param uin - уин
     * @param pass - пароль
     * @return - порядковый номер нового уина
     */
    public int addUin(String uin, String pass){
    	int c = uinCount();
    	setIntProperty("conn.uinCount", c+1);
    	setStringProperty("conn.uin"+c, uin);
    	setStringProperty("conn.pass"+c, pass);
    	return c;
    }
    
    /**
     * Удаление уина из настроек
     * @param c
     */
    public void delUin(int c) {
    	// Сдвигаем элементы после удаленного
    	for(int i=0; i<(uinCount()-1); i++){
    		if(i>=c){
    			setStringProperty("conn.uin"+i, getUin(i+1));
    			setStringProperty("conn.pass"+i, getPass(i+1));
    		}
    	}
    	//Удаляем самый последний элемент
    	appProps.remove("conn.uin"+(uinCount()-1));
    	appProps.remove("conn.pass"+(uinCount()-1));
    	setIntProperty("conn.uinCount", uinCount()-1);
    }
    
    public void registerProperties(Properties _appProps) {
        appProps = _appProps;
    }
    
    public String getProperty(String key) {
        return appProps.getProperty(key);
    }
    
    public String getStringProperty(String key) {
        return appProps.getProperty(key);
    }
    
    public String getProperty(String key, String def) {
        return appProps.getProperty(key,def);
    }
    
    public void setProperty(String key, String val) {
        appProps.setProperty(key,val);
    }
    
    public void setStringProperty(String key, String val) {
        appProps.setProperty(key,val);
    }
    
    public void setIntProperty(String key, int val) {
        appProps.setProperty(key,Integer.toString(val));
    }
    
    public void setBooleanProperty(String key, boolean val) {
        appProps.setProperty(key, val ? "true":"false");
    }
    
    public int getIntProperty(String key) {
        return Integer.parseInt(appProps.getProperty(key));
    }
    
    public boolean getBooleanProperty(String key) {
        return Boolean.valueOf(appProps.getProperty(key)).booleanValue();
    }

	public Properties getProps() {
		return appProps;
	}
}
