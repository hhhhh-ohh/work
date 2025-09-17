package com.wanmi.sbc.empower.api.response.logisticssetting;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @className DeliveryQueryResponse
 * @description 物流信息查询Response
 * @author songhanlin
 * @date 2021/4/13 上午11:24
 **/
@Schema
@Data
public class DeliveryQueryResponse implements Serializable {

    private static final long serialVersionUID = 9007481729244052671L;

    /**
     * 物流详情
     */
    @Schema(description = "物流详情")
    private List<Map<Object, Object>> orderList = new ArrayList<>();
}
