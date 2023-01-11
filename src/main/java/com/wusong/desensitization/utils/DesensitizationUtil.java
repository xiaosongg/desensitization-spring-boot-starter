package com.wusong.desensitization.utils;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 12:09
 * @description
 */
@UtilityClass
public class DesensitizationUtil extends DesensitizedUtil {

    /**
     * 中文姓名
     *
     * <pre>
     * 只显示第一个汉字，其他替换为2个{@code replacer}。
     *
     * 比如脱敏替换符为星号：
     * e.g: 刘子豪 -> 刘**
     * </pre>
     *
     * @param fullName 中文姓名
     * @param replacer 脱敏替换符
     * @return 脱敏后的姓名
     */
    public static String chineseName(String fullName, char replacer) {
        if (!StringUtils.hasLength(fullName)) {
            return CharSequenceUtil.EMPTY;
        }

        return CharSequenceUtil.replace(fullName, 1, fullName.length(), replacer);
    }

}
