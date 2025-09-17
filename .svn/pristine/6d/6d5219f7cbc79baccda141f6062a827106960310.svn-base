package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 优惠券分页列表响应结构
 * Created by daiyitian on 2018/11/22.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponInfoListByPageResponse extends BasicResponse {

    private static final long serialVersionUID = 8646676343068735384L;

    /**
     * 优惠券分页列表 {@link CouponInfoVO}
     */
    @Schema(description = "优惠券分页列表")
    private List<CouponInfoVO> couponInfos;

}
