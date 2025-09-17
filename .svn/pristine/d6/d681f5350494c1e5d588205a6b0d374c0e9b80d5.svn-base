package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午10:49 2019/5/24
 * @Description:
 */
@Schema
@Data
public class GrouponBuyRequest extends BaseRequest {

    /**
     * 商品skuId
     */
    @Schema(description = "商品skuId")
    @NotNull
    private String goodsInfoId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    @NotNull
    private Long buyCount;

    /**
     * 是否开团购买(true:开团 false:参团 null:非拼团购买)
     */
    @Schema(description = "是否开团购买")
    @NotNull
    private Boolean openGroupon;

    /**
     * 团号
     */
    @Schema(description = "团号")
    private String grouponNo;

    /**
     * 分享人
     */
    @Schema(description = "分享人")
    private String shareUserId;
}
