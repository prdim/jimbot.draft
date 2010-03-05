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

package ru.jimbot.util;

import ru.jimbot.core.UinConfig;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

/**
 * Разные пофторяющиеся функции для работы с файлами
 * @author Prolubnikov Dmitry
 */
public class FileUtils {
    private static HashSet<String> ignor;

    private FileUtils() {
        throw new AssertionError();
    }

    /**
     * Загрузка содержимого текстового файла в строковую переменную
     * @param fn - имя файла
     * @param encode - кодировка файла (windows-1251, utf8)
     * @return содержимое файла
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static String loadFile(String fn, String encode) throws IOException, UnsupportedEncodingException {
        String s = "";
        BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(fn),encode));
        while (r.ready()){
            s += r.readLine() + '\n';
        }
        r.close();
        return s;
    }

    /**
     * Загружает файл свойств
     * @param fn
     * @return
     */
    public static Properties loadProperties(String fn) throws IOException {
        Properties p = new Properties();
        FileInputStream fi = new FileInputStream(fn);
        p.loadFromXML(fi);
        fi.close();
        return p;
    }

    /**
     * Сохраняет файл свойств
     * @param p
     * @param fn
     * @throws IOException
     */
    public static void saveProperties(Properties p, String fn) throws IOException {
        FileOutputStream fo = new FileOutputStream(fn);
        p.storeToXML(fo, "JimBot Properties");
        fo.close();
    }

    public static void beanToXML(Object bean, String fn) throws FileNotFoundException {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fn)));
        encoder.writeObject(bean);
        encoder.close();
    }

    public static Object XmlToBean(String fn) throws FileNotFoundException {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fn)));
        Object o = decoder.readObject();
        decoder.close();
        return o;
    }

    /**
     * Возвращает строковый массив в виде строки с разделителем ";"
     * @param ss
     * @return
     */
    public static String arrayToString(String[] ss) {
        String s = "";
        int c = 0;
        for(String i : ss) {
            s += (c==0 ? "" : ";") + i;
            c++;
        }
        return s;
    }

    public static String[] addItem(String[] ss, String s) {
        String[] ss2 = new String[ss.length+1];
        System.arraycopy(ss,0,ss2,0,ss.length);
        ss2[ss2.length-1] = s;
        return ss2;
    }

    public static String[] removeItem(String[] ss, int item) {
        ArrayList<String> list = new ArrayList(Arrays.asList(ss));
        list.remove(item);
        return (String[])list.toArray();
    }

    public static UinConfig[] addItem(UinConfig[] ss, UinConfig u) {
        UinConfig[] ss2 = new UinConfig[ss.length+1];
        System.arraycopy(ss,0,ss2,0,ss.length);
        ss2[ss2.length-1] = u;
        return ss2;
    }

    public static UinConfig[] removeItem(UinConfig[] ss, int item) {
        ArrayList<String> list = new ArrayList(Arrays.asList(ss));
        list.remove(item);
        return (UinConfig[])list.toArray();
    }


    /**
     * Загружает игнор-лист из файла
     */
    public static synchronized void loadIgnorList(){
    	String s;
    	ignor = new HashSet<String>();
        try{
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream("ignore.txt"),"windows-1251"));
            while (r.ready()){
                s = r.readLine();
                if(!s.equals("")){
                    ignor.add(s);
                }
            }
            r.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * УИН в игноре?
     * @param uin
     * @return
     */
    public static boolean isIgnor(String uin){
    	if(ignor==null) return false;
    	return ignor.contains(uin);
    }    
}
