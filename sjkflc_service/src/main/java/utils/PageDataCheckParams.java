package utils;

import com.hqep.dataSharingPlatform.common.utils.PageData;


/***
 * PageData转对象
 * 和对象转PageData
 * create by: sssJL
 * create time: 2022-05-12
 */
public class PageDataCheckParams {


    public static  boolean checkParamNull(PageData pd,String key) {
        if (pd.get(key) == null || "".equals(pd.get(key)) ) {
            return true;
        }
        return false;
    }


    public static boolean checkParamListNull(PageData pd,String[] keys) {
        for (int i = 0; i < keys.length; i++) {
            if (pd.get(keys[i]) == null || "".equals(pd.get(keys[i])) ) {
                return true;
            }
        }
        return false;
    }
}
