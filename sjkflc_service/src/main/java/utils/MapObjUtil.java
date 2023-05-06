package utils;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/***
 * 实体转对象
 * 和对象转实体
 * create by: sssJL
 * create time: 2021-02-02
 */
public class MapObjUtil {

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        return object2Map(obj, false);
    }

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> object2Map(Object obj, boolean flag) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (flag) {
                    if ((field.get(obj) != null && !"".equals(field.get(obj)) &&
                            !"null".equals(field.get(obj)) && !"NULL".equals(field.get(obj)))) {
                        map.put(field.getName(), field.get(obj));
                    } else {
                        map.put(field.getName(), "");
                    }
                } else {
                    if ((field.get(obj) != null && !"".equals(field.get(obj)) &&
                            !"null".equals(field.get(obj)) && !"NULL".equals(field.get(obj)))) {
                        map.put(field.getName(), field.get(obj));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Map转成实体对象
     *
     * @param map   map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                String filedTypeName = field.getType().getName();
                if (filedTypeName.equalsIgnoreCase("java.util.date")) {
                    String datetimestamp = String.valueOf(map.get(field.getName()));
                    if (datetimestamp.equalsIgnoreCase("null")) {
                        field.set(obj, null);
                    } else {
                        if(isValidDate(datetimestamp)) {
                            field.set(obj, DateUtils.parseDate(datetimestamp,"ALL"));
                        } else {
                            field.set(obj, new Date(Long.parseLong(datetimestamp)));
                        }
                    }
                } else {
                    field.set(obj, map.get(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }



    /**
     * PageData转成实体对象
     *
     * @param pageData   PageData实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static <T> T pageData2Object(PageData pageData, Class<T> clazz) {
        if (pageData == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                String filedTypeName = field.getType().getName();
                if (filedTypeName.equalsIgnoreCase("java.util.date")) {
                    String datetimestamp = String.valueOf(pageData.get(field.getName()));
                    if (datetimestamp.equalsIgnoreCase("null")) {
                        field.set(obj, null);
                    } else {
                        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
                        Date date = fmt.parse(datetimestamp);
                        field.set(obj, date);
                    }
                } else if(filedTypeName.equalsIgnoreCase("int")) {
                  //  System.out.println("field.getName():"+ pageData.get(field.getName()));
                 //   System.out.println();
                    String datetimestamp = String.valueOf(pageData.get(field.getName()));
                    if (datetimestamp == null || "null".equals(datetimestamp)
                            || datetimestamp.equalsIgnoreCase("null")) {

                    } else {
                        field.set(obj, Integer.parseInt(String.valueOf(pageData.get(field.getName()))));
                    }
                } else {
                    field.set(obj, pageData.get(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 实体转成实体对象
     *object2Map
     * @param model  obj实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static <T> T Object2Object(Object model, Class<T> clazz) {
        Map<String, Object> map = object2Map(model);
        T obj = map2Object(map,clazz);
        return obj;

    }



    private static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            // format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            // e.printStackTrace();
// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }


    /**
     * 将Map中的值拷贝到另一个Map中
     * @param yMap 源Map
     * @param obj 实体
     * @return
     */
    public static Map ObjectIntoMap(Object obj, Map yMap) {
        if (yMap == null) {
            yMap = new HashMap();
        }
        if (obj == null) {
            return yMap;
        } else {
            Map<String, Object> tarMap = object2Map(obj);
            Iterator it=tarMap.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry entry=(Map.Entry)it.next();
                Object key=entry.getKey();
                if(key!=null && tarMap.get(key)!=null) {
                    yMap.put(key.toString(), tarMap.get(key));
                }
            }
        }
        return yMap;
    }


    /**
     * 将Map中的值拷贝到另一个Map中
     * @param tarMap 拷贝的Map
     * @param yMap  源Map
     * @return
     */
    public static Map mapIntoMap(Map tarMap,Map yMap) {
        if (yMap == null) {
            yMap = new HashMap();
        } else {
            if (tarMap == null || tarMap.isEmpty()) {
                return yMap;
            } else {
                Iterator it=tarMap.entrySet().iterator();
                while(it.hasNext()) {
                    Map.Entry entry=(Map.Entry)it.next();
                    Object key=entry.getKey();
                    if(key!=null && tarMap.get(key)!=null) {
                        yMap.put(key.toString(), tarMap.get(key));
                    }
                }
            }
        }
        return yMap;
    }


}
