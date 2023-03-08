package com.example.gladoscheckin.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: counterparty_standard
 * @Package: com.bfjz.commons.aspect
 * @ClassName: WebLogAspect
 * @Author: xyz
 * @Description: 自定义日志编译器
 * @Date: 2021/6/15 14:32
 */
@Aspect
@Component
public class WebLogAspect {

    private final static Logger logger  = LoggerFactory.getLogger(WebLogAspect.class);
    /** 换行符 */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    /** 以自定义 @WebLog 注解为切点 */
    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void webLog() {}

    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes==null){
            return;
        }
        HttpServletRequest request = attributes.getRequest();

        // 获取 @WebLog 注解的描述信息
        String methodDescription = getAspectLogDescription(joinPoint);


        // 打印请求 url
        logger.info("URL            : {}", request.getRequestURL().toString());
//        logger.info("userId         : {}", BaseContextHandler.getUserID());
        // 打印描述信息
        logger.info("Description    : {}", methodDescription);
        // 打印 Http method
        logger.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        logger.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        logger.info("IP             : {}", request.getRemoteAddr());
        // 打印请求入参

        Object[] args = joinPoint.getArgs();

        if(!ArrayUtils.isEmpty(args)){
            List<Object> logArgs = new ArrayList<>();
            // List<Object> logArgs2 =new ArrayList<>();

            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if(!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)){
                    if(!(arg instanceof StandardMultipartHttpServletRequest )){
                        try{
                            String string = JSON.toJSONString(arg);
                            logArgs.add(string);
                        }catch (Exception e ){
                            //do nothing
                            logArgs.add("class:"+arg.getClass());
                        }
                    }
                }
            }
            //过滤后序列化无异常
            if(!CollectionUtils.isEmpty(logArgs)){
                String string = logArgs.toString();
                logger.info("Request Args   : {}", string);
            }
        }
    }





    /**
     * 在切点之后织入
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() throws Throwable {

    }

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 打印请求相关参数
        logger.info("========================================== Start ==========================================");

        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        logger.info("Response Args  : {}", JSONObject.toJSONString(result));
        // 执行耗时
        logger.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        // 接口结束后换行，方便分割查看
        logger.info("=========================================== End ===========================================" + LINE_SEPARATOR);
        return result;
    }

    /**
     * 获取切面注解的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     * @throws Exception
     */
    public String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    ApiOperation annotation = method.getAnnotation(ApiOperation.class);
                    if(annotation!=null){
                        description.append(annotation.value());
                    }
                    break;
                }
            }
        }
        return description.toString();
    }

}