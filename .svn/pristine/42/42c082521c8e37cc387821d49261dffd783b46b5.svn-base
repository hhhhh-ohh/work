package com.wanmi.sbc.marketing.coupon.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.handler.aop.MasterRouteOnly;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailListByConditionRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerIdsListRequest;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelForMarketingRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerDetailListByConditionResponse;
import com.wanmi.sbc.customer.api.response.store.ListStoreByIdsResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.request.coupon.EsActivityCouponModifyRequest;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandListRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateVO;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCheckoutResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodeValidOrderCommitResponse;
import com.wanmi.sbc.marketing.api.response.coupon.GetCouponGroupResponse;
import com.wanmi.sbc.marketing.bean.constant.Constant;
import com.wanmi.sbc.marketing.bean.dto.*;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.CheckGoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.marketing.common.request.TradeItemInfo;
import com.wanmi.sbc.marketing.coupon.model.entity.TradeCouponSnapshot;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponActivityCache;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.*;
import com.wanmi.sbc.marketing.coupon.model.vo.CouponView;
import com.wanmi.sbc.marketing.coupon.repository.CouponActivityRepository;
import com.wanmi.sbc.marketing.coupon.repository.CouponCodeRepository;
import com.wanmi.sbc.marketing.coupon.repository.CouponInfoRepository;
import com.wanmi.sbc.marketing.coupon.repository.CouponMarketingScopeRepository;
import com.wanmi.sbc.marketing.coupon.request.CouponCodePageRequest;
import com.wanmi.sbc.marketing.coupon.request.*;
import com.wanmi.sbc.marketing.coupon.response.CouponAutoSelectResponse;
import com.wanmi.sbc.marketing.coupon.response.CouponCodeCountResponse;
import com.wanmi.sbc.marketing.coupon.response.CouponCodeQueryResponse;
import com.wanmi.sbc.marketing.coupon.response.CouponLeftResponse;
import com.wanmi.sbc.marketing.mq.ProducerService;
import com.wanmi.sbc.marketing.newplugin.impl.coupon.CouponCounterCommonService;
import com.wanmi.sbc.marketing.newplugin.impl.coupon.CouponPluginContext;
import com.wanmi.sbc.marketing.newplugin.impl.coupon.MarketingCouponPluginInterface;
import com.wanmi.sbc.marketing.util.common.CodeGenUtil;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 优惠券码Service
 *
 * @author CHENLI
 */
@Slf4j
@Service
@Primary
public class CouponCodeService implements CouponCodeServiceInterface {

    //优惠券库存key
    private final static String COUPON_BANK = "COUPON_BANK:";

    @Autowired
    private CouponCodeService couponCodeService;

    @Autowired
    private CouponCodeRepository couponCodeRepository;

    @Autowired
    private CouponInfoRepository couponInfoRepository;

    @Autowired
    private CouponMarketingScopeRepository couponMarketingScopeRepository;

    @Autowired
    protected TradeCouponSnapshotService tradeCouponSnapshotService;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    private CouponActivityService couponActivityService;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private CouponActivityConfigService couponActivityConfigService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CouponCacheService couponCacheService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Lazy
    @Autowired
    private CouponScopeCacheService couponScopeCacheService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private CouponCounterCommonService couponCounterCommonService;

    @Autowired
    private CouponPluginContext couponPluginContext;

    @Autowired
    private CouponActivityRepository couponActivityRepository;

    /**
     * 根据条件查询优惠券码列表
     */
    @Override
    public List<CouponCode> listCouponCodeByCondition(CouponCodeQueryRequest request) {
        Sort sort = request.getSort();
        if (Objects.nonNull(sort)) {
            return couponCodeRepository.findAll(this.getWhereCriteria(request), sort);
        } else {
            return couponCodeRepository.findAll(this.getWhereCriteria(request));
        }
    }


    @Override
    public Page<CouponCode> pageCouponCodeByCondition(CouponCodeQueryRequest request) {
        return couponCodeRepository.findAll(this.getWhereCriteria(request), request.getPageable());
    }


    @MasterRouteOnly
    public Page<CouponCode> pageCouponCodeByCondition_test(CouponCodeQueryRequest request) {
//        hintManager.addTableShardingValue("coupon_code",1);
        Page<CouponCode> page =  couponCodeRepository.findAll(this.getWhereCriteria(request), request.getPageable());
        return page;
    }

    /**
     * 查询我的未使用优惠券(订单)
     */
    @Override
    @MasterRouteOnly
    public List<CouponCode> findNotUseStatusCouponCode(CouponCodeQueryRequest request) {
        return this.listCouponCodeByCondition(request);
    }


    private Specification<CouponCode> getWhereCriteria(CouponCodeQueryRequest request) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 优惠券券码id集合
            if (CollectionUtils.isNotEmpty(request.getCouponCodeIds())) {
                predicates.add(root.get("couponCodeId").in(request.getCouponCodeIds()));
            }

            // 领取人id
            if (Objects.nonNull(request.getCustomerId())) {
                predicates.add(cbuild.equal(root.get("customerId"), request.getCustomerId()));
            }

            // 优惠券id
            if (Objects.nonNull(request.getCouponId())) {
                predicates.add(cbuild.equal(root.get("couponId"), request.getCouponId()));
            }

            // 优惠券id集合
            if (CollectionUtils.isNotEmpty(request.getCouponIds())) {
                CriteriaBuilder.In in = cbuild.in(root.get("couponId"));
                request.getCouponIds().forEach(in::value);
                predicates.add(in);
            }

            // 优惠券活动id
            if (Objects.nonNull(request.getActivityId())) {
                predicates.add(cbuild.equal(root.get("activityId"), request.getActivityId()));
            }

            // 优惠券活动id集合
            if (CollectionUtils.isNotEmpty(request.getActivityIds())) {
                CriteriaBuilder.In in = cbuild.in(root.get("activityId"));
                request.getActivityIds().forEach(in::value);
                predicates.add(in);
            }

            // 使用状态
            if (Objects.nonNull(request.getUseStatus())) {
                predicates.add(cbuild.equal(root.get("useStatus"), request.getUseStatus()));
            }

            // 删除标记
            if (Objects.nonNull(request.getDelFlag())) {
                predicates.add(cbuild.equal(root.get("delFlag"), request.getDelFlag()));
            }

            // 未过期标识
            if (Objects.nonNull(request.getNotExpire())) {
                predicates.add(cbuild.greaterThan(root.get("endTime"), LocalDateTime.now()));
            }

