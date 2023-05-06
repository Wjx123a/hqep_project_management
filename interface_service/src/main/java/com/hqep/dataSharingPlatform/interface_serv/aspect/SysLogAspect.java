package com.hqep.dataSharingPlatform.interface_serv.aspect;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.common.model.ErrorReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.config.ConfigInfo;
import com.hqep.dataSharingPlatform.interface_serv.dao.SysLogsDao;
import com.hqep.dataSharingPlatform.common.exception.CustomException;
import com.hqep.dataSharingPlatform.common.model.SysLogs;
import com.hqep.dataSharingPlatform.common.utils.IPUtils;
import com.hqep.dataSharingPlatform.common.utils.JsonMsg;
import com.hqep.dataSharingPlatform.common.utils.JwtUtil;
import com.hqep.dataSharingPlatform.utils.JoinPointUtils;
import com.hqep.dataSharingPlatform.utils.UUIDUtil;
import com.mchange.v1.util.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.hqep.dataSharingPlatform.common.utils.StringUtil2.isBlank;

/**
 * @Aspect 注解描述的类为一个切面对象
 * 此对象通常要定义：
 * 1）通知（扩展业务）
 * 2）切入点（织入扩展业务的哪些点）
 */
//@Order(20)
@Aspect
@Component
//@Service
@EnableAspectJAutoProxy
public class SysLogAspect {

    @Autowired
    private SysLogsDao sysLogDao;

    /**
     * @param joinPonit 连接点（通常指核心业务方法）
     * @Around 注解修饰的方法为一个环绕通知对象
     */
    @Around(value = "@within(requestLog) || @annotation(requestLog)")
    public Object around(ProceedingJoinPoint joinPonit, RequestLog requestLog) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //执行目标方法(result就是目标方法的执行结果)
        Object result = joinPonit.proceed();
        stopWatch.stop();
        try{
            if("登陆系统".equals(requestLog.value())){
                this.saveLoginLog(joinPonit, stopWatch.getTotalTimeMillis(), requestLog, (ResultBodyBean) result);
                return result;
            } else if("创建能力开放登录的用户并默认创建权限".equals(requestLog.value())
                    || requestLog.value().startsWith("能力开放跳转登录")
                    || requestLog.value().startsWith("能力开放的统计分析")){
                this.nlkfApiLog(joinPonit, stopWatch.getTotalTimeMillis(), requestLog, (ResultBodyBean) result);
                return result;
            } else if ("菜单查询列表".equals(requestLog.value())) {
                saveOperationLog(joinPonit, stopWatch.getTotalTimeMillis(), requestLog);
                return result;
            }  else if ("中台授权".equals(requestLog.value())) {
                saveUltraLog(joinPonit, stopWatch.getTotalTimeMillis(), requestLog);
                return result;
            }   else if ("将待申请信息保存至购物车".equals(requestLog.value())) {
                saveUltraLog(joinPonit, stopWatch.getTotalTimeMillis(), requestLog);
                return result;
            } else {
                saveLog(joinPonit, stopWatch.getTotalTimeMillis(), requestLog);
            }
        }catch (CustomException e){
            e.printStackTrace();
            ResultBodyBean resultBodyBean = new ResultBodyBean();
            ErrorReasonBean errorReasonBean = new ErrorReasonBean();
            errorReasonBean.setCode("401");
            errorReasonBean.setText(e.getMessage());
            resultBodyBean.setError(errorReasonBean);
//            return JsonMsg.getToMapmsMsg(false,e.getMessage());
            return resultBodyBean;
        }catch (Exception e){
            e.printStackTrace();
            Gson gson = new Gson();
            String args = gson.toJson(joinPonit.getArgs());
            return JsonMsg.autoResultMap(0, args,"",e.getMessage());
        }

