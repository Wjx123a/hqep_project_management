package com.hqep.dataSharingPlatform.common.utils;

import com.hqep.dataSharingPlatform.common.model.ReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResponseBodyBean;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

/**
 * @类名: JwtUtil
 * @功能描述 JWT
 * @作者信息 Wang_XD
 * @创建时间 2019/9/19
 */
public class JwtUtil {
    /**
     * 【描 述】：私钥
     */
    private static final String SECRET = ConfigUtil.getString("jwtSecret");

    /**
     * @param issuer    该JWT的签发者
     * @param ttlMillis 签名到期时间
     * @param pd        该JWT所面向的用户
     * @方法名:createJWT
     * @功能描述:创建JWT
     * @作者信息：Wang_XD
     * @创建时间:13:12 2019/9/19
     **/
    public static String createJWT(String issuer, PageData pd, long ttlMillis) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 设置JWT声明
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())// JWT ID
                .setHeaderParam("typ", "JWT")// 头信息
                .setIssuer(issuer)// 该JWT的签发者
                .setSubject(pd.getString("id"))// 该JWT所面向的用户
//                .setAudience()//接受者
                .setIssuedAt(now)// 签证时间时间
                .claim("name", pd.getString("loginNum"))//用户名
                .claim("personId", pd.getString("personId"))//用户id
                .claim("roleId", pd.getString("roleId"))//角色id
                .claim("roleName", pd.getString("roleName"))//角色信息
                .claim("phone",pd.getString("phone"))//用户电话
                .claim("isAdmin",pd.getString("isAdmin"))
//				.compressWith(CompressionCodecs.GZIP)// JWT压缩
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes());//使用JWT签名算法，对token进行签名

        // 如果过期时间被指定，则添加到到期时间中
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);// 签名到期时间
        }

        // 将JWT序列换为一个紧凑、安全的URL字符串
        return builder.compact();
    }

    /**
     * @param compactJws
     * @方法名:verifyJWT
     * @功能描述:验证JWT
     * @作者信息：Wang_XD
     * @创建时间:13:34 2019/9/19
     **/
    public static ResponseBodyBean verifyJWT(String compactJws) {
        ResponseBodyBean apiBean = new ResponseBodyBean();
        ReasonBean reason = new ReasonBean();
        try {
            Jws<Claims> claims = Jwts.parser()
//					        .requireSubject("admin")
//					        .require("hasMotorcycle", true)
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(compactJws);
            apiBean.setResult(claims.getBody().getSubject());
            reason.setCode("200");
            reason.setText("Token验证通过");
        } catch (ExpiredJwtException e) {
            System.err.println("JWT签名已经过期：" + e.getMessage());
            reason.setCode("500");
            reason.setText("JWT签名已经过期");
        } catch (SignatureException e) {
            System.err.println("claimJws JWS签名验证失败：" + e.getMessage());
            reason.setCode("500");
            reason.setText("JWS签名验证失败");
        } catch (MalformedJwtException e) {
            System.err.println("claimJws字符串不是有效的JWS：" + e.getMessage());
            reason.setCode("500");
            reason.setText("Token字符串不是有效的JWS");
        } catch (IllegalArgumentException e) {
            System.err.println("claimJws的字符串为空或为空，或仅为空格：" + e.getMessage());
            reason.setCode("500");
            reason.setText("Token字符串为空或仅为空格");
        } catch (UnsupportedJwtException e) {
            System.err.println("claimJws参数不代表Claim JWS：" + e.getMessage());
            reason.setCode("500");
            reason.setText("Jws参数不代表Claim JWS");
        } catch (MissingClaimException e) {
            System.err.println("1111" + e.getMessage());
            reason.setCode("500");
        } catch (IncorrectClaimException e) {
            System.err.println("22222" + e.getMessage());
            reason.setCode("500");
        }
//		if(apiBean.getCode() != 1) {
//			apiBean.setNote("非法请求");
//		}
        apiBean.setReason(reason);
        return apiBean;
    }

    /**
     * @param token
     * @方法名:refreshToken
     * @功能描述:刷新Token
     * @作者信息：Wang_XD
     * @创建时间:13:42 2019/9/19
     **/
    public static ResponseBodyBean refreshToken(String token) {
        ResponseBodyBean apiBean = verifyJWT(token);
        PageData useData = new PageData();
        if (apiBean.getReason().getCode() == "200") {
            Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token).getBody();
            useData.put("id", claims.getSubject());
            useData.put("idcard", claims.get("idcard"));
            useData.put("name", claims.get("name"));
            useData.put("idcard", claims.get("idcard"));
            useData.put("roleId", claims.get("roleId"));
            useData.put("roleName", claims.get("roleName"));
            String jwtToken = createJWT("dongdong Center", useData, 30 * 60 * 1000);
            apiBean.setResult(jwtToken);
            return apiBean;
        }
        return apiBean;
    }

    /**
     * @param compactJws
     * @方法名:parseJwt
     * @功能描述:解析Token
     * @作者信息：Wang_XD
     * @创建时间:13:34 2019/9/19
     **/
    public static Jws<Claims> parseJwt(String compactJws) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(compactJws);
        return claims;
    }

    /**
     * @param compactJws
     * @方法名:getUserIdOfToken
     * @功能描述:解析Token得到 personId
     * @作者信息：邵文强
     * @创建时间: 2021-03-25
     **/
    public static String getUserIdOfToken(String compactJws) {
        return (String) parseJwt(compactJws).getBody().get("sub");
    }

    /**
     * @param compactJws
     * @方法名:getUserNameOfToken
     * @功能描述:解析Token得到 personId
     * @作者信息：邵文强
     * @创建时间: 2021-03-25
     **/
    public static String getUserNameOfToken(String compactJws) {
        return (String) parseJwt(compactJws).getBody().get("name");
    }
    /**
     * @param compactJws
     * @方法名:getUserNameOfToken
     * @功能描述:解析Token得到 personId
     * @作者信息：邵文强
     * @创建时间: 2021-03-25
     **/
    public static String getRoleNameOfToken(String compactJws) {
        return (String) parseJwt(compactJws).getBody().get("roleName");
    }

    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();
        PageData pageData = new PageData();
        pageData.put("name", "王冬冬");
        pageData.put("idcard", "0972");
        pageData.put("id", "7788899");
        pageData.put("roleId", "1301");
        pageData.put("roleName", "管理员");
