package com.wanmi.sbc.empower.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @Author: songhanlin
 * @Date: Created In 下午1:40 2021/4/1
 * @Description: 快递 Enum类
 */
@ApiEnum
public enum LogisticsType {

    @ApiEnumProperty("快递100")
    KUAI_DI_100(LogisticsType.EXPRESS_QUERY_SERVICE_KUAIDI100),

    @ApiEnumProperty("达达配送")
    DADA(LogisticsType.EXPRESS_QUERY_SERVICE_DADA);

    public static final String EXPRESS_QUERY_SERVICE_KUAIDI100 = "KuaiDi100Service";

    public static final String EXPRESS_QUERY_SERVICE_DADA = "DadaService";

    private String exPressQueryService;

    LogisticsType(String exPressQueryService) {
        this.exPressQueryService = exPressQueryService;
    }

    public String getLogisticsType() {
        return exPressQueryService;
    }

    @JsonCreator
    public static LogisticsType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
