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

package ru.jimbot.modules.anek;

import java.io.File;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import ru.jimbot.modules.anek.commands.*;
import ru.jimbot.modules.MsgStatCounter;
import ru.jimbot.util.MainProps;
import ru.jimbot.core.*;

/**
 *
 * @author Prolubnikov Dmitry
 */
public class AnekCommandParser extends DefaultCommandParser implements QueueListener {
//    public AnekService srv;
    public ConcurrentHashMap <String,StateUin> uq;
    public long state=0; //Статистика запросов
    public long state_add = 0;
//    public HashMap<String,Cmd> commands = new HashMap<String,Cmd>();
//    public CommandParser parser = null;
    private boolean firstStartMsg = false;
    
    /** Creates a new instance of AnekCommandProc
     * @param s
     */
    public AnekCommandParser(AnekService s) {
        super(s);
        uq = new ConcurrentHashMap<String,StateUin>();
        srv.addParserListener(this);
        initCommands();
    }

    /**
     * Создаем реестр всех команд
     */
    public void initCommands() {
        addCommand(new CmdAbout(this));
        addCommand(new CmdHelp(this));
        addCommand(new Cmd1(this));
        addCommand(new CmdAdsstat(this));
        addCommand(new CmdStat(this));
        addCommand(new CmdAnek(this));
        addCommand(new CmdRefresh(this));
        addCommand(new CmdAdd(this));
        addCommand(new CmdFree(this));
        for(Command i:commands.values()) {
            i.init();
        }
    }

    public void onMessage(Message m) {
        parse(m);
    }

    public void parse(Message m) {
        // Игнорируем все лишнее
        if(m.getType()!=Message.TYPE_TEXT) return;
        firstMsg(m);
        addState(m.getSnIn());
        String c = this.getCommand(m);
//        System.out.println(c);
        Command cmd = this.getCommand(c);
        if(cmd==null){
            notify(new Message(m.getSnOut(), m.getSnIn(), "Неверная команда! Для справки отправте !help"));
        } else {
            notify(cmd.exec(m));
        }
    }

    public void notify(Message m) {
        for(QueueListener i:srv.getOutQueueListeners()) {
            i.onMessage(m);
        }
    }

    /**
     * Возвращает список разрешенных полномочий для пользователя с заданным УИНом
     *
     * @param screenName
     * @return
     */
    public Set<String> getAuthList(String screenName) {
        HashSet<String> h = new HashSet<String>();
        if(srv.getProps().testAdmin(screenName)){
            h.add("admin");
        }
        return h;
    }

    private void firstMsg(Message m){
    	if(!firstStartMsg){
    		String[] s = srv.getProps().getAdmins();
    		for(int i=0;i<s.length;i++){
    		    String ss = "Бот успешно запущен!\n";
                if(MainProps.checkNewVersion())
                    ss += "На сайте http://jimbot.ru Доступна новая версия!\n" + MainProps.getNewVerDesc();
                else
                    ss += "Вся информация о боте из первых рук только на сайте: http://jimbot.ru";
                notify(new Message(m.getSnOut(), s[i], ss));
    		}
    		firstStartMsg=true;
    	}
    }

//    public AbstractServer getServer(){
//    	return srv;
//    }
//
//    public void parse(IcqProtocol proc, String uin, String msg) {
//    	firstMsg(proc);
//    	String s = WorkScript.getInstance(srv.getName()).startAnekScript("main", proc, this, uin, msg);
//    }
//
//    public void commandExec(IcqProtocol proc, String uin, Vector v){
//        if(!AnekProps.getInstance(srv.getName()).testAdmin(uin)){
//            proc.mq.add(uin,"Вы не имеете доступа к данной команде.");
//            return;
//        }
//        try{
//            String s = (String)v.get(0);
//            String s1 = WorkScript.getInstance(srv.getName()).startCommandScript(s, proc, srv, uin, (String)v.get(1));
//            if(!s1.equals(""))
//                proc.mq.add(uin,"Ошибка при выполнении: " + s1);
//        }catch(Exception ex){
//            ex.printStackTrace();
//            proc.mq.add(uin,ex.getMessage());
//        }
//    }
//
//    /**
//     * Добавление анекдота
//     */
//    public void commandAdd(IcqProtocol proc, String uin, Vector v){
//        try {
//            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream("./temp_aneks.txt",true),"windows-1251");
//            String s = (String)v.get(0) + "\n\n";
//            ow.write(s);
//            ow.close();
//            Log.info("Add anek <" + uin + ">: " + (String)v.get(0));
//            proc.mq.add(uin,"Анекдот сохранен. После рассмотрения администрацией он будет добавлен в базу.");
//            state_add++;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            Log.info("Error save anek: " + ex.getMessage());
//            proc.mq.add(uin,"Ошибка добавления");
//        }
//    }
//
    /**
     * Определение времени запуска бота
     */
    public long getTimeStart(){
        long t = 0;
        try{
            File f = new File("./state");
            t = f.lastModified();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return t;
    }

    public long getUpTime(){
        return System.currentTimeMillis()-getTimeStart();
    }

    public long getHourStat(){
        if(getUpTime()>1000*60*60){
            return state/(getUpTime()/3600000);
        }
        return 0;
    }

    public long getDayStat(){
        if(getUpTime()>1000*60*60*24){
            return state/(getUpTime()/86400000);
        }
        return 0;
    }

    public String getTime(long t){
        Date dt = new Date(t);
        DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return (t/86400000) + " дней " + df.format(dt);
    }

    /**
     * Возвращает наименее загруженный номер
     * @return
     */
    public String getFreeUin(){
    	String u = "";
    	int k = 99;
    	int c = 0;
    	for(int i=0;i<srv.getProps().uinCount();i++){
            String s = srv.getProps().getUin(i);
    		if(srv.getProtocol(s).isOnLine()){
//    			c = srv.getOutQueue(s).size();
                c = MsgStatCounter.getElement(s).getMsgCount(MsgStatCounter.M1);
//    			if(c==0) return s;
    			if(k>c){
    				k = c;
    				u = s;
    			}
    		}
    	}
    	return u;
    }

    public void addState(String uin){
        if(!uq.containsKey(uin)){
            StateUin u = new StateUin(uin,0);
            uq.put(uin,u);
        }
    }

    public void stateInc(String uin){
        StateUin u = uq.get(uin);
        u.cnt++;
        uq.put(uin,u);
    }

    public class StateUin {
        public String uin="";
        public int cnt=0;

        public StateUin(String u, int c){
            uin = u;
            cnt = c;
        }
    }
}