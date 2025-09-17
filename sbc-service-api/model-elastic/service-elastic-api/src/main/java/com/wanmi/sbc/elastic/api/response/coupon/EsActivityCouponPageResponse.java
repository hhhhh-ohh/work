package com.wanmi.sbc.elastic.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsActivityCouponVO;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponScopeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 优惠券分页列表响应结构
 * Created by daiyitian on 2018/11/22.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsActivityCouponPageResponse extends BasicResponse {


    /**
     * 优惠券分页列表 {@link EsCouponScopeVO}
     */
    @Schema(description = "优惠券分页列表")
    private MicroServicePage<EsActivityCouponVO> activityCouponPage;

}
