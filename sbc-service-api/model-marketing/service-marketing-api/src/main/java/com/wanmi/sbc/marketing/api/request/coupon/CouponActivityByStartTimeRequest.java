package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Schema
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponActivityByStartTimeRequest extends BaseRequest {

    private static final long serialVersionUID = -1144976258555148289L;

    @Schema(description = "单位分钟")
    private int minute;
}
