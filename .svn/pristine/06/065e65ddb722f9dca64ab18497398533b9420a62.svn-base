package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Range;

/**
 * <p>积分订单商品兑换请求结构</p>
 * Created by yinxianzhi on 2019-05-20-上午9:20.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class PointsTradeItemRequest extends BaseRequest {

    private static final long serialVersionUID = 3228778527828317959L;

    /**
     * 积分商品Id
     */
    @Schema(description = "积分商品Id")
    @NotBlank
    private String pointsGoodsId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    @Range(min = 1)
    private long num;

}
