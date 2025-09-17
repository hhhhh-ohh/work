package com.wanmi.sbc.elastic.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponActivityVO;

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
public class EsCouponActivityPageResponse extends BasicResponse {



    @Schema(description = "优惠券活动信息分页列表")
    private MicroServicePage<EsCouponActivityVO> couponActivityVOPage;
}
