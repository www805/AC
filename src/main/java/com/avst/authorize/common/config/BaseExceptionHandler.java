package com.avst.authorize.common.config;


import com.avst.authorize.common.utils.LogUtil;
import com.avst.authorize.common.utils.RResult;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

//全局异常类
@RestControllerAdvice
public class BaseExceptionHandler {

    //拦截后台所有代码中的异常
    @ExceptionHandler(value = Exception.class)
    public RResult exception(Exception e) {
        e.printStackTrace();
        LogUtil.intoLog(4, e.getClass(), e.getMessage());
        RResult result = new RResult();
        result.setMessage("系统繁忙,请稍后再试");
        return result;
    }

//    @ExceptionHandler(value = BindException.class)
//    public RResult bindExceptionErrorHandler(BindException ex) throws Exception {
////        logger.error("bindExceptionErrorHandler info:{}",ex.getMessage());
//        RResult result = new RResult();
//        StringBuilder sb = new StringBuilder();
//        FieldError fieldError = ex.getFieldError();
//        sb.append(fieldError.getDefaultMessage());
//        result.setData(sb.toString());
//        return result;
//    }
//
//
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public RResult jsonErrorHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
//
//        //获取错误的url来路
//        System.out.println(req.getRequestURL().toString());
//
//        FieldError fieldError = e.getBindingResult().getFieldError();
//
//        //获取全部错误信息
//        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//
//        //取出最后一个错误信息
//        int size = fieldErrors.size();
//        FieldError error = fieldErrors.get(0);
//
//        System.out.println(error.getDefaultMessage());
//
//        RResult result = new RResult();
//        result.setMessage(fieldError.getDefaultMessage());
//
//
//        return result;
//    }


    /**
     * 返回shiro需要身份验证才能访问的接口
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public RResult nauthenticatedException(UnauthenticatedException e) {
        RResult result = new RResult();
        result.changeToTrue();
        result.setMessage("请登录后再进行操作");
        return result;
    }

    /**
     * 处理自定义异常
     */
//    @ExceptionHandler(BizException.class)
//    public RspDTO handleRRException(BizException e) {
//        logger.error(e.getMessage(), e);
//
//
//
//        return new RspDTO(e.getCode(), e.getMessage());
//    }

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        RResult result = new RResult();
        result.setMessage(e.getBindingResult().getFieldError().getDefaultMessage());
        return result;
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public RResult handleValidationException(ValidationException e) {
        RResult result = new RResult();
        result.setMessage(e.getCause().getMessage());
        return result;
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(value = {ConstraintViolationException.class })
    public String handleResourceNotFoundException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations ) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        System.out.println("1111111111111111111来来异常");
        System.out.println(e.getMessage());
        return strBuilder.toString();
    }

    /**
     * 404
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public RResult handlerNoFoundException(Exception e) {
        LogUtil.intoLog(e.getClass(), e.getMessage());
        RResult result = new RResult();
        result.setMessage("路径不存在，请检查路径是否正确");
        result.setData(404);
        return result;
    }





}
