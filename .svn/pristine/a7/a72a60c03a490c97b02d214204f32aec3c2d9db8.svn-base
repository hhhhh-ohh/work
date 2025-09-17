package com.wanmi.sbc.empower.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信平台类型枚举类
 **/
@ApiEnum
public enum SmsPlatformType {

    @ApiEnumProperty(" 0：阿里云短信平台")
    ALIYUN(SmsPlatformType.SMS_SEND_SERVICE_ALIYUN),
    @ApiEnumProperty(" 1：华信短信平台")
    HUAXIN(SmsPlatformType.SMS_SEND_SERVICE_HUAXIN);

    public static final String SMS_SEND_SERVICE_ALIYUN = "SmsSendAliyunService";
    public static final String SMS_SEND_SERVICE_HUAXIN = "SmsSendHuaxinService";

    private String smsSendService;

    SmsPlatformType(String smsSendService) {
        this.smsSendService = smsSendService;
    }

    public String getSmsSendService() {
        return smsSendService;
    }

    @JsonCreator
    public static SmsPlatformType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
