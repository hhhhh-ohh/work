package com.wanmi.sbc.newcomer.response;

import com.wanmi.sbc.goods.response.GoodsInfoListVO;
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
 * @className NewcomerPurchaseDetailMobileResponse
 * @description
 * @date 2022/8/23 10:32 AM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseDetailMobileResponse implements Serializable {
    private static final long serialVersionUID = 4460160532328853156L;

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
     * 商品列表
     */
    @Schema(description = "商品列表")
    private List<GoodsInfoListVO> goods;

    /**
     * 是否已领取过优惠券
     */
    @Schema(description = "是否已领取过优惠券")
    private Boolean fetchFlag;
}
