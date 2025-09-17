package com.wanmi.sbc.coupon.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponGoodsListResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 优惠券凑单页返回
 */
@Schema
@Data
public class CouponGoodsPageResponse extends BasicResponse {

    /**
     * 商品列表数据
     */
    @Schema(description = "商品列表数据")
    private EsGoodsInfoResponse esGoodsInfoResponse;

    /**
     * 优惠券信息
     */
    @Schema(description = "优惠券信息")
    private CouponGoodsListResponse couponInfo;
}
