package com.wusong.desensitization.strategy;

import com.wusong.desensitization.annocation.Desensitization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 12:04
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesensitizationWrapper {

    /** 字段 */
    private Field field;

    /** 字段值 */
    private String fieldValue;

    /** {@link Desensitization}注解信息 */
    private Desensitization desensitization;
}
