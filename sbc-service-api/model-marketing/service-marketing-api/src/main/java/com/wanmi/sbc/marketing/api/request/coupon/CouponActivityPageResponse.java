package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityVO;

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
@NoArgsConstructor
@AllArgsConstructor
public class CouponActivityPageResponse extends BasicResponse {

    private static final long serialVersionUID = 7351839516840123629L;

    @Schema(description = "优惠券活动信息分页列表")
    private MicroServicePage<CouponActivityVO> couponActivityVOPage;
}
