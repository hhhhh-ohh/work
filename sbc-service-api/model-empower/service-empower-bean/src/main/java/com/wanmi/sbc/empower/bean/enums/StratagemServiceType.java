package com.wanmi.sbc.empower.bean.enums;

import com.wanmi.sbc.common.annotation.ApiEnum;

import java.util.Arrays;

/**
 * @description   数谋业务处理枚举
 * @author  wur
 * @date: 2022/11/17 9:37
 **/
@ApiEnum
public enum StratagemServiceType {

    /**
     * 智能推荐业务处理
     */
    RECOMMEND_SERVICE("recommend_service"),

    ;

    StratagemServiceType(String service) {
        this.service = service;
    }

    private String service;

    public String getService() {
        return service;
    }

    public static StratagemServiceType get(String service) {
        return Arrays.stream(StratagemServiceType.values()).filter(data -> data.getService().equals(service)).findFirst().orElse(null);
    }
}
