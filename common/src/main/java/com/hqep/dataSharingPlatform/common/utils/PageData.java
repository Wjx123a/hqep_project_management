package com.hqep.dataSharingPlatform.common.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @类名: PageData
 * @功能描述 参数封装
 * @作者信息 Wang_XD
 * @创建时间 2019/8/18
 */
public class PageData extends LinkedHashMap implements Map {


    Map map = null;
    HttpServletRequest request;

    public PageData(HttpServletRequest request) {
        this.request = request;
        Map properties = request.getParameterMap();
        Map returnMap = new LinkedHashMap();
        Iterator entries = properties.entrySet().iterator();
        Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        map = returnMap;
    }

    public PageData() {
        map = new LinkedHashMap();
    }

    public PageData(String key, Object value) {
        map = new LinkedHashMap();
        map.put(key, value);
    }

    public PageData(Object... args) {
        map = new LinkedHashMap();
        if (args != null && args.length > 0) {
            int loop = (int) Math.ceil((double) args.length / (double) 2);
            for (int i = 0; i < loop; i++) {
                map.put(args[i * 2], (i * 2 + 1) < args.length ? args[i * 2 + 1] : null);
            }
        }
    }

    public PageData(Map linkedHashMap) {
        map = JSON.parseObject(JSON.toJSON(linkedHashMap).toString(), PageData.class);
    }

    @Override
    public Object get(Object key) {
        Object obj = null;
        if (map.get(key) instanceof Object[]) {
            Object[] arr = (Object[]) map.get(key);
            obj = request == null ? arr : (request.getParameter((String) key) == null ? arr : arr[0]);
        } else {
            obj = map.get(key);
        }
        return obj;
    }

    public String getString(Object key) {
        return (String) get(key);
    }

    public int getInt(Object key) {
        return Integer.parseInt(get(key).toString());
    }

    public double getDouble(Object key) {
        return Double.parseDouble(get(key).toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object put(Object key, Object value) {
        return map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    public void clear() {
        map.clear();
    }

    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        return map.containsValue(value);
    }

    public Set entrySet() {
        // TODO Auto-generated method stub
        return map.entrySet();
    }

    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return map.isEmpty();
    }

    public Set keySet() {
        // TODO Auto-generated method stub
        return map.keySet();
    }

    @SuppressWarnings("unchecked")
    public void putAll(Map t) {
        // TODO Auto-generated method stub
        map.putAll(t);
    }

    public int size() {
        // TODO Auto-generated method stub
        return map.size();
    }

    public Collection values() {
        // TODO Auto-generated method stub
        return map.values();
    }

}
