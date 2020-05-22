package com.ys.office.common.annotation;

import com.ys.office.common.configure.LettuceRedisConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LettuceRedisConfigure.class)
public @interface EnableLettuceRedis {
}
