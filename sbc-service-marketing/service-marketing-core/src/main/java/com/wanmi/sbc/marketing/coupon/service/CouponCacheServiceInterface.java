package com.wanmi.sbc.marketing.coupon.service;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.common.model.entity.MarketingGoods;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.response.CouponGoodsQueryResponse;
import com.wanmi.sbc.marketing.coupon.response.CouponListResponse;

import java.util.List;
import java.util.Map;

/***
 *
 * @className CouponCacheServiceInterface
 * @author zhengyang
 * @date 2021/10/21 16:25
 **/
public interface CouponCacheServiceInterface {
    /**
     * 通过商品列表，查询相关优惠券
     *
     * @param goodsInfoIds
     * @param customerId
     * @return
     */
    CouponListResponse listCouponForGoodsList(List<String> goodsInfoIds, String customerId, Long storeId, PluginType pluginType);

    /**
     * 通过商品列表，查询相关优惠券
     *
     * @param goodsInfoList
     * @param customer
     * @param pluginType
     * @return
     */
    List<CouponCache> listCouponForGoodsList(List<GoodsInfoVO> goodsInfoList, CustomerVO customer, Long storeId, PluginType pluginType);

    /**
     * 通过商品列表，查询相关优惠券
     *
     * @param goodsInfoList     商品营销集合
     * @param levelMap          会员等级
     * @param storeId           门店ID
     * @param pluginType        插件Type
     * @return
     */
    List<CouponCache> listCouponForGoodsList(List<MarketingGoods> goodsInfoList, Map<Long, CommonLevelVO> levelMap,
                                                    Long storeId, PluginType pluginType);

    /**
     * 通过商品，查询相关优惠券
     *
     * @param goodsInfoId
     * @param customerId
     * @return
     */
    CouponListResponse listCouponForGoodsDetail(String goodsInfoId, String customerId, Long storeId, PluginType pluginType);

    /**
     * 通过商品 + 用户等级，查询相关优惠券 = 提供给营销插件使用
     * <p>
     * 营销插件，列表 + 详情 都会使用该方法
     * 默认入参GoodsInfo已经包含了平台类目cateId和品牌brandId
     *
     * @param goodsInfo
     * @param levelMap
     * @return
     */
    List<CouponCache> listCouponForGoodsInfo(MarketingGoods goodsInfo, Map<Long, CommonLevelVO> levelMap,
                                                    Long storeId, PluginType pluginType);

    /**
     * 通过商品，查询相关优惠券
     *
     * @param goodsInfo
     * @param levelMap
     * @return
     */
    List<CouponCache> listCouponForGoodsInfos(GoodsInfoVO goodsInfo, Map<Long, CommonLevelVO> levelMap,Long storeId,
                                              List<Long> storeCateIds, PluginType pluginType);



    /**
     * 凑单页方法
     *
     * @param couponId
     * @return 返回查询条件，供bff调用goodsEs查询
     */
    CouponGoodsQueryResponse listGoodsByCouponId(String couponId, String activityId, String customerId,
                                                 Long storeId);

    /**
     * 凑单页方法
     *
     * @param couponId
     * @return 返回查询条件，供bff调用goodsEs查询
     */
    CouponGoodsQueryResponse couponGoodsById(String couponId, Long storeId);

    /**
     * 更新缓存
     */
    void refreshCache();
}
