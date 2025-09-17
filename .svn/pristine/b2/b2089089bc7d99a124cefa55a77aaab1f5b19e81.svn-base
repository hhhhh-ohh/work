package com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig;

import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.NewcomerPurchaseConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuyunpeng
 * @className NewcomerPurchaseDetailResponse
 * @description
 * @date 2022/8/22 6:08 PM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseDetailResponse implements Serializable {
    private static final long serialVersionUID = 8407724757877718805L;

    /**
     * 新人专享设置信息
     */
    @Schema(description = "新人专享设置信息")
    private NewcomerPurchaseConfigVO newcomerPurchaseConfig;

    /**
     * 优惠券列表
     */
    @Schema(description = "优惠券列表")
    private List<CouponInfoVO> coupons;

    /**
     * 商品ids
     */
    @Schema(description = "商品ids")
    private List<String> skuIds;

    /**
     * 是否已领取过优惠券
     */
    @Schema(description = "是否已领取过优惠券")
    private Boolean fetchFlag;
}
