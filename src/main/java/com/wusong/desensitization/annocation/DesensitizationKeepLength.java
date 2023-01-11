package com.wusong.desensitization.annocation;

import java.lang.annotation.*;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 21:54
 * @description
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface DesensitizationKeepLength {

    /**
     * Pre-reserved digits
     *
     * <pre>
     * If less than or equal to 0, it means ignore
     * </pre>
     *
     * @return pre-reserved digits
     * @throws IllegalArgumentException If it is less than -1, an exception will be thrown
     */
    int preKeep();

    /**
     * Post-reserved digits
     *
     * <pre>
     * If less than or equal to 0, it means ignore
     * </pre>
     *
     * @return post-reserved digits
     * @throws IllegalArgumentException If it is less than -1, an exception will be thrown
     */
    int postKeep();
}
