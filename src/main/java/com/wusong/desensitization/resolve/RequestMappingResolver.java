package com.wusong.desensitization.resolve;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 13:39
 * @description
 */
@Slf4j
@RequiredArgsConstructor
public class RequestMappingResolver implements HandlerMethodResolver {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public HandlerMethod resolve() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .map(this::getHandler)
                .map(HandlerExecutionChain::getHandler)
                .filter(HandlerMethod.class::isInstance)
                .map(HandlerMethod.class::cast)
                .orElse(null);
    }

    public final HandlerExecutionChain getHandler(HttpServletRequest request) {
        try {
            return requestMappingHandlerMapping.getHandler(request);
        } catch (Exception e) {
            log.error("Cannot get handler from current HttpServletRequest: {}", e.getMessage(), e);
            return null;
        }
    }
}
