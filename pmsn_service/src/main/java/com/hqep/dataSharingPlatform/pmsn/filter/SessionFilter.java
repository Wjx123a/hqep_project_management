package com.hqep.dataSharingPlatform.pmsn.filter;

import com.hqep.dataSharingPlatform.common.utils.*;
import io.jsonwebtoken.Jwts;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @类名: SessionFilter
 * @功能描述 过滤器
 * @作者信息 Wang_XD
 * @创建时间 2019/9/27
 */
//@Component
public class SessionFilter extends OncePerRequestFilter implements Filter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authorization = "";
        String methodType = request.getMethod();
//        Map<String, Object> extendParams = new HashMap<String, Object>();
        PageData extendParams = new PageData();
        String code = null;
        String text = null;
        try {
            if ("true".equals(ConfigUtil.getString("custom.isValidationToken"))) {
                if (RequestMethod.POST.name().equals(methodType) || RequestMethod.OPTIONS.name().equals(methodType)
                        || RequestMethod.PUT.name().equals(methodType)) {
                    authorization = request.getHeader("Authorization");
//                authorization = getBodyJsonParam(request);
                } else if (RequestMethod.GET.name().equals(methodType)) {
                    authorization = request.getParameter("Authorization");
                }
                logger.info("拦截器开始....");
                logger.info("MethodType:" + methodType);
                logger.debug("Authorization:" + authorization);
                if (!request.getRequestURI().contains("/swagger-ui.html")
                        && !request.getRequestURI().contains("/swagger-resources")
                        && !request.getRequestURI().contains("/v2/api-docs")
                        && !request.getRequestURI().contains("/webjars")
                        && !request.getRequestURI().contains("/login")) {
                    if (authorization == null || "".equals(authorization)) {
//                        throw new ServletException("未知token");
                        PageData pageData = new PageData("code", "500", "text", "未知token");
                        throw new MySessionFilterException(pageData);
                    } else {
                        // Jws<Claims> claims = Jwts.parser().isSigned(authorization);
                        // 如果符合签发格式
                        if (Jwts.parser().isSigned(authorization)) {
                            String PayloadString = authorization.split("\\.")[1];
                            //String jsonStr = new String(Base64Utils.decodeFromString(PayloadString));
                            //JSONObject obj = JSON.parseObject(jsonStr);
                            JwtUtil jwtUtil = new JwtUtil();
                            // ResponseBodyBean responseBodyBean = jwtUtil.verifyJWT(authorization);
                            code = jwtUtil.verifyJWT(authorization).getReason().getCode();
                            text = jwtUtil.verifyJWT(authorization).getReason().getText();
                            if ("200".equals(code)) {
                                extendParams.put("code", code);
                                extendParams.put("text", text);
                            } else {
                                extendParams.put("code", code);
                                extendParams.put("text", text);
                                throw new MySessionFilterException(extendParams);
                            }
                        }
                    }
                }
            }
        } catch (MySessionFilterException e) {
            e.printStackTrace();
            System.err.println("error-->>" + e.getMessage());
            extendParams.put("error", ResponseJsonApiUtil.getErrorMsg("Exception", e.getMessage()));
        } catch (ServletException e) {
            System.err.println("error1-->>" + e.getMessage());
            extendParams.put("error", ResponseJsonApiUtil.getErrorMsg("Exception", e.getMessage()));
        } finally {
//            response.setStatus(status);
            ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request, extendParams);
            filterChain.doFilter(requestWrapper, response);
            logger.info("拦截器结束....");
        }


        /**
         * @方法名:getJsonParam
         * @功能描述:获取body 参数里的token
         * @作者信息：Wang_XD
         * @创建时间:10:25 2019/9/27
         **/
//    private String getBodyJsonParam(HttpServletRequest request) throws IOException {
//        String token = "";
//        ServletInputStream inputStream = null;
//        int contentLength = request.getContentLength();
//        if (!(contentLength < 0)) {
//            byte[] buffer = new byte[contentLength];
//            inputStream = request.getInputStream();
//            for (int i = 0; i < contentLength; ) {
//                int len = inputStream.read(buffer, i, contentLength);
//                if (len == -1) {
//                    break;
//                }
//                i += len;
//            }
//            String jsonParam = new String(buffer, "utf-8");
//            PageData pageData = JSON.parseObject(jsonParam, PageData.class);
//            token = pageData.getString("token");
//        }
//        return token;
//    }
    }
}