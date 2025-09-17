package com.wanmi.sbc.order.api.request.pointstrade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PointsTradeGetByIdRequest
 * @Description 根据积分订单id获取积分订单详情request
 * @Author lvzhenwei
 * @Date 2019/5/10 14:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PointsTradeGetByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private String tid;

    /**
     * 是否需要查询linkedmall子订单
     */
    @Schema(description = "是否需要查询linkedmall子订单")
    private Boolean needLmOrder;
}
