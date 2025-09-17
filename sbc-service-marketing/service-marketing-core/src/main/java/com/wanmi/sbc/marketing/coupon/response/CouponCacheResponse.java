package com.wanmi.sbc.marketing.coupon.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CouponCacheResponse extends BasicResponse {

    private List<CouponCache> couponCacheVOList;
}