            // 已过期标识
            if (Objects.nonNull(request.getAlrExpire())) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"), LocalDateTime.now()));
            }

            //获取优惠券的时间，大于开始时间
            if (Objects.nonNull(request.getAcquireStartTime())) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("acquireTime"), request.getAcquireStartTime()));
            }
            //获取优惠券的时间，大于结束时间
            if (Objects.nonNull(request.getAcquireEndTime())) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("acquireTime"), request.getAcquireEndTime()));
            }

            //优惠券到期时间
            if (Objects.nonNull(request.getEndTime())) {
                predicates.add(cbuild.equal(root.get("endTime"), request.getEndTime()));
            }

            //优惠券结束时间，大于开始时间
            if (Objects.nonNull(request.getEndStartTime())) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), request.getEndStartTime()));
            }
            //优惠券结束时间，大于结束时间
            if (Objects.nonNull(request.getEndEndTime())) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"), request.getEndEndTime()));
            }

            //付费会员记录id
            if (Objects.nonNull(request.getPayingMemberRecordId())) {
                predicates.add(cbuild.equal(root.get("payingMemberRecordId"), request.getPayingMemberRecordId()));
            }

            // 优惠券过期前24小时,是否发送订阅消息
            if (Objects.nonNull(request.getCouponExpiredSendFlag())) {
                predicates.add(cbuild.equal(root.get("couponExpiredSendFlag"), request.getCouponExpiredSendFlag()));
            }
            //优惠券状态
            if (CollectionUtils.isNotEmpty(request.getCouponStatusList())) {
                LocalDateTime now = LocalDateTime.now();
                List<Predicate> orPredicates = new ArrayList<>();
                request.getCouponStatusList().forEach(status -> {
                    switch (status) {
                        case NOT_START:
                            orPredicates.add(cbuild.greaterThanOrEqualTo(root.get("startTime"), now));
                            break;
                        case STARTED:
                            orPredicates.add(
                                    cbuild.and(cbuild.lessThan(root.get("startTime"), now),
                                            cbuild.greaterThanOrEqualTo(root.get("endTime"), now)));
                            break;
                    }
                });
                predicates.add(cbuild.or(orPredicates.toArray(new Predicate[0])));
            }
            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }

    /**
     * @description 构造券快照信息
     * @author malianfeng
     * @date 2022/9/30 10:42
     * @param request 请求
     * @param couponCodeVos 券码列表
     * @return com.wanmi.sbc.marketing.coupon.model.entity.TradeCouponSnapshot
     */
    public TradeCouponSnapshot generateCheckInfo(CouponCodeListForUseRequest request, List<CouponCodeVO> couponCodeVos) {
        List<TradeItemInfo> tradeItemInfos = request.getTradeItems();
        TradeCouponSnapshot checkInfo = new TradeCouponSnapshot();
        if (CollectionUtils.isNotEmpty(couponCodeVos)) {
            BaseResponse<ListStoreByIdsResponse> baseResponse =
                    storeQueryProvider.listByIds(new ListStoreByIdsRequest(couponCodeVos.stream().map(CouponCodeVO::getStoreId).collect(Collectors.toList())));
            List<StoreVO> storeVOList = baseResponse.getContext().getStoreVOList();
            for (CouponCodeVO couponCodeVO : couponCodeVos) {
                for (StoreVO storeVO : storeVOList) {
                    if (Objects.equals(couponCodeVO.getStoreId(), storeVO.getStoreId())) {
                        couponCodeVO.setStoreName(storeVO.getStoreName());
                    }
                }
            }
            List<CouponMarketingScope> allScopeList = couponMarketingScopeRepository.findByCouponIdIn(
                    couponCodeVos.stream().map(CouponCodeVO::getCouponId).collect(Collectors.toList())
            );
            couponCodeVos.forEach(couponCode -> {

                // 3.1.标记未到可用时间优惠券
                if (!(LocalDateTime.now().isAfter(couponCode.getStartTime())
                        && LocalDateTime.now().isBefore(couponCode.getEndTime()))) {
                    couponCode.setStatus(CouponCodeStatus.UN_REACH_TIME);
                }

                //3.2.判断优惠券是否即将过期 结束时间加上5天，大于现在时间，即将过期 true 是 false 否
                if (Objects.nonNull(couponCode.getEndTime())) {
                    if (LocalDateTime.now().plusDays(Constant.OUT_OF_DAYS).isAfter(couponCode.getEndTime())) {
                        couponCode.setNearOverdue(true);
                    } else {
                        couponCode.setNearOverdue(false);
                    }
                }

                // 3.3.根据优惠券营销类型计算关联的订单商品
                List<CouponMarketingScope> scopeList = allScopeList.stream()
                        .filter(scope -> StringUtils.equals(scope.getCouponId(), couponCode.getCouponId()))
                        .collect(Collectors.toList());
                List<TradeItemInfo> tradeItems = this.listCouponSkuIds(tradeItemInfos, couponCode, scopeList);
                // 排除分销商品
                // 现在分销商品可以使用优惠券了
                /*DefaultFlag openFlag = distributionCacheService.queryOpenFlag();
                tradeItems = tradeItems.stream()
                        .filter(item -> {
                            DefaultFlag storeOpenFlag =
                                    distributionCacheService.queryStoreOpenFlag(item.getStoreId().toString());
                            return !(DefaultFlag.YES.equals(openFlag)
                                    && DefaultFlag.YES.equals(storeOpenFlag)
                                    && DistributionGoodsAudit.CHECKED.equals(item.getDistributionGoodsAudit()));
                        }).collect(Collectors.toList());*/
                // 优惠券关联的订单商品总价
                BigDecimal totalPrice = tradeItems.stream()
                        .map((item) -> item.getPrice()).reduce(BigDecimal.ZERO, (total, price) -> total.add(price));
                // 优惠券关联的订单商品
                List<String> goodsInfoIds = tradeItems.stream().map((item) -> item.getSkuId()).collect(Collectors.toList());
                // 3.4.根据优惠券关联的订单商品，计算优惠券状态
                if (couponCode.getStatus() != CouponCodeStatus.UN_REACH_TIME) {
                    if (goodsInfoIds.size() == 0) {
                        couponCode.setStatus(CouponCodeStatus.NO_AVAILABLE_SKU);
                    } else {
                        if (FullBuyType.FULL_MONEY == couponCode.getFullBuyType() &&
                                couponCode.getFullBuyPrice().compareTo(totalPrice) > 0) {
                            couponCode.setStatus(CouponCodeStatus.UN_REACH_PRICE);
                        } else {
                            couponCode.setStatus(CouponCodeStatus.AVAILABLE);
                        }
                    }
                }
                // 如果是O2O状态，将所有非门店券状态改为本单商品不可用
                if (PluginType.O2O == request.getPluginType()) {
                    if (CouponType.STOREFRONT_VOUCHER != couponCode.getCouponType()
                            && CouponType.BOSS_STOREFRONT_VOUCHER != couponCode.getCouponType()) {
                        couponCode.setStatus(CouponCodeStatus.NO_AVAILABLE_SKU);
                    }
                }

                //0元订单所有优惠券都不可使用
                if (Objects.nonNull(request.getPrice()) && !(request.getPrice().compareTo(BigDecimal.ZERO) > 0)) {
                    couponCode.setStatus(CouponCodeStatus.NO_AVAILABLE_SKU);
                }

                // 3.5.将优惠券和商品关联关系放入缓存对象（仅存放本单可用的券）
                if (CouponCodeStatus.AVAILABLE == couponCode.getStatus()) {
                    TradeCouponSnapshot.CheckCouponCode checkCouponCode = new TradeCouponSnapshot.CheckCouponCode();
                    checkCouponCode.setGoodsInfoIds(goodsInfoIds);
                    checkCouponCode.setStoreId(couponCode.getStoreId());
                    checkCouponCode.setActivityId(couponCode.getActivityId());
                    checkCouponCode.setCouponCodeId(couponCode.getCouponCodeId());
                    checkCouponCode.setCouponId(couponCode.getCouponId());
                    checkCouponCode.setFullBuyPrice(couponCode.getFullBuyPrice());
                    checkCouponCode.setFullBuyType(couponCode.getFullBuyType());
                    checkCouponCode.setDenomination(couponCode.getDenomination());
                    checkCouponCode.setCouponType(couponCode.getCouponType());
                    checkCouponCode.setCouponMarketingType(couponCode.getCouponMarketingType());
                    checkCouponCode.setCouponDiscountMode(couponCode.getCouponDiscountMode());
                    checkCouponCode.setMaxDiscountLimit(couponCode.getMaxDiscountLimit());
                    checkCouponCode.setEndTime(couponCode.getEndTime());
                    checkCouponCode.setAcquireTime(couponCode.getAcquireTime());
                    checkInfo.getCouponCodes().add(checkCouponCode);
                }
            });
        }

        // 4.将缓存对象存入mongo
        checkInfo.setGoodsInfos(request.getTradeItems().stream().map((item) -> {
            TradeCouponSnapshot.CheckGoodsInfo checkGoodsInfo = new TradeCouponSnapshot.CheckGoodsInfo();
            checkGoodsInfo.setStoreId(item.getStoreId());
            checkGoodsInfo.setGoodsInfoId(item.getSkuId());
            checkGoodsInfo.setSplitPrice(item.getPrice());
            return checkGoodsInfo;
        }).collect(Collectors.toList()));
        checkInfo.setBuyerId(request.getCustomerId());
        checkInfo.setTerminalToken(request.getTerminalToken());

        /*TradeCouponSnapshot tradeCouponSnapshot =
                tradeCouponSnapshotRepository.findByBuyerId(request.getCustomerId()).orElse(null);


        if (Objects.nonNull(tradeCouponSnapshot)) {
            tradeCouponSnapshotService.deleteTradeCouponSnapshot(tradeCouponSnapshot.getId());
        }*/
        checkInfo.setId(UUIDUtil.getUUID());
        return checkInfo;
    }

    /**
     * 查询使用优惠券页需要的优惠券列表
     */
    @Override
    @Transactional
    public List<CouponCodeVO> listCouponCodeForUse(CouponCodeListForUseRequest request) {

        // 1.设置tradeItem的storeCateIds
        List<TradeItemInfo> tradeItemInfos = request.getTradeItems();
        List<String> goodsIds = tradeItemInfos.stream()
                .map(TradeItemInfo::getSpuId).distinct().collect(Collectors.toList());
        List<StoreCateGoodsRelaVO> relas =
                storeCateQueryProvider.listByGoods(new StoreCateListByGoodsRequest(goodsIds)).getContext().getStoreCateGoodsRelaVOList();
        Map<String, List<StoreCateGoodsRelaVO>> relasMap = relas.stream()
                .collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId));
        tradeItemInfos.forEach(item -> {
            item.setStoreCateIds(relasMap.getOrDefault(item.getSpuId(), Collections.emptyList()).stream()
                    .map(StoreCateGoodsRelaVO::getStoreCateId).collect(Collectors.toList()));
        });

        // 2.查询我的未使用的优惠券，并关联优惠券信息
        // 查询用户优惠券
        List<CouponCodeVO> couponCodeVos = new ArrayList<>();
        CouponCodeQueryRequest codeQueryRequest = new CouponCodeQueryRequest();
        codeQueryRequest.setCustomerId(request.getCustomerId());
        codeQueryRequest.setDelFlag(DeleteFlag.NO);
        codeQueryRequest.setUseStatus(DefaultFlag.NO);
        codeQueryRequest.setNotExpire(Boolean.TRUE);
        List<CouponCode> couponCodeList = couponCodeRepository.findAll(this.getWhereCriteria(codeQueryRequest));
        if (CollectionUtils.isNotEmpty(couponCodeList)) {
            // 根据优惠券查询相关券活动
            Map<String, CouponInfo> couponMap = new HashMap<>();
            List<String> couponIds = couponCodeList.stream().map(CouponCode::getCouponId).collect(Collectors.toList());
            CouponInfoQueryRequest couponInfoQueryRequest = new CouponInfoQueryRequest();
            couponInfoQueryRequest.setCouponIds(couponIds);
            couponInfoQueryRequest.setCouponType(request.getCouponType());
            couponInfoQueryRequest.setCouponMarketingType(request.getCouponMarketingType());
            couponInfoQueryRequest.setCouponMarketingTypeList(request.getCouponMarketingTypes());
            List<CouponInfo> couponList = couponInfoService.queryCouponInfos(couponInfoQueryRequest);
            if (CollectionUtils.isNotEmpty(couponList)) {
                couponMap = couponList.stream().collect(Collectors.toMap(CouponInfo::getCouponId, Function.identity()));
            }

            // 根据优惠券查询相关优惠券信息
            Map<String, CouponActivity> activitieMap = new HashMap<>();
            List<String> activityIds = couponCodeList.stream().map(CouponCode::getActivityId).collect(Collectors.toList());
            List<CouponActivity> activitieList = couponActivityRepository.findAllById(activityIds);
            if (CollectionUtils.isNotEmpty(activitieList)) {
                activitieMap = activitieList.stream().collect(Collectors.toMap(CouponActivity::getActivityId, Function.identity()));
            }

            Map<String, CouponActivity> finalActivitieMap = activitieMap;
            Map<String, CouponInfo> finalCouponMap = couponMap;
            couponCodeVos = couponCodeList.stream().map(couponCode -> {
                if (finalCouponMap.containsKey(couponCode.getCouponId())) {
                    if (finalActivitieMap.containsKey(couponCode.getActivityId())) {
                        return CouponCodeListForUseRequest.converter(couponCode, finalCouponMap.get(couponCode.getCouponId()), finalActivitieMap.get(couponCode.getActivityId()));
                    } else {
                        return CouponCodeListForUseRequest.converter(couponCode, finalCouponMap.get(couponCode.getCouponId()));
                    }
                }
                return null;
            }).collect(Collectors.toList());
        }

        // couponCodeVos根据优惠券denomination降序
        couponCodeVos = couponCodeVos.stream().filter(Objects::nonNull).sorted(Comparator.comparing(CouponCodeVO::getDenomination).reversed()).collect(Collectors.toList());

        // 3.循环处理每个优惠券，构造券快照数据
        TradeCouponSnapshot checkInfo = this.generateCheckInfo(request, couponCodeVos);

        tradeCouponSnapshotService.addTradeCouponSnapshot(checkInfo);

        // 4.再一次为券填充状态
        this.fillCodeStatusAgain(couponCodeVos, request);
        
        return couponCodeVos;
    }

    /**
     * @description 再一次为券填充状态
     * @author malianfeng
     * @date 2022/11/7 18:44
     * @param couponCodeVos 券列表
     * @param request 请求
     * @return void
     */
    private void fillCodeStatusAgain(List<CouponCodeVO> couponCodeVos, CouponCodeListForUseRequest request) {
        // 1. 存在用户已选券时，为不可用平台券和店铺运费券赋"未达到使用门槛"
        if (CollectionUtils.isNotEmpty(request.getSelectedCouponCodeIds())) {
            // 因为系统按照 店铺满减/折券 -> 平台券 -> 店铺运费券 的顺序判断使用门槛和算价，前者的时候可能会导致后者不满足门槛
            Set<String> allUnreachedIds = new HashSet<>();
            // 这里通过传入已选券 codeIds，二次算价判断门槛，对不可用的后者，进行过滤
            CouponCheckoutRequest checkoutRequest = new CouponCheckoutRequest();
            checkoutRequest.setCustomerId(request.getCustomerId());
            checkoutRequest.setTerminalToken(request.getTerminalToken());
            List<String> selectedCouponCodeIds = request.getSelectedCouponCodeIds();
            // 构造券Map，[couponCodeId] => [CouponCodeVO]
            Map<String, CouponCodeVO> selectedCouponCodeMap =
                    couponCodeVos.stream()
                            .filter(item -> selectedCouponCodeIds.contains(item.getCouponCodeId()))
                            .collect(Collectors.toMap(CouponCodeVO::getCouponCodeId, Function.identity()));
            // 1.1 筛选出店铺满减券、满折券，找出因此不可用的平台券
            List<String> storeFullCouponCodeIds = selectedCouponCodeIds.stream().filter(couponCodeId -> {
                CouponCodeVO coupon = selectedCouponCodeMap.get(couponCodeId);
                return Objects.nonNull(coupon) && CouponType.STORE_VOUCHERS == coupon.getCouponType() && coupon.isFullAmount();
            }).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(selectedCouponCodeIds)) {
                checkoutRequest.setCouponCodeIds(storeFullCouponCodeIds);
                checkoutRequest.setUnreachedTypes(Collections.singletonList(QueryCouponType.GENERAL_REDUCTION));
                allUnreachedIds.addAll(this.checkoutCoupons(checkoutRequest).getAllUnreachedIds());
            }
            // 1.2 筛选出平台满减券，找出因此不可用的店铺运费券
            List<String> platformFullCouponCodeIds = selectedCouponCodeIds.stream().filter(couponCodeId -> {
                CouponCodeVO coupon = selectedCouponCodeMap.get(couponCodeId);
                return Objects.nonNull(coupon) && CouponType.GENERAL_VOUCHERS == coupon.getCouponType() && coupon.isFullAmount();
            }).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(selectedCouponCodeIds)) {
                storeFullCouponCodeIds.addAll(platformFullCouponCodeIds);
                checkoutRequest.setCouponCodeIds(storeFullCouponCodeIds);
                checkoutRequest.setUnreachedTypes(Collections.singletonList(QueryCouponType.STORE_FREIGHT));
                allUnreachedIds.addAll(this.checkoutCoupons(checkoutRequest).getAllUnreachedIds());
            }
            // 1.3 为不可用的券重新赋值
            couponCodeVos.stream()
                    .filter(item -> allUnreachedIds.contains(item.getCouponCodeId()))
                    .forEach(item -> item.setStatus(CouponCodeStatus.UN_REACH_PRICE));
        }

        // 2. 存在店铺运费信息时，为店铺运费券赋"本单商品不可用"
        if (CollectionUtils.isNotEmpty(request.getStoreFreights())) {
            // 2.1 店铺运费为0，该店铺下所有运费券不可用
            couponCodeVos.stream()
                    .filter(item -> this.isNoAvailableSkuForFreightCoupon(request.getStoreFreights(), item.getCouponMarketingType(), item.getStoreId()))
                    .forEach(item -> item.setStatus(CouponCodeStatus.NO_AVAILABLE_SKU));
        }
    }

    /**
     * @description 判断是否"本单商品不可用"的运费券
     * @author malianfeng
     * @date 2022/11/7 19:09
     * @param storeFreights 店铺运费列表
     * @param couponMarketingType 券营销类型
     * @param couponStoreId 券信息
     * @return boolean
     */
    public boolean isNoAvailableSkuForFreightCoupon(List<StoreFreightDTO> storeFreights,
                                                    CouponMarketingType couponMarketingType, Long couponStoreId) {
        if (Objects.nonNull(storeFreights) && CouponMarketingType.FREIGHT_COUPON == couponMarketingType) {
            return storeFreights.stream()
                    .filter(item -> item.getStoreId().equals(couponStoreId))
                    .anyMatch(item -> Objects.isNull(item.getFreight()) || item.getFreight().compareTo(BigDecimal.ZERO) <= 0);
        }
        return false;
    }

    @Override
    public List<CouponCodeAutoSelectDTO> autoSelectForCart(CouponCodeListForUseRequest request, CouponCodeAutoSelectForCartRequest selectRequest) {

        List<CouponCodeVO> couponCodeVos = new ArrayList<>();
        CouponCodeQueryRequest codeQueryRequest = new CouponCodeQueryRequest();
        codeQueryRequest.setCustomerId(request.getCustomerId());
        codeQueryRequest.setDelFlag(DeleteFlag.NO);
        codeQueryRequest.setUseStatus(DefaultFlag.NO);
        codeQueryRequest.setNotExpire(Boolean.TRUE);
        List<CouponCode> couponCodeList = couponCodeRepository.findAll(this.getWhereCriteria(codeQueryRequest));
        if (CollectionUtils.isNotEmpty(couponCodeList)) {
            // 根据优惠券查询相关券活动
            Map<String, CouponInfo> couponMap = new HashMap<>();
            List<String> couponIds = couponCodeList.stream().map(CouponCode::getCouponId).collect(Collectors.toList());
            CouponInfoQueryRequest couponInfoQueryRequest = new CouponInfoQueryRequest();
            couponInfoQueryRequest.setCouponIds(couponIds);
            couponInfoQueryRequest.setCouponType(request.getCouponType());
            couponInfoQueryRequest.setCouponMarketingType(request.getCouponMarketingType());
            couponInfoQueryRequest.setCouponMarketingTypeList(request.getCouponMarketingTypes());
            List<CouponInfo> couponList = couponInfoService.queryCouponInfos(couponInfoQueryRequest);
            if (CollectionUtils.isNotEmpty(couponList)) {
                couponMap = couponList.stream().collect(Collectors.toMap(CouponInfo::getCouponId, Function.identity()));
            }

            // 根据优惠券查询相关优惠券信息
            Map<String, CouponActivity> activitieMap = new HashMap<>();
            List<String> activityIds = couponCodeList.stream().map(CouponCode::getActivityId).collect(Collectors.toList());
            List<CouponActivity> activitieList = couponActivityRepository.findAllById(activityIds);
            if (CollectionUtils.isNotEmpty(activitieList)) {
                activitieMap = activitieList.stream().collect(Collectors.toMap(CouponActivity::getActivityId, Function.identity()));
            }

            Map<String, CouponActivity> finalActivitieMap = activitieMap;
            Map<String, CouponInfo> finalCouponMap = couponMap;
            couponCodeVos = couponCodeList.stream().map(couponCode -> {
                if (finalCouponMap.containsKey(couponCode.getCouponId())) {
                    if (finalActivitieMap.containsKey(couponCode.getActivityId())) {
                        return CouponCodeListForUseRequest.converter(couponCode, finalCouponMap.get(couponCode.getCouponId()), finalActivitieMap.get(couponCode.getActivityId()));
                    } else {
                        return CouponCodeListForUseRequest.converter(couponCode, finalCouponMap.get(couponCode.getCouponId()));
                    }
                }
                return null;
            }).collect(Collectors.toList());
        }

        // couponCodeVos根据优惠券denomination降序
        couponCodeVos = couponCodeVos.stream().sorted(Comparator.comparing(CouponCodeVO::getDenomination).reversed()).collect(Collectors.toList());

        // 用户已领券ID集合，key为[activityId_couponId]唯一确定一张券
        Set<String> customerCouponKeys = couponCodeVos.stream()
                .map(item -> String.format("%s_%s", item.getActivityId(), item.getCouponId())).collect(Collectors.toSet());

        // 2.1 请求中获取构造的SKU优惠券（仅根据商品和活动构造出的券，用户可能并不一定有，相当于预发）
        List<CouponCodeVO> skuCouponCodeVos = selectRequest.getSkuCouponCodeVos();
        // 2.2 将构造的SKU券和客户已有的券去重合并（保证 couponCodeVos 同时包含 用户已领和预领，这样好一起进行计算）
        for (CouponCodeVO couponCodeVO : skuCouponCodeVos) {
            String couponKey = String.format("%s_%s", couponCodeVO.getActivityId(), couponCodeVO.getCouponId());
            if (!customerCouponKeys.contains(couponKey)) {
                // 已领券中不包含SKU券时，添加构造的SKU券（同活动的下同couponId，用户已领时，无需再追加）
                couponCodeVos.add(couponCodeVO);
            }
        }
        // 2.3 填充已领取的券ID列表，用于区分已领和待领抵扣
        selectRequest.setHasFetchedCouponIds(customerCouponKeys);

        // 3.处理 couponCodeVos 并构造快照数据
        TradeCouponSnapshot checkInfo = this.generateCheckInfo(request, couponCodeVos);

        // 4. 自动选取优惠券
        // 根据用户自选券 couponCodeIds 是否有值，决定走完整的自动选券逻辑，还是部分自动选券逻辑
        if (Objects.isNull(selectRequest.getCustomCouponCodeIds())) {
            return couponCodeService.autoSelectCoupons(checkInfo, selectRequest.getStoreFreights());
        } else {
            return couponCodeService.autoSelectCouponsWithCustom(checkInfo, selectRequest.getCustomCouponCodeIds(), selectRequest.getStoreFreights());
        }
    }

    /**
     * 具有用户自选券时的部分自动选券
     * @param checkInfo 快照数据
     * @param customCouponCodeIds 用户自选的券码id
     * @param storeFreights 店铺运费列表
     * @return
     */
    public List<CouponCodeAutoSelectDTO> autoSelectCouponsWithCustom(TradeCouponSnapshot checkInfo,
                                                                     List<String> customCouponCodeIds,
                                                                     List<StoreFreightDTO> storeFreights) {
        // 参数校验
        List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos = checkInfo.getGoodsInfos();
        List<TradeCouponSnapshot.CheckCouponCode> checkCouponCodes = checkInfo.getCouponCodes();
        if (CollectionUtils.isEmpty(checkGoodsInfos) || CollectionUtils.isEmpty(checkCouponCodes)) {
            return Collections.emptyList();
        }

        // 最终返回的选券列表
        List<CouponCodeAutoSelectDTO> selectDTOList = new ArrayList<>();

        // 筛选出用户自选的满系券（满减券和满折券），此列表有值，则不走满系券的自动选券逻辑
        // 适用于，用户更改了满系券的自动选券方案，但是没有更改运费券的方案，此时需要单独为运费券返回方案
        List<TradeCouponSnapshot.CheckCouponCode> customFullCheckCouponCodes = null;
        if (CollectionUtils.isNotEmpty(customCouponCodeIds)) {
            customFullCheckCouponCodes = checkCouponCodes.stream()
                    .filter(item -> customCouponCodeIds.contains(item.getCouponCodeId()))
                    .filter(item -> CouponMarketingType.REDUCTION_COUPON == item.getCouponMarketingType()
                            || CouponMarketingType.DISCOUNT_COUPON == item.getCouponMarketingType()).collect(Collectors.toList());
        }

        // 1. 存在用户自选的满系券时，按照先店铺券，再平台券顺序处理券的算价和优惠均摊
        if (CollectionUtils.isNotEmpty(customFullCheckCouponCodes)) {
            // 公共算价入参
            CouponCounterRequest couponCounterRequest = new CouponCounterRequest();
            couponCounterRequest.setGoodsInfos(checkGoodsInfos);
            customFullCheckCouponCodes.stream()
                    .sorted(Comparator.comparing(TradeCouponSnapshot.CheckCouponCode::getCouponType).reversed())
                    .forEach(item -> {
                        // 获取券策略
                        MarketingCouponPluginInterface strategy = couponPluginContext.getCouponService(item.getCouponMarketingType());
                        couponCounterRequest.setCheckCouponCode(item);
                        // 计算券并均摊券的实际优惠
                        BigDecimal actualDiscount = strategy.calcAndSplitPriceForCheckoutCoupons(couponCounterRequest);
                        CouponCodeAutoSelectDTO targetCoupon = KsBeanUtil.convert(item, CouponCodeAutoSelectDTO.class);
                        targetCoupon.setActualDiscount(actualDiscount);
                        selectDTOList.add(targetCoupon);
                    });
        }

        // 2. 店铺运费券，自动选券
        if (CollectionUtils.isNotEmpty(storeFreights)) {
            CouponAutoSelectRequest autoSelectRequest = new CouponAutoSelectRequest();
            autoSelectRequest.setCheckGoodsInfos(checkGoodsInfos);
            autoSelectRequest.setCheckCouponCodes(checkCouponCodes);
            autoSelectRequest.setStoreFreights(storeFreights);
            MarketingCouponPluginInterface freightStrategy = couponPluginContext.getCouponService(CouponMarketingType.FREIGHT_COUPON);
            CouponAutoSelectResponse freightSelectRes = freightStrategy.autoSelect(autoSelectRequest);
            selectDTOList.addAll(freightSelectRes.getSelectCoupons());
        }

        return selectDTOList;
    }

    /**
     * 自动选取最佳券组合
     * @param checkInfo 快照数据
     * @param storeFreights 店铺运费列表
     * @return
     */
    public List<CouponCodeAutoSelectDTO> autoSelectCoupons(TradeCouponSnapshot checkInfo,
                                                           List<StoreFreightDTO> storeFreights) {

        // 参数校验
        List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos = checkInfo.getGoodsInfos();
        List<TradeCouponSnapshot.CheckCouponCode> checkCouponCodes = checkInfo.getCouponCodes();
        if (CollectionUtils.isEmpty(checkGoodsInfos) || CollectionUtils.isEmpty(checkCouponCodes)) {
            return Collections.emptyList();
        }


        // 券的使用与选取具有先后顺序，由先到后依次是：
        // 1. 店铺满减/满折券（同店铺下不可叠加，应选2个子类中优惠更多的一种）
        // 2. 平台满减券
        // 3. 店铺运费券


        // 选券列表
        List<CouponCodeAutoSelectDTO> selectDTOList = new ArrayList<>();

        // 将checkCouponCodes转为Map，[couponCodeId] => [CheckCouponCode]
        Map<String, TradeCouponSnapshot.CheckCouponCode> checkCouponCodeMap = checkCouponCodes.stream()
                .collect(Collectors.toMap(TradeCouponSnapshot.CheckCouponCode::getCouponCodeId, Function.identity()));

        // 选券公共入参
        CouponAutoSelectRequest autoSelectRequest = new CouponAutoSelectRequest();
        autoSelectRequest.setCheckGoodsInfos(checkGoodsInfos);


        // 1. 先分别走一遍店铺满减/满折券，需要过滤掉平台券
        List<TradeCouponSnapshot.CheckCouponCode> storeCoupons = checkCouponCodes.stream()
                .filter(item -> CouponType.GENERAL_VOUCHERS != item.getCouponType()).collect(Collectors.toList());
        autoSelectRequest.setCheckCouponCodes(storeCoupons);
        // 1.1 店铺满减券选取
        MarketingCouponPluginInterface reductionStrategy = couponPluginContext.getCouponService(CouponMarketingType.REDUCTION_COUPON);
        CouponAutoSelectResponse reductionSelectRes = reductionStrategy.autoSelect(autoSelectRequest);
        Map<Long, CouponCodeAutoSelectDTO> reductionCouponMap = reductionSelectRes.getSelectCoupons().stream()
                .collect(Collectors.toMap(CouponCodeAutoSelectDTO::getStoreId, Function.identity()));
        // 1.2 店铺折券选取
        MarketingCouponPluginInterface discountStrategy = couponPluginContext.getCouponService(CouponMarketingType.DISCOUNT_COUPON);
        CouponAutoSelectResponse discountSelectRes = discountStrategy.autoSelect(autoSelectRequest);
        Map<Long, CouponCodeAutoSelectDTO> discountCouponMap = discountSelectRes.getSelectCoupons().stream()
                .collect(Collectors.toMap(CouponCodeAutoSelectDTO::getStoreId, Function.identity()));
        // 1.3 遍历所有店铺，取同店铺下实际优惠金额更大的券类型
        Set<Long> storeIdSet = storeCoupons.stream().map(TradeCouponSnapshot.CheckCouponCode::getStoreId).collect(Collectors.toSet());
        for (Long storeId : storeIdSet) {
            // 该店铺下的最优满减券
            CouponCodeAutoSelectDTO storeReductionCoupon = reductionCouponMap.get(storeId);
            // 该店铺下的最优满折券
            CouponCodeAutoSelectDTO storeDiscountCoupon = discountCouponMap.get(storeId);
            CouponCodeAutoSelectDTO selectDTO = couponCounterCommonService.maxByActualDiscount(storeReductionCoupon, storeDiscountCoupon);
            if (Objects.nonNull(selectDTO)) {
                selectDTOList.add(selectDTO);
            }
        }
        // 1.4 确定了店铺券后，要对 checkGoodsInfos 中的商品均摊一下优惠金额，因为后续选券还要基于优惠后的商品金额
        selectDTOList.forEach(item -> {
            TradeCouponSnapshot.CheckCouponCode checkCouponCode = checkCouponCodeMap.get(item.getCouponCodeId());
            if (Objects.nonNull(checkCouponCode)) {
                couponCounterCommonService.splitSkuPriceForCoupon(checkGoodsInfos, checkCouponCode, item.getActualDiscount());
            }
        });


        // 2. 平台满减券，需要筛选出平台券
        // 判断是否存在未抵扣完的商品，存在则继续选出平台满减券
        boolean hasSurplus = checkGoodsInfos.stream().anyMatch(item -> item.getSplitPrice().compareTo(BigDecimal.ZERO) > 0);
        if (hasSurplus) {
            List<TradeCouponSnapshot.CheckCouponCode> platformCoupons = checkCouponCodes.stream()
                    .filter(item -> CouponType.GENERAL_VOUCHERS == item.getCouponType()).collect(Collectors.toList());
            autoSelectRequest.setCheckCouponCodes(platformCoupons);
            CouponAutoSelectResponse platformReductionSelectRes = reductionStrategy.autoSelect(autoSelectRequest);
            platformReductionSelectRes.getSelectCoupons().forEach(selectDTO -> {
                TradeCouponSnapshot.CheckCouponCode checkCouponCode = checkCouponCodeMap.get(selectDTO.getCouponCodeId());
                if (Objects.nonNull(checkCouponCode)) {
                    // 均摊金额
                    couponCounterCommonService.splitSkuPriceForCoupon(checkGoodsInfos, checkCouponCode, selectDTO.getActualDiscount());
                    // 追加至选券列表
                    selectDTOList.add(selectDTO);
                }
            });
        }


        // 3. 店铺运费券
        if (CollectionUtils.isNotEmpty(storeFreights)) {
            autoSelectRequest.setCheckCouponCodes(storeCoupons);
            autoSelectRequest.setStoreFreights(storeFreights);
            MarketingCouponPluginInterface freightStrategy = couponPluginContext.getCouponService(CouponMarketingType.FREIGHT_COUPON);
            CouponAutoSelectResponse freightSelectRes = freightStrategy.autoSelect(autoSelectRequest);
            selectDTOList.addAll(freightSelectRes.getSelectCoupons());
        }


        return selectDTOList;
    }

    /**
     * 过滤出优惠券包含的商品列表
     *
     * @param tradeItems 待过滤商品列表
     * @param couponCode 优惠券信息
     * @param scopeList  优惠券作用范围
     * @return
     */
    @Override
    public List<TradeItemInfo> listCouponSkuIds(List<TradeItemInfo> tradeItems, CouponCodeVO couponCode,
                                                List<CouponMarketingScope> scopeList) {
        Stream<TradeItemInfo> tradeItemsStream = tradeItems.stream();
        List<String> scopeIds = scopeList.stream().map(CouponMarketingScope::getScopeId).collect(Collectors.toList());
        switch (couponCode.getScopeType()) {
            case ALL: //全部商品
                if (couponCode.getPlatformFlag() != DefaultFlag.YES) {
                    tradeItemsStream =
                            tradeItemsStream.filter((item) -> couponCode.getStoreId().equals(item.getStoreId()));
                }
                break;
            case BRAND: //按品牌
//                couponBrandName(couponCode, scopeList);
                if (couponCode.getPlatformFlag() == DefaultFlag.YES) {
                    tradeItemsStream = tradeItemsStream
                            .filter((item) -> scopeIds.contains(String.valueOf(item.getBrandId())));
                } else {
                    tradeItemsStream = tradeItemsStream
                            .filter((item) -> couponCode.getStoreId().equals(item.getStoreId()))
                            .filter((item) -> scopeIds.contains(String.valueOf(item.getBrandId())));
                }
                break;
               case BOSS_CATE: //按平台分类
//                couponGoodsCateName(couponCode, scopeList);
                tradeItemsStream = filterTradeItemsStream(tradeItemsStream, scopeIds);
                break;
            case STORE_CATE: //按店铺分类
//                couponStoreCateName(couponCode, scopeList);
                tradeItemsStream = tradeItemsStream
                        .filter((item) -> !Collections.disjoint(scopeIds,
                                item.getStoreCateIds().stream().map(String::valueOf).collect(Collectors.toList()))
                        );
                break;
            case SKU: // 自定义货品
                tradeItemsStream = tradeItemsStream
                        .filter((item) -> scopeIds.contains(item.getSkuId()));
                break;
            case STORE: // 店铺
                tradeItemsStream = tradeItemsStream
                        .filter((item) -> scopeIds.contains(item.getStoreId().toString()));
                break;
            default:
                break;
        }
        tradeItems = tradeItemsStream.collect(Collectors.toList());

        return tradeItems;
    }

    /**
     * 根据勾选的优惠券，返回不可用的平台券、以及优惠券实际优惠总额、每个店铺优惠总额
     */
    @Override
    public CouponCheckoutResponse checkoutCoupons(CouponCheckoutRequest request) {

        // Optional<TradeCouponSnapshot> snapshot = tradeCouponSnapshotRepository.findByBuyerId(request.getCustomerId());
        TradeCouponSnapshot snapshot = tradeCouponSnapshotService.getByTerminalToken(request.getTerminalToken());

        BigDecimal freightCouponTotalPrice = BigDecimal.ZERO;
        List<TradeCouponSnapshot.CheckCouponCode> couponCodes = Objects.nonNull(snapshot) ? snapshot.getCouponCodes() : Collections.emptyList();
        List<TradeCouponSnapshot.CheckGoodsInfo> goodsInfos = Objects.nonNull(snapshot) ? snapshot.getGoodsInfos() : Collections.emptyList();

        // 将checkCouponCodes转为Map，[couponCodeId] => [CheckCouponCode]
        Map<String, TradeCouponSnapshot.CheckCouponCode> couponCodeMap = couponCodes.stream()
                .collect(Collectors.toMap(TradeCouponSnapshot.CheckCouponCode::getCouponCodeId, Function.identity()));

        // 限定满减券和满折券
        java.util.function.Predicate<TradeCouponSnapshot.CheckCouponCode> fullCouponPredicate = (couponCode) ->
                couponCode.getCouponMarketingType() == CouponMarketingType.REDUCTION_COUPON
                        || couponCode.getCouponMarketingType() == CouponMarketingType.DISCOUNT_COUPON;
        // 限定运费券
        java.util.function.Predicate<TradeCouponSnapshot.CheckCouponCode> freightCouponPredicate = (couponCode) ->
                couponCode.getCouponMarketingType() == CouponMarketingType.FREIGHT_COUPON;

        // 统计可用数量，用于前端展示 "X张可用" 文案
        long couponAvailableCount = couponCodes.stream().filter(fullCouponPredicate).count();
        long freightCouponAvailableCount = couponCodes.stream().filter(freightCouponPredicate).count();

        // 1.将选择的店铺券（仅包含满减券和满折券）产生的折扣价，均摊到相应的商品均摊价下，并计算优惠券优惠总价
        BigDecimal couponTotalPrice = couponCodes.stream()
                // 限定店铺券
                .filter(couponCode -> couponCode.getCouponType() == CouponType.STORE_VOUCHERS)
                // 限定满减券和满折券
                .filter(fullCouponPredicate)
                .filter(couponCode -> request.getCouponCodeIds().contains(couponCode.getCouponCodeId()))
                .map(couponCode -> {
                    // 获取该店铺券适用的计算策略
                    MarketingCouponPluginInterface strategy = couponPluginContext.getCouponService(couponCode.getCouponMarketingType());
                    // 计算券并均摊券的实际优惠
                    return strategy.calcAndSplitPriceForCheckoutCoupons(CouponCounterRequest.builder()
                            .checkCouponCode(couponCode)
                            .goodsInfos(goodsInfos)
                            .build());
                }).reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2.计算平台券是否还满足，返回不满足的平台券id
        Set<String> unreachedIds = couponCodes.stream()
                .filter(couponCode -> couponCode.getCouponType() == CouponType.GENERAL_VOUCHERS)
                .filter(couponCode -> !couponCounterCommonService.isReachedThreshold(goodsInfos, couponCode))
                .map(TradeCouponSnapshot.CheckCouponCode::getCouponCodeId).collect(Collectors.toSet());

        // 3.将选择的平台券产生的折扣价，均摊到相应的商品均摊价下
        TradeCouponSnapshot.CheckCouponCode generalCouponCode = couponCodes.stream()
                .filter(c -> c.getCouponType() == CouponType.GENERAL_VOUCHERS)
                .filter(c -> !unreachedIds.contains(c.getCouponCodeId()))
                .filter(c -> request.getCouponCodeIds().contains(c.getCouponCodeId())).findFirst()
                .orElse(null);

        if (generalCouponCode != null) {
            // 获取该平台券适用的计算策略
            MarketingCouponPluginInterface strategy = couponPluginContext.getCouponService(generalCouponCode.getCouponMarketingType());
            // 计算券并均摊券的实际优惠
            BigDecimal actualDiscount = strategy.calcAndSplitPriceForCheckoutCoupons(CouponCounterRequest.builder()
                    .checkCouponCode(generalCouponCode)
                    .goodsInfos(goodsInfos)
                    .build());
            couponTotalPrice = couponTotalPrice.add(actualDiscount);
        }

        // 4.将选择的店铺券运费券产生的折扣价，均摊到相应的商品均摊价下，并计算优惠券优惠总价
        if (CollectionUtils.isNotEmpty(request.getStoreFreights())) {
            // 店铺运费Map，[storeId] => [freight]
            Map<Long, BigDecimal> storeFreightMap =
                    request.getStoreFreights().stream().collect(Collectors.toMap(StoreFreightDTO::getStoreId, StoreFreightDTO::getFreight));
            List<TradeCouponSnapshot.CheckCouponCode> freightCoupons =
                    couponCodes.stream()
                            // 限定店铺券
                            .filter(coupon -> coupon.getCouponType() == CouponType.STORE_VOUCHERS)
                            // 限定满运费券
                            .filter(freightCouponPredicate)
                            .filter(coupon -> request.getCouponCodeIds().contains(coupon.getCouponCodeId()))
                            .collect(Collectors.toList());
            for (TradeCouponSnapshot.CheckCouponCode freightCoupon : freightCoupons) {
                // 券是否达到使用门槛
                boolean reachedThreshold = couponCounterCommonService.isReachedThreshold(goodsInfos, freightCoupon);
                if (reachedThreshold) {
                    // 获取该运费券适用的计算策略
                    MarketingCouponPluginInterface strategy = couponPluginContext.getCouponService(freightCoupon.getCouponMarketingType());
                    // 计算券实际优惠（运费券不会对商品均摊优惠）
                    BigDecimal actualDiscount = strategy.calcAndSplitPriceForCheckoutCoupons(CouponCounterRequest.builder()
                            .checkCouponCode(freightCoupon)
                            .goodsInfos(goodsInfos)
                            .freight(storeFreightMap.get(freightCoupon.getStoreId()))
                            .build());
                    freightCouponTotalPrice = freightCouponTotalPrice.add(actualDiscount);
                } else {
                    unreachedIds.add(freightCoupon.getCouponCodeId());
                }
            }
        }

        // 统计快照里其他未达到使用门槛的券codeIds
        Set<String> allUnreachedIds = new HashSet<>(unreachedIds);
        couponCodes.stream()
                // 非本次已选的券
                .filter(item -> !request.getCouponCodeIds().contains(item.getCouponCodeId()))
                // 未达到使用门槛的券
                .filter(item -> !couponCounterCommonService.isReachedThreshold(goodsInfos, item))
                // 指定的券类型列表（任意满足）
                .filter(item -> CollectionUtils.isEmpty(request.getUnreachedTypes())
                        || request.getUnreachedTypes().stream().anyMatch(type ->
                        type.equals(QueryCouponType.fromValue(item.getCouponType(), item.getCouponMarketingType()))))
                .forEach(item -> allUnreachedIds.add(item.getCouponCodeId()));

        // 区分商品券和运费券不可用数量
        // 判断本次已选是否包含店铺商品券
        boolean hasStoreGoodsCouponFlag = request.getCouponCodeIds().stream()
                .anyMatch(codeId -> {
                    TradeCouponSnapshot.CheckCouponCode coupon = couponCodeMap.get(codeId);
                    if (Objects.nonNull(coupon)) {
                        return fullCouponPredicate.test(coupon) && CouponType.STORE_VOUCHERS == coupon.getCouponType();
                    }
                    return false;
                });

        for (TradeCouponSnapshot.CheckCouponCode couponCode : couponCodes) {
            String couponCodeId = couponCode.getCouponCodeId();
            Long storeId = couponCode.getStoreId();
            CouponMarketingType couponMarketingType = couponCode.getCouponMarketingType();
            if (allUnreachedIds.contains(couponCodeId)) {
                // 未达到使用门槛的券，需要从可用数量中扣除
                if (fullCouponPredicate.test(couponCode) && hasStoreGoodsCouponFlag) {
                    // 本次已选券包含店铺商品券，平台可用券可用数量自减
                    couponAvailableCount -= 1;
                } else if (freightCouponPredicate.test(couponCode)) {
                    // 运费券可用数量自减
                    freightCouponAvailableCount -= 1;
                }
            } else if (this.isNoAvailableSkuForFreightCoupon(request.getStoreFreights(), couponMarketingType, storeId)) {
                // 店铺没有运费时，运费券可用数量自减
                freightCouponAvailableCount -= 1;
            }
        }

        // 计算完优惠券均摊价的商品总价
        BigDecimal totalPrice = goodsInfos.stream()
                .map(TradeCouponSnapshot.CheckGoodsInfo::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CouponCheckoutResponse(
                unreachedIds,
                allUnreachedIds,
                totalPrice.setScale(2, RoundingMode.HALF_UP),
                couponTotalPrice.setScale(2, RoundingMode.HALF_UP),
                freightCouponTotalPrice.setScale(2, RoundingMode.HALF_UP),
                KsBeanUtil.convert(goodsInfos, CheckGoodsInfoVO.class),
                couponAvailableCount,
                freightCouponAvailableCount
        );
    }

    /**
     * APP / H5 查询我的优惠券
     *
     * @param request
     * @return
     * @returncustomerFetchCoupon
     */
    @Override
    public CouponCodeQueryResponse listMyCouponList(CouponCodePageRequest request) {
        if (request.getPluginType() == PluginType.O2O){
            request = couponCodeService.buildRequest(request);
        }

        CouponCodeCountResponse countResponse = this.queryCouponCodeCount(request);

        return CouponCodeQueryResponse.builder().couponCodeVos(this.pageMyCouponList(request))
                .unUseCount(countResponse.getUnUseCount())
                .usedCount(countResponse.getUsedCount())
                .overDueCount(countResponse.getOverDueCount()).build();
    }

    /**
     * PC - 分页查询我的优惠券
     *
     * @param request
     * @return
     */
    @Override
    public Page<CouponCodeVO> pageMyCouponList(CouponCodePageRequest request) {
        //如果storeId或者couponType不为空则查询couponInfo
        List<CouponInfo> couponInfoList = new ArrayList<>();
        List<String> couponInfoIDList = new ArrayList<>();
        if (request.getStoreId() != null
                || request.getCouponType() != null
                || request.getLikeCouponName() != null
                || request.getPlatformFlag() != null
                || request.getCouponMarketingType() != null
                || request.getScopeType() != null
                || CollectionUtils.isNotEmpty(request.getStoreIds())
        ) {
            couponInfoList = couponInfoService.queryCouponInfos(CouponInfoQueryRequest.builder().storeId(request.getStoreId()).couponType(request.getCouponType())
                    .likeCouponName(request.getLikeCouponName())
                    .platformFlag(request.getPlatformFlag())
                    .couponMarketingType(request.getCouponMarketingType())
                    .scopeType(request.getScopeType())
                    .storeIds(request.getStoreIds())
                    .build());
            if (CollectionUtils.isNotEmpty(couponInfoList)) {
                couponInfoIDList = couponInfoList.stream().map(CouponInfo::getCouponId).collect(Collectors.toList());
            }
        }
        //如果storeId不为空则查询优惠券活动
        List<CouponActivity> couponActivityList = new ArrayList<>();
        List<String> couponActivityIdList = new ArrayList<>();
        if (request.getStoreId() != null) {
            CouponActivityPageRequest request1= new CouponActivityPageRequest();
            request1.setStoreId(request.getStoreId());
            couponActivityList = couponActivityService.queryActivityInfoList(request1);
            if (CollectionUtils.isNotEmpty(couponActivityList)) {
                couponActivityIdList = couponActivityList.stream().map(CouponActivity::getActivityId).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(request.getCouponActivityTypes())){
                    couponActivityIdList =
                            couponActivityList.stream().filter(a -> request.getCouponActivityTypes().contains(a.getCouponActivityType()))
                                    .map(CouponActivity::getActivityId).collect(Collectors.toList());
                }
            }
        }

        CouponCodeQueryRequest queryRequest = new CouponCodeQueryRequest();
        queryRequest.setCouponIds(couponInfoIDList);
        queryRequest.setActivityIdList(couponActivityIdList);
        queryRequest.setCustomerId(request.getCustomerId());
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setCouponStatusList(request.getCouponStatusList());
        queryRequest.setAcquireStartTime(request.getAcquireStartTime());
        queryRequest.setAcquireEndTime(request.getAcquireEndTime());
        queryRequest.setEndStartTime(request.getExpireStartTime());
        queryRequest.setEndEndTime(request.getExpireStartTime());

        //使用状态
        if (Objects.nonNull(request.getUseStatus())){
            switch (request.getUseStatus()){
                case 0 :
                    //未使用
                    queryRequest.setUseStatus(DefaultFlag.NO);
                    queryRequest.setNotExpire(Boolean.TRUE);
                    request.putSort("acquireTime", "DESC");
                    break;
                case 1 :
                    //已使用
                    queryRequest.setUseStatus(DefaultFlag.YES);
                    request.putSort("useDate", "DESC");
                    break;
                case 2 :
                    //已过期
                    queryRequest.setUseStatus(DefaultFlag.NO);
                    queryRequest.setAlrExpire(Boolean.TRUE);
                    request.putSort("endTime", "DESC");
                    break;
                default:
                    break;
            }
        }
        request.putSort("createTime", "DESC");

        Page<CouponCode> couponCodePage = couponCodeRepository.findAll(this.getWhereCriteria(queryRequest), request.getPageRequest());
        if (CollectionUtils.isEmpty(couponInfoList)) {
            couponInfoIDList = couponCodePage.stream().map(CouponCode::getCouponId).toList();
            couponInfoList = couponInfoRepository.findAllById(couponInfoIDList);
        }

        Map<String, CouponInfo> couponInfoMap = couponInfoList.stream().collect(Collectors.toMap(CouponInfo::getCouponId, Function.identity()));
        if (CollectionUtils.isEmpty(couponActivityList)) {
            couponActivityIdList =  couponCodePage.stream().map(CouponCode::getActivityId).toList();
            couponActivityList =  couponActivityRepository.findAllById(couponActivityIdList);
        }
        Map<String, CouponActivity> couponActivityMap = couponActivityList.stream().collect(Collectors.toMap(CouponActivity::getActivityId, Function.identity()));
        List<CouponCodeVO> couponCodeVoList = couponCodePage.stream().map(couponCode -> {
            if (couponInfoMap.containsKey(couponCode.getCouponId())) {
                if (couponActivityMap.containsKey(couponCode.getActivityId())) {
                    return CouponCodePageRequest.converter(couponCode, couponInfoMap.get(couponCode.getCouponId()), couponActivityMap.get(couponCode.getActivityId()));
                } else {
                    return CouponCodePageRequest.converter(couponCode, couponInfoMap.get(couponCode.getCouponId()));
                }

            }
            return null;
        }).collect(Collectors.toList());

        couponCodeVoList = couponCodeVoList.stream().filter(Objects::nonNull).toList();
        // 查询店铺信息
        BaseResponse<ListStoreByIdsResponse> baseResponse =
                storeQueryProvider.listByIds(new ListStoreByIdsRequest(couponCodeVoList.stream().map(CouponCodeVO::getStoreId).collect(Collectors.toList())));
        List<StoreVO> storeVOList = baseResponse.getContext().getStoreVOList();
        for (CouponCodeVO couponCodeVO : couponCodeVoList) {
            for (StoreVO storeVO : storeVOList) {
                if (couponCodeVO.getStoreId().equals(storeVO.getStoreId())) {
                    couponCodeVO.setStoreName(storeVO.getStoreName());
                }
            }
        }

        couponCodeVoList.forEach(couponCodeVo -> {
            //判断优惠券是否即将过期 结束时间加上5天，大于现在时间，即将过期 true 是 false 否
            if (Objects.nonNull(couponCodeVo.getEndTime())) {
                if (LocalDateTime.now().plusDays(Constant.OUT_OF_DAYS).isAfter(couponCodeVo.getEndTime())) {
                    couponCodeVo.setNearOverdue(true);
                } else {
                    couponCodeVo.setNearOverdue(false);
                }
            }
            //是否可以立即使用 true 是(立即使用) false(查看可用商品)
            if (request.getUseStatus() == 0) {
                if (Objects.nonNull(couponCodeVo.getStartTime())) {
                    if (couponCodeVo.getStartTime().isBefore(LocalDateTime.now())) {
                        couponCodeVo.setCouponCanUse(true);
                    } else {
                        couponCodeVo.setCouponCanUse(false);
                    }
                }
            }

            //优惠券商品范围
            List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponId(couponCodeVo
                    .getCouponId());
            //适用品牌
            if (ScopeType.BRAND == couponCodeVo.getScopeType()) {
                couponBrandName(couponCodeVo, scopeList);
            }

            //适用平台分类
            if (ScopeType.BOSS_CATE == couponCodeVo.getScopeType()) {
                couponGoodsCateName(couponCodeVo, scopeList);
            }

            //适用店铺分类
            if (ScopeType.STORE_CATE == couponCodeVo.getScopeType()) {
                couponStoreCateName(couponCodeVo, scopeList);
            }
        });
        couponCodeVoList = couponCodeVoList.stream().sorted(Comparator.comparing(CouponCodeVO::getDenomination).reversed()).collect(Collectors.toList());
        return new PageImpl<>(couponCodeVoList, request.getPageable(), couponCodePage.getTotalElements());
    }

    public CouponCodePageRequest buildRequest(CouponCodePageRequest request) {
        return null;
    }

    public void populateO2oStoreCateName(CouponCodeVO couponCodeVo, List<CouponMarketingScope> scopeList) {

    }

    /**
     * APP | H5 我的优惠券 查询优惠券总数
     *
     * @param request
     * @return 优惠券总数
     */
    public CouponCodeCountResponse queryCouponCodeCount(CouponCodePageRequest request) {
        //如果storeId或者couponType不为空则查询couponInfo
        List<CouponInfo> couponInfoList = new ArrayList<>();
        List<String> couponInfoIDList = new ArrayList<>();
        if (request.getStoreId() != null
                || request.getCouponType() != null
                || request.getLikeCouponName() != null
                || request.getPlatformFlag() != null
                || request.getCouponMarketingType() != null
                || request.getScopeType() != null
                || CollectionUtils.isNotEmpty(request.getStoreIds())
        ) {
            couponInfoList = couponInfoService.queryCouponInfos(CouponInfoQueryRequest.builder().storeId(request.getStoreId()).couponType(request.getCouponType())
                    .likeCouponName(request.getLikeCouponName())
                    .platformFlag(request.getPlatformFlag())
                    .couponMarketingType(request.getCouponMarketingType())
                    .scopeType(request.getScopeType())
                    .storeIds(request.getStoreIds())
                    .build());
            if (CollectionUtils.isNotEmpty(couponInfoList)) {
                couponInfoIDList = couponInfoList.stream().map(CouponInfo::getCouponId).collect(Collectors.toList());
            }
        }
        //如果storeId不为空则查询优惠券活动
        List<CouponActivity> couponActivityList = new ArrayList<>();
        List<String> couponActivityIdList = new ArrayList<>();
        if (request.getStoreId() != null) {
            CouponActivityPageRequest request1= new CouponActivityPageRequest();
            request1.setStoreId(request.getStoreId());
            couponActivityList = couponActivityService.queryActivityInfoList(request1);
            if (CollectionUtils.isNotEmpty(couponActivityList)) {
                couponActivityIdList = couponActivityList.stream().map(CouponActivity::getActivityId).collect(Collectors.toList());
            }
        }

        CouponCodeQueryRequest queryRequest = new CouponCodeQueryRequest();
        queryRequest.setCouponIds(couponInfoIDList);
        queryRequest.setActivityIdList(couponActivityIdList);
        queryRequest.setCustomerId(request.getCustomerId());
        queryRequest.setDelFlag(DeleteFlag.NO);

        //查询未使用的优惠券总数
        queryRequest.setUseStatus(DefaultFlag.NO);
        queryRequest.setNotExpire(Boolean.TRUE);
        long unUsedCount = couponCodeRepository.count(this.getWhereCriteria(queryRequest));
        //查询已使用的优惠券总数
        queryRequest.setUseStatus(DefaultFlag.YES);
        queryRequest.setNotExpire(null);
        long usedCount = couponCodeRepository.count(this.getWhereCriteria(queryRequest));
        //查询已过期的优惠券总数
        queryRequest.setUseStatus(DefaultFlag.NO);
        queryRequest.setNotExpire(null);
        queryRequest.setAlrExpire(Boolean.TRUE);
        long overDueCount = couponCodeRepository.count(this.getWhereCriteria(queryRequest));

        return CouponCodeCountResponse.builder().unUseCount(unUsedCount).usedCount(usedCount).overDueCount(overDueCount).build();
    }

    /**
     * 用户领取优惠券
     *
     * @param couponFetchRequest
     */
    @Override
    public BaseResponse<CouponCodeVO> customerFetchCoupon(CouponFetchRequest couponFetchRequest) {
        //优惠券是否存在
        CouponInfo couponInfo = couponInfoService.getCouponInfoById(couponFetchRequest.getCouponInfoId());
        if (couponInfo == null) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
        }
        if (Objects.nonNull(couponFetchRequest.getStoreId())) {
            if (!Objects.equals(couponInfo.getStoreId(), couponFetchRequest.getStoreId())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
            }
        }
        //优惠券活动是否存在
        CouponActivity couponActivity = couponActivityService.getCouponActivityByPk(couponFetchRequest
                .getCouponActivityId());
        if (couponActivity == null) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080052);
        }
        if (Objects.nonNull(couponFetchRequest.getStoreId())) {
            if (!Objects.equals(couponActivity.getStoreId(), couponFetchRequest.getStoreId())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
            }
        }
        //优惠券活动还未开始
        if (couponActivity.getStartTime().isAfter(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080054);
        }
        //优惠券活动已经过期
        if (couponActivity.getEndTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080054);
        }
        // 暂停
        if (couponActivity.getPauseFlag() == DefaultFlag.YES) { // 暂停
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080054);
        }
        //优惠券规则查询
        long couponConfigCount = couponActivityConfigService
                .countByActivityIdAndCouponId(couponFetchRequest.getCouponActivityId(), couponFetchRequest
                        .getCouponInfoId());
        if (couponConfigCount == 0) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080053);
        }
        //用户是否存在 && 获取用户等级，判断用户等级是否满足要求
        customerLevelQueryProvider.validLevelForMarketing(CustomerLevelForMarketingRequest.builder()
                .customerId(couponFetchRequest.getCustomerId()).storeId(couponActivity.getStoreId())
                .joinLevel(couponActivity.getJoinLevel()).build());
        String couponCustomerKey = getCouponBankKeyForCustomer(couponFetchRequest.getCustomerId(),couponFetchRequest.getCouponInfoId());
        RLock rFairLock = redissonClient.getFairLock(couponCustomerKey.concat("_locker"));
        rFairLock.lock();
        CouponCodeVO couponCodeVO = new CouponCodeVO();
        try{
            //判断重复领取
            List<CouponCode> couponCodeList =
                    listCouponCodeByCondition(CouponCodeQueryRequest.builder().couponId(couponFetchRequest.getCouponInfoId())
                            .activityId(couponFetchRequest.getCouponActivityId()).customerId(couponFetchRequest.getCustomerId())
                            .delFlag(DeleteFlag.NO).build());

            if (CollectionUtils.isNotEmpty(couponCodeList.stream().filter(c -> Objects.equals(DefaultFlag.NO,
                    c.getUseStatus()) && c.getEndTime().isAfter(LocalDateTime.now())).collect(Collectors.toList()))
                    && !Objects.equals(CouponActivityType.DRAW_COUPON,couponActivity.getCouponActivityType())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080089);
            }
            //判断领取次数
            if (Objects.equals(couponActivity.getReceiveType(), DefaultFlag.YES)) {
                if (couponActivity.getReceiveCount() - couponCodeList.size() <= 0) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080091);
                }
            }
            String redisKey = getCouponBankKey(couponFetchRequest.getCouponActivityId(), couponFetchRequest.getCouponInfoId());
