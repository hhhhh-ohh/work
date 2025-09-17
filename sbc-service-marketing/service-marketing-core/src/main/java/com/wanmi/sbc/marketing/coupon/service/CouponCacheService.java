package com.wanmi.sbc.marketing.coupon.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.coupon.EsActivityCouponQueryProvider;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponScopeQueryProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsActivityCouponPageRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponScopePageRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsActivityCouponVO;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponScopeVO;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListRequest;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandByIdsRequest;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandListRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdsRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByIdsRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.goods.bean.vo.ContractBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateVO;
import com.wanmi.sbc.marketing.MarketingPluginService;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeQueryRequest;
import com.wanmi.sbc.marketing.bean.constant.CouponErrorCode;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.RangeDayType;
import com.wanmi.sbc.marketing.common.model.entity.MarketingGoods;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCateRela;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.model.root.CouponMarketingScope;
import com.wanmi.sbc.marketing.coupon.model.vo.CouponView;
import com.wanmi.sbc.marketing.coupon.request.CouponCacheCenterRequest;
import com.wanmi.sbc.marketing.coupon.request.CouponCacheInitRequest;
import com.wanmi.sbc.marketing.coupon.response.CouponCenterPageResponse;
import com.wanmi.sbc.marketing.coupon.response.CouponGoodsQueryResponse;
import com.wanmi.sbc.marketing.coupon.response.CouponListResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CouponCacheService implements CouponCacheServiceInterface {


    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    private CouponActivityConfigService couponActivityConfigService;

    @Autowired
    private CouponMarketingScopeService couponMarketingScopeService;

    @Autowired
    protected StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private MarketingPluginService marketingPluginService;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private CouponCateService couponCateService;

    @Autowired
    private CouponCodeService couponCodeService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private ContractBrandQueryProvider contractBrandQueryProvider;

    @Autowired
    private EsActivityCouponQueryProvider esActivityCouponQueryProvider;

    @Autowired
    private EsCouponScopeQueryProvider esCouponScopeQueryProvider;

    @Lazy
    @Autowired
    private CouponScopeCacheService couponScopeCacheService;

    @Autowired
    private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;


    /**
     * 领券中心
     *
     * @param queryRequest
     * @return
     */
    public CouponCenterPageResponse getCouponStarted(CouponCacheCenterRequest queryRequest) {
        EsActivityCouponPageRequest request = EsActivityCouponPageRequest.builder()
                .couponType(queryRequest.getCouponType())
                .couponMarketingType(queryRequest.getCouponMarketingType())
                .couponCateId(queryRequest.getCouponCateId())
                .build();
        request.setPageNum(queryRequest.getPageNum());
        request.setPageSize(queryRequest.getPageSize());
        if (Objects.nonNull(queryRequest.getStoreId())) {
            request.setStoreIds(Collections.singletonList(queryRequest.getStoreId()));
        }
        request.setFilterPlugin(BoolFlag.NO);
        /**
         * 活动先生效的在前
         * 通用券＞店铺券＞运费券
         * 优惠券开始时间
         * */
        MicroServicePage<EsActivityCouponVO> microServicePage = esActivityCouponQueryProvider.page(request).getContext().getActivityCouponPage();
        List<EsActivityCouponVO> activityCouponVOS = microServicePage.getContent();
        List<CouponCache> couponCacheList = couponScopeCacheService.convertByActivityCoupon(activityCouponVOS);
        if (CollectionUtils.isNotEmpty(couponCacheList)) {
            return CouponCenterPageResponse.builder()
                    //券详情
                    .couponViews(
                            new PageImpl<>(CouponView.converter(couponCacheList
                                    //券库存
                                    , couponCodeService.mapLeftCount(couponCacheList)
                                    //领用状态
                                    , couponCodeService.mapFetchStatus(couponCacheList, queryRequest.getCustomerId()))
                                    , request.getPageable(), microServicePage.getTotalElements()))
                    .build();
        } else {
            return CouponCenterPageResponse.builder().couponViews(new PageImpl<>(Collections.emptyList(), request.getPageable()
                    , 0)).build();
        }
    }


    /**
     * 通过商品列表，查询相关优惠券
     *
     * @param goodsInfoIds
     * @param customerId
     * @return
     */
    @Override
    public CouponListResponse listCouponForGoodsList(List<String> goodsInfoIds, String customerId, Long storeId, PluginType pluginType) {
        GoodsInfoListByIdsRequest goodsInfoListByIdsRequest = new GoodsInfoListByIdsRequest();
        goodsInfoListByIdsRequest.setGoodsInfoIds(goodsInfoIds);
        if (Objects.nonNull(storeId)) {
            goodsInfoListByIdsRequest.setStoreId(storeId);
        }
        // 组装ESGoodsInfo查询条件
        EsGoodsInfoQueryRequest goodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        goodsInfoQueryRequest.setGoodsInfoIds(goodsInfoIds);
        goodsInfoQueryRequest.setPageSize(goodsInfoIds.size());
        List<EsGoodsInfoVO> esGoodsInfoList = esGoodsInfoElasticQueryProvider.skuPage(goodsInfoQueryRequest).getContext().getEsGoodsInfoPage().getContent();
        if (CollectionUtils.isEmpty(esGoodsInfoList)) {
            return CouponListResponse.builder()
                    //券详情
                    .couponViews(Collections.emptyList())
                    //storeNameList
                    .storeMap(Collections.emptyMap())
                    .build();
        }
        // 封装成GoodsInfoVO列表
        List<GoodsInfoVO> goodsInfoList = new ArrayList<>();
        for (EsGoodsInfoVO item : esGoodsInfoList) {
            goodsInfoList.add(KsBeanUtil.convert(item.getGoodsInfo(), GoodsInfoVO.class));
        }
        CustomerVO customer = null;
        if (customerId != null) {
            customer = customerQueryProvider.getCustomerNoThirdImgById(new CustomerGetByIdRequest(customerId)).getContext();
        }
        List<CouponCache> couponCacheList = this.listCouponForGoodsList(goodsInfoList, customer, storeId, pluginType);

        // 获取店铺ids，排除平台优惠券
        List<Long> storeIds =
                couponCacheList.stream().filter(item -> item.getCouponInfo().getPlatformFlag() == DefaultFlag.NO)
                        .map(item -> item.getCouponInfo().getStoreId()).distinct().collect(Collectors.toList());
        return CouponListResponse.builder()
                //券详情
                .couponViews(CouponView.converter(couponCacheList
                        //券库存
                        , couponCodeService.mapLeftCount(couponCacheList)
                        //领用状态
                        , couponCodeService.mapFetchStatus(couponCacheList, customerId)))
                //storeNameList
                .storeMap(this.mapStoreNameById(storeIds))
                .build();
    }

    /**
     * 通过商品列表，查询相关优惠券
     *
     * @param goodsInfoList
     * @param customer
     * @param pluginType
     * @return
     */
    @Override
    public List<CouponCache> listCouponForGoodsList(List<GoodsInfoVO> goodsInfoList, CustomerVO customer, Long storeId, PluginType pluginType) {
        EsCouponScopePageRequest request = getCouponCacheQueryRequest(goodsInfoList, customer, storeId, pluginType);

        /**
         * 通用券＞店铺券
         * 面值大在前
         * 如有重复：按创建时间，最先创建的在前
         * */
        List<EsCouponScopeVO> scopeVOList = esCouponScopeQueryProvider.page(request).getContext().getCouponScopes().getContent();
        return couponScopeCacheService.convertByScope(scopeVOList);
    }

    /**
     * 多个商品查询优惠券
     *
     * @param goodsInfoList 商品营销集合
     * @param levelMap      会员等级
     * @param storeId       门店ID
     * @param pluginType    插件Type
     * @return
     */
    @Override
    public List<CouponCache> listCouponForGoodsList(List<MarketingGoods> goodsInfoList, Map<Long, CommonLevelVO> levelMap,
                                                    Long storeId, PluginType pluginType) {
        List<GoodsInfoVO> goodsInfoVoList = goodsInfoList.stream().filter(item -> item.getGoodsId() != null)
                .map(item -> {
                    GoodsInfoVO vo = new GoodsInfoVO();
                    vo.setGoodsId(item.getGoodsId());
                    vo.setStoreId(item.getStoreId());
                    vo.setGoodsInfoId(item.getGoodsInfoId());
                    return vo;
                }).collect(Collectors.toList());

        EsCouponScopePageRequest request = getCacheQueryRequest(goodsInfoVoList, null, levelMap, storeId, pluginType);
        List<EsCouponScopeVO> scopeVOList = esCouponScopeQueryProvider.page(request).getContext().getCouponScopes().getContent();
        return couponScopeCacheService.convertByScope(scopeVOList);
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
     * 通过商品，查询相关优惠券
     *
     * @param goodsInfoId
     * @param customerId
     * @return
     */
    @Override
    public CouponListResponse listCouponForGoodsDetail(String goodsInfoId, String customerId, Long storeId, PluginType pluginType) {
        GoodsInfoByIdRequest goodsInfoByIdRequest = new GoodsInfoByIdRequest();
        goodsInfoByIdRequest.setGoodsInfoId(goodsInfoId);
        if (Objects.nonNull(storeId)) {
            goodsInfoByIdRequest.setStoreId(storeId);
        }
        GoodsInfoByIdResponse goodsInfo = goodsInfoQueryProvider.getById(goodsInfoByIdRequest).getContext();
        if (goodsInfo == null) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080085);
        }

        Map<Long, CommonLevelVO> levelMap = marketingPluginService.getCustomerLevels(Collections.singletonList
                (goodsInfo), customerId);

        GoodsByIdResponse goods = goodsQueryProvider.getById(new GoodsByIdRequest(goodsInfo.getGoodsId())).getContext();
        if (goods == null) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080085);
        }
        //组装分类
        goodsInfo.setCateId(goods.getCateId());
        //组装品牌
        goodsInfo.setBrandId(goods.getBrandId());
        List<CouponCache> couponCacheList = listCouponForGoodsInfo(MarketingGoods
                .builder()
                .goodsId(goodsInfo.getGoodsId())
                .brandId(goodsInfo.getBrandId())
                .cateId(goodsInfo.getCateId())
                .goodsInfoId(goodsInfo.getGoodsInfoId())
                .storeId(goodsInfo.getStoreId()).build(), levelMap, storeId, pluginType);
