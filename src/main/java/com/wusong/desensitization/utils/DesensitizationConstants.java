package com.wusong.desensitization.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 12:07
 * @description
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DesensitizationConstants {

    /** 默认脱敏替换符 */
    public static final char REPLACER = '*';

    /** 不处理脱敏保留的标志 */
    public static final int NOP_KEEP = -1;

    /** 普通车牌号长度 */
    public static final int ORDINARY_CAR_LICENSE_LENGTH = 7;

    /** 新能源车牌号长度 */
    public static final int NEW_ENERGY_CAR_LICENSE_LENGTH = 8;

    /** 错误的银行卡号长度 */
    public static final int ERROR_BANK_CARD_LENGTH = 9;
}
