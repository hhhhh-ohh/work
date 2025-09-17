package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.CouponActivityConfigAndCouponInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 精准发券
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCodeBatchSendCouponRequest extends BaseRequest {

    private static final long serialVersionUID = 4257934098662479018L;

    @Schema(description = "会员ID集合")
    private List<String> customerIds;

    /**
     * 优惠券活动配置信息以及优惠券信息
     */
    @Schema(description = "优惠券活动配置信息以及优惠券信息")
    @NotEmpty
    private List<CouponActivityConfigAndCouponInfoDTO> list;

    public CouponCodeBatchSendCouponRequest(List<CouponActivityConfigAndCouponInfoDTO> list) {
        this.list = list;
    }
}
