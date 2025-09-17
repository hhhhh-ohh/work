package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:28 2019/3/6
 * @Description: 提货卡购买请求
 */
@Schema
@Data
public class PickupCardBuyRequest extends BaseRequest {

    /**
     * 商品skuId
     */
    @Schema(description = "商品skuId")
    @NotNull
    private List<String> goodsInfoIds;

    @Schema(description = "用户礼品卡ID")
    @NotNull
    private Long userGiftCardId;
}
