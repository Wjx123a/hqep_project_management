package com.hqep.dataSharingPlatform.common.utils;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
* @Description:    StringBuilder 使用汇总
* @Author:         shaowenqiang
* @CreateDate:     2019/7/10 9:58
* @Version:        1.0
*/
public class StringBuilderUtil {

    public static void main(String[] args) {
        System.out.println(concatString("22",null,","));
    }

    /**
     * 将字符串进行拼接 不去空格
     * @param strings 未知个数的字符串
     * @return
     */
    public static String concatString(String... strings){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<strings.length;i++){
            String s = strings[i];
            //空格可以拼接
            if(isNotEmpty(s)){
//                if(trim){
//                    s = s.trim();
//                }
                sb.append(s);
            }
        }
        return sb.toString();
    }

    /**
     * 在输入的字符串中取出数字
     * @param str
     * @return
     */
    public static List<String> getFullNumFromString(String str){
        List<String> resultList = new ArrayList<>();
        StringBuilder numBuilder = new StringBuilder();
        str = str.replaceAll(" ","");
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch >= 48 && ch <= 57) {
                numBuilder.append(ch);
                if ( i == str.length() -1) {
                    resultList.add(numBuilder.toString());
                }
            } else {
                if (!numBuilder.toString().equals("") && numBuilder.length() > 0) {
                    resultList.add(numBuilder.toString());
                    numBuilder = new StringBuilder();
                }
            }
        }
        return resultList;
    }


    public static boolean  equalForDouble(double a, double b) {
        if ((a- b> -0.000001) && (a- b) < 0.000001)
            return true;
        else
            return false;
    }

    /***
     * 下划线命名转为驼峰命名
     *
     * @param param
     *        下划线命名的字符串
     */

    public static String UnderlineToHump(String param){
        StringBuilder result=new StringBuilder();
        String a[]=param.split("_");
        for(String s:a){
            if (!param.contains("_")) {
                result.append(s);
                continue;
            }
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }



    /***
     * 驼峰命名转为下划线命名
     *
     * @param param
     *        驼峰命名的字符串
     */

    public static String HumpToUnderline(String param){
        StringBuilder sb=new StringBuilder(param);
        int temp=0;//定位
        if (!param.contains("_")) {
            for(int i=0;i<param.length();i++){
                if(Character.isUpperCase(param.charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }
}
