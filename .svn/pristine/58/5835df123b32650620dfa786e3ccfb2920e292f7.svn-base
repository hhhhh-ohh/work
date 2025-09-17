package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.CouponActivityConfigAndCouponInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 精准发券
 * @author daiyitian
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCodeBatchSendRequest extends BaseRequest {

    private static final long serialVersionUID = 4257934098662479018L;

    @NotEmpty
    @Schema(description = "会员ID集合")
    private List<String> customerIds;

    /**
     * 优惠券活动配置信息以及优惠券信息
     */
    @NotEmpty
    @Schema(description = "优惠券活动配置信息以及优惠券信息")
    private List<CouponActivityConfigAndCouponInfoDTO> list;
}
