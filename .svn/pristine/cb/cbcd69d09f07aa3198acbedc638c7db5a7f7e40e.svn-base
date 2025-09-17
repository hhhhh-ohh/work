package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.MagicCouponInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 魔方组件中优惠券分页列表
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MagicCouponInfoPageResponse extends BasicResponse {

    /**
     * 优惠券分页列表 {@link MagicCouponInfoVO}
     */
    @Schema(description = "魔方组件中优惠券分页列表")
    private MicroServicePage<MagicCouponInfoVO> couponInfos;

}
