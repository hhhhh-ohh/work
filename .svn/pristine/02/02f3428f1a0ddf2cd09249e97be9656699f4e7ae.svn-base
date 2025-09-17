package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
public class CouponCateModifyResponse extends BasicResponse {

    private static final long serialVersionUID = 8577735134893119312L;

    /**
     * 优惠券分类Id
     */
    @Schema(description = "优惠券分类Id")
    private String couponCateId;

    /**
     * 优惠券分类名称
     */
    @Schema(description = "优惠券分类名称")
    private String couponCateName;

    /**
     * 是否平台专用 0：否，1：是
     */
    @Schema(description = "是否平台专用")
    private DefaultFlag onlyPlatformFlag;

}
