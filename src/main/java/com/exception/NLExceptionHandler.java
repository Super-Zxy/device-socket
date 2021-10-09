package com.exception;

import com.util.RespInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zxy
 * @date 2021/7/2 9:42
 * @description
 */
@ControllerAdvice
@ResponseBody
public class NLExceptionHandler {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(NLExceptionHandler.class);

    /**
     * 自定义异常
     */
    @ExceptionHandler(value = NLException.class)
    public RespInfo exceptionHandler(NLException e) {
        logger.error(e.getMessage(),e);
        return RespInfo.error(String.valueOf(e.getResType()), String.valueOf(e.getResCode()), e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RespInfo exceptionHandler(Exception e) {
        logger.error(e.getMessage(),e);
        return RespInfo.error(String.valueOf(NLErrorCode.ERR_CLASS_OTHER), e.getMessage());
    }
}
