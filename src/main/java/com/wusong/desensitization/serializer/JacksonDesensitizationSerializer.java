package com.wusong.desensitization.serializer;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wusong.desensitization.annocation.Desensitization;
import com.wusong.desensitization.annocation.IgnoreDesensitization;
import com.wusong.desensitization.resolve.HandlerMethodResolver;
import com.wusong.desensitization.strategy.DesensitizationStrategy;
import com.wusong.desensitization.strategy.DesensitizationWrapper;
import com.wusong.desensitization.utils.HandlerMethodUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 12:11
 * @description
 */
@Slf4j
public class JacksonDesensitizationSerializer extends JsonSerializer<String> {


    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        if (Objects.isNull(value)) {
            jsonGenerator.writeNull();
            return;
        }

        HandlerMethodResolver handlerMethodResolver = SpringUtil.getBean(HandlerMethodResolver.class);
        HandlerMethod handlerMethod = handlerMethodResolver.resolve();
        if (ObjectUtils.isEmpty(handlerMethod)) {
            jsonGenerator.writeString(value);
            return;
        }

        IgnoreDesensitization ignoreSensitive =
                HandlerMethodUtil.getAnnotation(handlerMethod, IgnoreDesensitization.class);


        if (Objects.isNull(ignoreSensitive)) {

            String currentName = jsonGenerator.getOutputContext().getCurrentName();
            Object currentValue = jsonGenerator.getCurrentValue();
            Class<?> currentValueClass = currentValue.getClass();
            Field field = ReflectUtil.getField(currentValueClass, currentName);
            
            Desensitization desensitization = field.getAnnotation(Desensitization.class);

            if (Objects.nonNull(desensitization)) {

                DesensitizationStrategy strategy = desensitization.strategy();
                String finalValue = strategy.apply(new DesensitizationWrapper(field, value, desensitization));
                jsonGenerator.writeString(finalValue);
                return;
            }
        }

        jsonGenerator.writeString(value);
    }
}
