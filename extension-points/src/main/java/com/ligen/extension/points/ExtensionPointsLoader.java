package com.ligen.extension.points;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class ExtensionPointsLoader implements BeanPostProcessor {

    private Map<Class<?>, Map<String, Object>> concreteExtensionPointsRegistry = new HashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (AnnotationUtils.findAnnotation(bean.getClass(), ConcreteExtensionPoints.class) != null) {
            try{
                doRegister(bean);
            }catch (Exception e) {
                throw new BeanCreationException("register extension points error", e);
            }
        }
        return bean;
    }

    @SuppressWarnings("unchecked")
    public <T> T getExtensionLoader(Class<T> clazz, String bizCode) {
        if (StringUtils.isEmpty(bizCode) || clazz == null) {
            throw new IllegalArgumentException("null param");
        }
        if (! clazz.isInterface()) {
            throw new IllegalArgumentException("Extension type(" + clazz + ") is not interface");
        }
        if (AnnotationUtils.findAnnotation(clazz, AbstractExtensionPoints.class) == null) {
            throw new IllegalArgumentException("Extension type without @AbstractExtensionPoints Annotation");
        }
        Map<String, Object> bizCodeToConcreteExtPoints = concreteExtensionPointsRegistry.get(clazz);
        if (bizCodeToConcreteExtPoints == null) {
            log.error("doesn't find any ConcreteExtensionPoints by class : {}", clazz.getName());
            throw new ExtensionPointsException("doesn't find any ConcreteExtensionPoints by class");
        }

        T concreteExtPoints = (T) bizCodeToConcreteExtPoints.get(bizCode);
        if (concreteExtPoints == null) {
            log.error("doesn't find specific abstractExtensionPoints {} by bizCode : {}", clazz.getName(), bizCode);
            throw new ExtensionPointsException("doesn't find specific abstractExtensionPoints by bizCode");
        }
        return concreteExtPoints;
    }

    private void doRegister(Object bean) {
        Class clazz = bean.getClass();
        ConcreteExtensionPoints concreteExtensionPoints = AnnotationUtils.findAnnotation(clazz, ConcreteExtensionPoints.class);
        String bizCode = concreteExtensionPoints.bizCode();
        if (StringUtils.isEmpty(bizCode)) {
            log.error("@ConcreteExtensionPoints Class : {} doesn't set concrete bizCode value", clazz.getName());
            throw new ExtensionPointsException("@ConcreteExtensionPoints bean does't set bizCode value");
        }
        Class[] interfaces = clazz.getInterfaces();
        List<Class<?>> abstractExtensionPoints = null;
        if (interfaces != null && interfaces.length > 0) {
            for (Class i : interfaces) {
                if (AnnotationUtils.findAnnotation(clazz, AbstractExtensionPoints.class) != null) {
                    if (abstractExtensionPoints == null) {
                        abstractExtensionPoints = new ArrayList<>();
                    }
                    abstractExtensionPoints.add(i);
                }
            }
        }
        if (abstractExtensionPoints == null) {
            log.error("@ConcreteExtensionPoints Class : {} doesn't implements any abstract extension points", clazz.getName());
            throw new ExtensionPointsException("ConcreteExtensionPoints bean doesn't implements any AbstractExtensionPoints");
        }
        for (Class abstractExt : abstractExtensionPoints) {
            Map<String, Object> bizCodeToConcreteExtPoints = concreteExtensionPointsRegistry.computeIfAbsent(abstractExt, k -> new HashMap<>());
            Object presentConcreteExtPoints = bizCodeToConcreteExtPoints.get(bizCode);
            if (presentConcreteExtPoints != null) {
                log.error("@ConcreteExtensionPoints Class {} and Class {} have the same bizCode {}", Object.class.getName(), clazz.getName(), bizCode);
                throw new ExtensionPointsException("different concrete extension points have the same bizCode");
            }
            bizCodeToConcreteExtPoints.put(bizCode, bean);
        }
    }

}
