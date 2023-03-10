package com.wusong.desensitization.annocation;

import java.lang.annotation.*;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 21:41
 * @description
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface DesensitizationFilterWords {

    /**
     * Definition desensitization words
     *
     * @return desensitization words array
     */
    String[] value() default {};
}
