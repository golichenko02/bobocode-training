package com.bobocode.context;

import com.bobocode.annotation.Bean;
import com.bobocode.annotation.Inject;
import com.bobocode.exception.NoSuchBeanException;
import com.bobocode.exception.NoUniqueBeanException;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.reflections.Reflections;

import java.util.*;
import java.util.stream.Collectors;

public class PackageScanApplicationContext implements ApplicationContext {

    private final String packageName;

    private Map<String, Object> contextStorage = new HashMap<>();

    public PackageScanApplicationContext(String packageName) {
        Objects.requireNonNull(packageName, "Package name should not be null!");
        this.packageName = packageName;
        init();
        injectBeans();
    }

    @SneakyThrows
    private void init() {
        Reflections reflections = new Reflections(this.packageName);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Bean.class);
        for (Class<?> type : typesAnnotatedWith) {
            String beanName = resolveBeanName(type);
            contextStorage.put(beanName, type.getConstructor().newInstance());
        }
    }

    private void injectBeans() {
        contextStorage.values().stream()
                .flatMap(bean -> Arrays.stream(bean.getClass().getDeclaredFields()))
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        Class<?> declaringClass = field.getDeclaringClass();
                        Object constructedBean = contextStorage.get(resolveBeanName(declaringClass));
                        Object injectableBean = contextStorage.get(resolveBeanName(field.getType()));
                        field.set(constructedBean, injectableBean);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }


    @Override
    public <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException {
        List<T> beans = contextStorage.values().stream()
                .filter(bean -> beanType.isAssignableFrom(bean.getClass()))
                .map(beanType::cast)
                .toList();

        if (beans.isEmpty()) {
            throw new NoSuchBeanException(String.format("No such bean for type: %s", beanType.getSimpleName()));
        }

        if (beans.size() > 1) {
            throw new NoUniqueBeanException(String.format("No unique value for type: %s", beanType.getSimpleName()));
        }

        return beans.get(0);
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) throws NoSuchBeanException {
        return Optional.ofNullable(contextStorage.get(name))
                .map(beanType::cast)
                .orElseThrow(() -> new NoSuchBeanException(String.format("No such bean with name: %s", name)));
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return contextStorage.entrySet().stream()
                .filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
    }

    private String resolveBeanName(Class<?> type) {
        String beanName = type.getAnnotation(Bean.class).name();
        if (StringUtils.isEmpty(beanName)) {
            String className = type.getSimpleName();
            beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
        }
        return beanName;
    }
}
