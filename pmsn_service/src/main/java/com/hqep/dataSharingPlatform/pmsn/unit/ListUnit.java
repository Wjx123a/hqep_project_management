package com.hqep.dataSharingPlatform.pmsn.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * create by sssjl
 * create time 2022-06-10
 * description List分割工具
 */
public class ListUnit {

    /**
     * 将大的list分割成小的list
     * @param list 需要分割的list
     * @param subNum  分割长度
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splistList(List<T> list, int subNum) {
        List<List<T>> tNewList = new ArrayList<List<T>>();
        int priIndex = 0;
        int lastPriIndex = 0;
        int insertTimes = list.size()/subNum;
        List<T> subList = new ArrayList<>();
        for (int i = 0;i <= insertTimes;i++) {
            priIndex = subNum*i;
            lastPriIndex = priIndex + subNum;
            if (i == insertTimes) {
                subList = list.subList(priIndex,list.size());
            } else {
                subList = list.subList(priIndex,lastPriIndex);
            }
            if (subList.size() > 0) {
                tNewList.add(subList);
            }
        }
        return tNewList;
    }


    /***
     * 拼接list的key值 以逗号分隔
     * 可以用于拼接主键使用
     * @param list
     * @param key
     * @return
     */
    public static String ListToStrWithComma(List<Map<String,Object>> list,String key) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).get(key));
            if ((i + 1) != list.size()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /***
     * 拼接list的key值 以逗号分隔
     * 可以用于拼接主键使用
     * @param list
     * @param key
     * @return
     */
    public static String ListMapToStrWithComma(List list,String key) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(((Map)list.get(i)).get(key));
            if ((i + 1) != list.size()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }


    /***
     * listMap 转 map
     * @param list
     * @param key
     * @return
     */
    public static Map ListMapToMap(List list,String key) {
        StringBuffer sb = new StringBuffer();
        Map map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            map.put(((Map)list.get(i)).get(key),list.get(i));
        }
        return map;
    }
}