package com.hqep.dataSharingPlatform.utils;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by spring on 2021/4/7.
 */
public class TestUtil {
    public static void main(String[] args) {
        String str = "[[{\"shjg\":\"2021-04-07_互联网部驳回\",\"dqjdbm\":\"LC-04-JD-02\",\"dqjdid\":\"19\",\"dqjdlx\":\"1\",\"dqjdmc\":\"互联网部审批\",\"id\":\"49cde6b4-973c-11eb-95ba-0242ac110008\",\"lcbm\":\"LC-04\",\"lcmc\":\"主流程\",\"lcpch\":\"c173cb589bb34668aed62113fe00d804\",\"sqrid\":\"2abf1679674043c6b8f4abba75d14baa\",\"sqrmc\":\"管理员\",\"sqrssdw\":\"\",\"sqrssbm\":\"电力交易中心\",\"sqsj\":\"2021-04-07 00:57:55\",\"sqqxid\":\"0\",\"sqbssxt\":\"电力交易\",\"sqbssbm\":\"电力交易中心\",\"sqbywbm\":\"ACTIVEMQ_LOG\",\"sqbzwbm\":\"消息总线日志\",\"spzt\":\"2\",\"bz\":\"\",\"sqyh\":\"sijia\",\"shrid\":\"21aeea853f2d4689b214cd2f2efcbe6e\",\"shrmc\":\"互联网部\",\"shrssdw\":\"国网黑龙江省电力本部\",\"shrssbm\":\"互联网部\"}]]";
        String str1 = str.substring(1, str.length() - 1);
        System.out.println(str1);
        PageData resultPd = new PageData();
        try {
            JSONArray jsonArray = JSONArray.fromObject(str1);
            for(int  i = 0; i < jsonArray.size(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                System.out.println("[" + i + "]sqsj=" + jsonObject.get("sqsj"));
//                resultPd.put("sqrid",jsonObject.get("sqrid").toString());
//                resultPd.put("sqrmc",jsonObject.get("sqrmc").toString());
                Iterator<String> iterator = jsonObject.keys();
                while(iterator.hasNext())
                {
                    String key = (String)iterator.next();
                    Object value = jsonObject.get(key);
                    resultPd.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resultPd.get("sqrid"));
        System.out.println(resultPd.get("sqrmc"));
        System.out.println(resultPd.get("shjg"));
    }
}
