package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.marketing.bean.vo.CouponGoodsVO;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.CouponStoresVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 优惠券详情
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponInfoDetailByIdResponse extends BasicResponse {

    private static final long serialVersionUID = -4132780407857588409L;

    /**
     * 优惠券实体对象
     */
    @Schema(description = "优惠券实体对象")
    private CouponInfoVO couponInfo;


    /**
     * 优惠券指定商品
     */
    @Schema(description = "优惠券指定商品")
    private CouponGoodsVO goodsList;


    /**
     * 指定门店
     */
    @Schema(description = "优惠券指定商品")
    private CouponStoresVO storesList;


}
