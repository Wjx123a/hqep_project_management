package com.hqep.dataSharingPlatform.pmsn.action;

import com.alibaba.druid.util.StringUtils;
import com.hqep.dataSharingPlatform.pmsn.unit.RCheckMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class tokenAction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 生成token
     * @param appId
     * @param appKey
     * @param timestamp
     * @return
     * @throws Exception
     */

    @RequestMapping("/getToken")
    public  Map<String,Object> createToken(String appId, String appKey, String timestamp) throws Exception {
        try {
            //用MD5加密生成秘钥串
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes  = md.digest((appId+appKey+timestamp).getBytes("UTF-8"));
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            String token = bigInt.toString(16);
            return RCheckMsg.successMsg("接口返回成功",token);
        } catch (Exception e) {
            System.out.println(e);
            return RCheckMsg.errorMsg(500,"接口返回成功",null);
        }
    }


    /**
     * 校验token是否合法
     * @param request
     * @return
     */
    @RequestMapping("/checkToken")
    private Map<String,Object> checkAuthorizeToken(ServletRequest request) {
        try {
            HttpServletRequest req = (HttpServletRequest) request;

            String token = req.getParameter ("token");
            String appId = req.getParameter ("appId");
            String timestamp = req.getParameter ("timestamp");
            //获取本地保存的省公司秘钥
            String appKey = "9e12a324e0a011ecabe7e84dd0b414aa";

            //用MD5加密生成秘钥串
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes  = md.digest((appId+appKey+timestamp).getBytes("UTF-8"));
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            String checkToken = bigInt.toString(16);

            if(!StringUtils.equals(token, checkToken)) {
                return RCheckMsg.errorMsg(-101,"token无效或已过期",null);
            }
            return RCheckMsg.errorMsg(200,"接口返回成功",checkToken);
        } catch (Exception ex) {
            System.out.println(ex);
            return RCheckMsg.errorMsg(-101,"token无效或已过期",null);
        }
    }




}
