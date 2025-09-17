package com.wanmi.sbc.marketing.coupon.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.elastic.api.provider.coupon.*;
import com.wanmi.sbc.elastic.api.request.coupon.*;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsActivityCouponDTO;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponScopeDTO;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsActivityCouponVO;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponActivityVO;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponScopeVO;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.marketing.MarketingPluginService;
import com.wanmi.sbc.marketing.bean.vo.CouponCacheVO;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponActivityCache;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponInfoCache;
import com.wanmi.sbc.marketing.coupon.model.root.CouponMarketingScope;
import com.wanmi.sbc.marketing.coupon.request.CouponCacheInitRequest;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CouponScopeCacheService {
    @Lazy
    @Autowired
    private CouponCacheService couponCacheService;

    @Lazy
    @Autowired
    private CouponCodeService couponCodeService;

    @Autowired
    private EsCouponScopeProvider esCouponScopeProvider;

    @Autowired
    private MarketingPluginService marketingPluginService;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;


    @Autowired
    private EsActivityCouponProvider esActivityCouponProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private EsCouponActivityProvider esCouponActivityProvider;

    @Autowired
    private EsCouponActivityQueryProvider esCouponActivityQueryProvider;
    private static final Integer BATCH_SIZE = 2000;


    /**
     * 新增活动优惠券和优惠券范围
     * @param activityId
     */
    @Async
    public void save(String activityId,Boolean isPause) {
        List<CouponCache> caches = couponCacheService.cacheList(CouponCacheInitRequest.builder()
                .couponActivityIds(Lists.newArrayList(activityId)).build());
        log.info("开始初始化抽奖活动相关缓存，刷新redis可领取数量=========>{}", JSONObject.toJSONString(caches));

        List<CouponCacheVO> couponCacheVOS = KsBeanUtil.convert(caches, CouponCacheVO.class);
        if (CollectionUtils.isNotEmpty(caches)){
            log.info("开始初始化抽奖活动相关缓存，刷新redis可领取数量=========>{}", JSONObject.toJSONString(caches));
            //刷新redis可领取数量 (编辑暂停的活动不用更改可领取数量)
            if(!isPause){
                caches.stream().forEach(cache->{
                    saveRedisCouponCount(
                            cache.getCouponInfoId(),
                            cache.getCouponActivityId(),
                            cache.getTotalCount()
                    );
                });
            }

            //刷新es
            EsActivityCouponBatchAddRequest activityCouponBatchAddRequest = new EsActivityCouponBatchAddRequest();
            activityCouponBatchAddRequest.setEsActivityCoupons(EsActivityCouponDTO.translateByCache(couponCacheVOS));
            esActivityCouponProvider.batchSave(activityCouponBatchAddRequest);
            //更新优惠券活动的范围表
            List<EsCouponScopeDTO> scopeList = EsCouponScopeDTO.translateByCache(couponCacheVOS);
            if (CollectionUtils.isNotEmpty(scopeList)) {
                Lists.partition(scopeList, BATCH_SIZE).stream().forEach(splitList -> {
                    EsCouponScopeBatchAddRequest addRequest = new EsCouponScopeBatchAddRequest();
                    addRequest.setCouponScopeDTOList(splitList);
                    esCouponScopeProvider.batchSave(addRequest);
                });
            }
        }
    }

    /**
     * 更新活动刷新es
     * @param activityId
     * @param isPause 是否是暂停活动编辑活动
     */
    @Async
    public void refresh(String activityId,Boolean isPause) {
        this.deleteByActivityIdNew(activityId);
        save(activityId,isPause);

    }

    /**
     *  保证和 refresh 的save处理的数据源保持一致
     * @param activityId
     */
    private void deleteByActivityIdNew(String activityId) {
        EsCouponScopeDeleteByActivityIdRequest deleteByActivityIdRequest = new EsCouponScopeDeleteByActivityIdRequest();
        deleteByActivityIdRequest.setActivityId(activityId);
        esCouponScopeProvider.deleteByActivityId(deleteByActivityIdRequest);

        EsActivityCouponDeleteByActivityIdRequest deleteReq = new EsActivityCouponDeleteByActivityIdRequest();
        deleteReq.setActivityId(activityId);
        esActivityCouponProvider.deleteByActivityId(deleteReq);
    }

    /**
     * 删除活动优惠券和优惠券范围
     * @param activityId
     */
    public void deleteByActivityId(String activityId) {
        EsCouponScopeDeleteByActivityIdRequest deleteByActivityIdRequest = new EsCouponScopeDeleteByActivityIdRequest();
        deleteByActivityIdRequest.setActivityId(activityId);
        esCouponScopeProvider.deleteByActivityId(deleteByActivityIdRequest);

        EsActivityCouponDeleteByActivityIdRequest deleteReq = new EsActivityCouponDeleteByActivityIdRequest();
        deleteReq.setActivityId(activityId);
        esActivityCouponProvider.deleteByActivityId(deleteReq);

        EsCouponActivityDeleteByIdRequest deleteByIdRequest = new EsCouponActivityDeleteByIdRequest();
        deleteByIdRequest.setActivityId(activityId);
        esCouponActivityProvider.deleteById(deleteByIdRequest);

    }

    /**
     * 更新指定字段
     * hasLeft
     * 启用、暂停
     * 活动结束时间
     *
     * @param request
     */
    @Async
    public void update(EsActivityCouponModifyRequest request) {
        esCouponScopeProvider.update(request);
        esActivityCouponProvider.update(request);
    }


    /**
     * 组装request条件
     * @param goodsInfoList
     * @param customer
     * @param levelMap
     * @param storeId
     * @param pluginType
     * @return
     */
    protected EsCouponScopePageRequest getCacheQueryRequest(List<GoodsInfoVO> goodsInfoList, CustomerVO customer,
                                                                 Map<Long, CommonLevelVO> levelMap,
                                                                 Long storeId, PluginType pluginType) {
        // 组装等级数据
        if (Objects.isNull(levelMap)) {
            levelMap = marketingPluginService.getCustomerLevels(goodsInfoList, customer==null?null:customer.getCustomerId());
        }

        List<String> goodsIds = goodsInfoList.stream().filter(item -> item.getGoodsId() != null)
                .map(GoodsInfoVO::getGoodsId).distinct().collect(Collectors.toList());

        // 组装商品分类 -- 店铺类目
        List<Long> storeCateIds =
                storeCateQueryProvider.listByGoods(new StoreCateListByGoodsRequest(goodsIds)).getContext().getStoreCateGoodsRelaVOList().stream().filter(item -> item.getStoreCateId() != null)
                        .map(StoreCateGoodsRelaVO::getStoreCateId).collect(Collectors.toList());

        GoodsListByIdsRequest goodsListByIdsRequest = new GoodsListByIdsRequest();
        goodsListByIdsRequest.setGoodsIds(goodsIds);
        List<GoodsVO> goodsList =
                goodsQueryProvider.listByIds(goodsListByIdsRequest).getContext().getGoodsVOList();
        // 组装商品分类 -- 平台类目
        List<Long> cateIds = goodsList.stream().filter(item -> item.getCateId() != null)
                .map(GoodsVO::getCateId).collect(Collectors.toList());
        // 组装品牌
        List<Long> brandIds = goodsList.stream().filter(item -> item.getBrandId() != null)
                .map(GoodsVO::getBrandId).distinct().collect(Collectors.toList());

        EsCouponScopePageRequest request = EsCouponScopePageRequest.builder()
                .brandIds(brandIds)
                .pluginType(pluginType)
                .cateIds(cateIds)
                .storeCateIds(storeCateIds)
                .goodsInfoIds(goodsInfoList.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList()))
                .storeIds(goodsInfoList.stream().map(GoodsInfoVO::getStoreId).collect(Collectors.toList()))
                .levelMap(levelMap).build();
        return request;
    }


    /**
     * es scope转换为couponCache结构返回
     * @param scopeVOList
     * @return
     */
    public List<CouponCache> convertByScope(List<EsCouponScopeVO> scopeVOList){
        List<CouponCache> couponCaches = Lists.newArrayList();
        if (CollectionUtils.isEmpty(scopeVOList)) {
            return couponCaches;
        }
        Map<String,List<EsCouponScopeVO>> scopeListMap = scopeVOList.stream()
                .collect(Collectors.groupingBy(a->a.getActivityId().concat("-").concat(a.getCouponId())));
        List<String> couponActivityIdList = scopeVOList.stream().map(EsCouponScopeVO::getActivityId).collect(Collectors.toList());
        couponActivityIdList = couponActivityIdList.stream().distinct().collect(Collectors.toList());
        EsCouponActivityPageRequest esCouponActivityPageRequest = new EsCouponActivityPageRequest();
        esCouponActivityPageRequest.setCouponActivityIdList(couponActivityIdList);
        esCouponActivityPageRequest.setPageSize(couponActivityIdList.size());
        MicroServicePage<EsCouponActivityVO> couponActivityVOPage = esCouponActivityQueryProvider.page(esCouponActivityPageRequest).getContext().getCouponActivityVOPage();
        List<EsCouponActivityVO> esCouponActivityVOList = couponActivityVOPage.getContent();
        Map<String,EsCouponActivityVO> esCouponActivityVOMap = esCouponActivityVOList.stream().map(a->a).collect(Collectors.toMap(EsCouponActivityVO::getActivityId, a->a));
        scopeListMap.keySet().forEach(key->{
            List<EsCouponScopeVO> scopeVOS = scopeListMap.get(key);
            List<CouponMarketingScope> marketingScopes = KsBeanUtil.convert(scopeVOS, CouponMarketingScope.class);
            EsCouponScopeVO esCouponScopeVO = scopeVOS.get(0);
            CouponInfoCache couponInfo = KsBeanUtil.convert(esCouponScopeVO, CouponInfoCache.class);
            CouponActivityCache activity = KsBeanUtil.convert(esCouponScopeVO, CouponActivityCache.class);
            activity.setStartTime(esCouponScopeVO.getActivityStartTime());
            activity.setEndTime(esCouponScopeVO.getActivityEndTime());
            EsCouponActivityVO esCouponActivityVO = esCouponActivityVOMap.get(activity.getActivityId());
            if(Objects.nonNull(esCouponActivityVO)){
                activity.setReceiveType(esCouponActivityVO.getReceiveType());
                activity.setReceiveCount(esCouponActivityVO.getReceiveCount());
            }
            couponCaches.add(CouponCache.builder()
                            .couponInfo(couponInfo)
                            .couponActivity(activity)
                            .couponInfoId(esCouponScopeVO.getCouponId())
                            .couponActivityId(esCouponScopeVO.getActivityId())
                            .couponCateIds(esCouponScopeVO.getCouponCateIds())
                            .couponStoreIds(esCouponScopeVO.getStoreIds())
                            .scopes(marketingScopes)
                            .hasLeft(esCouponScopeVO.getHasLeft())
                            .totalCount(esCouponScopeVO.getTotalCount())
                    .build());
        });

        return couponCaches;
    }


    /**
     * es 活动优惠券转换为couponCache结构返回
     * @param activityCouponList
     * @return
     */
    public List<CouponCache> convertByActivityCoupon(List<EsActivityCouponVO> activityCouponList){
        List<CouponCache> couponCaches = Lists.newArrayList();
        if (CollectionUtils.isEmpty(activityCouponList)) {
            return couponCaches;
        }
        activityCouponList.forEach(activityCouponVO->{

            CouponInfoCache couponInfo = KsBeanUtil.convert(activityCouponVO, CouponInfoCache.class);
            CouponActivityCache activity = KsBeanUtil.convert(activityCouponVO, CouponActivityCache.class);
            activity.setStartTime(activityCouponVO.getActivityStartTime());
            activity.setEndTime(activityCouponVO.getActivityEndTime());
            couponCaches.add(CouponCache.builder()
                    .couponInfo(couponInfo)
                    .couponActivity(activity)
                    .couponInfoId(activityCouponVO.getCouponId())
                    .couponActivityId(activity.getActivityId())
                    .couponCateIds(activityCouponVO.getCouponCateIds())
                    .couponStoreIds(activityCouponVO.getStoreIds())
                    .scopes(Lists.newArrayList())
                    .hasLeft(activityCouponVO.getHasLeft())
                    .totalCount(activityCouponVO.getTotalCount())
                    .build());
        });

        return couponCaches;
    }



    /**
     * 刷新优惠券库存
     *
     * @param
     * @return
     */
    private void saveRedisCouponCount(String couponId, String activityId,Long count) {
        log.info("开始初始化抽奖活动相关缓存，刷新redis可领取数量=========>activityId={}, couponId={}", activityId, couponId);
        String redisKey = couponCodeService.getCouponBankKey(activityId, couponId);
        redisService.setString(redisKey,count + "");
    }
}
