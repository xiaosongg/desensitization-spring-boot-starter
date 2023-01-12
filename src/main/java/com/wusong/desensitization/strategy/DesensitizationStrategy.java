package com.wusong.desensitization.strategy;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import com.wusong.desensitization.annocation.Desensitization;
import com.wusong.desensitization.annocation.DesensitizationFilterWords;
import com.wusong.desensitization.annocation.DesensitizationHandler;
import com.wusong.desensitization.annocation.DesensitizationKeepLength;
import com.wusong.desensitization.handler.CustomizeDesensitizationHandler;
import com.wusong.desensitization.utils.DesensitizationConstants;
import com.wusong.desensitization.utils.DesensitizationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Objects;

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
    },

    /** ID card */
    ID_CARD() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            Desensitization desensitization = desensitizationWrapper.getDesensitization();
            return DesensitizationUtil.idCardNum(desensitizationWrapper.getFieldValue(), 1, 2, desensitization.replacer());
        }
    },

    /** Fixed phone */
    FIXED_PHONE() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            Desensitization desensitization = desensitizationWrapper.getDesensitization();
            return DesensitizationUtil.fixedPhone(desensitizationWrapper.getFieldValue(), desensitization.replacer());
        }
    },

    /** Mobile phone */
    MOBILE_PHONE() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            Desensitization desensitization = desensitizationWrapper.getDesensitization();
            return DesensitizationUtil.mobilePhone(desensitizationWrapper.getFieldValue(), desensitization.replacer());
        }
    },

    /** Address */
    ADDRESS() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            Desensitization desensitization = desensitizationWrapper.getDesensitization();
            return DesensitizationUtil.address(desensitizationWrapper.getFieldValue(), 8, desensitization.replacer());
        }
    },

    /** Email */
    EMAIL() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            Desensitization desensitization = desensitizationWrapper.getDesensitization();
            return DesensitizationUtil.email(desensitizationWrapper.getFieldValue(), desensitization.replacer());
        }
    },

    /** Password */
    PASSWORD() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            Desensitization desensitization = desensitizationWrapper.getDesensitization();
            return DesensitizationUtil.password(desensitizationWrapper.getFieldValue(), desensitization.replacer());
        }
    },

    /** Chinese mainland license plates, including ordinary vehicles, new energy vehicles */
    CAR_LICENSE() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            Desensitization desensitization = desensitizationWrapper.getDesensitization();
            return DesensitizationUtil.carLicense(desensitizationWrapper.getFieldValue(), desensitization.replacer());
        }
    },

    /** Bank card */
    BANK_CARD() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            Desensitization desensitization = desensitizationWrapper.getDesensitization();
            return DesensitizationUtil.bankCard(desensitizationWrapper.getFieldValue(), desensitization.replacer());
        }
    },

    /** Customize desensitization keep length */
    CUSTOMIZE_FILTER_WORDS() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            String fieldValue = desensitizationWrapper.getFieldValue();
            Field field = desensitizationWrapper.getField();
            Desensitization desensitization = desensitizationWrapper.getDesensitization();
            DesensitizationFilterWords filterWords = field.getAnnotation(DesensitizationFilterWords.class);
            if (ObjectUtils.isEmpty(filterWords)) {
                log.warn(
                        "{} is marked CUSTOMIZE_FILTER_WORDS strategy, "
                                + "but not has @desensitizationFilterWords, will ignore desensitization it.",
                        field.getName());
                return fieldValue;
            }

            char replacer = desensitization.replacer();
            String[] words = filterWords.value();
            if (!ObjectUtils.isEmpty(words)) {
                for (String filterWord : words) {
                    if (fieldValue.contains(filterWord)) {
                        String replacers = CharSequenceUtil.repeat(replacer, filterWord.length());
                        fieldValue = fieldValue.replace(filterWord, replacers);
                    }
                }
            }

            return fieldValue;
        }
    },

    /** Customize desensitization keep length */
    CUSTOMIZE_KEEP_LENGTH() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            Field field = desensitizationWrapper.getField();
            String fieldValue = desensitizationWrapper.getFieldValue();
            Desensitization desensitization = desensitizationWrapper.getDesensitization();
            DesensitizationKeepLength desensitizationKeepLength = field.getAnnotation(DesensitizationKeepLength.class);
            int preKeep = desensitizationKeepLength.preKeep();
            int postKeep = desensitizationKeepLength.postKeep();
            Assert.isTrue(preKeep >= DesensitizationConstants.NOP_KEEP, "preKeep must greater than -1");
            Assert.isTrue(postKeep >= DesensitizationConstants.NOP_KEEP, "postKeep must greater than -1");

            boolean ignorePreKeep = preKeep <= 0;
            boolean ignoreSuffixKeep = postKeep <= 0;
            if (ignorePreKeep && ignoreSuffixKeep) {
                return fieldValue;
            }

            char replacer = desensitization.replacer();
            return CharSequenceUtil.replace(
                    fieldValue, preKeep, fieldValue.length() - postKeep, replacer);
        }
    },

    /** Customize desensitization handler */
    CUSTOMIZE_HANDLER() {
        @Override
        public String apply(DesensitizationWrapper desensitizationWrapper) {
            Field field = desensitizationWrapper.getField();
            DesensitizationHandler customizeHandler = field.getAnnotation(DesensitizationHandler.class);
            Class<? extends CustomizeDesensitizationHandler> handlerClass = customizeHandler.value();
            if(Objects.isNull(handlerClass)){
                return desensitizationWrapper.getFieldValue();
            }
            CustomizeDesensitizationHandler handler = ReflectUtil.newInstance(handlerClass);
            if(Objects.isNull(handler)){
                return desensitizationWrapper.getFieldValue();
            }
            return handler.customize(desensitizationWrapper);
        }
    };

    /**
     * Field desensitization strategy method
     *
     * @param desensitizationWrapper desensitization require message
     * @return after desensitization value
     */
    public abstract String apply(DesensitizationWrapper desensitizationWrapper);
}