//        List<Long> storeIds = couponCacheList.stream().map(item -> item.getCouponInfo().getStoreId()).distinct()
//        .collect(Collectors.toList());
        return CouponListResponse.builder()
                //券详情
                .couponViews(CouponView.converter(couponCacheList
                        // 券库存
                        , couponCodeService.mapLeftCount(couponCacheList)
                        // 领用状态
                        , couponCodeService.mapFetchStatus(couponCacheList, customerId)))
                .build();
    }

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
    @Override
    public List<CouponCache> listCouponForGoodsInfo(MarketingGoods goodsInfo, Map<Long, CommonLevelVO> levelMap,
                                                    Long storeId, PluginType pluginType) {
        EsCouponScopePageRequest request = getCouponCacheQueryRequest(goodsInfo, levelMap, storeId, pluginType);
        /**
         * 通用券＞店铺券
         * 面值大在前
         * 如有重复：按创建时间，最先创建的在前
         * */
        List<EsCouponScopeVO> scopeVOList = esCouponScopeQueryProvider.page(request).getContext().getCouponScopes().getContent();
        return couponScopeCacheService.convertByScope(scopeVOList);
    }

    @Override
    public List<CouponCache> listCouponForGoodsInfos(GoodsInfoVO goodsInfo, Map<Long, CommonLevelVO> levelMap, Long storeId,
                                                     List<Long> storeCateIds, PluginType pluginType) {

        EsCouponScopePageRequest request = getListCouponForGoodsInfoRequest(goodsInfo, levelMap, storeCateIds,
                storeId, pluginType);
        /**
         * 通用券＞店铺券
         * 面值大在前
         * 如有重复：按创建时间，最先创建的在前
         * */
        List<EsCouponScopeVO> scopeVOList = esCouponScopeQueryProvider.page(request).getContext().getCouponScopes().getContent();
        return couponScopeCacheService.convertByScope(scopeVOList);
    }


    /**
     * 凑单页方法
     *
     * @param couponId
     * @return 返回查询条件，供bff调用goodsEs查询
     */
    @Override
    public CouponGoodsQueryResponse listGoodsByCouponId(String couponId, String activityId, String customerId,
                                                        Long storeId) {
        CouponInfo couponInfo;
        if (Objects.nonNull(storeId)) {
            couponInfo = couponInfoService.findByCouponIdAndStoreIdAndDelFlag(couponId, storeId);
        } else {
            couponInfo = couponInfoService.getCouponInfoById(couponId);
        }
        if (couponInfo == null) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
        }

        List<CouponCode> couponCodeList = couponCodeService.listCouponCodeByCondition(CouponCodeQueryRequest.builder()
                .customerId(customerId).delFlag(DeleteFlag.NO).useStatus(DefaultFlag.NO)
                .couponId(couponId).activityId(activityId).build());

        if (CollectionUtils.isEmpty(couponCodeList)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
        }

        //当前用户所拥有的优惠券 -- 因为订单逆向流程会有退还券，有可能一个用户会有多张相同（相同活动+相同优惠券）的券，去第一张
        CouponCode couponCode = couponCodeList.get(0);

        //组装优惠券信息
        CouponGoodsQueryResponse couponGoodsQueryResponse = new CouponGoodsQueryResponse();
        if (couponInfo.getCouponType() == CouponType.STORE_VOUCHERS ||
                CouponType.STOREFRONT_VOUCHER == couponInfo.getCouponType() ||
                CouponType.BOSS_STOREFRONT_VOUCHER == couponInfo.getCouponType()) {
            couponGoodsQueryResponse.setStoreId(couponInfo.getStoreId());
        }
        couponGoodsQueryResponse.setStartTime(DateUtil.format(couponCode.getStartTime(), DateUtil.FMT_TIME_1));
        couponGoodsQueryResponse.setEndTime(DateUtil.format(couponCode.getEndTime(), DateUtil.FMT_TIME_1));
        couponGoodsQueryResponse.setScopeType(couponInfo.getScopeType());

        couponGoodsQueryResponse.setPlatformFlag(couponInfo.getPlatformFlag());

        if (couponInfo.getPlatformFlag() == DefaultFlag.NO) {
            StoreVO store =
                    storeQueryProvider.getById(new StoreByIdRequest(couponInfo.getStoreId())).getContext().getStoreVO();
            couponGoodsQueryResponse.setStoreName(store != null ? store.getStoreName() : null);
        }
        couponGoodsQueryResponse.setFullBuyType(couponInfo.getFullBuyType());
        couponGoodsQueryResponse.setFullBuyPrice(couponInfo.getFullBuyPrice());
        couponGoodsQueryResponse.setDenomination(couponInfo.getDenomination());
        couponGoodsQueryResponse.setCouponType(couponInfo.getCouponType());
        couponGoodsQueryResponse.setCouponMarketingType(couponInfo.getCouponMarketingType());
        couponGoodsQueryResponse.setCouponDiscountMode(couponInfo.getCouponDiscountMode());

        dealCouponInfo(couponId, couponInfo, couponGoodsQueryResponse);
        return couponGoodsQueryResponse;
    }


    /**
     * 刷新优惠券缓存方法
     */
    @Override
    public void refreshCache() {
    }



    /**
     * 组装优惠券涵盖范围
     *
     * @param cacheList
     */
    private void addScope(List<CouponCache> cacheList) {
        List<String> couponIds =
                cacheList.stream().map(CouponCache::getCouponInfoId).distinct().collect(Collectors.toList());
        Map<String, List<CouponMarketingScope>> scopeList = couponMarketingScopeService.mapScopeByCouponIds(couponIds);
        cacheList.forEach(item -> item.setScopes(scopeList.get(item.getCouponInfoId())));
    }

    /**
     * 组装优惠券分类
     *
     * @param cacheList
     */
    private void addCouponCate(List<CouponCache> cacheList) {
        List<String> couponIds =
                cacheList.stream().map(CouponCache::getCouponInfoId).distinct().collect(Collectors.toList());
        Map<String, List<CouponCateRela>> cateMap = couponCateService.mapCateByCouponIds(couponIds);
        cacheList.forEach(item -> {
            List<CouponCateRela> relaList = cateMap.get(item.getCouponInfoId());
            if (relaList != null) {
                item.setCouponCateIds(relaList.stream().map(CouponCateRela::getCateId).collect(Collectors.toList()));
            }
        });
    }



    /***
     * 构建优惠券缓存查询条件
     * @param goodsInfoList 商品SKU列表
     * @param customer      登录客户
     * @param storeId       门店ID
     * @param pluginType    优惠券类型
     * @return 优惠券缓存查询对象
     */
    protected EsCouponScopePageRequest getCouponCacheQueryRequest(List<GoodsInfoVO> goodsInfoList, CustomerVO customer,
                                                                 Long storeId, PluginType pluginType) {
        return getCouponCacheQueryRequest(goodsInfoList, customer, null, storeId, pluginType);
    }

    /***
     * 构建优惠券缓存查询条件
     * @param goodsInfoList 商品SKU列表
     * @param customer      登录客户
     * @param levelMap      会员等级
     * @param storeId       门店ID
     * @param pluginType    优惠券类型
     * @return 优惠券缓存查询对象
     */
    protected EsCouponScopePageRequest getCouponCacheQueryRequest(List<GoodsInfoVO> goodsInfoList, CustomerVO customer,
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

        // 刷新优惠券缓存
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


    /***
     * 构建优惠券缓存查询条件
     * @param goodsInfo     商品SKU列表
     * @param levelMap      等级Map
     * @param storeId       门店ID
     * @param pluginType    优惠券类型
     * @return 优惠券缓存查询对象
     */
    protected EsCouponScopePageRequest getCouponCacheQueryRequest(MarketingGoods goodsInfo, Map<Long, CommonLevelVO> levelMap,
                                                                 Long storeId, PluginType pluginType) {
        // 组装店铺分类
        List<Long> storeCateIds =
                storeCateQueryProvider.listByGoods(new StoreCateListByGoodsRequest(Collections.singletonList(goodsInfo.getGoodsId())))
                        .getContext().getStoreCateGoodsRelaVOList().stream().map(StoreCateGoodsRelaVO::getStoreCateId).collect(Collectors.toList());

        return EsCouponScopePageRequest.builder()
                .brandIds(goodsInfo.getBrandId() != null ? Collections.singletonList(goodsInfo.getBrandId()) : null)
                .cateIds(goodsInfo.getCateId() != null ? Collections.singletonList(goodsInfo.getCateId()) : null)
                .storeCateIds(storeCateIds)
                .goodsInfoIds(goodsInfo.getGoodsInfoId() != null ?
                        Collections.singletonList(goodsInfo.getGoodsInfoId()) : null)
                .levelMap(levelMap)
                .storeIds(Collections.singletonList(goodsInfo.getStoreId()))
                .build();
    }


    /***
     * 获得listCouponForGoodsInfo方法的查询请求对象
     * @param goodsInfo
     * @param levelMap
     * @param storeCateIds
     * @return
     */
    protected EsCouponScopePageRequest getListCouponForGoodsInfoRequest(GoodsInfoVO goodsInfo, Map<Long, CommonLevelVO> levelMap,
                                                                       List<Long> storeCateIds, Long storeId, PluginType pluginType) {
        return EsCouponScopePageRequest.builder()
                .brandIds(goodsInfo.getBrandId() != null ? Collections.singletonList(goodsInfo.getBrandId()) : null)
                .cateIds(goodsInfo.getCateId() != null ? Collections.singletonList(goodsInfo.getCateId()) : null)
                .storeCateIds(storeCateIds)
                .goodsInfoIds(goodsInfo.getGoodsInfoId() != null ?
                        Collections.singletonList(goodsInfo.getGoodsInfoId()) : null)
                .levelMap(levelMap)
                .storeIds(Collections.singletonList(goodsInfo.getStoreId()))
                .pluginType(pluginType)
                .build();
    }

    /**
     * 获取storeName的map
     *
     * @param storeIds
     * @return
     */
    protected Map<Long, String> mapStoreNameById(List<Long> storeIds) {
        if (CollectionUtils.isEmpty(storeIds)) {
            return null;
        }
        List<StoreVO> storeList = storeQueryProvider.listNoDeleteStoreByIds(new ListNoDeleteStoreByIdsRequest
                (storeIds)).getContext().getStoreVOList();
        return storeList.stream().collect(Collectors.toMap(StoreVO::getStoreId, StoreVO::getStoreName));
    }

    /**
     * 获取cateName的map
     *
     * @param cateIds
     * @return
     */
    protected Map<Long, String> mapCateById(List<Long> cateIds) {
        if (CollectionUtils.isEmpty(cateIds)) {
            return null;
        }
        List<GoodsCateVO> goodsCateList =
                goodsCateQueryProvider.getByIds(new GoodsCateByIdsRequest(cateIds)).getContext().getGoodsCateVOList();
        return goodsCateList.stream().collect(Collectors.toMap(GoodsCateVO::getCateId, GoodsCateVO::getCateName));
    }

    /**
     * 获取storeCateName的map
     *
     * @param storeCateIds
     * @return
     */
    protected Map<Long, String> mapStoreCateById(List<Long> storeCateIds) {
        if (CollectionUtils.isEmpty(storeCateIds)) {
            return null;
        }
        List<StoreCateVO> goodsCateList =
                storeCateQueryProvider.listByIds(new StoreCateListByIdsRequest(storeCateIds)).getContext().getStoreCateVOList();
        return goodsCateList.stream().collect(Collectors.toMap(StoreCateVO::getStoreCateId, StoreCateVO::getCateName));
    }

    /**
     * 获取storeCateName的map
     *
     * @param storeIds
     * @return
     */
    protected Map<Long, String> mapStoreById(List<Long> storeIds) {
        if (CollectionUtils.isEmpty(storeIds)) {
            return null;
        }
        List<StoreVO> storeVOS =
                storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build()).getContext().getStoreVOList();
        return storeVOS.stream().collect(Collectors.toMap(StoreVO::getStoreId, StoreVO::getStoreName));
    }

    /**
     * 获取storeCateName的map
     *
     * @param brandIds
     * @return
     */
    protected Map<Long, String> mapBrandById(List<Long> brandIds) {
        if (!CollectionUtils.isNotEmpty(brandIds)) {
            return null;
        }
        List<GoodsBrandVO> goodsCateList =
                goodsBrandQueryProvider.listByIds(new GoodsBrandByIdsRequest(brandIds)).getContext().getGoodsBrandVOList();
        return goodsCateList.stream().collect(Collectors.toMap(GoodsBrandVO::getBrandId, GoodsBrandVO::getBrandName));
    }

    /**
     * 凑单页方法
     *
     * @param couponId
     * @return 返回查询条件，供bff调用goodsEs查询
     */
    @Override
    public CouponGoodsQueryResponse couponGoodsById(String couponId, Long storeId) {
        CouponInfo couponInfo;
        if (Objects.nonNull(storeId)) {
            couponInfo = couponInfoService.findByCouponIdAndStoreIdAndDelFlag(couponId, storeId);
        } else {
            couponInfo = couponInfoService.getCouponInfoById(couponId);
        }
        if (couponInfo == null) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
        }

        //组装优惠券信息
        CouponGoodsQueryResponse couponGoodsQueryResponse = new CouponGoodsQueryResponse();
        if (couponInfo.getCouponType() == CouponType.STORE_VOUCHERS ||
                CouponType.STOREFRONT_VOUCHER == couponInfo.getCouponType() ||
                CouponType.BOSS_STOREFRONT_VOUCHER == couponInfo.getCouponType()) {
            couponGoodsQueryResponse.setStoreId(couponInfo.getStoreId());
        }
        couponGoodsQueryResponse.setRangeDayType(couponInfo.getRangeDayType());
        if (RangeDayType.RANGE_DAY == couponInfo.getRangeDayType()){
            couponGoodsQueryResponse.setStartTime(DateUtil.format(couponInfo.getStartTime(), DateUtil.FMT_TIME_1));
            couponGoodsQueryResponse.setEndTime(DateUtil.format(couponInfo.getEndTime(), DateUtil.FMT_TIME_1));
        }else {
            couponGoodsQueryResponse.setEffectiveDays(couponInfo.getEffectiveDays());
        }
        couponGoodsQueryResponse.setScopeType(couponInfo.getScopeType());

        couponGoodsQueryResponse.setPlatformFlag(couponInfo.getPlatformFlag());

        if (couponInfo.getPlatformFlag() == DefaultFlag.NO) {
            StoreVO store =
                    storeQueryProvider.getById(new StoreByIdRequest(couponInfo.getStoreId())).getContext().getStoreVO();
            couponGoodsQueryResponse.setStoreName(store != null ? store.getStoreName() : null);
        }
        couponGoodsQueryResponse.setFullBuyType(couponInfo.getFullBuyType());
        couponGoodsQueryResponse.setFullBuyPrice(couponInfo.getFullBuyPrice());
        couponGoodsQueryResponse.setDenomination(couponInfo.getDenomination());
        couponGoodsQueryResponse.setCouponType(couponInfo.getCouponType());
        couponGoodsQueryResponse.setCouponMarketingType(couponInfo.getCouponMarketingType());
        couponGoodsQueryResponse.setCouponDiscountMode(couponInfo.getCouponDiscountMode());

        dealCouponInfo(couponId, couponInfo, couponGoodsQueryResponse);
        return couponGoodsQueryResponse;
    }

    private void dealCouponInfo(String couponId, CouponInfo couponInfo,
                                CouponGoodsQueryResponse couponGoodsQueryResponse) {
        List<CouponMarketingScope> scopeList;
        switch (couponInfo.getScopeType()) {
            case ALL:
                couponGoodsQueryResponse.setIsAll(DefaultFlag.YES);
                break;
            case BRAND:
                scopeList = couponMarketingScopeService.listScopeByCouponId(couponId);
                couponGoodsQueryResponse.setIsAll(DefaultFlag.NO);
                final List<Long> brandIds =
                        scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).sorted().collect(Collectors.toList());
                couponGoodsQueryResponse.setBrandIds(brandIds);
                couponGoodsQueryResponse.setBrandMap(mapBrandById(brandIds));
                //过滤已经被删除或者取消签约的品牌

                if (DefaultFlag.NO.equals(couponInfo.getPlatformFlag())) {
                    //获取店铺签约的品牌
                    ContractBrandListRequest brandRequest = new ContractBrandListRequest();
                    brandRequest.setGoodsBrandIds(brandIds);
                    brandRequest.setStoreId(couponInfo.getStoreId());
                    //获取店铺签约的品牌
                    List<ContractBrandVO> brandList =
                            contractBrandQueryProvider.list(brandRequest).getContext().getContractBrandVOList();
                    //筛选出店铺签约的品牌信息
                    brandList = brandList.stream().filter(item ->
                            brandIds.stream().anyMatch(i ->
                                    i.equals(item.getGoodsBrand().getBrandId())
                            )
                    ).collect(Collectors.toList());
                    couponGoodsQueryResponse.setQueryBrandIds(
                            brandList.stream().map(i -> i.getGoodsBrand().getBrandId()).collect(Collectors.toList()));
                } else {
                    //获取平台的品牌
                    GoodsBrandListRequest brandRequest = new GoodsBrandListRequest();
                    brandRequest.setDelFlag(DeleteFlag.NO.toValue());
                    brandRequest.setBrandIds(brandIds);
                    List<GoodsBrandVO> brandList =
                            goodsBrandQueryProvider.list(brandRequest).getContext().getGoodsBrandVOList();
                    couponGoodsQueryResponse.setQueryBrandIds(
                            brandList.stream().map(GoodsBrandVO::getBrandId).collect(Collectors.toList()));
                }

                break;
            case BOSS_CATE:
                scopeList = couponMarketingScopeService.listScopeByCouponId(couponId);
                couponGoodsQueryResponse.setIsAll(DefaultFlag.NO);
                List<Long> cateIds =
                        scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).sorted().collect(Collectors.toList());
                List<Long> cateIds4es = scopeList.stream().filter(item -> item.getCateGrade() == 3)
                        .map(item -> Long.valueOf(item.getScopeId())).collect(Collectors.toList());
                couponGoodsQueryResponse.setCateIds(cateIds);
                couponGoodsQueryResponse.setCateIds4es(cateIds4es);
                couponGoodsQueryResponse.setCateMap(mapCateById(cateIds));
                break;
            case STORE_CATE:
                scopeList = couponMarketingScopeService.listScopeByCouponId(couponId);
                couponGoodsQueryResponse.setIsAll(DefaultFlag.NO);
                List<Long> storeCateIds =
                        scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).sorted().collect(Collectors.toList());
                couponGoodsQueryResponse.setStoreCateIds(storeCateIds);
                couponGoodsQueryResponse.setStoreCateMap(mapStoreCateById(storeCateIds));
                break;
            case SKU:
                scopeList = couponMarketingScopeService.listScopeByCouponId(couponId);
                couponGoodsQueryResponse.setIsAll(DefaultFlag.NO);
                couponGoodsQueryResponse.setGoodsInfoId(scopeList.stream().map(CouponMarketingScope::getScopeId).collect(Collectors.toList()));
                break;
            case STORE:
                scopeList = couponMarketingScopeService.listScopeByCouponId(couponId);
                couponGoodsQueryResponse.setIsAll(DefaultFlag.NO);
                List<Long> storeIds =
                        scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).sorted().collect(Collectors.toList());
                couponGoodsQueryResponse.setStoreIds(storeIds);
                couponGoodsQueryResponse.setStoreMap(mapStoreById(storeIds));
                break;
            default:
                couponGoodsQueryResponse.setIsAll(DefaultFlag.YES);
                break;
        }
    }

    /**
     * 获取数据库couponCache集合
     * @param request
     * @return
     */
    public List<CouponCache> cacheList(CouponCacheInitRequest request) {
        List<CouponCache> cacheList = couponActivityConfigService.queryCouponStarted(request);
        this.addScope(cacheList);
        this.addCouponCate(cacheList);
        return cacheList;

    }

    /**
     * 获取数据库couponCache集合
     *
     * @param request
     * @return
     */
    public List<CouponCache> cachePage(CouponCacheInitRequest request) {
        List<CouponCache> cacheList = couponActivityConfigService.queryCouponStartPage(request).getContent();
        if (CollectionUtils.isEmpty(cacheList)) {
            return Lists.newArrayList();
        }
        this.addScope(cacheList);
        this.addCouponCate(cacheList);
        return cacheList;

    }

    /**
     * 检查用户是否领取过新人券
     * @param customerId
     */
    public Boolean checkFetchNewcomer(String customerId){
        return couponCodeService.checkFetchNewcomer(customerId);
    }
}
