package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class CouponCodeListByConditionResponse extends BasicResponse {

    @Schema(description = "优惠券券码列表")
    private List<CouponCodeDTO> couponCodeList;
}
