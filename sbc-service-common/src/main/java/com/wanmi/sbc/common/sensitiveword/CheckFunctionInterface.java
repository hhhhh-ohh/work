package com.wanmi.sbc.common.sensitiveword;

import com.wanmi.sbc.common.base.Operator;

import java.util.List;

/**
 * @Author: wur
 * @Date: 2022/7/19 13:56
 */
public interface CheckFunctionInterface {

    default Boolean checkFunction(Operator operator, List<String> functionList) {
        return Boolean.FALSE;
    };
}
