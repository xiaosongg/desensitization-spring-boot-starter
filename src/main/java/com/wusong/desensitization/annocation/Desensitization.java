package com.wusong.desensitization.annocation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wusong.desensitization.serializer.JacksonDesensitizationSerializer;
import com.wusong.desensitization.strategy.DesensitizationStrategy;
import com.wusong.desensitization.utils.DesensitizationConstants;

import java.lang.annotation.*;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 11:39
 * @description 脱敏注解，用于属性上，只对String类型有效
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@JacksonAnnotationsInside
@JsonSerialize(using = JacksonDesensitizationSerializer.class)
public @interface Desensitization {

    /**
     * desensitization strategy
     *
     * @return strategy
     */
    DesensitizationStrategy strategy();

    /**
     * Sensitive replacer
     *
     * @return replacer
     */
    char replacer() default DesensitizationConstants.REPLACER;
}
