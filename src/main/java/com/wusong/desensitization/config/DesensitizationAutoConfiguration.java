package com.wusong.desensitization.config;

import com.wusong.desensitization.resolve.RequestMappingResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 20:45
 * @description
 */
@Configuration
@ConditionalOnExpression
@Import({FastJsonAutoConfiguration.class, FastJson2AutoConfiguration.class})
public class DesensitizationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RequestMappingResolver handlerMethodServletParser(
            @Qualifier("requestMappingHandlerMapping")
            RequestMappingHandlerMapping requestMappingHandlerMapping) {
        return new RequestMappingResolver(requestMappingHandlerMapping);
    }
}
