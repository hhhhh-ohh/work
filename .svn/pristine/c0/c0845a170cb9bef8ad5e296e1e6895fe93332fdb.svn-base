package com.wanmi.sbc.marketing.newplugin.bean;

import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className MarketingPluginBaseRequest
 * @description TODO
 * @date 2021/6/26 11:31
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPluginBaseParam {
    /**
     * 当前客户的等级
     * 内容：<店铺ID，等级信息>
     */
    private Map<Long, CommonLevelVO> levelMap;

    /**
     * 满系营销Map
     * 内容:<SkuId,多个营销>
     */
    private Map<String, List<MarketingResponse>> multiTypeMarketingMap;

    /**
     * 非满系营销map
     */
    private Map<String, Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>>> skuMarketingMap;


    /**
     * 优惠券信息
     */
    private List<CouponCache> couponCaches;

    /**
     * 仅供列表使用，详情不考虑
     */
    private Map<MarketingPluginType,Map<String, GoodsInfoDetailPluginResponse>> customerPriceMap;

    /**
     * 付费会员等级
     */
    private PayingMemberLevelVO payingMemberLevel;

    /**
     * 新人专享券
     */
    private Map<String, List<CouponInfoVO>> newComerMap;

    /**
     * 会员信息
     */
    private CustomerVO customerVO;

}
