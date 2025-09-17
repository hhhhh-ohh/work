package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponCateVO extends BasicResponse {

    private static final long serialVersionUID = 4860141949630966270L;

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
