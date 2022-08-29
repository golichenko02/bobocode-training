package com.magic.annotation;


import com.magic.configuration.TrimmedAnnotationBeanPostProcessorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(TrimmedAnnotationBeanPostProcessorConfig.class)
public @interface EnableStringTrimming {
}