        return result;
    }

    /***
     * 保存登录日志信息
     * @param joinPonit
     * @param totalTimeMillis
     * @param requestLog
     * @param result
     */
    private void saveLoginLog(ProceedingJoinPoint joinPonit, long totalTimeMillis, RequestLog requestLog, ResultBodyBean result) {
            //3获取操作的方法（哪个类哪个方法）
            MethodSignature ms = (MethodSignature) joinPonit.getSignature();
            //3.1获取类名
            String clsName = joinPonit.getTarget().getClass().getName();
            //3.2获取参数类型
            //3.3获取方法名称
            String methodName = ms.getName();
            String clsMethod = clsName + "." + methodName;
            // TODO 此处获取登录后信息
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Map resultMap = (Map )result.getData();
            if(null==resultMap||resultMap.size()==0) {
                return;
            }
            String managerName =  ((Map)resultMap.get("personInfo")).get("loginNum").toString();
            String roleName =  ((Map)resultMap.get("personInfo")).get("roleName").toString();
            String managerid =  ((Map)resultMap.get("personInfo")).get("id").toString();
            String operationcode = UUIDUtil.getUUID();
            System.err.println("======================================================");
            System.err.println(clsMethod+":login:managerName:"+managerName);
            System.err.println(clsMethod+":url:"+request.getRequestURL().toString());
            System.err.println("======================================================");
            //4 获取操作方法时的实际参数
            Gson gson = new Gson();
//            Object obj = joinPonit.getArgs();
            String args = gson.toJson(joinPonit.getArgs());
            //5获取当前操作的说明（什么操作）
            //5.1不能用ms去获取 这样只能只能回去到它接口的方法，这样就获取不了实现类上面的注解
            //6获取当前用户的ip地址
            String ip = IPUtils.getIpAddr();
//            List<String> IPWhiteList=sysLogDao.getIPWhiteList();
//            if(!IPWhiteList.contains(ip)){
//    //            throw new CustomException("当前IP并未被允许访问此应用！");
//            }
            List<String> powerRoleNameList = sysLogDao.getPowerRoleNameList();
            if(ConfigInfo.powerRoleNameList().contains(roleName)&&!powerRoleNameList.contains(ip) && !"最后一级测试用户".equals(managerName)){
                throw new CustomException("当前IP并未被允许登录此应用高权用户！");
            }
        //7封装日志信息
            SysLogs log = new SysLogs();
            log.setIp(ip);
            log.setOperation(requestLog.value());
            log.setParams(args);
            log.setMethod(clsMethod);
            log.setManagerName(managerName);
            log.setTime(totalTimeMillis);
            log.setCreatedTime(new Date());
            log.setManagerid(managerid);
            log.setOperationcode(operationcode);
            //8.将日志对象存储到数据库
           int num =  sysLogDao.insert(log);
            System.out.println(num);
    }

    /**
     * 通用存储操作功能
     * @param joinPonit
     * @param processTime
     * @param requestLog
     */
    private void saveLog(JoinPoint joinPonit, long processTime, RequestLog requestLog){
        //2获取操作的用户
//		SysManagers manager = (SysManagers)SecurityUtils.getSubject().getPrincipal();
//		if(VerifyUtil.isEmpty(manager)) {
//			return;
//		}
//        String token = (String) request.getAttribute("Token");


        //3获取操作的方法（哪个类哪个方法）
        MethodSignature ms = (MethodSignature) joinPonit.getSignature();
        //3.1获取类名
        String clsName = joinPonit.getTarget().getClass().getName();
        //3.2获取参数类型
        //3.3获取方法名称
        String methodName = ms.getName();

        String clsMethod = clsName + "." + methodName;
        // TODO 此处获取token信息
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        String managerName = null;
        if(isBlank(token)){
            managerName = "没找到用户就是这个";
            throw new CustomException("验证token信息失败");
        }else{
            managerName = JwtUtil.getUserNameOfToken(token);
        }
        System.err.println("======================================================");
        System.err.println(clsMethod+":managerName:"+managerName);
        System.err.println(clsMethod+":roleName:"+ JwtUtil.getRoleNameOfToken(token));
        System.err.println(clsMethod+":url:"+request.getRequestURL().toString());
        System.err.println("======================================================");


        //4 获取操作方法时的实际参数
        Gson gson = new Gson();
        String args = gson.toJson(joinPonit.getArgs());
        //5获取当前操作的说明（什么操作）
        //5.1不能用ms去获取 这样只能只能回去到它接口的方法，这样就获取不了实现类上面的注解
        //Method method = ms.getMethod();
        //6获取当前用户的ip地址
        String ip = IPUtils.getIpAddr();
        ArrayList list = JSON.parseObject(args, ArrayList.class);
        if( list.size() > 0 ) {
            String operation = requestLog.value();
//            Map map = null;
            if ("用户提交申请".equals(operation)) {
//                map = (Map)((List) list.get(0)).get(0);
            }
//            map = (Map) list.get(0);
//            System.out.println(map);
        if (operation != null && !"".equals(operation)) {
            SysLogs qsys = new SysLogs();
//            String userId = (String) map.get("userId");
            String userId = JwtUtil.getUserIdOfToken(token);
            qsys.setManagerid(userId);
            List<SysLogs> sysLogsList = sysLogDao.queryNewLastForPerson(qsys);
            String operationcode = null;
            if (sysLogsList != null && sysLogsList.size() > 0) {
                operationcode = sysLogsList.get(0).getOperationcode();
            }
            //7封装日志信息
            SysLogs log = new SysLogs();
            log.setIp(ip);
            log.setOperation(operation);
            log.setParams(args);
            log.setMethod(clsMethod);
            log.setManagerName(managerName);
            log.setTime(processTime);
            log.setCreatedTime(new Date());
            log.setOperationcode(operationcode);
            log.setManagerid(userId);
            //8.将日志对象存储到数据库
            sysLogDao.insert(log);
        }
    }
    }

    /**
     * 为 数据管理运营月报 数据提供支撑
     * @param joinPonit
     * @param processTime
     * @param requestLog
     */
    private void saveOperationLog(JoinPoint joinPonit, long processTime, RequestLog requestLog){
        // 做个异常捕获 如果封装日志信息过程出错就不保存操作信息了
        try {
            //3获取操作的方法（哪个类哪个方法）
            MethodSignature ms = (MethodSignature) joinPonit.getSignature();
            //3.1获取类名
            String clsName = joinPonit.getTarget().getClass().getName();
            //3.2获取参数类型
            //3.3获取方法名称
            String methodName = ms.getName();
            String clsMethod = clsName + "." + methodName;
            // TODO 此处获取token信息
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization");
            String managerName = null;
            if(isBlank(token)){
                throw new CustomException("验证token信息失败");
            }else{
                managerName = JwtUtil.getUserNameOfToken(token);
            }
            //4 获取操作方法时的实际参数
            Gson gson = new Gson();
            String args = gson.toJson(joinPonit.getArgs());
            //5获取当前操作的说明（什么操作）
            //5.1不能用ms去获取 这样只能只能回去到它接口的方法，这样就获取不了实现类上面的注解
            //Method method = ms.getMethod();
            //6获取当前用户的ip地址
            String ip = IPUtils.getIpAddr();
            ArrayList list = JSON.parseObject(args, ArrayList.class);
            if( list.size() > 0 ) {
                Map map = (Map) list.get(0);
                System.out.println(map);
                String operation = (String) map.get("ONE_CATALOG_NAME");
                if ("数据目录管理".equals(operation) || "数据中台".equals(operation) ) {
                    String temp = (String) map.get("TWO_CATALOG_NAME");
                    if (temp != null && !"".equals(temp)) {
                        operation = operation + ":" + temp;
                    }
                    temp = (String) map.get("THREE_CATALOG_NAME");
                    // 如果末级目录不为空，拼接用户操作列
                    if (temp != null && !"".equals(temp)) {
                        operation = operation + ":" + temp;
                        temp = (String) map.get("FOUR_CATALOG_NAME");
                        // 如果末级目录不为空，拼接用户操作列
                        if (temp != null && !"".equals(temp)) {
                            operation = operation + ":" + temp;
                            temp = (String) map.get("FIVE_CATALOG_NAME");
                            // 如果末级目录不为空，拼接用户操作列
                            if (temp != null && !"".equals(temp)) {
                                operation = operation + ":" + temp;
                                temp = (String) map.get("SIX_CATALOG_NAME");
                                // 如果末级目录不为空，拼接用户操作列
                                if (temp != null && !"".equals(temp)) {
                                    operation = operation + ":" + temp;
                                }
                            }

                        }
                    }
                } else {
                }
                if (operation != null && !"".equals(operation)) {
                    SysLogs qsys = new SysLogs();
                    String userId =  (String) map.get("userId");
                    qsys.setManagerid(userId);
                    List<SysLogs> sysLogsList = sysLogDao.queryNewLastForPerson(qsys);
                    String operationcode = null;
                    if (sysLogsList != null && sysLogsList.size() > 0) {
                        operationcode = sysLogsList.get(0).getOperationcode();
                    }
                    //7封装日志信息
                    SysLogs log = new SysLogs();
                    log.setIp(ip);
                    log.setOperation(operation);
                    log.setParams(args);
                    log.setMethod(clsMethod);
                    log.setManagerName(managerName);
                    log.setTime(processTime);
                    log.setCreatedTime(new Date());
                    log.setOperationcode(operationcode);
                    log.setManagerid(userId);
                    //8.将日志对象存储到数据库
                    sysLogDao.insert(log);
                }
            }
            System.err.println("======================================================");
            System.err.println(clsMethod+":managerName:"+managerName);
            System.err.println(clsMethod+":roleName:"+ JwtUtil.getRoleNameOfToken(token));
            System.err.println(clsMethod+":url:"+request.getRequestURL().toString());
            System.err.println("======================================================");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 为 数据管理运营月报 数据提供支撑
     * @param joinPonit
     * @param processTime
     * @param requestLog
     */
    private void saveUltraLog(JoinPoint joinPonit, long processTime, RequestLog requestLog){
        // 做个异常捕获 如果封装日志信息过程出错就不保存操作信息了
        try {
            //3获取操作的方法（哪个类哪个方法）
            MethodSignature ms = (MethodSignature) joinPonit.getSignature();
            //3.1获取类名
            String clsName = joinPonit.getTarget().getClass().getName();
            //3.2获取参数类型
            //3.3获取方法名称
            String methodName = ms.getName();
            String clsMethod = clsName + "." + methodName;
            // TODO 此处获取token信息
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization");
            String managerName = null;
            if(isBlank(token)){
                throw new CustomException("验证token信息失败");
            }else{
                managerName = JwtUtil.getUserNameOfToken(token);
            }
            //4 获取操作方法时的实际参数
            Gson gson = new Gson();
            String args = gson.toJson(joinPonit.getArgs());
            //5获取当前操作的说明（什么操作）
            //5.1不能用ms去获取 这样只能只能回去到它接口的方法，这样就获取不了实现类上面的注解
            //Method method = ms.getMethod();
            //6获取当前用户的ip地址
            String ip = IPUtils.getIpAddr();
            //7封装日志信息
            SysLogs log = new SysLogs();
            log.setIp(ip);
            log.setOperation(requestLog.value());
            log.setParams(args);
            log.setMethod(clsMethod);
            log.setManagerName(managerName);
            log.setTime(processTime);
            log.setCreatedTime(new Date());
            //8.将日志对象存储到数据库
            sysLogDao.insert(log);
            System.err.println("======================================================");
            System.err.println(clsMethod+":managerName:"+managerName);
            System.err.println(clsMethod+":roleName:"+ JwtUtil.getRoleNameOfToken(token));
            System.err.println(clsMethod+":url:"+request.getRequestURL().toString());
            System.err.println("======================================================");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /***
     * 能力开发跳转日志记录
     * @param joinPonit
     * @param totalTimeMillis
     * @param requestLog
     * @param result
     */
    private void nlkfApiLog(ProceedingJoinPoint joinPonit, long totalTimeMillis, RequestLog requestLog, ResultBodyBean result) {
        try {
            //2获取操作的用户
//		SysManagers manager = (SysManagers)SecurityUtils.getSubject().getPrincipal();
//		if(VerifyUtil.isEmpty(manager)) {
//			return;
//		}
//        String token = (String) request.getAttribute("Token");


            //3获取操作的方法（哪个类哪个方法）
            MethodSignature ms = (MethodSignature) joinPonit.getSignature();
            //3.1获取类名
            String clsName = joinPonit.getTarget().getClass().getName();
            //3.2获取参数类型
            //3.3获取方法名称
            String methodName = ms.getName();

            String clsMethod = clsName + "." + methodName;
            // TODO 此处获取登录后信息
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Map resultMap = (Map )result.getData();
            if(null==resultMap||resultMap.size()==0){
                return;
            }
            String managerName = "能力开放用户";
            if(resultMap.get("personInfo") != null && ((Map)resultMap.get("personInfo")).get("loginNum") !=null) {
                managerName =  ((Map)resultMap.get("personInfo")).get("loginNum").toString();
            }
            System.err.println("======================================================");
            System.err.println(clsMethod+":login:managerName:"+managerName);
            System.err.println(clsMethod+":url:"+request.getRequestURL().toString());
            System.err.println("======================================================");

            //4 获取操作方法时的实际参数
            Gson gson = new Gson();
            Object obj = joinPonit.getArgs();
            String args = gson.toJson(joinPonit.getArgs());
            //5获取当前操作的说明（什么操作）
            //5.1不能用ms去获取 这样只能只能回去到它接口的方法，这样就获取不了实现类上面的注解
            //Method method = ms.getMethod();
            //6获取当前用户的ip地址
            String ip = IPUtils.getIpAddr();

            //7封装日志信息
            SysLogs log = new SysLogs();
            log.setIp(ip);
            log.setOperation(requestLog.value());
            log.setParams(args);
            log.setMethod(clsMethod);
            log.setManagerName(managerName);
//        log.setTime(processTime);
            log.setCreatedTime(new Date());
            //8.将日志对象存储到数据库
            int num =  sysLogDao.insert(log);
            System.out.println(num);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

}
