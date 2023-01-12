package com.wusong.desensitization.handler;

import com.wusong.desensitization.strategy.DesensitizationWrapper;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/12 10:04
 * @description
 */
public interface CustomizeDesensitizationHandler {

    /**
     * Customize the filed value
     *
     * @param desensitizationWrapper desensitization require message
     * @return after customize field value
     */
    String customize(DesensitizationWrapper desensitizationWrapper);
}
