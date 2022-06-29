package com.example.gladoscheckin.common.hander;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    /**
//     * 处理自定义的业务异常
//     * @param req
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(value = BizException.class)
//    @ResponseBody
//    public ResultBody bizExceptionHandler(HttpServletRequest req, BizException e){
//
//        return ResultBody.error(e.commonEnum.getResultCode(),e.commonEnum.getResultMsg());
//    }
//
//
//
//    /**
//     * 处理其他异常
//     * @param req
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(value =Exception.class)
//    @ResponseBody
//    public ResultBody exceptionHandler(HttpServletRequest req, Exception e){
//        logger.error("服务器异常:",e);
//        return ResultBody.error(CommonEnum.SYSTEM_ERROR);
//    }

    public  static void setHeader(HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
    }
}
