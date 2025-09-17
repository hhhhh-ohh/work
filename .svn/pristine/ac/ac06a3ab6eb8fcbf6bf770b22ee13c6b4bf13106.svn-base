package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponNoScopeVO;
import com.wanmi.sbc.marketing.bean.vo.CouponVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-23 15:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCacheListNoScopeResponse extends BasicResponse {

    /**
     * 优惠券数据
     */
    @Schema(description = "优惠券数据")
    private List<CouponNoScopeVO> couponViews;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称map<key为店铺id，value为店铺名称>")
    private Map<Long, String> storeMap;

}
