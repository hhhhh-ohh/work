package com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon;

import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuyunpeng
 * @className NewcomerPurchaseCouponFetchResponse
 * @description
 * @date 2022/8/24 11:31 AM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseCouponFetchResponse implements Serializable {
    private static final long serialVersionUID = 2253036244475451142L;

    /**
     * 优惠券列表
     */
    @Schema(description = "优惠券列表")
    private List<CouponInfoVO> coupons;
}
