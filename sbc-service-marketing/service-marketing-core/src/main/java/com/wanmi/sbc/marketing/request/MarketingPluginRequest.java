package com.wanmi.sbc.marketing.request;

import com.wanmi.sbc.common.base.BaseRequest;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponVO;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 插件公共Request
 * Created by dyt on 2017/11/20.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPluginRequest extends BaseRequest {

    /**
     * 当前客户
     */
    private String customerId;
//    private CustomerVO customer;

    /**
     * 当前客户的等级
     * 内容：<店铺ID，等级信息>
     */
    private Map<Long, CommonLevelVO> levelMap;

    /**
     * 营销Map
     * 内容:<SkuId,多个营销>
     */
    private Map<String, List<MarketingResponse>> marketingMap;

    /**
     * 优惠券Map
     * 内容:<SkuId,多个优惠券>
     */
    private Map<String, List<CouponCache>> couponMap;

    /**
     * 拼团营销信息
     * 用户是否拼团
     * 开团or参团-是否团长
     */
    private GrouponVO grouponVO;

    /**
     * 是否魔方商品列表
     */
    private Boolean moFangFlag;

    /**
     * 是否为秒杀走购物车普通商品提交订单
     */
    private Boolean isFlashSaleMarketing;


    /**
     * 是否提交订单commit接口
     */
    private Boolean commitFlag = Boolean.FALSE;

    /**
     * 查询数据终端，为了处理pc端分销和企业价逻辑
     */
    private String terminalSource;

    /**
     * 门店ID
     */
    private Long storeId;

    /***
     * 插件类型
     */
    private PluginType pluginType = PluginType.NORMAL;

    /**
     * 付费会员等级
     * 内容：<Integer, 等级信息>
     */
    private PayingMemberLevelVO payingMemberLevelVO;
}
