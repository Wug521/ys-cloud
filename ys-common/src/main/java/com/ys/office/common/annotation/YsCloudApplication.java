package com.ys.office.common.annotation;


import com.ys.office.common.selector.CloudApplicationSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CloudApplicationSelector.class)
public @interface YsCloudApplication {
}
