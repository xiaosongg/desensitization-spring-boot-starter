package com.wusong.desensitization.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import lombok.experimental.UtilityClass;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 13:44
 * @description
 */
@UtilityClass
public class HandlerMethodUtil {

    /**
     * According to {@code annotationType}, get the annotation from {@code handlerMethod}
     *
     * @param handlerMethod {@link HandlerMethod}
     * @param annotationType annotationType
     * @return {@link Annotation}
     */
    public static <T extends Annotation> T getAnnotation(
            HandlerMethod handlerMethod, Class<T> annotationType) {
        Class<?> beanType = handlerMethod.getBeanType();
        // First get the annotation from the class of the method
        T annotation = AnnotationUtil.getAnnotation(beanType, annotationType);
        if (Objects.isNull(annotation)) {
            // If not get the annotation from this class, will get it from the current method
            annotation = handlerMethod.getMethodAnnotation(annotationType);
        }
        return annotation;
    }
}
