package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.CouponNoScopeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-23 15:04
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCacheCenterPageResponse extends BasicResponse {

    /**
     * 优惠券分页数据
     */
    @Schema(description = "优惠券分页数据")
    private MicroServicePage<CouponNoScopeVO> couponViews;

    /**
     * 平台类目名称
     */
    @Schema(description = "平台类目名称map<key为平台类目id，value为平台类目名称>")
    private Map<Long, String> cateMap;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称map<key为品牌id，value为品牌名称>")
    private Map<Long, String> brandMap;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称map<key为店铺id，value为店铺名称>")
    private Map<Long, String> storeMap;

    /**
     * 店铺分类名称
     */
    @Schema(description = "店铺分类名称map<key为店铺分类id，value为店铺分类名称>")
    private Map<Long, String> storeCateMap;

}