//            RLock rLock = redissonClient.getFairLock(redisKey.concat("_locker"));
            // 获取剩余优惠券
            String countCache = redisService.getString(redisKey);
            long leftCount = StringUtils.isNotEmpty(countCache)?Long.parseLong(countCache):0;
            if (leftCount <= 0 && !Objects.equals(CouponActivityType.DRAW_COUPON,couponActivity.getCouponActivityType())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080088);
            }

            try {
                //加锁，最多等待10秒
//                if (rLock.tryLock(10, 10, TimeUnit.SECONDS)) {
                //抽奖活动不需要校验活动库存
                if (!Objects.equals(CouponActivityType.DRAW_COUPON,couponActivity.getCouponActivityType())) {
                    Long left = redisService.decrKey(redisKey);
                    if (left < 0) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080088);
                    }
                    if (left == 0) {
                        couponNoLeft(couponFetchRequest.getCouponActivityId(), couponFetchRequest
                                .getCouponInfoId(), redisKey);
                    }
                }
                CouponCode couponCode = couponCodeService.sendCouponCode(couponInfo, couponFetchRequest);
                if (Objects.nonNull(couponCode)) {
                    KsBeanUtil.copyPropertiesThird(couponCode, couponCodeVO);
                }

            } catch (Exception e) {
                redisService.incrKey(redisKey);
                log.error(e.getMessage(), e);
                throw e;
            }
