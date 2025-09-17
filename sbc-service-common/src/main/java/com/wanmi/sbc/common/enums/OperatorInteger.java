package com.wanmi.sbc.common.enums;

/**
 * @author houshuai
 * @date 2021/6/1 11:18
 * @description
 *     <p>简单的多if else判断与业务解耦
 */
public enum OperatorInteger {
    SPLIT {
        @Override
        public int apply(int size) {
            if (size <= 1000) {
                return 2;
            } else if (size <= 4500) {
                return 7;
            }
            return 12;
        }
    };

    public abstract int apply(int size);
}
