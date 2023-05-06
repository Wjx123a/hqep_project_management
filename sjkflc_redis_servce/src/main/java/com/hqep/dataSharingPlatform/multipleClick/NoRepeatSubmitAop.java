package com.hqep.dataSharingPlatform.multipleClick;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author Tzeao
 */
@Component
@Aspect
//@Slf4j
public class NoRepeatSubmitAop {

    @Autowired
    private RedisService redisService;

    /**
     * 切入点
     */
    @Pointcut("@annotation(NoRepeatSubmit)")
    public void pt() {
    }

    @Around("pt()")
    public Object arround(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        //这里是唯一标识 根据情况而定
        String key = "1" + "-" + request.getServletPath();
        // 如果缓存中有这个url视为重复提交
        if (!redisService.haskey(key)) {
            //通过，执行下一步
            Object o = joinPoint.proceed();
            //然后存入redis 并且设置15s倒计时
            redisService.setCacheObject(key, 0, 15, TimeUnit.SECONDS);
            //返回结果
            return o;
        } else {
//            return Result.fail(400, "请勿重复提交或者操作过于频繁！");
            return "error";
        }

    }
}
