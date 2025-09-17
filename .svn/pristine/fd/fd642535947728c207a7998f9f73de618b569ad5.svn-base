package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.Range;

/**
 * @Author: yinxianzhi
 * @Date: Created In 下午3:28 2019/5/20
 */
@Schema
@Data
public class ImmediateExchangeRequest extends BaseRequest {

    /**
     * 积分商品id
     */
    @Schema(description = "积分商品id")
    @NotNull
    private String pointsGoodsId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    @Range(min = 1)
    private long num;

}
