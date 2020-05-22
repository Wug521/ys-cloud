package com.ys.office.common.handler;

import com.ys.office.common.entity.ResponseMessage;
import com.ys.office.common.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;

@Slf4j
public class BaseExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return new ResponseMessage().message("系统内部异常");
    }


    @ExceptionHandler(value = AuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleAuthException(Exception e) {
        log.error("系统错误", e);
        return new ResponseMessage().message(e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseMessage handleAccessDeniedException(){
        return new ResponseMessage().message("没有权限访问该资源");
    }
}
