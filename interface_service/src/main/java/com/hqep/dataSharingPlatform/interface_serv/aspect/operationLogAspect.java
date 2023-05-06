package com.hqep.dataSharingPlatform.interface_serv.aspect;

import com.google.gson.Gson;
import com.hqep.dataSharingPlatform.common.config.ConfigInfo;
import com.hqep.dataSharingPlatform.common.exception.CustomException;
import com.hqep.dataSharingPlatform.common.model.SysLogs;
import com.hqep.dataSharingPlatform.common.utils.IPUtils;
import com.hqep.dataSharingPlatform.common.utils.JwtUtil;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.annotation.OperationLog;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.interface_serv.dao.SysLogsDao;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.hqep.dataSharingPlatform.common.utils.StringUtil2.isBlank;

@Aspect // 作用是把当前类标识为一个切面供容器读取 //  标注增强处理类（切面类）
@Component // bean的注入 //交由Spring容器管理
@EnableAspectJAutoProxy // 开启注解版的AOP功能
public class operationLogAspect {

    @Autowired
    private SysLogsDao sysLogDao;

    /**
     * // @within 对象级别 @annotation 方法级别
     * @param point
     * @param operationLog
     * @return
     */
    @Around(value = "@within(operationLog) || @annotation(operationLog)")
    public Object around(ProceedingJoinPoint point,OperationLog operationLog) throws Throwable{
        StopWatch stopWatch = new StopWatch();
        //执行目标方法(result就是目标方法的执行结果)
        Object obj = point.proceed();
        stopWatch.start();
        stopWatch.stop();
        try {
//            System.out.println("ANNOTATION welcome:"+operationLog);
//            System.out.println("ANNOTATION 调用方法："+ operationLog.value());
//            System.out.println("ANNOTATION 执行结果：" + point.proceed());
//            System.out.println("ANNOTATION 调用类：" + point.getSignature().getDeclaringTypeName());
//            System.out.println("ANNOTATION 调用类名" + point.getSignature().getDeclaringType().getSimpleName());
//            System.out.println("ANNOTATION 入参信息" + point.getArgs());
//            Gson gson = new Gson();
//
//            for (Object args : point.getArgs()) {
//                PageData pdata = (PageData) args;
//                System.out.println(args);
//                System.out.println(pdata);
//                System.out.println("ANNOTATION 入参信息:userId:" + pdata.get("userId"));
//            }
            saveLog(point,stopWatch.getTotalTimeMillis(), operationLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ANNOTATION login success");
        return  obj;

    }


    public Map getParams(ProceedingJoinPoint point) {
        String targetMethodParams= Arrays.toString(point.getArgs());
        Gson gson = new Gson();
        return gson.fromJson(targetMethodParams,Map.class);

    }


    private void saveLog(JoinPoint joinPonit, long processTime, OperationLog operationLog){
        //2获取操作的用户
//		SysManagers manager = (SysManagers)SecurityUtils.getSubject().getPrincipal();
//		if(VerifyUtil.isEmpty(manager)) {
//			return;
//		}
//        String token = (String) request.getAttribute("Token");


        //3获取操作的方法（哪个类哪个方法）
        MethodSignature ms = (MethodSignature) joinPonit.getSignature();
        System.out.println("获取操作的方法（哪个类哪个方法）:"+ms);
        //3.1获取类名
        String clsName = joinPonit.getTarget().getClass().getName();
        System.out.println("获取类名:"+clsName);
        //3.2获取参数类型
        //3.3获取方法名称
        String methodName = ms.getName();
        String clsMethod = clsName + "." + methodName;
        System.out.println("2获取参数类型+3获取方法名称:"+clsMethod);

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
//        System.err.println("======================================================");
//        System.err.println(clsMethod+":managerName:"+managerName);
//        System.err.println(clsMethod+":roleName:"+ JwtUtil.getRoleNameOfToken(token));
//        System.err.println(clsMethod+":url:"+request.getRequestURL().toString());
//        System.err.println("======================================================");
        //4 获取操作方法时的实际参数
        Gson gson = new Gson();
        String args = gson.toJson(joinPonit.getArgs());
        //5获取当前操作的说明（什么操作）
        //5.1不能用ms去获取 这样只能只能回去到它接口的方法，这样就获取不了实现类上面的注解
        //Method method = ms.getMethod();
        //6获取当前用户的ip地址
        String ip = IPUtils.getIpAddr();
        String roleName = JwtUtil.getRoleNameOfToken(token);
//        List<String> IPWhiteList=sysLogDao.getIPWhiteList();
//        if(!IPWhiteList.contains(ip)){
//            throw new CustomException("当前IP并未被允许访问此应用！");
//        }
        PageData qpd = new PageData();
        qpd.put("username","测试");
        List<String> powerRoleNameList=sysLogDao.getPowerRoleNameList();
        if(ConfigInfo.powerRoleNameList().contains(roleName)&&!powerRoleNameList.contains(ip)){
            throw new CustomException("当前IP并未被允许登录此应用高权用户！");
        }
        //7封装日志信息
        SysLogs log = new SysLogs();
        log.setIp(ip);
        log.setOperation(operationLog.value());
        log.setParams(args);
        log.setMethod(clsMethod);
        log.setManagerName(managerName);
        log.setTime(processTime);
        log.setCreatedTime(new Date());
        //8.将日志对象存储到数据库
       // sysLogDao.insert(log);
    }



}
