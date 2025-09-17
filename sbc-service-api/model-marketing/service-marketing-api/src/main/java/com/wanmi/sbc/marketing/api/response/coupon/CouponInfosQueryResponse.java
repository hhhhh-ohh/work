package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.dto.CouponInfoDTO;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class CouponInfosQueryResponse extends BasicResponse {

    @Schema(description = "优惠券列表")
    private List<CouponInfoVO> couponCodeList;
}
