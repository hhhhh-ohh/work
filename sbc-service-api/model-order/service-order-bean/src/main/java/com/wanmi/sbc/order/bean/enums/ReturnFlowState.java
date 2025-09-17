package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 退货流程状态
 * Created by jinwei on 20/4/2017.
 */
@ApiEnum(dataType = "java.lang.String")
public enum ReturnFlowState {

    @ApiEnumProperty("INIT:创建退单")
    INIT("INIT", "创建退单"),

    @ApiEnumProperty("AUDIT:已审核")
    AUDIT("AUDIT", "已审核"),

    @ApiEnumProperty("DELIVERED:已发退货")
    DELIVERED("DELIVERED", "已发退货"),

    @ApiEnumProperty("RECEIVED:已收到退货")
    RECEIVED("RECEIVED", "已收到退货"),

    @ApiEnumProperty("REFUNDED:已退款")
    REFUNDED("REFUNDED", "已退款"),

    @ApiEnumProperty("COMPLETED:已完成")
    COMPLETED("COMPLETED", "已完成"),

    @ApiEnumProperty("REJECT_REFUND:拒绝退款")
    REJECT_REFUND("REJECT_REFUND", "拒绝退款"),

    @ApiEnumProperty("REJECT_RECEIVE:拒绝收货")
    REJECT_RECEIVE("REJECT_RECEIVE", "拒绝收货"),

    @ApiEnumProperty("VOID:已作废")
    VOID("VOID", "已作废"),

    @ApiEnumProperty("REFUND_FAILED:退款失败")
    REFUND_FAILED("REFUND_FAILED", "退款失败");

    private String stateId;

    private String desc;

    ReturnFlowState(String stateId, String desc) {
        this.stateId = stateId;
        this.desc = desc;
    }

    protected static final Map<String, ReturnFlowState> map = new HashMap<>();

    static {
        Arrays.stream(ReturnFlowState.values()).forEach(
            t -> map.put(t.getStateId(), t)
        );
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ReturnFlowState forValue(String stateId){
        return map.get(stateId);
    }

    @JsonValue
    public String toValue(){
        return this.getStateId();
    }

    public String getStateId() {
        return stateId;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, ReturnFlowState> getMap() {
        return map;
    }
}
