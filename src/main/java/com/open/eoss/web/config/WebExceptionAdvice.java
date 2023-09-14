package com.open.eoss.web.config;

import com.open.eoss.exception.BusinessException;
import com.open.eoss.web.vo.EossRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class WebExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(WebExceptionAdvice.class);

    @ExceptionHandler({
            BusinessException.class
    })
    @ResponseBody
    public EossRes<String> handleBusinessException(
            BusinessException e,
            HttpServletRequest request,
            HttpServletResponse response){
        logger.error(e.getMessage(), e);
        return EossRes.failed(org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler({
            Exception.class
    })
    @ResponseBody
    public EossRes<String> handleException(
            Exception e,
            HttpServletRequest request,
            HttpServletResponse response){
        logger.error(e.getMessage(), e);
        return EossRes.failed(org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务端错误");
    }
}
