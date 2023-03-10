package com.wusong.desensitization.annocation;

import com.wusong.desensitization.handler.CustomizeDesensitizationHandler;

import java.lang.annotation.*;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 21:45
 * @description
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface DesensitizationHandler {

    /**
     * desensitization customizes handler
     *
     * @return {@link CustomizeDesensitizationHandler}
     */
    Class<? extends CustomizeDesensitizationHandler> value();
}
