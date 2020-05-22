package com.ys.office.common.annotation;

import com.ys.office.common.configure.AuthExceptionConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AuthExceptionConfigure.class)
public @interface EnableAuthExceptionHandler {
}
