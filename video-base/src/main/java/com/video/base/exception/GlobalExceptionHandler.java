package com.video.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(VideoException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 该异常枚举错误码为500，
    public RestErrorResponse customException(VideoException exception) {
        log.error("系统异常：{}", exception.getErrMessage());
        return new RestErrorResponse(exception.getErrMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(Exception exception) {
        log.error("系统异常：{}", exception.getMessage());
        return new RestErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse doMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        System.out.println("ssss");
        // 由于用户输入的内容可能存在多处错误，所以我们要将所有错误信息都提示给用户
        BindingResult bindingResult = exception.getBindingResult();
        List<String>list = new ArrayList<>();
        // 获取错误集合
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            list.add(fieldError.getDefaultMessage());
        }
        // 拼接字符串
        //fieldErrors.forEach(fieldError -> stringBuffer.append(fieldError.getDefaultMessage()).append(","));
        String errormessage = StringUtils.join(list, ",");
        // 记录日志
        // 响应给用户
        return new RestErrorResponse(errormessage);
    }
}
