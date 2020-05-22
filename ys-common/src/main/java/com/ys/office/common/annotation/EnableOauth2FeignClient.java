package com.ys.office.common.annotation;

import com.ys.office.common.configure.OAuth2FeignConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(OAuth2FeignConfigure.class)
public @interface EnableOauth2FeignClient {
}
