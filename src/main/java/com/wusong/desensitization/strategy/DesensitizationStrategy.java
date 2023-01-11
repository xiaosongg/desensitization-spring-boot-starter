package com.wusong.desensitization.strategy;

import com.wusong.desensitization.annocation.Desensitization;
import com.wusong.desensitization.utils.DesensitizationUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 12:03
 * @description Desensitization Strategy
 */
@Slf4j
public enum DesensitizationStrategy {

    /** Chinese name */
    CHINESE_NAME() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            Desensitization desensitization = desensitizationWrapper.getDesensitization();
            return DesensitizationUtil.chineseName(desensitizationWrapper.getFieldValue(), desensitization.replacer());
        }
    };

    /**
     * Field sensitive strategy method
     *
     * @param desensitizationWrapper desensitization require message
     * @return after sensitive value
     */
    public abstract String apply(DesensitizationWrapper desensitizationWrapper);
}
