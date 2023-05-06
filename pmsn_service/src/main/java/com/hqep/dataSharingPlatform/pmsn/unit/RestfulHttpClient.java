package com.hqep.dataSharingPlatform.pmsn.unit;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestfulHttpClient {


    public String getRestApi(Map<String,String> map, String REST_API_URL) {
        try {
            if(REST_API_URL == null) {
                return "NR_ERROR";
            }
            RestTemplate template =new RestTemplate();
            String obj = template.getForObject(REST_API_URL+"?access_token={access_token}", String.class, map);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NR_ERROR";
    }
    public String getRestApiForAll(Map<String,String> map, String REST_API_URL) {
        try {
            if(REST_API_URL == null) {
                return "NR_ERROR";
            }
            RestTemplate template =new RestTemplate();
            String obj = template.getForObject(REST_API_URL+"?iscUserId={iscUserId}", String.class, map);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NR_ERROR";
    }

    public String getRestApiFuncTree(Map<String,String> map, String REST_API_URL) {
        try {
            if(REST_API_URL == null) {
                return "NR_ERROR";
            }
            RestTemplate template =new RestTemplate();
            String obj = template.getForObject(REST_API_URL+"?iscUserId={iscUserId}", String.class, map);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NR_ERROR";
    }

}