//            finally {
//                rLock.unlock();
//            }
        } catch (Exception e){
            throw e;
        } finally {
            rFairLock.unlock();
        }
        return BaseResponse.success(couponCodeVO);
    }

    /**
     * 自动领券
     * @param couponView
     * @param customerId
     */
    @Transactional(rollbackFor = Exception.class)
    public void autoFetchCoupon(CouponView couponView, String customerId) {
        // 获取会员优惠券key
        String couponCustomerKey = getCouponBankKeyForCustomer(customerId, couponView.getCouponId());
        RLock rFairLock = redissonClient.getFairLock(couponCustomerKey.concat("_locker"));
        rFairLock.lock();
        try {
            // 获取优惠券库存key
            String redisKey = getCouponBankKey(couponView.getActivityId(), couponView.getCouponId());
            // 获取剩余优惠券
            String countCache = redisService.getString(redisKey);
            long leftCount = StringUtils.isNotEmpty(countCache) ? Long.parseLong(countCache) : 0;
            if (leftCount <= 0) {
                // 优惠券数量为0，直接返回
                return;
            }
            boolean hasFetched = couponView.isHasFetched();
            // 获取剩余优惠券
            Long left = redisService.decrKey(redisKey);
            try {
                if (left < 0) {
                    // 标记优惠券已经被抢光
                    return;
                }
                if (left == 0) {
                    // 标记优惠券已经被抢光
                    couponNoLeft(couponView.getActivityId(), couponView.getCouponId(), redisKey);
                }
                // 构造券码实体
                CouponCode couponCode = new CouponCode();
                couponCode.setCouponCode(CodeGenUtil.toSerialCode(RandomUtils.nextInt(1, 10000)).toUpperCase());
                couponCode.setCouponId(couponView.getCouponId());
                couponCode.setActivityId(couponView.getActivityId());
                couponCode.setCustomerId(customerId);
                couponCode.setUseStatus(DefaultFlag.NO);
                couponCode.setAcquireTime(LocalDateTime.now());
                if (Objects.equals(RangeDayType.RANGE_DAY, couponView.getRangeDayType())) {
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    // 优惠券的起止时间
                    couponCode.setStartTime(LocalDateTime.parse(couponView.getCouponStartTime(),df));
                    couponCode.setEndTime(LocalDateTime.parse(couponView.getCouponEndTime(),df));
                } else {
                    // 领取生效
                    couponCode.setStartTime(LocalDateTime.now());
                    couponCode.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(couponView.getEffectiveDays()).minusSeconds(1));
                }
                couponCode.setDelFlag(DeleteFlag.NO);
                couponCode.setCreateTime(LocalDateTime.now());
                couponCode.setCreatePerson(customerId);
                couponCode.setCouponExpiredSendFlag(Boolean.FALSE);
                // 保存优惠券
                couponCodeRepository.save(couponCode);

            } catch (Exception e) {
                // 产生异常，则恢复优惠券数量
                redisService.incrKey(redisKey);
                throw e;
            }
        } catch (Exception e){
            throw e;
        } finally {
            rFairLock.unlock();
        }
    }

    private void sendCouponMiniProgramMsg(String customerId, CouponCode couponCode, ScopeType scopeType,
                                          String couponName, String denomination) {
        try {
            String scopeTypeDec = "";
            switch (scopeType) {
                case ALL:
                    scopeTypeDec = "全部商品";
                    break;
                case BRAND:
                    scopeTypeDec = "指定品牌";
                    break;
                case BOSS_CATE:
                    scopeTypeDec = "指定平台类目";
                    break;
                case STORE_CATE:
                    scopeTypeDec = "指定店铺分类";
                    break;
                case SKU:
                    scopeTypeDec = "指定商品";
                    break;
                case O2O_CATE:
                    scopeTypeDec = "指定门店分类";
                    break;
                case STORE:
                    scopeTypeDec = "指定店铺";
                    break;
                default:
                    break;
            }
            // 有效期
            String effectiveDate =
                    DateUtil.format(couponCode.getStartTime(), DateUtil.FMT_TIME_1) + "~" + DateUtil.format(couponCode.getEndTime(), DateUtil.FMT_TIME_1);

            producerService.sendMiniProgramSubscribeMessage(CouponSendMiniMsgRequest.builder()
                    .triggerNodeId(TriggerNodeType.COUPON_ISSUANCE).customerId(customerId).couponName(couponName)
                    .denomination(denomination).scopeTypeDec(scopeTypeDec)
                    .effectiveDate(effectiveDate).couponNum(String.valueOf(Constants.ONE)).build());
        }catch (Exception e){
            log.error("优惠券，订阅消息发送失败，couponCode={}", JSON.toJSONString(couponCode));
        }
    }


    /**
     * 标记优惠券已经被抢光
     *
     */
    private void couponNoLeft(String activityId, String couponId, String redisKey) {
        // 更新mysql
        couponActivityConfigService.updateHasLeft(activityId,couponId,DefaultFlag.NO);
        // 删除redis
        redisService.delete(redisKey);
        //更新es缓存
        couponScopeCacheService.update(EsActivityCouponModifyRequest.builder().hasLeft(DefaultFlag.NO)
                .activityId(activityId)
                .couponId(couponId).build());
//        // 更新缓存
//        couponCacheService.updateCouponLeftCache(couponActivityConfig.getActivityConfigId(), DefaultFlag.NO);
    }


    /**
     * 刷新优惠券库存
     *
     * @param couponActivityConfig
     * @return
     */
    private long refreshCouponLeftCount(String redisKey, CouponActivityConfig couponActivityConfig) {
        String couponCountCache = redisService.getString(redisKey);
        //优惠券状态 - 是否被抢完
        if (couponActivityConfig.getHasLeft() == DefaultFlag.NO) {
            return 0L;
        }
        //redis中不存在，需要重新加载
        if (couponCountCache == null) {
            int couponUsedCount = couponCodeRepository.countCouponUsed(couponActivityConfig.getCouponId(),
                    couponActivityConfig.getActivityId());
            if (couponUsedCount == couponActivityConfig.getTotalCount()) {
                this.couponNoLeft(couponActivityConfig.getActivityId(),couponActivityConfig.getCouponId(), redisKey);
                return 0L;
            } else {
                long leftCount = couponActivityConfig.getTotalCount() - couponUsedCount;
                redisService.setString(redisKey, leftCount + "");
                return leftCount;
            }
        } else if (Long.parseLong(couponCountCache) == 0L) {
            this.couponNoLeft(couponActivityConfig.getActivityId(),couponActivityConfig.getCouponId(), redisKey);
            return 0L;
        } else {
            return Long.parseLong(couponCountCache);
        }
    }


    /**
     * 初始化剩余库存
     * @param couponId
     * @param activityId
     * @param totalCount
     */
    public void initCouponLeftCount(String couponId,String activityId, long totalCount) {
        String redisKey = getCouponBankKey(activityId, couponId);
        String couponCountCache = redisService.getString(redisKey);
        //redis中不存在，需要初始化
        if (couponCountCache == null) {
            int couponUsedCount = couponCodeRepository.countCouponUsed(couponId,activityId);
            long leftCount = totalCount - couponUsedCount;
            redisService.setString(redisKey, leftCount + "");
        }
    }

    /**
     * 获取优惠券库存 -- 不精确
     *
     * @param activityId
     * @param couponId
     * @param totalCount
     * @param hasLeft
     * @return
     */
    @Override
    public long getCouponLeftCount(String activityId, String couponId, Long totalCount, DefaultFlag hasLeft) {
        String redisKey = getCouponBankKey(activityId, couponId);
        String couponCountCache = redisService.getString(redisKey);
        //优惠券状态 - 是否被抢完
        if (hasLeft == DefaultFlag.NO) {
            return 0L;
        }
        //redis中不存在，需要重新加载
        if (couponCountCache == null) {
            //因为优惠券的数量不再缓存里面，要么是缓存丢失，要么是优惠券未被领取过，数量并不要求那么精确，返回totalCount
            return 0L;
//            return totalCount;
        } else if (Long.parseLong(couponCountCache) == 0L) {
            return 0L;
        } else {
            return Long.parseLong(couponCountCache);
        }
    }

    /**
     * 批量获取优惠券的领取状态
     *
     * @param couponCacheList
     * @return <优惠券活动Id，<优惠券Id, 领取状态>>
     */
    @Override
    public Map<String, Map<String, CouponLeftResponse>> mapLeftCount(List<CouponCache> couponCacheList) {
        if (CollectionUtils.isEmpty(couponCacheList)) {
            return null;
        }
        Map<String, Map<String, CouponLeftResponse>> result = new HashMap<>();
        couponCacheList.forEach(item -> {
            Map<String, CouponLeftResponse> itemMap = result.computeIfAbsent(item.getCouponActivityId(),
                    k -> new HashMap<>());
            itemMap.put(item.getCouponInfoId(),
                    CouponLeftResponse.builder()
                            .leftCount(getCouponLeftCount(item.getCouponActivityId(), item.getCouponInfoId(),
                                    item.getTotalCount(), item.getHasLeft()))
                            .totalCount(item.getTotalCount()).build()
            );
        });
        return result;
    }


    /**
     * 批量获取优惠券领用状态
     *
     * @param couponCacheList
     * @return <优惠券活动Id，<优惠券Id, 领取状态>>
     */
    @Override
    public Map<String, Map<String, String>> mapFetchStatus(List<CouponCache> couponCacheList, String customerId) {
        if (CollectionUtils.isEmpty(couponCacheList)) {
            return null;
        }
        Map<String, Map<String, String>> result = new HashMap<>();
        List<CouponCode> couponCodeList = new ArrayList<>();
        if (StringUtils.isNotBlank(customerId)) {
            couponCodeList = listCouponCodeByCondition(CouponCodeQueryRequest.builder()
                    .customerId(customerId).delFlag(DeleteFlag.NO).notExpire(Boolean.TRUE)
                    .build());
        }
        Map<String, List<CouponCode>> couponCodeMap = couponCodeList.stream().collect(Collectors.groupingBy(
                item -> item.getActivityId().concat("_").concat(item.getCouponId())));
        couponCacheList.forEach(
                item -> {
                    Map<String, String> itemMap =
                            result.computeIfAbsent(
                                    item.getCouponActivityId(), k -> new HashMap<>());
                    CouponActivityCache couponActivity = item.getCouponActivity();
                    DefaultFlag receiveType = couponActivity.getReceiveType();
                    Integer receiveCount = couponActivity.getReceiveCount();
                    List<CouponCode> couponCodes =
                            couponCodeMap.getOrDefault(
                                    item.getCouponActivityId()
                                            .concat("_")
                                            .concat(item.getCouponInfoId()),
                                    new ArrayList<>());
                    if (DefaultFlag.YES == receiveType) {
                        // 每人限领场景
                        if (CollectionUtils.isNotEmpty(couponCodes)
                                && couponCodes.size() >= receiveCount) {
                            itemMap.put(item.getCouponInfoId(), couponCodes.get(0).getCouponCodeId());
                        }
                    } else {
                        couponCodes =
                                couponCodes.stream()
                                        .filter(
                                                c ->
                                                        Objects.equals(
                                                                DefaultFlag.NO, c.getUseStatus()))
                                        .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(couponCodes)) {
                            itemMap.put(item.getCouponInfoId(), couponCodes.get(0).getCouponCodeId());
                        }
                    }
                });
        return result;
    }

    /**
     * 批量修改优惠券
     */
    @Override
    @Transactional
    public void batchModify(List<CouponCodeBatchModifyDTO> batchModifyDTOList) {
        batchModifyDTOList.stream().forEach(couponCode ->
                couponCodeRepository.updateCouponCode(couponCode.getCouponCodeId(), couponCode.getCustomerId(), couponCode.getUseStatus(), couponCode.getOrderCode())
        );
    }

    /**
     * 批量修改优惠券
     */
    @Override
    @Transactional
    public void modifyByCustomerId(String customerId) {
        couponCodeRepository.updateCouponCodeByCustomerId(customerId);
    }

    /**
     * 修改优惠券发送状态
     */
    @Override
    @Transactional
    public void updateCouponExpiredSendFlagById(String id) {
        couponCodeRepository.updateCouponExpiredSendFlagById(id);
    }

    @Override
    @Transactional
    public void recycleCoupon(String couponCodeId, String customerId) {
        CouponCode couponCode = couponCodeRepository.findByCouponCodeId(couponCodeId, customerId);
        if (couponCode != null) {
            couponCodeRepository.recycleCoupon(couponCodeId, customerId);
        }
    }


    /**
     * 优惠券商品品牌信息
     *
     * @param couponCodeVo
     * @param scopeList
     */
    private void couponBrandName(CouponCodeVO couponCodeVo, List<CouponMarketingScope> scopeList) {
        //优惠券品牌信息
        if (CollectionUtils.isNotEmpty(scopeList) && Objects.equals(couponCodeVo.getScopeType(), ScopeType.BRAND)) {
            //优惠券包含的所有品牌Id
            List<Long> brandsIds = scopeList.stream().map(scope -> Long.valueOf(scope.getScopeId())).collect
                    (Collectors.toList());
            GoodsBrandListRequest brandRequest = new GoodsBrandListRequest();
            brandRequest.setBrandIds(brandsIds);
            List<GoodsBrandVO> brandList =
                    goodsBrandQueryProvider.list(brandRequest).getContext().getGoodsBrandVOList();
            couponCodeVo.setBrandNames(brandList.stream().map(GoodsBrandVO::getBrandName).collect(Collectors.toList()));
        }
    }

    /**
     * 优惠券适用平台分类
     *
     * @param couponCodeVo
     * @param scopeList
     */
    private void couponGoodsCateName(CouponCodeVO couponCodeVo, List<CouponMarketingScope> scopeList) {
        if (CollectionUtils.isNotEmpty(scopeList) && Objects.equals(couponCodeVo.getScopeType(), ScopeType.BOSS_CATE)) {
            //优惠券包含的所有品牌Id
            List<Long> cateIds = scopeList.stream().map(scope -> Long.valueOf(scope.getScopeId())).collect(Collectors
                    .toList());
            //平台分类
            if (Objects.equals(couponCodeVo.getPlatformFlag(), DefaultFlag.YES)) {
                List<GoodsCateVO> cateList =
                        goodsCateQueryProvider.getByIds(new GoodsCateByIdsRequest(cateIds)).getContext().getGoodsCateVOList();
                couponCodeVo.setGoodsCateNames(cateList.stream().map(GoodsCateVO::getCateName).collect(Collectors.toList()));
            }
        }
    }

    /**
     * 优惠券适用店铺分类
     *
     * @param couponCodeVo
     * @param scopeList
     */
    private void couponStoreCateName(CouponCodeVO couponCodeVo, List<CouponMarketingScope> scopeList) {
        if (CollectionUtils.isNotEmpty(scopeList) && Objects.equals(couponCodeVo.getScopeType(), ScopeType
                .STORE_CATE)) {
            //优惠券包含的所有品牌Id
            List<Long> cateIds = scopeList.stream().map(scope -> Long.valueOf(scope.getScopeId())).collect(Collectors
                    .toList());
            //店铺分类
            if (Objects.equals(couponCodeVo.getPlatformFlag(), DefaultFlag.NO)) {
                List<StoreCateVO> storeCateList =
                        storeCateQueryProvider.listByIds(new StoreCateListByIdsRequest(cateIds)).getContext().getStoreCateVOList();
                couponCodeVo.setStoreCateNames(storeCateList.stream().map(StoreCateVO::getCateName).collect(Collectors
                        .toList()));
            }
        }
    }

    /**
     * 发放优惠券码
     *
     * @return
     */
    @Override
    @Transactional
    public CouponCode sendCouponCode(CouponInfo couponInfo, CouponFetchRequest couponFetchRequest) {
        CouponCode couponCode = new CouponCode();
        couponCode.setCouponCode(CodeGenUtil.toSerialCode(RandomUtils.nextInt(1, 10000)).toUpperCase());
        couponCode.setCouponId(couponFetchRequest.getCouponInfoId());
        couponCode.setActivityId(couponFetchRequest.getCouponActivityId());
        couponCode.setCustomerId(couponFetchRequest.getCustomerId());
        couponCode.setUseStatus(DefaultFlag.NO);
        couponCode.setAcquireTime(LocalDateTime.now());
        if (Objects.equals(RangeDayType.RANGE_DAY, couponInfo.getRangeDayType())) {//优惠券的起止时间
            couponCode.setStartTime(couponInfo.getStartTime());
            couponCode.setEndTime(couponInfo.getEndTime());
        } else {//领取生效
            couponCode.setStartTime(LocalDateTime.now());
            couponCode.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(couponInfo.getEffectiveDays()).minusSeconds(1));
        }
        couponCode.setDelFlag(DeleteFlag.NO);
        couponCode.setCreateTime(LocalDateTime.now());
        couponCode.setCreatePerson(couponFetchRequest.getCustomerId());
        couponCode.setCouponExpiredSendFlag(Boolean.FALSE);
        CouponCode sendCouponCode = couponCodeRepository.save(couponCode);
        // 发送订阅消息
        ScopeType scopeType = couponInfo.getScopeType();
        String couponName = couponInfo.getCouponName();
        String denomination = String.valueOf(couponInfo.getDenomination());
        if (Objects.isNull(couponFetchRequest.getFetchCouponFlag())||!couponFetchRequest.getFetchCouponFlag()){
            sendCouponMiniProgramMsg(couponFetchRequest.getCustomerId(), couponCode, scopeType, couponName, denomination);
        }
        return sendCouponCode;
    }

    /**
     * 获取优惠券库存key
     *
     * @param activityId 活动
     * @param couponId   优惠券
     * @return
     */
    public String getCouponBankKey(String activityId, String couponId) {
        return CouponCodeService.COUPON_BANK.concat(activityId).concat("_").concat(couponId);
    }

    /**
     * 获取会员优惠券key
     *
     * @param customerId 会员id
     * @param couponId   优惠券
     * @return
     */
    public String getCouponBankKeyForCustomer(String customerId, String couponId) {
        return CouponCodeService.COUPON_BANK.concat(customerId).concat("_").concat(couponId);
    }

    /**
     * 退优惠券
     *
     * @param couponCodeId
     */
    @Override
    @Transactional
    public void returnCoupon(String couponCodeId, String customerId) {
        CouponCode couponCode = couponCodeRepository.findByCouponCodeId(couponCodeId, customerId);
        if (Objects.nonNull(couponCode) && couponCode.getUseStatus() == DefaultFlag.YES) {
            couponCodeRepository.returnCoupon(couponCodeId, couponCode.getCustomerId());
        }
    }

    /**
     * 对同一个用户，批量发放优惠券
     *
     * @param couponInfoList
     * @param customerId
     * @param couponActivityId
     * @return
     */
    @Override
    @Transactional
    public List<CouponCode> sendBatchCouponCodeByCustomer(List<GetCouponGroupResponse> couponInfoList,
                                                          String customerId, String couponActivityId,String recordId) {
        List<CouponCode> couponCodeList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        couponInfoList.forEach(item -> {
            for (int i = 0; i < item.getTotalCount(); i++) {
                CouponCode couponCode = new CouponCode();
                couponCode.setCouponCode(CodeGenUtil.toSerialCode(RandomUtils.nextInt(1, 10000)).toUpperCase());
                couponCode.setCouponId(item.getCouponId());
                couponCode.setActivityId(couponActivityId);
                couponCode.setCustomerId(customerId);
                couponCode.setUseStatus(DefaultFlag.NO);
                couponCode.setAcquireTime(LocalDateTime.now());
                if (Objects.equals(RangeDayType.RANGE_DAY, item.getRangeDayType())) {//优惠券的起止时间
                    couponCode.setStartTime(item.getStartTime());
                    couponCode.setEndTime(item.getEndTime());
                } else {//领取生效
                    couponCode.setStartTime(now);
                    couponCode.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(item.getEffectiveDays()).minusSeconds(1));
                }
                couponCode.setDelFlag(DeleteFlag.NO);
                couponCode.setCreateTime(LocalDateTime.now());
                couponCode.setCreatePerson(customerId);
                if (StringUtils.isEmpty(recordId)) {
                    couponCode.setMarketingCustomerType(MarketingCustomerType.ORDINARY);
                } else {
                    couponCode.setMarketingCustomerType(MarketingCustomerType.PAYING_MEMBER);
                    couponCode.setPayingMemberRecordId(recordId);
                }
                couponCode.setCouponExpiredSendFlag(Boolean.FALSE);
                couponCodeList.add(couponCode);
            }
        });
        List<CouponCode> responseList =  couponCodeRepository.saveAll(couponCodeList);
        try {
            for (GetCouponGroupResponse couponInfoDTO : couponInfoList) {
                // 过滤过期优惠券
                if (RangeDayType.RANGE_DAY == couponInfoDTO.getRangeDayType() && couponInfoDTO.getEndTime().isBefore(LocalDateTime.now())) {
                    continue;
                }
                // 发送订阅消息
                ScopeType scopeType = couponInfoDTO.getScopeType();
                String couponName = couponInfoDTO.getCouponName();
                String denomination = String.valueOf(couponInfoDTO.getDenomination());
                RangeDayType rangeDayType = couponInfoDTO.getRangeDayType();
                LocalDateTime startTime = couponInfoDTO.getStartTime();
                LocalDateTime endTime = couponInfoDTO.getEndTime();
                Integer effectiveDays = couponInfoDTO.getEffectiveDays();

                sendMiniProgramMsg(customerId, scopeType, couponName, rangeDayType, startTime, endTime, effectiveDays,
                                 denomination, String.valueOf(Constants.ONE));
            }
        }catch (Exception e){
            log.error("优惠券，订阅消息发送失败，method=sendBatchCouponCodeByCustomer()");
        }
        return responseList;
    }

    private void sendMiniProgramMsg(String customerId, ScopeType scopeType, String couponName,
     RangeDayType rangeDayType, LocalDateTime startTime, LocalDateTime endTime, Integer effectiveDays, String denomination,
      String couponNum) {
        String scopeTypeDec = "";
        switch (scopeType) {
            case ALL:
                scopeTypeDec = "全部商品";
                break;
            case BRAND:
                scopeTypeDec = "指定品牌";
                break;
            case BOSS_CATE:
                scopeTypeDec = "指定平台类目";
                break;
            case STORE_CATE:
                scopeTypeDec = "指定店铺分类";
                break;
            case SKU:
                scopeTypeDec = "指定商品";
                break;
            case O2O_CATE:
                scopeTypeDec = "指定门店分类";
                break;
            case STORE:
                scopeTypeDec = "指定店铺";
                break;
            default:
                break;
        }
        // 有效期
        String effectiveDate;
        if (rangeDayType == RangeDayType.RANGE_DAY) {
            effectiveDate =
                    DateUtil.format(startTime, DateUtil.FMT_TIME_1) + "~" + DateUtil.format(endTime,
                     DateUtil.FMT_TIME_1);
        } else {
            effectiveDate =
                    LocalDate.now().toString() + "~" + LocalDate.now().plusDays(effectiveDays - Constants.ONE).toString();
        }

        producerService.sendMiniProgramSubscribeMessage(CouponSendMiniMsgRequest.builder()
                .triggerNodeId(TriggerNodeType.COUPON_ISSUANCE).customerId(customerId).couponName(couponName)
                .denomination(denomination).scopeTypeDec(scopeTypeDec)
                .effectiveDate(effectiveDate).couponNum(couponNum).build());
    }

    /**
     * 权益券礼包， 周期性对一批用户发券
     * @param request
     * @return
     */
    @Transactional
    public List<CouponCode> sendBatchCouponCodeByCustomer(RightsCouponRequest request) {
        // 查询券礼包权益关联的优惠券活动配置列表
        List<CouponActivityConfig> couponActivityConfigList = couponActivityConfigService.queryByActivityId(request.getActivityId());
        // 根据配置查询需要发放的优惠券列表
        List<CouponInfo> couponInfoList = couponInfoRepository.queryByIds(couponActivityConfigList.stream().map(
                CouponActivityConfig::getCouponId).collect(Collectors.toList()));
        // 组装优惠券发放数据
        List<GetCouponGroupResponse> getCouponGroupResponse = KsBeanUtil.copyListProperties(couponInfoList, GetCouponGroupResponse.class);
        getCouponGroupResponse = getCouponGroupResponse.stream().peek(item -> couponActivityConfigList.forEach(config -> {
            if (item.getCouponId().equals(config.getCouponId())) {
                item.setTotalCount(config.getTotalCount());
            }
        })).collect(Collectors.toList());

        List<CouponCode> couponCodeList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        List<String> customerIds = MarketingCustomerType.ORDINARY == request.getCustomerType()
                ? request.getCustomerIds() : Lists.newArrayList(request.getPayingMemberInfo().keySet());
        if (CollectionUtils.isNotEmpty(customerIds)) {
            for (String customerId : customerIds) {
                //避免重复发券
                Integer count = couponCodeRepository.countByCouponHasToday(customerId, request.getActivityId(), request.getCheckTime(), request.getCustomerType());
                if (count > NumberUtils.INTEGER_ZERO) {
                    continue;
                }

                String recordId = MarketingCustomerType.PAYING_MEMBER == request.getCustomerType()
                        ? request.getPayingMemberInfo().get(customerId) : null;
                getCouponGroupResponse.forEach(item -> {
                    for (int i = 0; i < item.getTotalCount(); i++) {
                        CouponCode couponCode = new CouponCode();
                        couponCode.setCouponCode(CodeGenUtil.toSerialCode(RandomUtils.nextInt(1, 10000)).toUpperCase());
                        couponCode.setCouponId(item.getCouponId());
                        couponCode.setActivityId(request.getActivityId());
                        couponCode.setCustomerId(customerId);
                        couponCode.setUseStatus(DefaultFlag.NO);
                        couponCode.setAcquireTime(LocalDateTime.now());
                        if (Objects.equals(RangeDayType.RANGE_DAY, item.getRangeDayType())) {//优惠券的起止时间
                            couponCode.setStartTime(item.getStartTime());
                            couponCode.setEndTime(item.getEndTime());
                        } else {//领取生效
                            couponCode.setStartTime(now);
                            couponCode.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(item.getEffectiveDays()).minusSeconds(1));
                        }
                        couponCode.setDelFlag(DeleteFlag.NO);
                        couponCode.setCreateTime(LocalDateTime.now());
                        couponCode.setCreatePerson(customerId);
                        couponCode.setMarketingCustomerType(request.getCustomerType());
                        couponCode.setPayingMemberRecordId(recordId);
                        couponCode.setCouponExpiredSendFlag(Boolean.FALSE);
                        couponCodeList.add(couponCode);
                    }
                });
            }
        }

        List<CouponCode> couponCodes = couponCodeRepository.saveAll(couponCodeList);
        try {
            if (CollectionUtils.isNotEmpty(customerIds)) {
                for (String customerId : customerIds) {
                    for (GetCouponGroupResponse couponInfoDTO : getCouponGroupResponse) {
                        // 过滤过期优惠券
                        if (RangeDayType.RANGE_DAY == couponInfoDTO.getRangeDayType() && couponInfoDTO.getEndTime().isBefore(LocalDateTime.now())) {
                            continue;
                        }

                        sendMiniProgramMsg(customerId, couponInfoDTO.getScopeType(), couponInfoDTO.getCouponName(), couponInfoDTO.getRangeDayType(), couponInfoDTO.getStartTime(), couponInfoDTO.getEndTime(), couponInfoDTO.getEffectiveDays(), String.valueOf(couponInfoDTO.getDenomination()), String.valueOf(Constants.ONE));
                    }
                }
            }
        }catch (Exception e){
            log.error("优惠券，订阅消息发送失败，method=sendBatchCouponCodeByCustomer");
        }
        return couponCodes;
    }

    /**
     * 批量发券，根据会员id和活动id
     */
    @Override
    public List<String> sendBatchCouponCodeByCustomerList(CouponCodeBatchSendCouponRequest request) {
        List<String> customerIdList = request.getCustomerIds();

        if (CollectionUtils.isNotEmpty(request.getList()) && CollectionUtils.isNotEmpty(customerIdList)) {

            for (CouponActivityConfigAndCouponInfoDTO activity : request.getList()) {
                // 查询券礼包权益关联的优惠券活动配置列表
                List<CouponActivityConfig> couponActivityConfigList = couponActivityConfigService.queryByActivityId(activity.getActivityId());
                // 根据配置查询需要发放的优惠券列表
                List<CouponInfo> couponInfoList = couponInfoRepository.queryByIds(couponActivityConfigList.stream().map(
                        CouponActivityConfig::getCouponId).collect(Collectors.toList()));
                // 组装优惠券发放数据
                List<GetCouponGroupResponse> getCouponGroupResponse = KsBeanUtil.copyListProperties(couponInfoList, GetCouponGroupResponse.class);
                getCouponGroupResponse = getCouponGroupResponse.stream().peek(item -> couponActivityConfigList.forEach(config -> {
                    if (item.getCouponId().equals(config.getCouponId())) {
                        item.setTotalCount(config.getTotalCount());
                    }
                })).collect(Collectors.toList());
                for (String customerId : customerIdList) {

                    // 批量发放优惠券
                    couponCodeService.sendBatchCouponCodeByCustomer(getCouponGroupResponse, customerId, activity.getActivityId(),null);
                }
            }
        }
        return customerIdList;

    }


    public void couponSendBatchAsync(CouponCodeBatchSendRequest sendCodeInfo) {
        try {
                List<String> customerIds = sendCodeInfo.getCustomerIds();
                OperatorInteger operatorInteger = OperatorInteger.valueOf(OperatorInteger.SPLIT.name());
                int maxSize = operatorInteger.apply(customerIds.size());
                List<List<String>> splitList = IterableUtils.splitList(customerIds, maxSize);
                sendCouponAsync(splitList, sendCodeInfo.getList());
        } catch (Exception e) {
            log.error("精准发券消息处理失败,json==========>" + JSONObject.toJSONString(sendCodeInfo), e);
        }
    }

    public void sendCouponAsync(List<List<String>> splitList,
                                List<CouponActivityConfigAndCouponInfoDTO> configAndCouponInfoS) {
        try {
            final CountDownLatch count = new CountDownLatch(splitList.size());
            ExecutorService executor = Executors.newFixedThreadPool(splitList.size());
            for (List<String> customerIds : splitList) {
                executor.execute(() -> {
                    try {
                        couponCodeService.sendCouponToCustomer(customerIds, configAndCouponInfoS);
                        log.info("发券子批次处理成功,活动编号:{},子批次处理总数量{}==========>",
                                configAndCouponInfoS.get(0).getActivityId(),
                                 customerIds.size());
                    } catch (Exception e) {
                        log.error("发券子批次处理失败,活动编号:{},子批次处理总数量{}==========>",
                                configAndCouponInfoS.get(0).getActivityId(),
                                 customerIds.size());
                    } finally {
                        // 无论是否报错始终执行countDown()，否则报错时主进程一直会等待线程结束
                        count.countDown();
                    }
                });
            }
            count.await();
            executor.shutdown();
        } catch (Exception e) {
            log.error("异步编排导入异常：{}", e.getMessage());
        }
    }

    /**
     * @param customerId
     * @return
     */
    @Override
    public Integer countByCustomerId(String customerId) {
        return couponCodeRepository.countByCustomerId(customerId);
    }

    /**
     * 根据customerId和activityId查询优惠券数量
     *
     * @param customerId
     * @param activieyId
     * @return
     */
    @Override
    public Integer countByCustomerIdAndActivityId(String customerId, String activieyId) {
        return couponCodeRepository.countByCustomerIdAndActivityId(customerId, activieyId);
    }

    /**
     * 查询用户领取某个活动券的信息
     *
     * @param customerId
     * @param activieyId
     * @return
     */
    @MasterRouteOnly
    public List<CouponCode> findByCustomerIdAndActivityId(String customerId, String activieyId) {
        return couponCodeRepository.findByCustomerIdAndActivityId(customerId, activieyId);
    }
    @Autowired
    private MqSendProvider mqSendProvider;

    @Override
    public void precisionVouchers(List<String> customerIds, List<CouponActivityConfigAndCouponInfoDTO> list){
        //用户发券批量发送mq处理
        if (CollectionUtils.isNotEmpty(customerIds)) {
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.COUPON_SEND_CUSTOMER);
            CouponCodeBatchSendRequest sendRequest = new CouponCodeBatchSendRequest();
            sendRequest.setCustomerIds(customerIds);
            sendRequest.setList(list);
            mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
            mqSendProvider.send(mqSendDTO);
        } else {
            log.warn("====精准发券消息发送，暂无用户发送===");
        }
    }

    /**
     * 精准发券
     *
     * @param customerIds
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void sendCouponToCustomer(List<String> customerIds, List<CouponActivityConfigAndCouponInfoDTO> list) {
        if (CollectionUtils.isEmpty(customerIds)) {
            log.info("=============精准发券用户ID集合不存在===========================");
            return;
        }
        CustomerDetailListByConditionResponse context = customerQueryProvider.listCustomerDetailByCondition(CustomerDetailListByConditionRequest.builder().customerIds(customerIds).logOutStatus(LogOutStatus.LOGGED_OUT).build()).getContext();
        if (Objects.nonNull(context)) {
            List<CustomerDetailVO> detailResponseList = context.getDetailResponseList();
            List<String> logOutCustomerIds = new ArrayList<>();
            detailResponseList.forEach(customerDetailVO -> {
                logOutCustomerIds.add(customerDetailVO.getCustomerId());
            });
            customerIds.removeAll(logOutCustomerIds);
        }
        if (CollectionUtils.isEmpty(customerIds)) {
            log.info("=============过滤已注销客户，精准发券用户ID集合不存在===========================");
            return;
        }
        CouponInfoDTO couponInfoDTO = null;
        CouponCode couponCode;
        int resultSize = customerIds.size() * list.size();
        List<CouponCode> result = new ArrayList<>(resultSize);
        for (String customerId : customerIds) {
            for (CouponActivityConfigAndCouponInfoDTO dto : list) {
//                 过滤过期优惠券
                if (RangeDayType.RANGE_DAY == dto.getCouponInfoDTO().getRangeDayType() && dto.getCouponInfoDTO().getEndTime().isBefore(LocalDateTime.now())) {
                    continue;
                }
                Long totalCount = dto.getTotalCount();
                String couponId = dto.getCouponId();
                String activityId = dto.getActivityId();
                couponInfoDTO = dto.getCouponInfoDTO();
                for (long i = NumberUtils.LONG_ZERO; i < totalCount; i++) {
                    couponCode = new CouponCode();
                    couponCode.setCouponCode(CodeGenUtil.toSerialCode(RandomUtils.nextInt(1, 10000)).toUpperCase());
                    couponCode.setCouponId(couponId);
                    couponCode.setActivityId(activityId);
                    couponCode.setCustomerId(customerId);
                    couponCode.setUseStatus(DefaultFlag.NO);
                    couponCode.setAcquireTime(LocalDateTime.now());
                    //优惠券的起止时间
                    if (RangeDayType.RANGE_DAY == couponInfoDTO.getRangeDayType()) {
                        couponCode.setStartTime(couponInfoDTO.getStartTime());
                        couponCode.setEndTime(couponInfoDTO.getEndTime());
                    } else {//领取生效
                        couponCode.setStartTime(LocalDateTime.now());
                        couponCode.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
                                .plusDays(couponInfoDTO.getEffectiveDays()).minusSeconds(NumberUtils.INTEGER_ONE));
                    }
                    couponCode.setDelFlag(DeleteFlag.NO);
                    couponCode.setCreateTime(LocalDateTime.now());
                    couponCode.setCreatePerson(customerId);
                    couponCode.setCouponExpiredSendFlag(Boolean.FALSE);
                    result.add(couponCode);
                }
            }
        }
        couponCodeRepository.saveAll(result);
        try {
            for (String customerId : customerIds) {
                for (CouponActivityConfigAndCouponInfoDTO dto : list) {
                    // 过滤过期优惠券
                    if (RangeDayType.RANGE_DAY == dto.getCouponInfoDTO().getRangeDayType() && dto.getCouponInfoDTO().getEndTime().isBefore(LocalDateTime.now())) {
                        continue;
                    }
                    couponInfoDTO = dto.getCouponInfoDTO();
                    sendMiniProgramMsg(customerId, couponInfoDTO.getScopeType(), couponInfoDTO.getCouponName(), couponInfoDTO.getRangeDayType(), couponInfoDTO.getStartTime(), couponInfoDTO.getEndTime(), couponInfoDTO.getEffectiveDays(), String.valueOf(couponInfoDTO.getDenomination()), String.valueOf(dto.getTotalCount()));
                }
            }
        }catch (Exception e){
            log.error("优惠券，订阅消息发送失败，method=sendCouponToCustomer()");
        }

    }

//    /**
//     * 精准发券
//     *
//     * @param customerIds
//     * @param list
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void precisionVouchers(List<String> customerIds, List<CouponActivityConfigAndCouponInfoDTO> list) {
//        if (CollectionUtils.isEmpty(customerIds)) {
//            log.info("=============精准发券用户ID集合不存在===========================");
//            return;
//        }
//        CouponInfoDTO couponInfoDTO;
//        CouponCode couponCode;
//        int resultSize = customerIds.size() * list.size();
//        List<CouponCode> result = new ArrayList<>(resultSize);
//        for (String customerId : customerIds) {
//            for (CouponActivityConfigAndCouponInfoDTO dto : list) {
//                String couponId = dto.getCouponId();
//                String activityId = dto.getActivityId();
//                couponInfoDTO = dto.getCouponInfoDTO();
//                for (long i = 0; i < totalCount; i++) {
//                    // 过滤过期优惠券
//                    if (RangeDayType.RANGE_DAY == couponInfoDTO.getRangeDayType() && couponInfoDTO.getEndTime().isBefore(LocalDateTime.now())) {
//                        continue;
//                    }
//                    couponCode = getCouponCode(couponInfoDTO, customerId, couponId, activityId);
//                    result.add(couponCode);
//                    couponCodeRepository.saveAndFlush(couponCode);
//                }
//            }
//        }
//
//        if(log.isInfoEnabled()){
//            log.info("根据优惠券活动ID:{},发放{} 张优惠券，券码：{}", list.get(0).getActivityId(), result.size(),
//                    result.stream().map(CouponCode::getCouponCode).collect(Collectors.toList()));
//        }
//    }

    private CouponCode getCouponCode(CouponInfoDTO couponInfoDTO, String customerId, String couponId,
                                     String activityId) {
        CouponCode couponCode;
        couponCode = new CouponCode();
        couponCode.setCouponCode(CodeGenUtil.toSerialCode(RandomUtils.nextInt(1, 10000)).toUpperCase());
        couponCode.setCouponId(couponId);
        couponCode.setActivityId(activityId);
        couponCode.setCustomerId(customerId);
        couponCode.setUseStatus(DefaultFlag.NO);
        couponCode.setAcquireTime(LocalDateTime.now());
        //优惠券的起止时间
        if (RangeDayType.RANGE_DAY == couponInfoDTO.getRangeDayType()) {
            couponCode.setStartTime(couponInfoDTO.getStartTime());
            couponCode.setEndTime(couponInfoDTO.getEndTime());
        } else {//领取生效
            couponCode.setStartTime(LocalDateTime.now());
            couponCode.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(couponInfoDTO.getEffectiveDays()).minusSeconds(1));
        }
        couponCode.setDelFlag(DeleteFlag.NO);
        couponCode.setCreateTime(LocalDateTime.now());
        couponCode.setCreatePerson(customerId);
        return couponCode;
    }

    /**
     * 满返支付精准发券
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean precisionCoupon(CouponCodeBatchSendFullReturnRequest request) {
        String customerId = request.getCustomerId();
        boolean sendCouponFlag = false;
        // 获取优惠券信息
        List<CouponInfo> couponInfoList = couponInfoService.queryByIds(request.getCouponIds());
        // 组装发券信息
        List<CouponInfoDTO> couponInfoDTOList =
                KsBeanUtil.convert(couponInfoList, CouponInfoDTO.class);
        CouponCode couponCode;
        List<CouponCode> result = Lists.newArrayList();
        for (CouponInfoDTO couponInfoDTO : couponInfoDTOList) {
            // 过滤过期优惠券
            if (RangeDayType.RANGE_DAY == couponInfoDTO.getRangeDayType() && couponInfoDTO.getEndTime().isBefore(LocalDateTime.now())) {
                continue;
            }
            String couponId = couponInfoDTO.getCouponId();
            couponCode = getCouponCode(couponInfoDTO, customerId, couponId, MarketingType.RETURN.toString());
            couponCode.setCouponExpiredSendFlag(Boolean.FALSE);
            result.add(couponCode);
            couponCodeRepository.saveAndFlush(couponCode);
            sendCouponFlag = true;
            // 发送订阅消息
            ScopeType scopeType = couponInfoDTO.getScopeType();
            String couponName = couponInfoDTO.getCouponName();
            String denomination = String.valueOf(couponInfoDTO.getDenomination());
            sendCouponMiniProgramMsg(customerId, couponCode, scopeType, couponName, denomination);
        }

        if(log.isInfoEnabled()){
            log.info("满返优惠券发放{} 张优惠券，券码：{}", result.size(),
                    result.stream().map(CouponCode::getCouponCode).collect(Collectors.toList()));
        }
        return sendCouponFlag;
    }

    /**
     * 验证优惠券
     *
     * @param request
     * @return
     */
    @Override
    public CouponCodeValidOrderCommitResponse validOrderCommit(CouponCodeValidOrderCommitRequest request) {
        String customerId = request.getCustomerId();
        List<String> couponCodeIds = request.getCouponCodeIds();
        // 2.1.查询我的未使用优惠券
        CouponCodeQueryRequest codeQueryRequest = CouponCodeQueryRequest.builder().customerId(customerId)
                .build();
        List<CouponCode> couponCodes = this.listCouponCodeByCondition(codeQueryRequest);

        couponCodes = couponCodes.stream()
                .filter(item -> couponCodeIds.contains(item.getCouponCodeId())).collect(Collectors.toList());

        // 2.2.判断所传优惠券是否为我的未使用优惠券
        if (couponCodeIds.size() != couponCodes.size()) {
              throw new SbcRuntimeException(MarketingErrorCodeEnum.K080090);
        }

        // 2.3.过滤过期及未开始的优惠券
        couponCodes =
                couponCodes.stream()
                        .filter(
                                couponCode ->
                                        couponCode.getUseStatus().equals(DefaultFlag.YES)
                                                || couponCode.getDelFlag().equals(DeleteFlag.YES)
                                                || LocalDateTime.now()
                                                        .isAfter(couponCode.getEndTime())
                                                || LocalDateTime.now()
                                                        .isBefore(couponCode.getStartTime()))
                        .collect(Collectors.toList());

        String validInfo = StringUtils.EMPTY;
        // 2.4.针对过期的优惠券进行提示
        if (CollectionUtils.isNotEmpty(couponCodes)) {
            List<CouponInfo> couponInfos = couponInfoService.queryCouponInfos(CouponInfoQueryRequest.builder()
                    .couponIds(couponCodes.stream().map(CouponCode::getCouponId).collect(Collectors.toList())).build()
            );
            List<String> couponValidInfos = couponInfos.stream().map(couponInfo -> couponValid(couponInfo)).collect(Collectors.toList());
            validInfo = validInfo + StringUtils.join(couponValidInfos, "、") + "优惠券无效！";
        }
        // 2.5.从request对象中移除过期的优惠券
        List<String> invalidCodeIds = couponCodes.stream().map(CouponCode::getCouponCodeId).collect(Collectors.toList());

        return CouponCodeValidOrderCommitResponse.builder().invalidCodeIds(invalidCodeIds).validInfo(validInfo).build();
    }

    /**
     * @description  处理优惠券描述信息
     * @author  wur
     * @date: 2022/10/9 10:18
     * @param couponInfo
     * @return
     **/
    public String couponValid(CouponInfo couponInfo) {
        MarketingCouponPluginInterface couponPluginService = couponPluginContext.getCouponService(couponInfo.getCouponMarketingType());
        if (Objects.isNull(couponPluginService)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return couponPluginService.getLabelMap(couponInfo);
    }

    @Transactional
    public List<CouponCodeVO> sendBatchCouponCodeByCustomer(CouponCodeBatchSendRequest request) {
        List<String> activityIds =
                request.getList().stream()
                        .map(CouponActivityConfigAndCouponInfoDTO::getActivityId)
                        .filter(StringUtils::isNotBlank)
                        .distinct()
                        .collect(Collectors.toList());
        List<String> couponIds =
                request.getList().stream()
                        .map(CouponActivityConfigAndCouponInfoDTO::getCouponId)
                        .filter(StringUtils::isNotBlank)
                        .distinct()
                        .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(activityIds) || CollectionUtils.isEmpty(couponIds)) {
            return Collections.emptyList();
        }

        // 过滤客户id
        CustomerIdsListRequest customerRequest = new CustomerIdsListRequest();
        customerRequest.setCustomerIds(request.getCustomerIds());
        List<String> customerIds =
                customerQueryProvider.getCustomerListByIds(customerRequest).getContext()
                        .getCustomerVOList().stream()
                        .map(CustomerVO::getCustomerId)
                        .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(customerIds)) {
            return Collections.emptyList();
        }

        if (customerIds.size() == 1 && activityIds.size()==1 && couponIds.size() ==1){
            String activityId = activityIds.get(0);
            CouponActivity couponActivity = couponActivityService.getCouponActivityByPk(activityId);
            if (couponActivity == null) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080052);
            }
            if (CouponActivityType.ALL_COUPONS.equals(couponActivity.getCouponActivityType())) {
                CouponFetchRequest fetchRequest = new CouponFetchRequest();
                fetchRequest.setCustomerId(customerIds.get(0));
                fetchRequest.setCouponInfoId(couponIds.get(0));
                fetchRequest.setCouponActivityId(couponActivity.getActivityId());
                CouponCodeVO couponCodeVO = this.customerFetchCoupon(fetchRequest).getContext();
                return Lists.newArrayList(couponCodeVO);
            }
        }

        // 查询券礼包权益关联的优惠券活动配置列表
        List<CouponActivityConfig> couponActivityConfigList =
                couponActivityConfigService.queryByActivityIds(activityIds);
        // 形成<活动id, list<优惠券id>>
        Map<String, List<String>> activityMap =
                couponActivityConfigList.stream()
                        .collect(
                                Collectors.groupingBy(
                                        CouponActivityConfig::getActivityId,
                                        Collectors.mapping(
                                                CouponActivityConfig::getCouponId,
                                                Collectors.toList())));

        // 根据配置查询需要发放的优惠券列表
        List<CouponInfo> couponInfoList = couponInfoRepository.queryByIds(couponIds);
        if (CollectionUtils.isEmpty(couponInfoList)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
        }

        // 形成<活动id, list<优惠券id>>
        Map<String, GetCouponGroupResponse> couponInfoMap =
                couponInfoList.stream()
                        .map(c -> KsBeanUtil.copyPropertiesThird(c, GetCouponGroupResponse.class))
                        .collect(
                                Collectors.toMap(
                                        GetCouponGroupResponse::getCouponId, Function.identity()));

        request.getList()
                .forEach(
                        a -> {
                            List<String> tmpCouponIds = activityMap.get(a.getActivityId());
                            // 活动配置下没有优惠券 or 优惠券id不在该活动配置下 则有问题
                            if (CollectionUtils.isEmpty(tmpCouponIds)
                                    || (!tmpCouponIds.contains(a.getCouponId()))
                                    || (!couponInfoMap.containsKey(a.getCouponId()))) {
                                throw new SbcRuntimeException(
                                        MarketingErrorCodeEnum.K080048);
                            }
                            //设置赠送数
                            couponInfoMap.get(a.getCouponId()).setTotalCount(a.getTotalCount());
                        });

        List<CouponCodeVO> vos = new ArrayList<>();

        activityIds.forEach(activityId -> {
            List<GetCouponGroupResponse> response = activityMap.get(activityId).stream().map(couponInfoMap::get)
					.filter(Objects::nonNull).collect(Collectors.toList());
            for (String customerId : customerIds) {
                // 批量发放优惠券
                List<CouponCode> couponCodes =
                        couponCodeService.sendBatchCouponCodeByCustomer(
                                response, customerId, activityId,null);
                vos.addAll(
                        couponCodes.stream()
                                .map(c -> KsBeanUtil.copyPropertiesThird(c, CouponCodeVO.class))
                                .collect(Collectors.toList()));
            }
        });
        return vos;
    }

    /**
     * 组装查询参数
     *
     * @param query
     * @param request
     */
    private void wrapperQueryParam(Query query, CouponCodePageRequest request) {
        if (Objects.nonNull(request.getCustomerId())) {
            query.setParameter("customerId", request.getCustomerId());
        }
        if (CollectionUtils.isNotEmpty(request.getCouponActivityTypes())) {
            query.setParameter("couponActivityTypes", request.getCouponActivityTypes().stream().map(c -> String.valueOf(c.toValue())).collect(Collectors.toList()));
        }

        if (PluginType.O2O != request.getPluginType()) {
            if (Objects.nonNull(request.getStoreId())) {
                query.setParameter("storeId", request.getStoreId());
                query.setParameter("activityStoreId", request.getStoreId());
            }
        } else {
            if (Objects.nonNull(request.getStoreId())) {
                query.setParameter("storeId", request.getStoreId());
            }
        }

        //优惠券类型
        if (Objects.nonNull(request.getCouponType())) {
            query.setParameter("couponType", request.getCouponType().toValue());
        }

        // 优惠券营销类型
        if (Objects.nonNull(request.getCouponMarketingType())) {
            query.setParameter("couponMarketingType", request.getCouponMarketingType().toValue());
        }

        //券ID列表
        if (CollectionUtils.isNotEmpty(request.getCouponIds())) {
            query.setParameter("couponId", request.getCouponIds());
        }

        //店铺ID
        if (Objects.nonNull(request.getStoreId())) {
            query.setParameter("storeId", request.getStoreId());
        }

        //店铺ID
        if (Objects.nonNull(request.getStoreIds())) {
            query.setParameter("storeIds", request.getStoreIds());
        }

        //是否平台优惠券 1平台 0店铺
        if (Objects.nonNull(request.getPlatformFlag())) {
            query.setParameter("platformFlag", request.getPlatformFlag().toValue());
        }

        //使用范围
        if (Objects.nonNull(request.getScopeType()) && request.getScopeType()!= ScopeType.BOSS_CATE) {
            query.setParameter("scopeType", request.getScopeType().toValue());
        }

        //模糊查询名称
        if (StringUtils.isNotBlank(request.getLikeCouponName())) {
            query.setParameter("likeCouponName", request.getLikeCouponName());
        }

        //优惠券状态
//        if (Objects.nonNull(request.getCouponStatus())) {
//            query.setParameter("couponStatus", request.getCouponStatus());
//        }

        //couponCodeId
        if (CollectionUtils.isNotEmpty(request.getCouponCodeIds())) {
            query.setParameter("couponCodeIds",request.getCouponCodeIds());
        }

        //领取开始时间
        if (request.getAcquireStartTime() != null) {
            query.setParameter("acquireStartTime", DateUtil.format(request.getAcquireStartTime(), DateUtil.FMT_TIME_1));
        }

        //领取结束时间
        if (request.getAcquireEndTime() != null) {
            query.setParameter("acquireEndTime", DateUtil.format(request.getAcquireEndTime(), DateUtil.FMT_TIME_1));
        }

        //过期开始时间
        if (request.getExpireStartTime() != null) {
            query.setParameter("expireStartTime", DateUtil.format(request.getExpireStartTime(), DateUtil.FMT_TIME_1));
        }

        //过期结束时间
        if (request.getExpireEndTime() != null) {
            query.setParameter("expireEndTime", DateUtil.format(request.getExpireEndTime(), DateUtil.FMT_TIME_1));
}
    }

    protected Stream<TradeItemInfo> filterTradeItemsStream(Stream<TradeItemInfo> tradeItemsStream, List<String> scopeIds) {
        return tradeItemsStream
                .filter((item) -> scopeIds.contains(String.valueOf(item.getCateId())));
    }

    public void queryStoreTypeForAspect(CouponCodeListForUseRequest useRequest){}

    /**
     * 查询用户是否领取过优惠券
     * @param customerId
     * @return
     */
    public Boolean checkFetchNewcomer(String customerId) {
        CouponActivity newComerActivity = couponActivityService.findNewComerActivity();
        Integer total = countByCustomerIdAndActivityId(customerId, newComerActivity.getActivityId());
        if (total > 0 ) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }




}
