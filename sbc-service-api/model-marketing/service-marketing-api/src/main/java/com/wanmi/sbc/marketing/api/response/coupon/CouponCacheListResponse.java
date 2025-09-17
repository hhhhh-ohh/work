package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponCacheVO;
import com.wanmi.sbc.marketing.bean.vo.CouponMarketingScopeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *  活动券集合
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCacheListResponse extends BasicResponse {

    private static final long serialVersionUID = 6423440972731726905L;

    /**
     * 活动券集合
     */
    @Schema(description = "活动券集合")
    private List<CouponCacheVO> cacheList;
}
