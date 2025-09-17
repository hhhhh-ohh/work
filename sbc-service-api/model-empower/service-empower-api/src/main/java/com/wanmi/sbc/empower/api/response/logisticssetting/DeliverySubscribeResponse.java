package com.wanmi.sbc.empower.api.response.logisticssetting;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @className DeliverySubscribeResponse
 * @description 物流订阅Response
 * @author songhanlin
 * @date 2021/4/12 下午4:44
 **/
@Schema
@Data
public class DeliverySubscribeResponse implements Serializable {

    private static final long serialVersionUID = 7548203857484763589L;

    /**
     * 物流详情
     */
    @Schema(description = "物流详情")
    private Map<String, Object> orderList = new HashMap<>();

}
