package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class GetRegisterOrStoreCouponResponse extends BasicResponse {

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String desc;

    /**
     * 优惠券列表
     */
    @Schema(description = "优惠券列表")
    private List<GetCouponGroupResponse> couponList;

}
