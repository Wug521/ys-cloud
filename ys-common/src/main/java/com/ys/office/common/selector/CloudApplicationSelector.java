package com.ys.office.common.selector;

import com.ys.office.common.configure.AuthExceptionConfigure;
import com.ys.office.common.configure.OAuth2FeignConfigure;
import com.ys.office.common.configure.ServerProtectConfigure;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class CloudApplicationSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
             return new String[]{
                AuthExceptionConfigure.class.getName(),
                OAuth2FeignConfigure.class.getName(),
                ServerProtectConfigure.class.getName()
             };
    }
}
