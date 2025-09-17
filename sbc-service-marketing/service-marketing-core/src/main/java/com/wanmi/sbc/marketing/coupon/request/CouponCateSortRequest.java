package com.wanmi.sbc.marketing.coupon.request;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: songhanlin
 * @Date: Created In 6:05 PM 2018/9/13
 * @Description: 优惠券分类排序request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponCateSortRequest extends BaseRequest {

    /**
     * 优惠券分类Id
     */
    @NotBlank
    private String couponCateId;

    /**
     * 优惠券排序顺序
     */
    @NotNull
    private Integer cateSort;
}
