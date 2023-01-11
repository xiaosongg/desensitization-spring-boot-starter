package com.wusong.desensitization.resolve;

import org.springframework.web.method.HandlerMethod;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 13:38
 * @description
 */
public interface HandlerMethodResolver {

    /**
     * Get HandlerMethod
     *
     * @return {@link HandlerMethod}
     */
    HandlerMethod resolve();
}
