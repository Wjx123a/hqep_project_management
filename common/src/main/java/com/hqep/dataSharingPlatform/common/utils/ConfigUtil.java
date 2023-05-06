package com.hqep.dataSharingPlatform.common.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @类名: ConfigUtil
 * @功能描述 读取配置文件
 * @作者信息 Wang_XD
 * @创建时间 2019/9/17
 */
public class ConfigUtil {
    private static ResourceBundle resourceBundle = null;

    public static Map<String, String> environmentMap = null;

    static {
        resourceBundle = ResourceBundle.getBundle("config");
        environmentMap  = System.getenv();
    }

    /**
     * 得到配置文件中的值
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        if (resourceBundle.containsKey(key)) {
            return resourceBundle.getString(key);
        } else if(environmentMap.containsKey(key)) {
        	return environmentMap.get(key);
        }
        return null;
    }

    /**
     * @param bundleName
     * @return
     */
    public static Map<String, String> getBundleInfoMap(String bundleName) {
        ResourceBundle rb = ResourceBundle.getBundle(bundleName);
        Map<String, String> map = new HashMap<String, String>();

        Enumeration<String> en = rb.getKeys();

        while (en.hasMoreElements()) {
            String key = en.nextElement();
            map.put(key, String.valueOf(rb.getString(key)));
        }
        return map;
    }
}
