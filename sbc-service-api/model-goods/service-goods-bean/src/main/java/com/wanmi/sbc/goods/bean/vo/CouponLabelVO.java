package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 营销标签
 * Created by hht on 2018/9/18.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponLabelVO extends BasicResponse {

    /**
     * 优惠券Id
     */
    @Schema(description = "优惠券Id")
    private String couponInfoId;

    /**
     * 优惠券活动Id
     */
    @Schema(description = "优惠券活动Id")
    private String couponActivityId;

    /**
     * 促销描述
     */
    @Schema(description = "促销描述")
    private String couponDesc;

}
