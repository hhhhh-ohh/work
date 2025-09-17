package com.wanmi.sbc.empower.bean.vo.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className ChannelsCheckAccessInfoVO
 * @description 审核状态
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformOrderDetailVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商家自定义商品ID
     */
    @NotNull
    @Schema(description = "商家自定义商品ID")
    private List<PlatformProductInfoVO> product_infos;

    /**
     * 商家自定义skuID
     */
    @NotNull
    @Schema(description = "价格信息")
    private PlatformPriceVO price_info;

    /**
     * 支付信息
     */
    @Schema(description = "fund_type = 0 普通订单必传")
    private PlatformPayVO pay_info;

    /**
     * 推广员、分享员信息
     */
    private PlatformPromotionInfoVO promotion_info;

}