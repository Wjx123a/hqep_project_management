package com.hqep.dataSharingPlatform.pmsn.job;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import java.io.*;
import java.util.*;

@Component
public class CallInterface {

    //    @Scheduled(cron = "0/10 * * * * ? ") // 间隔10秒执行
    public void taskCycle() {
        try {
            System.out.println("定时任务开始.....");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 调用阿里接口服务
    public String callAL(String xString) {
        try {
            // 调用地址
            String endpoint = "http://10.166.4.50:8000/sap/bc/srt/wsdl/sdef_ZMMAPP_DDSH/wsdl11/ws_policy/document?sap-client=800";
            String namespace = "urn:sap-com:document:sap:soap:functions:mc-style";
            String methodname = "ZmmappDdsh";
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setOperationName(new QName(namespace, methodname));
            call.addParameter("Input", XMLType.XSD_STRING, ParameterMode.IN);
            call.setReturnType(XMLType.XSD_STRING);
            // 用户名密码
            call.setUsername("WEBSERVICE");
            call.setPassword("web123456");
            String result = (String) call.invoke(new Object[]{xString});
            System.out.println("接口返回=========" + result);
            return result;
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return "";
    }

    // 本地测试 读取JSON文件
    public static String getXML() {
        StringBuffer sb = new StringBuffer();
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    new File("C:/Users/spring/Desktop/ematerial_erp_return/zmmapp_ECP2jjReturn.xml"));
            InputStream is = fileInputStream;
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while (br.ready()) {
                sb.append(br.readLine() + "\n");
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
