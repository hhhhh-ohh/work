//package com.wanmi.sbc.marketing.coupon.service;
//
//import com.google.common.collect.Lists;
//import com.wanmi.sbc.common.enums.BoolFlag;
//import com.wanmi.sbc.common.enums.DefaultFlag;
//import com.wanmi.sbc.common.enums.DeleteFlag;
//import com.wanmi.sbc.common.enums.PluginType;
//import com.wanmi.sbc.common.exception.SbcRuntimeException;
//import com.wanmi.sbc.common.redis.util.RedisUtil;
//import com.wanmi.sbc.common.util.DateUtil;
//import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
//import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
//import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
//import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
//import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
//import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
//import com.wanmi.sbc.customer.bean.vo.CustomerVO;
//import com.wanmi.sbc.customer.bean.vo.StoreVO;
//import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
//import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
//import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
//import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
//import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
//import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
//import com.wanmi.sbc.goods.api.request.brand.ContractBrandListRequest;
//import com.wanmi.sbc.goods.api.request.brand.GoodsBrandByIdsRequest;
//import com.wanmi.sbc.goods.api.request.brand.GoodsBrandListRequest;
//import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdsRequest;
//import com.wanmi.sbc.goods.api.request.goods.GoodsByIdRequest;
//import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
//import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
//import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
//import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
//import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByIdsRequest;
//import com.wanmi.sbc.goods.api.response.goods.GoodsByIdResponse;
//import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
//import com.wanmi.sbc.goods.bean.vo.*;
//import com.wanmi.sbc.marketing.MarketingPluginService;
//import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeQueryRequest;
//import com.wanmi.sbc.marketing.bean.constant.CouponErrorCode;
//import com.wanmi.sbc.marketing.bean.enums.CouponType;
//import com.wanmi.sbc.marketing.bean.enums.RangeDayType;
//import com.wanmi.sbc.marketing.bean.enums.ScopeType;
//import com.wanmi.sbc.marketing.common.model.entity.MarketingGoods;
//import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
//import com.wanmi.sbc.marketing.coupon.model.root.*;
//import com.wanmi.sbc.marketing.coupon.model.vo.CouponView;
//import com.wanmi.sbc.marketing.coupon.mongorepository.CouponCacheRepository;
//import com.wanmi.sbc.marketing.coupon.request.CouponCacheCenterRequest;
//import com.wanmi.sbc.marketing.coupon.request.CouponCacheInitRequest;
//import com.wanmi.sbc.marketing.coupon.request.CouponCacheQueryRequest;
//import com.wanmi.sbc.marketing.coupon.response.CouponCenterPageResponse;
//import com.wanmi.sbc.marketing.coupon.response.CouponGoodsQueryResponse;
//import com.wanmi.sbc.marketing.coupon.response.CouponListResponse;
//import org.apache.commons.collections4.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class OldCouponCacheService implements CouponCacheServiceInterface {
//
//    /***
//     * 优惠券缓存刷新时间
//     */
//    private static final String COUPON_REFRESH_TIME_KEY = "COUPON_REFRESH_TIME_KEY:";
//
//    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//
//    @Autowired
//    private RedisUtil redisService;
//
//    @Autowired
//    private CouponCacheRepository couponCacheRepository;
//
//    @Autowired
//    private CouponActivityService couponActivityService;
//
//    @Autowired
//    private CouponInfoService couponInfoService;
//
//    @Autowired
//    private CouponActivityConfigService couponActivityConfigService;
//
//    @Autowired
//    protected MongoTemplate mongoTemplate;
//
//    @Autowired
//    private CouponMarketingScopeService couponMarketingScopeService;
//
//    @Autowired
//    protected StoreCateQueryProvider storeCateQueryProvider;
//
//    @Autowired
//    private MarketingPluginService marketingPluginService;
//
//    @Autowired
//    private CustomerQueryProvider customerQueryProvider;
//
//    @Autowired
//    private GoodsInfoQueryProvider goodsInfoQueryProvider;
//
//    @Autowired
//    private GoodsQueryProvider goodsQueryProvider;
//
//    @Autowired
//    private CouponCateService couponCateService;
//
//    @Autowired
//    private CouponCodeService couponCodeService;
//
//    @Autowired
//    private StoreQueryProvider storeQueryProvider;
//
//    @Autowired
//    private GoodsCateQueryProvider goodsCateQueryProvider;
//
//    @Autowired
//    private GoodsBrandQueryProvider goodsBrandQueryProvider;
//
//    @Autowired
//    private ContractBrandQueryProvider contractBrandQueryProvider;
//
//    @Autowired
//    private OldCouponCacheService couponCacheService;
//
//    /**
//     * 领券中心 - 查询正在进行的优惠券活动，暂时全部查询，不带任何条件，领券的时候做判断
//     *
//     * @param queryRequest
//     * @return
//     */
//    public CouponCenterPageResponse getCouponStarted(CouponCacheCenterRequest queryRequest) {
//        this.refreshCache();
//        CouponCacheQueryRequest request = couponCacheService.buildRequest(queryRequest);
//        if (Objects.nonNull(queryRequest.getStoreId())) {
//            request.setStoreIds(Collections.singletonList(queryRequest.getStoreId()));
//        }
//        request.setFilterPlugin(BoolFlag.NO);
//        /**
//         * 活动先生效的在前
//         * 通用券＞店铺券＞运费券
//         * 优惠券开始时间
//         * */
//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponActivity.startTime"));
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.couponTypeInteger"));
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.createTime"));
//        PageRequest pageRequest = PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize(),
//                Sort.by(orders));
//
//        Query query = new Query(request.getCriteria());
//        List<CouponCache> couponCacheList = mongoTemplate.find(query.with(pageRequest),
//                CouponCache.class);
//        if (CollectionUtils.isNotEmpty(couponCacheList)) {
//            long count = mongoTemplate.count(new Query(request.getCriteria()), CouponCache.class);
//            // 获取店铺ids，排除平台优惠券
//            List<Long> storeIds =
//                    couponCacheList.stream().filter(item -> item.getCouponInfo().getPlatformFlag() == DefaultFlag.NO)
//                            .map(item -> item.getCouponInfo().getStoreId()).collect(Collectors.toList());
//            // 获取平台类目
//            List<Long> cateIds =
//                    couponCacheList.stream().filter(item -> item.getCouponInfo().getScopeType() == ScopeType.BOSS_CATE)
//                            .flatMap(item -> item.getScopes().stream().map(CouponMarketingScope::getScopeId)).map(Long::valueOf).distinct().sorted().collect(Collectors.toList());
//            // 获取店铺类目
//            List<Long> storeCateIds =
//                    couponCacheList.stream().filter(item -> item.getCouponInfo().getScopeType() == ScopeType.STORE_CATE)
//                            .flatMap(item -> item.getScopes().stream().map(CouponMarketingScope::getScopeId)).map(Long::valueOf).distinct().sorted().collect(Collectors.toList());
//            // 获取品牌
//            List<Long> brandIds =
//                    couponCacheList.stream().filter(item -> item.getCouponInfo().getScopeType() == ScopeType.BRAND)
//                            .flatMap(item -> item.getScopes().stream().map(CouponMarketingScope::getScopeId)).map(Long::valueOf).distinct().sorted().collect(Collectors.toList());
//
//            return CouponCenterPageResponse.builder()
//                    //券详情
//                    .couponViews(
//                            new PageImpl<>(CouponView.converter(couponCacheList
//                                    //券库存
//                                    , couponCodeService.mapLeftCount(couponCacheList)
//                                    //领用状态
//                                    , couponCodeService.mapFetchStatus(couponCacheList, queryRequest.getCustomerId()))
//                                    , pageRequest, count))
//                    //storeNameList
//                    .storeMap(this.mapStoreNameById(storeIds))
//                    //平台分类+品牌+商家分类
//                    .cateMap(this.mapCateById(cateIds))
//                    .brandMap(this.mapBrandById(brandIds))
//                    .storeCateMap(this.mapStoreCateById(storeCateIds))
//                    .build();
//        } else {
//            return CouponCenterPageResponse.builder().couponViews(new PageImpl<>(Collections.emptyList(), pageRequest
//                    , 0)).build();
//        }
//    }
//
//    public CouponCacheQueryRequest buildRequest(CouponCacheCenterRequest queryRequest) {
//        return CouponCacheQueryRequest.builder()
//                .couponType(queryRequest.getCouponType())
//                .couponCateId(queryRequest.getCouponCateId())
//                .build();
//    }
//
//    /**
//     * 通过商品列表，查询相关优惠券
//     *
//     * @param goodsInfoIds
//     * @param customerId
//     * @return
//     */
//    @Override
//    public CouponListResponse listCouponForGoodsList(List<String> goodsInfoIds, String customerId, Long storeId, PluginType pluginType) {
//        GoodsInfoListByIdsRequest goodsInfoListByIdsRequest = new GoodsInfoListByIdsRequest();
//        goodsInfoListByIdsRequest.setGoodsInfoIds(goodsInfoIds);
//        if (Objects.nonNull(storeId)) {
//            goodsInfoListByIdsRequest.setStoreId(storeId);
//        }
//        List<GoodsInfoVO> goodsInfoList =
//                goodsInfoQueryProvider.listByIds(goodsInfoListByIdsRequest).getContext().getGoodsInfos();
//        if (CollectionUtils.isEmpty(goodsInfoList)) {
//            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080085);
//        }
//        CustomerVO customer = null;
//        if (customerId != null) {
//            customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(customerId)).getContext();
//        }
//        List<CouponCache> couponCacheList = this.listCouponForGoodsList(goodsInfoList, customer, storeId, pluginType);
//
//        // 获取店铺ids，排除平台优惠券
//        List<Long> storeIds =
//                couponCacheList.stream().filter(item -> item.getCouponInfo().getPlatformFlag() == DefaultFlag.NO)
//                        .map(item -> item.getCouponInfo().getStoreId()).distinct().collect(Collectors.toList());
//        return CouponListResponse.builder()
//                //券详情
//                .couponViews(CouponView.converter(couponCacheList
//                        //券库存
//                        , couponCodeService.mapLeftCount(couponCacheList)
//                        //领用状态
//                        , couponCodeService.mapFetchStatus(couponCacheList, customerId)))
//                //storeNameList
//                .storeMap(this.mapStoreNameById(storeIds))
//                .build();
//    }
//
//    /**
//     * 通过商品列表，查询相关优惠券
//     *
//     * @param goodsInfoList
//     * @param customer
//     * @param pluginType
//     * @return
//     */
//    @Override
//    public List<CouponCache> listCouponForGoodsList(List<GoodsInfoVO> goodsInfoList, CustomerVO customer, Long storeId, PluginType pluginType) {
//        CouponCacheQueryRequest request = getCouponCacheQueryRequest(goodsInfoList, customer, storeId, pluginType);
//
//        /**
//         * 通用券＞店铺券
//         * 面值大在前
//         * 如有重复：按创建时间，最先创建的在前
//         * */
//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.couponTypeInteger"));
//        orders.add(new Sort.Order(Sort.Direction.DESC, "couponInfo.denomination"));
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.createTime"));
//        Query with = new Query(request.getCriteria()).with(Sort.by(orders));
//        return mongoTemplate.find(with, CouponCache.class);
//    }
//
//    /**
//     * 通过商品列表，查询相关优惠券（更改到es查询）
//     *
//     * @param goodsInfoList     商品营销集合
//     * @param levelMap          会员等级
//     * @param storeId           门店ID
//     * @param pluginType        插件Type
//     * @return
//     */
//    public List<CouponCache> listCouponForGoodsList(List<MarketingGoods> goodsInfoList, Map<Long, CommonLevelVO> levelMap,
//                                                    Long storeId, PluginType pluginType) {
//
//        List<GoodsInfoVO> goodsInfoVoList = goodsInfoList.stream().filter(item -> item.getGoodsId() != null)
//                .map(item->{
//                    GoodsInfoVO vo = new GoodsInfoVO();
//                    vo.setGoodsId(item.getGoodsId());
//                    vo.setStoreId(item.getStoreId());
//                    vo.setGoodsInfoId(item.getGoodsInfoId());
//                    return vo;
//                }).collect(Collectors.toList());
//
//        CouponCacheQueryRequest request = getCouponCacheQueryRequest(goodsInfoVoList, null, levelMap, storeId, pluginType);
//        /**
//         * 通用券＞店铺券
//         * 面值大在前
//         * 如有重复：按创建时间，最先创建的在前
//         * */
//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.couponTypeInteger"));
//        orders.add(new Sort.Order(Sort.Direction.DESC, "couponInfo.denomination"));
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.createTime"));
//        Query query = new Query(request.getCriteria()).with(Sort.by(orders));
//        return mongoTemplate.find(query, CouponCache.class);
//    }
//
//    /**
//     * 不做storeCateId的判断，默认前端已经判断
//     * @param goodsInfoList
//     * @param levelMap
//     * @return
//     */
//    public List<CouponCache> listCouponForGoodsListFast(List<MarketingGoods> goodsInfoList, Map<Long, CommonLevelVO> levelMap) {
//
//        List<String> goodsIds = goodsInfoList.stream().filter(item -> item.getGoodsId() != null)
//                .map(MarketingGoods::getGoodsId).distinct().collect(Collectors.toList());
//
//        GoodsListByIdsRequest goodsListByIdsRequest = new GoodsListByIdsRequest();
//        goodsListByIdsRequest.setGoodsIds(goodsIds);
//
//        //组装商品分类 -- 平台类目
//        List<Long> cateIds = goodsInfoList.stream().filter(item -> item.getCateId() != null)
//                .map(MarketingGoods::getCateId).collect(Collectors.toList());
//        //组装品牌
//        List<Long> brandIds = goodsInfoList.stream().filter(item -> item.getBrandId() != null)
//                .map(MarketingGoods::getBrandId).distinct().collect(Collectors.toList());
//
//        List<Long> storeCateIds = new ArrayList<>();
//        goodsInfoList.forEach(
//                item -> {
//                    if (item.getStoreCateIds() != null) {
//                        storeCateIds.addAll(item.getStoreCateIds());
//                    }
//                });
//        this.refreshCache();
//        CouponCacheQueryRequest request = CouponCacheQueryRequest.builder()
//                .brandIds(brandIds)
//                .cateIds(cateIds)
//                .storeCateIds(storeCateIds)
//                .goodsInfoIds(goodsInfoList.stream().map(MarketingGoods::getGoodsInfoId).collect(Collectors.toList()))
//                .storeIds(goodsInfoList.stream().map(MarketingGoods::getStoreId).collect(Collectors.toList()))
//                .levelMap(levelMap).build();
//        /**
//         * 通用券＞店铺券
//         * 面值大在前
//         * 如有重复：按创建时间，最先创建的在前
//         * */
//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.couponTypeInteger"));
//        orders.add(new Sort.Order(Sort.Direction.DESC, "couponInfo.denomination"));
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.createTime"));
//        Query query = new Query(request.getCriteria()).with(Sort.by(orders));
//        return mongoTemplate.find(query, CouponCache.class);
//    }
//
//    /**
//     * 通过商品，查询相关优惠券
//     *
//     * @param goodsInfoId
//     * @param customerId
//     * @return
//     */
//    @Override
//    public CouponListResponse listCouponForGoodsDetail(String goodsInfoId, String customerId, Long storeId, PluginType pluginType) {
//        GoodsInfoByIdRequest goodsInfoByIdRequest = new GoodsInfoByIdRequest();
//        goodsInfoByIdRequest.setGoodsInfoId(goodsInfoId);
//        if (Objects.nonNull(storeId)) {
//            goodsInfoByIdRequest.setStoreId(storeId);
//        }
//        GoodsInfoByIdResponse goodsInfo = goodsInfoQueryProvider.getById(goodsInfoByIdRequest).getContext();
//        if (goodsInfo == null) {
//            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080085);
//        }
//
//        CustomerVO customer = null;
//        if (customerId != null) {
//            customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(customerId)).getContext();
//        }
//
////        Map<Long, CustomerLevel> levelMap = marketingPluginService.getCustomerLevels(Collections.singletonList
////        (goodsInfo), customer);
//        Map<Long, CommonLevelVO> levelMap = marketingPluginService.getCustomerLevels(Collections.singletonList
//                (goodsInfo), customer);
//
//        GoodsByIdResponse goods = goodsQueryProvider.getById(new GoodsByIdRequest(goodsInfo.getGoodsId())).getContext();
//        if (goods == null) {
//            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080085);
//        }
//        //组装分类
//        goodsInfo.setCateId(goods.getCateId());
//        //组装品牌
//        goodsInfo.setBrandId(goods.getBrandId());
//        List<CouponCache> couponCacheList = listCouponForGoodsInfo(MarketingGoods
//                .builder()
//                .goodsId(goodsInfo.getGoodsId())
//                .brandId(goodsInfo.getBrandId())
//                .cateId(goodsInfo.getCateId())
//                .goodsInfoId(goodsInfo.getGoodsInfoId())
//                .storeId(goodsInfo.getStoreId()).build(), levelMap, storeId, pluginType);
////        List<Long> storeIds = couponCacheList.stream().map(item -> item.getCouponInfo().getStoreId()).distinct()
////        .collect(Collectors.toList());
//        return CouponListResponse.builder()
//                //券详情
//                .couponViews(CouponView.converter(couponCacheList
//                        // 券库存
//                        , couponCodeService.mapLeftCount(couponCacheList)
//                        // 领用状态
//                        , couponCodeService.mapFetchStatus(couponCacheList, customerId)))
//                .build();
//    }
//
//    /**
//     * 通过商品 + 用户等级，查询相关优惠券 = 提供给营销插件使用
//     * <p>
//     * 营销插件，列表 + 详情 都会使用该方法
//     * 默认入参GoodsInfo已经包含了平台类目cateId和品牌brandId
//     *
//     * @param goodsInfo
//     * @param levelMap
//     * @return
//     */
//    @Override
//    public List<CouponCache> listCouponForGoodsInfo(MarketingGoods goodsInfo, Map<Long, CommonLevelVO> levelMap,
//                                                    Long storeId, PluginType pluginType) {
//        CouponCacheQueryRequest request = getCouponCacheQueryRequest(goodsInfo, levelMap, storeId, pluginType);
//        /**
//         * 通用券＞店铺券
//         * 面值大在前
//         * 如有重复：按创建时间，最先创建的在前
//         * */
//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.couponTypeInteger"));
//        orders.add(new Sort.Order(Sort.Direction.DESC, "couponInfo.denomination"));
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.createTime"));
//        return mongoTemplate.find(new Query(request.getCriteria()).with(Sort.by(orders)), CouponCache.class);
//    }
//
//    @Override
//    public List<CouponCache> listCouponForGoodsInfos(GoodsInfoVO goodsInfo, Map<Long, CommonLevelVO> levelMap, Long storeId,
//                                                     List<Long> storeCateIds, PluginType pluginType) {
//
//        CouponCacheQueryRequest request = getListCouponForGoodsInfoRequest(goodsInfo, levelMap, storeCateIds,
//                storeId, pluginType);
//        /**
//         * 通用券＞店铺券
//         * 面值大在前
//         * 如有重复：按创建时间，最先创建的在前
//         * */
//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.couponTypeInteger"));
//        orders.add(new Sort.Order(Sort.Direction.DESC, "couponInfo.denomination"));
//        orders.add(new Sort.Order(Sort.Direction.ASC, "couponInfo.createTime"));
//        return mongoTemplate.find(new Query(request.getCriteria()).with(Sort.by(orders)), CouponCache.class);
//    }
//
//
//    /**
//     * 凑单页方法
//     *
//     * @param couponId
//     * @return 返回查询条件，供bff调用goodsEs查询
//     */
//    @Override
//    public CouponGoodsQueryResponse listGoodsByCouponId(String couponId, String activityId, String customerId,
//                                                        Long storeId) {
//        CouponInfo couponInfo;
//        if (Objects.nonNull(storeId)) {
//            couponInfo = couponInfoService.findByCouponIdAndStoreIdAndDelFlag(couponId, storeId);
//        } else {
//            couponInfo = couponInfoService.getCouponInfoById(couponId);
//        }
//        if (couponInfo == null) {
//            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
//        }
//
//        List<CouponCode> couponCodeList = couponCodeService.listCouponCodeByCondition(CouponCodeQueryRequest.builder()
//                .customerId(customerId).delFlag(DeleteFlag.NO).useStatus(DefaultFlag.NO)
//                .couponId(couponId).activityId(activityId).build());
//
//        if (CollectionUtils.isEmpty(couponCodeList)) {
//            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
//        }
//
//        //当前用户所拥有的优惠券 -- 因为订单逆向流程会有退还券，有可能一个用户会有多张相同（相同活动+相同优惠券）的券，去第一张
//        CouponCode couponCode = couponCodeList.get(0);
//
//        //组装优惠券信息
//        CouponGoodsQueryResponse couponGoodsQueryResponse = new CouponGoodsQueryResponse();
//        if (couponInfo.getCouponType() == CouponType.STORE_VOUCHERS ||
//                CouponType.STOREFRONT_VOUCHER == couponInfo.getCouponType() ||
//                CouponType.BOSS_STOREFRONT_VOUCHER == couponInfo.getCouponType()) {
//            couponGoodsQueryResponse.setStoreId(couponInfo.getStoreId());
//        }
//        couponGoodsQueryResponse.setStartTime(DateUtil.format(couponCode.getStartTime(), DateUtil.FMT_DATE_1));
//        couponGoodsQueryResponse.setEndTime(DateUtil.format(couponCode.getEndTime(), DateUtil.FMT_DATE_1));
//        couponGoodsQueryResponse.setScopeType(couponInfo.getScopeType());
//
//        couponGoodsQueryResponse.setPlatformFlag(couponInfo.getPlatformFlag());
//
//        if (couponInfo.getPlatformFlag() == DefaultFlag.NO) {
//            StoreVO store =
//                    storeQueryProvider.getById(new StoreByIdRequest(couponInfo.getStoreId())).getContext().getStoreVO();
//            couponGoodsQueryResponse.setStoreName(store != null ? store.getStoreName() : null);
//        }
//        couponGoodsQueryResponse.setFullBuyType(couponInfo.getFullBuyType());
//        couponGoodsQueryResponse.setFullBuyPrice(couponInfo.getFullBuyPrice());
//        couponGoodsQueryResponse.setDenomination(couponInfo.getDenomination());
//        couponGoodsQueryResponse.setCouponType(couponInfo.getCouponType());
//
//        dealCouponInfo(couponId, couponInfo, couponGoodsQueryResponse);
//        return couponGoodsQueryResponse;
//    }
//
//
//    /**
//     * 刷新优惠券缓存方法
//     */
//    @Override
//    public void refreshCache() {
//        boolean hasKey = redisService.hasKey(COUPON_REFRESH_TIME_KEY);
//        LocalDateTime now = LocalDateTime.now();
//        CouponActivity lastActivity = couponActivityService.getLastUpdate();
//        if (lastActivity == null) {
//            return;
//        }
//        if (!hasKey) {
//            // 全量刷新缓存
//            List<CouponCache> cacheList = couponActivityConfigService
//                    .queryCouponStarted(CouponCacheInitRequest.builder().build());
//            this.addScope(cacheList);
//            this.addCouponCate(cacheList);
//            couponCacheService.addCouponStoreIds(cacheList);
//            couponCacheRepository.deleteAll();
//            couponCacheRepository.saveAll(cacheList);
//            redisService.setString(COUPON_REFRESH_TIME_KEY, now.format(TIME_FORMAT));
//        } else {
//            LocalDateTime lastTime = lastActivity.getUpdateTime();
//            LocalDateTime cacheTime = LocalDateTime.parse(redisService.getString(COUPON_REFRESH_TIME_KEY), TIME_FORMAT);
//            // Fix Bug ID1068765
//            // 先创建的优惠券StartTime 晚于后创建的优惠券会造成缓存丢失问题
//            if (lastTime.isAfter(cacheTime.minusMinutes(1))) {
//                // 缓存需要部分更新
//                List<CouponCache> cacheList = couponActivityConfigService
//                        .queryCouponStarted(CouponCacheInitRequest.builder().updateTimeStart(cacheTime)
//                                .updateTimeEnd(now).build());
//                if (CollectionUtils.isEmpty(cacheList)) {
//                    return;
//                }
//                this.addScope(cacheList);
//                this.addCouponCate(cacheList);
//                couponCacheService.addCouponStoreIds(cacheList);
//                Set<String> activityIdSet =
//                        cacheList.stream().map(CouponCache::getCouponActivityId).collect(Collectors.toSet());
//                //按id集合删除缓存
//                couponCacheRepository.deleteByCouponActivityIdIn(activityIdSet);
//                couponCacheRepository.saveAll(cacheList);
//                redisService.setString(COUPON_REFRESH_TIME_KEY, now.format(TIME_FORMAT));
//            }
//        }
//    }
//
//    public void addCouponStoreIds(List<CouponCache> cacheList) {
//    }
//
//    /**
//     * 刷新优惠券缓存方法
//     * 按优惠券活动ids局部刷新
//     */
//    public void refreshCachePart(List<String> activityIds) {
//        //缓存需要部分更新
//        CouponCacheInitRequest request = CouponCacheInitRequest.builder().couponActivityIds(activityIds).build();
//        List<CouponCache> cacheList = couponActivityConfigService.queryCouponStarted(request);
//        this.addScope(cacheList);
//        this.addCouponCate(cacheList);
//        couponCacheService.addCouponStoreIds(cacheList);
//        Set<String> activityIdSet =
//                cacheList.stream().map(CouponCache::getCouponActivityId).collect(Collectors.toSet());
//        //按id集合删除缓存
//        couponCacheRepository.deleteByCouponActivityIdIn(activityIdSet);
//        couponCacheRepository.saveAll(cacheList);
//    }
//
//    /**
//     * 刷新优惠券缓存方法
//     * 按优惠券活动ids局部刷新
//     */
//    public void deleteCachePart(String activityId) {
//        //按id集合删除缓存
//        couponCacheRepository.deleteByCouponActivityId(activityId);
//    }
//
//    /**
//     * 组装优惠券涵盖范围
//     *
//     * @param cacheList
//     */
//    private void addScope(List<CouponCache> cacheList) {
//        List<String> couponIds =
//                cacheList.stream().map(CouponCache::getCouponInfoId).distinct().collect(Collectors.toList());
//        Map<String, List<CouponMarketingScope>> scopeList = couponMarketingScopeService.mapScopeByCouponIds(couponIds);
//        cacheList.forEach(item -> item.setScopes(scopeList.get(item.getCouponInfoId())));
//    }
//
//    /**
//     * 组装优惠券分类
//     *
//     * @param cacheList
//     */
//    private void addCouponCate(List<CouponCache> cacheList) {
//        List<String> couponIds =
//                cacheList.stream().map(CouponCache::getCouponInfoId).distinct().collect(Collectors.toList());
//        Map<String, List<CouponCateRela>> cateMap = couponCateService.mapCateByCouponIds(couponIds);
//        cacheList.forEach(item -> {
//            List<CouponCateRela> relaList = cateMap.get(item.getCouponInfoId());
//            if (relaList != null) {
//                item.setCouponCateIds(relaList.stream().map(CouponCateRela::getCateId).collect(Collectors.toList()));
//            }
//        });
//    }
//
//    /**
//     * 因为优惠券分类随时可以修改
//     *
//     * @param couponCateId
//     * @param isDelete
//     */
//    public void updateCateRelaCache(String couponCateId, boolean isDelete) {
//        Query query;
//        if (isDelete) {
//            query = Query.query(Criteria.where("couponCateIds").is(couponCateId));
//        } else {
//            query = Query.query(new Criteria().andOperator(
//                    Criteria.where("couponCateIds").is(couponCateId),
//                    Criteria.where("couponInfo.platformFlag").is(DefaultFlag.NO.toString())
//            ));
//        }
//        mongoTemplate.updateMulti(query, new Update().pull("couponCateIds", couponCateId), CouponCache.class);
//    }
//
//    /***
//     * 构建优惠券缓存查询条件
//     * @param goodsInfoList 商品SKU列表
//     * @param customer      登录客户
//     * @param storeId       门店ID
//     * @param pluginType    优惠券类型
//     * @return 优惠券缓存查询对象
//     */
//    protected CouponCacheQueryRequest getCouponCacheQueryRequest(List<GoodsInfoVO> goodsInfoList, CustomerVO customer,
//                                                                 Long storeId, PluginType pluginType) {
//        return getCouponCacheQueryRequest(goodsInfoList, customer, null, storeId, pluginType);
//    }
//
//    /***
//     * 构建优惠券缓存查询条件
//     * @param goodsInfoList 商品SKU列表
//     * @param customer      登录客户
//     * @param levelMap      会员等级
//     * @param storeId       门店ID
//     * @param pluginType    优惠券类型
//     * @return 优惠券缓存查询对象
//     */
//    protected CouponCacheQueryRequest getCouponCacheQueryRequest(List<GoodsInfoVO> goodsInfoList, CustomerVO customer,
//                                                                 Map<Long, CommonLevelVO> levelMap,
//                                                                 Long storeId, PluginType pluginType) {
//        // 组装等级数据
//        if (Objects.isNull(levelMap)) {
//            levelMap = marketingPluginService.getCustomerLevels(goodsInfoList, customer);
//        }
//
//        List<String> goodsIds = goodsInfoList.stream().filter(item -> item.getGoodsId() != null)
//                .map(GoodsInfoVO::getGoodsId).distinct().collect(Collectors.toList());
//
//        // 组装商品分类 -- 店铺类目
//        List<Long> storeCateIds =
//                storeCateQueryProvider.listByGoods(new StoreCateListByGoodsRequest(goodsIds)).getContext().getStoreCateGoodsRelaVOList().stream().filter(item -> item.getStoreCateId() != null)
//                        .map(StoreCateGoodsRelaVO::getStoreCateId).collect(Collectors.toList());
//
//        GoodsListByIdsRequest goodsListByIdsRequest = new GoodsListByIdsRequest();
//        goodsListByIdsRequest.setGoodsIds(goodsIds);
//        List<GoodsVO> goodsList =
//                goodsQueryProvider.listByIds(goodsListByIdsRequest).getContext().getGoodsVOList();
//        // 组装商品分类 -- 平台类目
//        List<Long> cateIds = goodsList.stream().filter(item -> item.getCateId() != null)
//                .map(GoodsVO::getCateId).collect(Collectors.toList());
//        // 组装品牌
//        List<Long> brandIds = goodsList.stream().filter(item -> item.getBrandId() != null)
//                .map(GoodsVO::getBrandId).distinct().collect(Collectors.toList());
//
//        // 刷新优惠券缓存
//        this.refreshCache();
//        CouponCacheQueryRequest request = CouponCacheQueryRequest.builder()
//                .brandIds(brandIds)
//                .pluginType(pluginType)
//                .cateIds(cateIds)
//                .storeCateIds(storeCateIds)
//                .goodsInfoIds(goodsInfoList.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList()))
//                .storeIds(goodsInfoList.stream().map(GoodsInfoVO::getStoreId).collect(Collectors.toList()))
//                .levelMap(levelMap).build();
//        return request;
//    }
//
//
//    /***
//     * 构建优惠券缓存查询条件
//     * @param goodsInfo     商品SKU列表
//     * @param levelMap      等级Map
//     * @param storeId       门店ID
//     * @param pluginType    优惠券类型
//     * @return 优惠券缓存查询对象
//     */
//    protected CouponCacheQueryRequest getCouponCacheQueryRequest(MarketingGoods goodsInfo, Map<Long, CommonLevelVO> levelMap,
//                                                                 Long storeId, PluginType pluginType) {
//        // 组装店铺分类
//        List<Long> storeCateIds =
//                storeCateQueryProvider.listByGoods(new StoreCateListByGoodsRequest(Collections.singletonList(goodsInfo.getGoodsId())))
//                        .getContext().getStoreCateGoodsRelaVOList().stream().map(StoreCateGoodsRelaVO::getStoreCateId).collect(Collectors.toList());
//
//        this.refreshCache();
//        return CouponCacheQueryRequest.builder()
//                .brandIds(goodsInfo.getBrandId() != null ? Collections.singletonList(goodsInfo.getBrandId()) : null)
//                .cateIds(goodsInfo.getCateId() != null ? Collections.singletonList(goodsInfo.getCateId()) : null)
//                .storeCateIds(storeCateIds)
//                .goodsInfoIds(goodsInfo.getGoodsInfoId() != null ?
//                        Collections.singletonList(goodsInfo.getGoodsInfoId()) : null)
//                .levelMap(levelMap)
//                .storeIds(Collections.singletonList(goodsInfo.getStoreId()))
//                .build();
//    }
//
//    /**
//     * 更新优惠券剩余标识
//     *
//     * @param activityConfigId
//     * @param defaultFlag
//     */
//    public void updateCouponLeftCache(String activityConfigId, DefaultFlag defaultFlag) {
//        mongoTemplate.updateFirst(Query.query(Criteria.where("activityConfigId").is(activityConfigId)),
//                Update.update("hasLeft", defaultFlag), CouponCache.class);
//    }
//
//    /***
//     * 获得listCouponForGoodsInfo方法的查询请求对象
//     * @param goodsInfo
//     * @param levelMap
//     * @param storeCateIds
//     * @return
//     */
//    protected CouponCacheQueryRequest getListCouponForGoodsInfoRequest(GoodsInfoVO goodsInfo, Map<Long, CommonLevelVO> levelMap,
//                                                                       List<Long> storeCateIds, Long storeId, PluginType pluginType) {
//        return CouponCacheQueryRequest.builder()
//                .brandIds(goodsInfo.getBrandId() != null ? Collections.singletonList(goodsInfo.getBrandId()) : null)
//                .cateIds(goodsInfo.getCateId() != null ? Collections.singletonList(goodsInfo.getCateId()) : null)
//                .storeCateIds(storeCateIds)
//                .goodsInfoIds(goodsInfo.getGoodsInfoId() != null ?
//                        Collections.singletonList(goodsInfo.getGoodsInfoId()) : null)
//                .levelMap(levelMap)
//                .storeIds(Collections.singletonList(goodsInfo.getStoreId()))
//                .pluginType(pluginType)
//                .build();
//    }
//
//    /**
//     * 获取storeName的map
//     *
//     * @param storeIds
//     * @return
//     */
//    protected Map<Long, String> mapStoreNameById(List<Long> storeIds) {
//        if (CollectionUtils.isEmpty(storeIds)) {
//            return null;
//        }
//        List<StoreVO> storeList = storeQueryProvider.listNoDeleteStoreByIds(new ListNoDeleteStoreByIdsRequest
//                (storeIds)).getContext().getStoreVOList();
//        return storeList.stream().collect(Collectors.toMap(StoreVO::getStoreId, StoreVO::getStoreName));
//    }
//
//    /**
//     * 获取cateName的map
//     *
//     * @param cateIds
//     * @return
//     */
//    protected Map<Long, String> mapCateById(List<Long> cateIds) {
//        if (CollectionUtils.isEmpty(cateIds)) {
//            return null;
//        }
//        List<GoodsCateVO> goodsCateList =
//                goodsCateQueryProvider.getByIds(new GoodsCateByIdsRequest(cateIds)).getContext().getGoodsCateVOList();
//        return goodsCateList.stream().collect(Collectors.toMap(GoodsCateVO::getCateId, GoodsCateVO::getCateName));
//    }
//
//    /**
//     * 获取storeCateName的map
//     *
//     * @param storeCateIds
//     * @return
//     */
//    protected Map<Long, String> mapStoreCateById(List<Long> storeCateIds) {
//        if (CollectionUtils.isEmpty(storeCateIds)) {
//            return null;
//        }
//        List<StoreCateVO> goodsCateList =
//                storeCateQueryProvider.listByIds(new StoreCateListByIdsRequest(storeCateIds)).getContext().getStoreCateVOList();
//        return goodsCateList.stream().collect(Collectors.toMap(StoreCateVO::getStoreCateId, StoreCateVO::getCateName));
//    }
//
//    /**
//     * 获取storeCateName的map
//     *
//     * @param brandIds
//     * @return
//     */
//    protected Map<Long, String> mapBrandById(List<Long> brandIds) {
//        if (!CollectionUtils.isNotEmpty(brandIds)) {
//            return null;
//        }
//        List<GoodsBrandVO> goodsCateList =
//                goodsBrandQueryProvider.listByIds(new GoodsBrandByIdsRequest(brandIds)).getContext().getGoodsBrandVOList();
//        return goodsCateList.stream().collect(Collectors.toMap(GoodsBrandVO::getBrandId, GoodsBrandVO::getBrandName));
//    }
//
//    /**
//     * 凑单页方法
//     *
//     * @param couponId
//     * @return 返回查询条件，供bff调用goodsEs查询
//     */
//    @Override
//    public CouponGoodsQueryResponse couponGoodsById(String couponId, Long storeId) {
//        CouponInfo couponInfo;
//        if (Objects.nonNull(storeId)) {
//            couponInfo = couponInfoService.findByCouponIdAndStoreIdAndDelFlag(couponId, storeId);
//        } else {
//            couponInfo = couponInfoService.getCouponInfoById(couponId);
//        }
//        if (couponInfo == null) {
//            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
//        }
//
//        //组装优惠券信息
//        CouponGoodsQueryResponse couponGoodsQueryResponse = new CouponGoodsQueryResponse();
//        if (couponInfo.getCouponType() == CouponType.STORE_VOUCHERS ||
//                CouponType.STOREFRONT_VOUCHER == couponInfo.getCouponType() ||
//                CouponType.BOSS_STOREFRONT_VOUCHER == couponInfo.getCouponType()) {
//            couponGoodsQueryResponse.setStoreId(couponInfo.getStoreId());
//        }
//        couponGoodsQueryResponse.setRangeDayType(couponInfo.getRangeDayType());
//        if (RangeDayType.RANGE_DAY == couponInfo.getRangeDayType()){
//            couponGoodsQueryResponse.setStartTime(DateUtil.format(couponInfo.getStartTime(), DateUtil.FMT_DATE_1));
//            couponGoodsQueryResponse.setEndTime(DateUtil.format(couponInfo.getEndTime(), DateUtil.FMT_DATE_1));
//        }else {
//            couponGoodsQueryResponse.setEffectiveDays(couponInfo.getEffectiveDays());
//        }
//        couponGoodsQueryResponse.setScopeType(couponInfo.getScopeType());
//
//        couponGoodsQueryResponse.setPlatformFlag(couponInfo.getPlatformFlag());
//
//        if (couponInfo.getPlatformFlag() == DefaultFlag.NO) {
//            StoreVO store =
//                    storeQueryProvider.getById(new StoreByIdRequest(couponInfo.getStoreId())).getContext().getStoreVO();
//            couponGoodsQueryResponse.setStoreName(store != null ? store.getStoreName() : null);
//        }
//        couponGoodsQueryResponse.setFullBuyType(couponInfo.getFullBuyType());
//        couponGoodsQueryResponse.setFullBuyPrice(couponInfo.getFullBuyPrice());
//        couponGoodsQueryResponse.setDenomination(couponInfo.getDenomination());
//        couponGoodsQueryResponse.setCouponType(couponInfo.getCouponType());
//
//        dealCouponInfo(couponId, couponInfo, couponGoodsQueryResponse);
//        return couponGoodsQueryResponse;
//    }
//
//    private void dealCouponInfo(String couponId, CouponInfo couponInfo,
//                                CouponGoodsQueryResponse couponGoodsQueryResponse) {
//        List<CouponMarketingScope> scopeList;
//        switch (couponInfo.getScopeType()) {
//            case ALL:
//                couponGoodsQueryResponse.setIsAll(DefaultFlag.YES);
//                break;
//            case BRAND:
//                scopeList = couponMarketingScopeService.listScopeByCouponId(couponId);
//                couponGoodsQueryResponse.setIsAll(DefaultFlag.NO);
//                final List<Long> brandIds =
//                        scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).sorted().collect(Collectors.toList());
//                couponGoodsQueryResponse.setBrandIds(brandIds);
//                couponGoodsQueryResponse.setBrandMap(mapBrandById(brandIds));
//                //过滤已经被删除或者取消签约的品牌
//
//                if (DefaultFlag.NO.equals(couponInfo.getPlatformFlag())) {
//                    //获取店铺签约的品牌
//                    ContractBrandListRequest brandRequest = new ContractBrandListRequest();
//                    brandRequest.setGoodsBrandIds(brandIds);
//                    brandRequest.setStoreId(couponInfo.getStoreId());
//                    //获取店铺签约的品牌
//                    List<ContractBrandVO> brandList =
//                            contractBrandQueryProvider.list(brandRequest).getContext().getContractBrandVOList();
//                    //筛选出店铺签约的品牌信息
//                    brandList = brandList.stream().filter(item ->
//                            brandIds.stream().anyMatch(i ->
//                                    i.equals(item.getGoodsBrand().getBrandId())
//                            )
//                    ).collect(Collectors.toList());
//                    couponGoodsQueryResponse.setQueryBrandIds(
//                            brandList.stream().map(i -> i.getGoodsBrand().getBrandId()).collect(Collectors.toList()));
//                } else {
//                    //获取平台的品牌
//                    GoodsBrandListRequest brandRequest = new GoodsBrandListRequest();
//                    brandRequest.setDelFlag(DeleteFlag.NO.toValue());
//                    brandRequest.setBrandIds(brandIds);
//                    List<GoodsBrandVO> brandList =
//                            goodsBrandQueryProvider.list(brandRequest).getContext().getGoodsBrandVOList();
//                    couponGoodsQueryResponse.setQueryBrandIds(
//                            brandList.stream().map(GoodsBrandVO::getBrandId).collect(Collectors.toList()));
//                }
//
//                break;
//            case BOSS_CATE:
//                scopeList = couponMarketingScopeService.listScopeByCouponId(couponId);
//                couponGoodsQueryResponse.setIsAll(DefaultFlag.NO);
//                List<Long> cateIds =
//                        scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).sorted().collect(Collectors.toList());
//                List<Long> cateIds4es = scopeList.stream().filter(item -> item.getCateGrade() == 3)
//                        .map(item -> Long.valueOf(item.getScopeId())).collect(Collectors.toList());
//                couponGoodsQueryResponse.setCateIds(cateIds);
//                couponGoodsQueryResponse.setCateIds4es(cateIds4es);
//                couponGoodsQueryResponse.setCateMap(mapCateById(cateIds));
//                break;
//            case STORE_CATE:
//                scopeList = couponMarketingScopeService.listScopeByCouponId(couponId);
//                couponGoodsQueryResponse.setIsAll(DefaultFlag.NO);
//                List<Long> storeCateIds =
//                        scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).sorted().collect(Collectors.toList());
//                couponGoodsQueryResponse.setStoreCateIds(storeCateIds);
//                couponGoodsQueryResponse.setStoreCateMap(mapStoreCateById(storeCateIds));
//                break;
//            case SKU:
//                scopeList = couponMarketingScopeService.listScopeByCouponId(couponId);
//                couponGoodsQueryResponse.setIsAll(DefaultFlag.NO);
//                couponGoodsQueryResponse.setGoodsInfoId(scopeList.stream().map(CouponMarketingScope::getScopeId).collect(Collectors.toList()));
//                break;
//            default:
//                couponGoodsQueryResponse.setIsAll(DefaultFlag.YES);
//                break;
//        }
//    }
//
//    /**
//     * 获取数据库couponCache集合
//     * @param request
//     * @return
//     */
//    public List<CouponCache> cacheList(CouponCacheInitRequest request) {
//        List<CouponCache> cacheList = couponActivityConfigService.queryCouponStarted(request);
//        this.addScope(cacheList);
//        this.addCouponCate(cacheList);
//        this.addCouponStoreIds(cacheList);
//        return cacheList;
//
//    }
//
//    /**
//     * 获取数据库couponCache集合
//     *
//     * @param request
//     * @return
//     */
//    public List<CouponCache> cachePage(CouponCacheInitRequest request) {
//        List<CouponCache> cacheList = couponActivityConfigService.queryCouponStartPage(request).getContent();
//        if (CollectionUtils.isEmpty(cacheList)) {
//            return Lists.newArrayList();
//        }
//        this.addScope(cacheList);
//        this.addCouponCate(cacheList);
//        this.addCouponStoreIds(cacheList);
//        return cacheList;
//
//    }
//}
