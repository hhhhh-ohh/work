package com.wanmi.sbc.vas.bean.vo.sellplatform;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className SellPlatformOrderDetailVO
 * @description 审核状态
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformOrderDetailVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商家自定义商品ID
     */
    @Schema(description = "商家自定义商品ID")
    private List<SellPlatformProductInfoVO> product_infos;

    /**
     * 商家自定义skuID
     */
    @Schema(description = "价格信息")
    private SellPlatformPriceVO price_info;

    /**
     * 推广员、分享员信息
     */
    private SellPlatformPromotionInfoVO promotion_info;

}