//		String creatToken = jwtUtil.creatToken(pageData);
//		System.out.println(creatToken);
//        String name1 = jwtUtil.verifyToken();
//        System.out.println("name--->>>" + name1);
//        String jj = JSONArray.toJSON(name1).toString();
//        System.out.println("ddd-->>" + jj);


        String token1 =
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NDA0ZTExNy0xYmE3LTQ5NzQtYjZmZi03YWZiYTUyOThjYzgiLCJpYXQiOjE1Njg5NTAzMjksInN1YiI6Ijc3ODg4OTkiLCJpc3MiOiJEb25nRG9uZyBDZW50ZXIiLCJuYW1lIjoi546L5Yas5YasIiwiaWRjYXJkIjoiMDk3MiIsInJvbGVJZCI6IjEzMDEiLCJyb2xlTmFtZSI6IueuoeeQhuWRmCIsImV4cCI6MTU3MTAyMzkyOX0.XzUE0VZ9gbeaQmdBKMDISvCJwaCrTEgUR5yC2nEMoQ4";
        ResponseBodyBean apiBean1 = new ResponseBodyBean();
        apiBean1 = refreshToken(token1);
        System.out.println("apiBean1-->>"+apiBean1);

        //        refreshToken(token1);
//		parseJwt(token1);
//        System.out.println("jixie-->>" + refreshToken(token1));
//        System.out.println("pppp-->>"+parseJwt(token1));


    }

    //	public String creatToken(User user){
    public String creatToken(PageData pd) {
//		String token = createJWT("DongDong Center", pd, 24*24*60*60*1000);
        String token = createJWT("DongDong Center", pd, 10 * 1000);
        return token;
    }

    public String verifyToken() {
        String token1 =
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkZDIzOTRiNC0wYjRmLTQ2OGYtYWY2OS04ZmE3N2YxMjJiNmEiLCJpYXQiOjE1Njg4NzEwOTEsInN1YiI6IjU1NjY5OSIsImlzcyI6IlNlY3VyaXR5IENlbnRlciIsIm5hbWUiOiLnjovlhqzlhqwiLCJpZGNhcmQiOiIwMDIzNTYiLCJleHAiOjE1Njg4NzExMDF9.RYglQl4J4pziqeQAmv-DYrokBhZadzaeQ4zDdaj6Se0";
        ResponseBodyBean apiBean1 = new ResponseBodyBean();
        apiBean1 = verifyJWT(token1);
        System.out.println("sdfsdfsdf__>>" + apiBean1);
        ReasonBean reason = new ReasonBean();
        System.out.println("getReason-->>" + apiBean1.getReason());
        reason = apiBean1.getReason();
        System.out.println("ewwewe-->>" + reason.getCode());
        System.out.println("ewwewe-->>" + reason.getText());
        String name = apiBean1.toString();
        return name;
    }

}
