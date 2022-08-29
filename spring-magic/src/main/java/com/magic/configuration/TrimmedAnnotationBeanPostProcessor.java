package com.magic.configuration;

import com.magic.annotation.Trimmed;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.util.Arrays;
import java.util.Objects;

public class TrimmedAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanType = bean.getClass();
        if (beanType.isAnnotationPresent(Trimmed.class)) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(beanType);
            enhancer.setCallback(createMethodInterceptor());
            return enhancer.create();
        }
        return bean;
    }

    private static MethodInterceptor createMethodInterceptor() {
        return (obj, method, args, methodProxy) -> trimResult(methodProxy.invokeSuper(obj, trimArgs(args)));
    }

    private static Object[] trimArgs(Object... args) {
        return Arrays.stream(args)
                .filter(Objects::nonNull)
                .map(obj -> {
                    if (obj instanceof String string) {
                        return string.trim();
                    }
                    return obj;
                })
                .toArray();
    }

    private static Object trimResult(Object result) {
        return result instanceof String string ? (string).trim() : result;
    }
}
