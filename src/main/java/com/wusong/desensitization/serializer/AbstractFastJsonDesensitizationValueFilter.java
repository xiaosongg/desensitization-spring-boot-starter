/*
 * Copyright 2022 lzhpo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wusong.desensitization.serializer;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.wusong.desensitization.annocation.Desensitization;
import com.wusong.desensitization.annocation.IgnoreDesensitization;
import com.wusong.desensitization.resolve.HandlerMethodResolver;
import com.wusong.desensitization.strategy.DesensitizationStrategy;
import com.wusong.desensitization.strategy.DesensitizationWrapper;
import com.wusong.desensitization.utils.HandlerMethodUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 20:03
 * @description
 */
public abstract class AbstractFastJsonDesensitizationValueFilter {

    protected Object process(Object object, String name, Object value) {
        HandlerMethodResolver methodResolver = SpringUtil.getBean(HandlerMethodResolver.class);
        HandlerMethod handlerMethod = methodResolver.resolve();
        if (ObjectUtils.isEmpty(value)
                || !String.class.isAssignableFrom(value.getClass())
                || Objects.isNull(handlerMethod)
                || Objects.nonNull(HandlerMethodUtil.getAnnotation(handlerMethod, IgnoreDesensitization.class))) {
            return value;
        }

        Class<?> clazz = object.getClass();
        Field field = ReflectUtil.getField(clazz, name);
        Desensitization desensitization = field.getAnnotation(Desensitization.class);
        if (Objects.isNull(desensitization)) {
            return value;
        }

        DesensitizationStrategy strategy = desensitization.strategy();
        return strategy.apply(new DesensitizationWrapper(field, (String) value, desensitization));
    }
}
