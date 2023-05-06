package com.hqep.dataSharingPlatform.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * @类名: ClobToString
 * @功能描述 将clob转换为字符串
 * @作者信息 Wang_XD
 * @创建时间 2019/8/21
 */
public class ClobToStringUtil {

    public static StringBuffer ClobToString(Clob clob) {
//        StringBuffer reString = "";
        Reader is = null;
        try {
            is = clob.getCharacterStream();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(is);
        String s = null;
        try {
            s = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        while (s != null) {
            //执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        reString = sb.toString();
        return sb;
    }
}
