package com.wanmi.sbc.marketing.coupon.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.handler.aop.MasterRouteOnly;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.storelevel.StoreLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerIdsListRequest;
import com.wanmi.sbc.customer.api.request.storelevel.CustomerLevelRequest;
import com.wanmi.sbc.customer.api.response.storelevel.CustomerLevelInfoResponse;
import com.wanmi.sbc.customer.bean.vo.CouponMarketingCustomerScopeVO;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.elastic.api.request.coupon.EsActivityCouponModifyRequest;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.response.coupon.*;
import com.wanmi.sbc.marketing.bean.constant.Constant;
import com.wanmi.sbc.marketing.bean.dto.DistributionRewardCouponDTO;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.*;
import com.wanmi.sbc.marketing.coupon.repository.*;
import com.wanmi.sbc.marketing.coupon.response.CouponActivityDetailResponse;
import com.wanmi.sbc.marketing.util.XssUtils;
import com.wanmi.sbc.marketing.util.common.CodeGenUtil;
import com.wanmi.sbc.marketing.util.mapper.CouponActivityMapper;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: songhanlin
 * @Date: Created In 11:43 AM 2018/9/12
 * @Description: 优惠券活动Service
 */
@Service
@Slf4j
public class CouponActivityService {

    @Autowired
    private CouponActivityRepository couponActivityRepository;

    @Autowired
    private CouponActivityConfigRepository couponActivityConfigRepository;

    @Autowired
    private CouponInfoRepository couponInfoRepository;

    @Autowired
    private StoreLevelQueryProvider storeLevelQueryProvider;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CouponCacheService couponCacheService;

    @Autowired
    private CouponActivityConfigService couponActivityConfigService;

    @Autowired
    private CouponCodeService couponCodeService;

    @Autowired
    private CouponMarketingCustomerScopeRepository couponMarketingCustomerScopeRepository;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CouponActivityMapper couponActivityMapper;

    @Autowired
    private CouponCodeRepository couponCodeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CouponActivityService couponActivityService;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private RedisUtil redisService;

    @Lazy
    @Autowired
    private CouponScopeCacheService couponScopeCacheService;

    /**
     * 创建活动
     */
    @Transactional(rollbackFor = Exception.class)
    public CouponActivityDetailResponse addCouponActivity(CouponActivityAddRequest couponActivityAddRequest) {
        // 校验 活动结束时间必须大于已选优惠券结束时间
        List<CouponActivityConfig> couponActivityConfigs = new ArrayList<>(10);
        if (CollectionUtils.isNotEmpty(couponActivityAddRequest.getCouponActivityConfigs())) {
            couponActivityConfigs = KsBeanUtil.copyListProperties(couponActivityAddRequest.getCouponActivityConfigs(), CouponActivityConfig.class);
            List<String> errorIds = this.checkCoupon(couponActivityAddRequest.getEndTime(), couponActivityConfigs);
            if (!errorIds.isEmpty()) {
                throw new SbcRuntimeException(errorIds, MarketingErrorCodeEnum.K080048);
            }
        }

        //校验 当前的storeid是否与数据库的storeid一致  如果是门店端取消校验
        if (PluginType.O2O != couponActivityAddRequest.getPluginType()) {
            List<String> ids = couponActivityConfigs.stream().map(CouponActivityConfig::getCouponId).collect(Collectors.toList());
            List<CouponInfo> couponInfos = couponInfoRepository.queryByIds(ids);
            couponInfos.forEach(couponInfo -> {
                if (!couponInfo.getStoreId().equals(couponActivityAddRequest.getStoreId())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            });
            couponActivityAddRequest.setPluginType(PluginType.NORMAL);
        }

        //保存活动
        CouponActivity couponActivity = new CouponActivity();
        KsBeanUtil.copyPropertiesThird(couponActivityAddRequest, couponActivity);
        couponActivity.setCreateTime(LocalDateTime.now());
        couponActivity.setUpdateTime(LocalDateTime.now());
        couponActivity.setDelFlag(DeleteFlag.NO);
        if(couponActivity.getBusinessSource() == null){
            couponActivity.setBusinessSource(CouponActivitySource.SBC);
        }
        //默认是审核通过
        couponActivity.setAuditState(AuditState.CHECKED);
        if (couponActivityAddRequest.getPauseFlag() == null) {
            couponActivity.setPauseFlag(DefaultFlag.NO);
        }
        if (CouponActivityType.REGISTERED_COUPON == couponActivityAddRequest.getCouponActivityType()
                || CouponActivityType.STORE_COUPONS == couponActivityAddRequest.getCouponActivityType()
                || CouponActivityType.ENTERPRISE_REGISTERED_COUPON == couponActivityAddRequest.getCouponActivityType()) {
            couponActivity.setLeftGroupNum(couponActivity.getReceiveCount());
        }
        /* 后期有并发问题，可以对这部分内容加分布式锁   start*/
        // 校验 进店赠券活动、注册赠券活动、企业注册赠券活动，同一时间段内只能有1个！
        if (couponActivityService.checkActivity(couponActivityAddRequest.getStartTime(),
                couponActivityAddRequest.getEndTime(), couponActivityAddRequest.getCouponActivityType(),
                couponActivityAddRequest.getStoreId(), null, couponActivityAddRequest.getPluginType())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080059);
        }

        //是否开启审核
        couponActivityService.couponActivityAudit(couponActivity);

        couponActivity.setScanVersion(generatorService.generate("V"));
        couponActivity.setScanType(0);
        couponActivity = couponActivityRepository.saveAndFlush(couponActivity);
        //保存活动关联的优惠券
        if (CollectionUtils.isNotEmpty(couponActivityConfigs)) {
            for (CouponActivityConfig item : couponActivityConfigs) {
                item.setActivityId(couponActivity.getActivityId());
                item.setHasLeft(DefaultFlag.YES);
                // 设置小程序二维码scene参数
                item.setScene(UUIDUtil.getUUID_16());
            }
            couponActivityConfigRepository.saveAll(couponActivityConfigs);
        }


        //保存活动关联的目标客户作用范围
        List<CouponMarketingCustomerScope> couponMarketingCustomerScope = saveMarketingCustomerScope
                (couponActivityAddRequest.getCustomerScopeIds(),
                        couponActivity);

        // 精准发券 小于任务扫描周期直接推送到队列
        if (CouponActivityType.SPECIFY_COUPON.equals(couponActivity.getCouponActivityType())) {
            Duration duration =
                    Duration.between(LocalDateTime.now(), couponActivity.getStartTime());
            if (duration.toMinutes() < couponActivityAddRequest.getWithinTime()) {
                MqSendDelayDTO dto = new MqSendDelayDTO();
                dto.setTopic(ProducerTopic.PRECISION_VOUCHERS);
                dto.setDelayTime(duration.toMillis());
                dto.setData(
                        CouponActivityGetDetailByIdAndStoreIdRequest.builder()
                                .id(couponActivity.getActivityId())
                                .storeId(couponActivity.getStoreId())
                                .scanVersion(couponActivity.getScanVersion())
                                .build());
                if (duration.toMinutes() < 0) {
                    mqSendProvider.send(dto);
                } else {
                    mqSendProvider.sendDelay(dto);
                }
                couponActivity.setScanType(1);
            }
        }

        //全场赠券刷新范围
        if (CouponActivityType.ALL_COUPONS.equals(couponActivity.getCouponActivityType()) ||
                CouponActivityType.DRAW_COUPON.equals(couponActivity.getCouponActivityType())){
            CouponActivity finalCouponActivity = couponActivity;
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    couponScopeCacheService.save(finalCouponActivity.getActivityId(),false);
                }
            });

        }

        CouponActivityDetailResponse response = new CouponActivityDetailResponse();
        response.setCouponActivity(KsBeanUtil.copyPropertiesThird(couponActivity, CouponActivityVO.class));
        response.setCouponActivityConfigList(KsBeanUtil.convertList(couponActivityConfigs, CouponActivityConfigVO.class));
        response.setCouponMarketingCustomerScope(KsBeanUtil.convertList(couponMarketingCustomerScope, CouponMarketingCustomerScopeVO.class));
        return response;
    }

    public void couponActivityAudit(CouponActivity couponActivity) {
        //环绕处理
    }

    @Transactional
    public void modifyNewCustomerCouponActivity(LocalDateTime startTime,LocalDateTime endTime) {
        couponActivityRepository.modifyNewCustomerCouponActivity(startTime,endTime);
    }

    /**
     * 编辑活动
     */
    @Transactional(rollbackFor = Exception.class)
    public CouponActivityModifyResponse modifyCouponActivity(CouponActivityModifyRequest couponActivityModifyRequest) {
        // 校验，如果活动未开始才可以编辑
        CouponActivity couponActivity = getCouponActivityByPk(couponActivityModifyRequest.getActivityId());
        if (!(couponActivityModifyRequest.getCouponActivityType() == CouponActivityType.POINTS_COUPON)) {
            if (couponActivity.getStartTime() != null && couponActivity.getStartTime().isBefore(LocalDateTime.now())) {
                // 活动已经开始，不可以编辑，删除
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080049);
            }
        }

        List<CouponActivityConfig> couponActivityConfigs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(couponActivityModifyRequest.getCouponActivityConfigs())) {
            couponActivityConfigs = KsBeanUtil.copyListProperties(couponActivityModifyRequest
                    .getCouponActivityConfigs(), CouponActivityConfig.class);
            // 校验 活动结束时间必须大于已选优惠券结束时间
            List<String> errorIds = this.checkCoupon(couponActivityModifyRequest.getEndTime(), couponActivityConfigs);
            if (!errorIds.isEmpty()) {
                throw new SbcRuntimeException(errorIds, MarketingErrorCodeEnum.K080048);
            }
        }

        //全部删除活动关联的优惠券
        couponActivityConfigRepository.deleteByActivityId(couponActivityModifyRequest.getActivityId());
        //保存活动
        KsBeanUtil.copyProperties(couponActivityModifyRequest, couponActivity);
        couponActivity.setUpdateTime(LocalDateTime.now());
        couponActivity.setScanVersion(generatorService.generate("V"));
        couponActivityRepository.save(couponActivity);
        if (CouponActivityType.REGISTERED_COUPON == couponActivityModifyRequest.getCouponActivityType()
                || CouponActivityType.STORE_COUPONS == couponActivityModifyRequest.getCouponActivityType()) {
            couponActivity.setLeftGroupNum(couponActivity.getReceiveCount());
        }
        /* 后期有并发问题，可以对这部分内容加分布式锁   start*/
        // 校验 进店赠券活动、注册赠券活动，同一时间段内只能各有1个！
        if (couponActivityService.checkActivity(couponActivityModifyRequest.getStartTime(),
                couponActivityModifyRequest.getEndTime(), couponActivityModifyRequest.getCouponActivityType(),
                couponActivityModifyRequest.getStoreId(), couponActivityModifyRequest.getActivityId(), couponActivityModifyRequest.getPluginType())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080055);
        }

        //是否开启审核
        couponActivityService.couponActivityAudit(couponActivity);
        //默认SBC来源
        if(couponActivity.getBusinessSource() == null){
            couponActivity.setBusinessSource(CouponActivitySource.SBC);
        }
        //保存活动
        if (DefaultFlag.NO.toValue() == couponActivity.getReceiveType().toValue()) {
            couponActivity.setReceiveCount(null);
        }
        couponActivity = couponActivityRepository.save(couponActivity);
        /* 后期有并发问题，可以对这部分内容加分布式锁   end*/
        //全部删除活动关联的优惠券
        couponActivityConfigRepository.deleteByActivityId(couponActivityModifyRequest.getActivityId());
        //保存活动关联的优惠券
        if (CollectionUtils.isNotEmpty(couponActivityConfigs)) {
            for (CouponActivityConfig item : couponActivityConfigs) {
                item.setActivityId(couponActivity.getActivityId());
                item.setHasLeft(DefaultFlag.YES);
                // 设置小程序二维码scene参数
                item.setScene(UUIDUtil.getUUID_16());
            }
            couponActivityConfigRepository.saveAll(couponActivityConfigs);
        }

        //保存活动关联的目标客户作用范围
        saveMarketingCustomerScope(couponActivityModifyRequest.getCustomerScopeIds(), couponActivity);

        CouponActivityVO couponActivityVO = couponActivityMapper.couponActivityToCouponActivityVO(couponActivity);

        // 精准发券 小于任务扫描周期直接推送到队列
        if (CouponActivityType.SPECIFY_COUPON.equals(couponActivity.getCouponActivityType())) {
            Duration duration =
                    Duration.between(LocalDateTime.now(), couponActivity.getStartTime());
            if (duration.toMinutes() < couponActivityModifyRequest.getWithinTime()) {
                MqSendDelayDTO dto = new MqSendDelayDTO();
                dto.setTopic(ProducerTopic.PRECISION_VOUCHERS);
                dto.setDelayTime(duration.toMillis());
                dto.setData(
                        CouponActivityGetDetailByIdAndStoreIdRequest.builder()
                                .id(couponActivity.getActivityId())
                                .storeId(couponActivity.getStoreId())
                                .scanVersion(couponActivity.getScanVersion())
                                .build());
                if (duration.toMinutes() < 0) {
                    mqSendProvider.send(dto);
                } else {
                    mqSendProvider.sendDelay(dto);
                }
                couponActivity.setScanType(1);
            } else {
                couponActivity.setScanType(0);
            }
        }

        //全场赠券刷新范围
        if (CouponActivityType.ALL_COUPONS.equals(couponActivity.getCouponActivityType())){
            CouponActivity finalCouponActivity = couponActivity;
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    couponScopeCacheService.refresh(finalCouponActivity.getActivityId(),false);
                }
            });

        }

        return new CouponActivityModifyResponse(couponActivityVO);
    }


    /**
     * 开始活动
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void startActivity(String id) {
        //当活动暂停时才可以开始
        CouponActivity couponActivity = getCouponActivityByPk(id);
        if (CouponActivityType.SPECIFY_COUPON == couponActivity.getCouponActivityType()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (couponActivity.getStartTime().isAfter(LocalDateTime.now()) && couponActivity.getEndTime().isBefore
                (LocalDateTime.now())) {
            //非进行中的活动
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080050);
        }
        couponActivityRepository.startActivity(id);


        couponScopeCacheService.update(EsActivityCouponModifyRequest.builder()
                .activityId(couponActivity.getActivityId()).pauseFlag(DefaultFlag.NO).build());
//        couponCacheService.refreshCachePart(Collections.singletonList(couponActivity.getActivityId()));
    }

    /**
     * 查询活动列表
     * @param request
     */
    public List<CouponActivity> queryActivityInfoList(CouponActivityPageRequest request) {
        return couponActivityRepository.findAll(getWhereCriteria(request));

    }

    /**
     * 暂停活动
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void pauseActivity(String id) {
        // 只有进行中的活动才可以暂停
        CouponActivity couponActivity = getCouponActivityByPk(id);
        if (CouponActivityType.SPECIFY_COUPON == couponActivity.getCouponActivityType()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (couponActivity.getStartTime().isAfter(LocalDateTime.now())) {
            //活动还未开始不能暂停
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080050);
        }
        if (couponActivity.getEndTime().isBefore(LocalDateTime.now())) {
            //活动已经结束
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080051);
        }
        couponActivityRepository.pauseActivity(id);

        couponScopeCacheService.update(EsActivityCouponModifyRequest.builder()
                .activityId(couponActivity.getActivityId()).pauseFlag(DefaultFlag.YES).build());
//        couponCacheService.refreshCachePart(Collections.singletonList(couponActivity.getActivityId()));
    }

    /**
     * 删除活动
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteActivity(String id, String operatorId) {
        //只有未开始的活动才可以删除
        CouponActivity couponActivity = getCouponActivityByPk(id);
        if (CouponActivityType.RIGHTS_COUPON != couponActivity.getCouponActivityType() && couponActivity.getStartTime().isBefore(LocalDateTime.now())) {
            //活动已开始不可以删除
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080049);
        }
        couponActivityRepository.deleteActivity(id, operatorId);
        couponActivityConfigRepository.deleteByActivityId(id);

        couponScopeCacheService.deleteByActivityId(id);
        //删除 couponCache
//        couponCacheService.deleteCachePart(id);
    }

    /**
     * 查询活动详情
     *
     * @param id
     */
    public CouponActivityDetailResponse getActivityDetail(String id, Long storeId) {
        // 1、查询活动基本信息
        CouponActivity couponActivity = getCouponActivityByPk(id);
        CouponActivityDetailResponse response = new CouponActivityDetailResponse();
        //  2、查询关联优惠券信息
        List<CouponActivityConfig> couponActivityConfigs = couponActivityConfigRepository.findByActivityId(id);
        List<String> ids = couponActivityConfigs.stream().map(CouponActivityConfig::getCouponId).collect(Collectors.toList());
        List<CouponInfo> couponInfos = couponInfoRepository.queryByIds(ids);
        //  3、查询客户等级信息
        List<CustomerLevelVO> customerLevels = customerLevels(couponActivity);

        //4.查询指定用户
        List<CouponMarketingCustomerScope> couponMarketingCustomerScope = couponMarketingCustomerScopeRepository
                .findByActivityId(couponActivity.getActivityId());
        if (CollectionUtils.isNotEmpty(couponMarketingCustomerScope)) {
            List<CustomerVO> detailResponseList = getCouponMarketingCustomers(couponMarketingCustomerScope,
                    couponActivity);
            response.setCustomerDetailVOS(detailResponseList);
        }
        response.setCouponMarketingCustomerScope(KsBeanUtil.convertList(couponMarketingCustomerScope, CouponMarketingCustomerScopeVO.class));
        response.setCouponActivity(KsBeanUtil.copyPropertiesThird(couponActivity, CouponActivityVO.class));
        response.setCouponActivityConfigList(KsBeanUtil.convertList(couponActivityConfigs, CouponActivityConfigVO.class));
        response.setCouponInfoList(KsBeanUtil.convertList(couponInfos, CouponInfoSaveVO.class));
        response.setCustomerLevelList(customerLevels);

        return response;
    }

    /**
     * 通过主键获取优惠券活动
     *
     * @param id
     * @return
     */
    @MasterRouteOnly
    public CouponActivity getCouponActivityByPk(String id) {
        CouponActivity couponActivity = couponActivityRepository.findById(id).orElseThrow(() ->
                new SbcRuntimeException(getDeleteIndex(id), MarketingErrorCodeEnum.K080001)
        );
        if (DeleteFlag.YES.equals(couponActivity.getDelFlag())) {
            throw new SbcRuntimeException(getDeleteIndex(id), MarketingErrorCodeEnum.K080001);
        }
        return couponActivity;
    }

    /**
     * 查询活动列表
     *
     * @param request
     */
    public Page<CouponActivity> pageActivityInfo(CouponActivityPageRequest request, Long storeId) {
        request.setStoreId(storeId);
        return couponActivityRepository.findAll(getWhereCriteria(request), request.getPageRequest());

    }

    /**
     * 封装公共条件
     *
     * @return
     */
    public Specification<CouponActivity> getWhereCriteria(CouponActivityPageRequest request) {
        CouponActivityType couponActivityType = request.getCouponActivityType();
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            //店铺id
            if (Objects.nonNull(request.getStoreId())) {
                predicates.add(cbuild.equal(root.get("storeId"), request.getStoreId()));
                predicates.add(cbuild.equal(root.get("platformFlag"), 0));
            } else {
                predicates.add(cbuild.equal(root.get("platformFlag"), 1));
            }

            //活动名称查找
            if (StringUtils.isNotBlank(request.getActivityName())) {
                predicates.add(cbuild.like(root.get("activityName"),
                        StringUtil.SQL_LIKE_CHAR.concat(XssUtils.replaceLikeWildcard(request.getActivityName().trim())).concat(StringUtil.SQL_LIKE_CHAR)));
            }
            //活动类型筛选
            if (CouponActivityType.ALL_COUPONS == couponActivityType
                    || CouponActivityType.SPECIFY_COUPON == couponActivityType
                    || CouponActivityType.STORE_COUPONS == couponActivityType
                    || CouponActivityType.REGISTERED_COUPON == couponActivityType
                    || CouponActivityType.RIGHTS_COUPON == couponActivityType
                    || CouponActivityType.DISTRIBUTE_COUPON == couponActivityType
                    || CouponActivityType.POINTS_COUPON == couponActivityType
                    || CouponActivityType.ENTERPRISE_REGISTERED_COUPON == couponActivityType) {
                predicates.add(cbuild.equal(root.get("couponActivityType"), couponActivityType));
            }

            if (Objects.nonNull(request.getStartTime())) {
                Predicate p1 = cbuild.greaterThanOrEqualTo(root.get("startTime"), request.getStartTime());
                predicates.add(p1);
            }
            if (Objects.nonNull(request.getEndTime())) {
                Predicate p1 = cbuild.lessThanOrEqualTo(root.get("endTime"), request.getEndTime());
                predicates.add(p1);
            }

            switch (request.getQueryTab()) {
                case STARTED://进行中
                    Predicate p1 = cbuild.lessThan(root.get("startTime"), LocalDateTime.now());
                    Predicate p2 = cbuild.greaterThanOrEqualTo(root.get("endTime"), LocalDateTime.now());
                    Predicate p3 = cbuild.and(p1, p2);
                    Predicate p4 = cbuild.equal(root.get("couponActivityType"), 4);
                    Predicate p5 = cbuild.or(p3, p4);
                    predicates.add(p5);
                    predicates.add(cbuild.equal(root.get("pauseFlag"), 0));
                    predicates.add(cbuild.notEqual(root.get("couponActivityType"), 1));
                    break;
                case PAUSED://暂停中
                    Predicate p6 = cbuild.lessThanOrEqualTo(root.get("startTime"), LocalDateTime.now());
                    Predicate p7 = cbuild.greaterThanOrEqualTo(root.get("endTime"), LocalDateTime.now());
                    Predicate p8 = cbuild.and(p6, p7);
                    Predicate p9 = cbuild.equal(root.get("couponActivityType"), 4);
                    Predicate p10 = cbuild.or(p8, p9);
                    predicates.add(p10);
                    predicates.add(cbuild.equal(root.get("pauseFlag"), 1));
                    break;
                case NOT_START://未开始
                    predicates.add(cbuild.greaterThan(root.get("startTime"), LocalDateTime.now()));
                    break;
                case ENDED://已结束
                    predicates.add(cbuild.lessThan(root.get("endTime"), LocalDateTime.now()));
                    break;
                default:
                    break;
            }

            if (StringUtils.isNotBlank(request.getJoinLevel())) {
                Expression<Integer> expression = cbuild.function("FIND_IN_SET", Integer.class, cbuild.literal(request.getJoinLevel()), root.get("joinLevel"));
                predicates.add(cbuild.greaterThan(expression, 0));
            }

            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }


    /**
     * 保存活动时，1 校验优惠券是否存在 2校验优惠券的结束时间是否都在活动结束时间内
     * 将不符合的优惠券id返回
     *
     * @return
     */
    private List<String> checkCoupon(LocalDateTime activityEndTime, List<CouponActivityConfig> couponActivityConfigs) {
        List<String> ids = new ArrayList<>();
        couponActivityConfigs.forEach(item -> ids.add(item.getCouponId()));
        List<CouponInfo> couponInfos = couponInfoRepository.queryByIds(ids);
        List<String> errorIds = new ArrayList<>(10);
        if (ids.size() > couponInfos.size()) {
            errorIds = ids.stream().filter(id ->
                    couponInfos.stream().noneMatch(couponInfo -> couponInfo.getCouponId().equals(id))).collect
                    (Collectors.toList());
            throw new SbcRuntimeException(errorIds, MarketingErrorCodeEnum.K080046);
        }

        // 会员权益赠券的活动没有结束时间，直接return
        if (Objects.isNull(activityEndTime)) {
            return errorIds;
        }

        for (CouponInfo item : couponInfos) {
            if (RangeDayType.RANGE_DAY == item.getRangeDayType() && item.getEndTime().isBefore(activityEndTime)) {
                errorIds.add(item.getCouponId());
            }
            continue;
        }
        return errorIds;
    }

    /**
     * 获取目前最后一个开始的优惠券活动
     *
     * @return
     */
    public CouponActivity getLastActivity() {
        List<CouponActivity> activityList = couponActivityRepository.getLastActivity(PageRequest.of(0, 1, Sort
                .Direction.DESC, "startTime"));
        return CollectionUtils.isNotEmpty(activityList) ? activityList.get(0) : null;
    }

    /**
     * 校验 进店赠券活动、注册赠券活动、企业注册赠券活动，同一时间段内只能有1个！
     * true 表示 校验失败
     *
     * @param statTime
     * @param endTime
     * @param type
     * @param storeId
     * @param activityId
     * @param pluginType  活动类型是SBC或者O2O，O2O插件中使用
     * @return
     */
    public Boolean checkActivity(LocalDateTime statTime, LocalDateTime endTime, CouponActivityType type, Long
            storeId, String activityId, PluginType pluginType) {
        Boolean flag = Boolean.FALSE;
        if (CouponActivityType.REGISTERED_COUPON == type || CouponActivityType.STORE_COUPONS == type
                || CouponActivityType.ENTERPRISE_REGISTERED_COUPON == type) {
            List<CouponActivity> activityList = couponActivityRepository.queryActivityByTime(statTime, endTime, type, storeId);
            //过滤当前活动id
            if (StringUtils.isNotBlank(activityId)) {
                activityList = activityList.stream().filter(
                        item -> !item.getActivityId().equals(activityId)).collect(Collectors.toList());
            }
            activityList = activityList.stream().filter(item -> item.getEndTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());
            if (!activityList.isEmpty()) {
                flag = Boolean.TRUE;
            }
        }
        return flag;
    }

    /**
     * 领取一组优惠券 （注册活动或者进店活动）
     * 用户注册成功或者进店后，发放赠券
     *
     * @param customerId
     * @param type
     * @param storeId
     * @return
     */
    @Transactional
    public GetRegisterOrStoreCouponResponse getCouponGroup(String customerId, CouponActivityType type, Long storeId) {
        //参数校验
        if (CouponActivityType.REGISTERED_COUPON != type && CouponActivityType.STORE_COUPONS != type && CouponActivityType.ENTERPRISE_REGISTERED_COUPON != type) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (customerId == null || storeId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 1、查询是否该类型的活动在进行中 并且活动剩余优惠券组数>0；
        List<CouponActivity> couponActivityList = couponActivityRepository.queryGoingActivityByType(type, storeId);
        //企业会员注册-企业会员的注册赠券活动，优先级高于全平台客户。
        //企业会员注册赠券活动不存在或者优惠券组数没有了，继续注册赠券的逻辑
        if (CouponActivityType.ENTERPRISE_REGISTERED_COUPON == type && CollectionUtils.isEmpty(couponActivityList)) {
            couponActivityList = couponActivityRepository.queryGoingActivityByType(CouponActivityType.REGISTERED_COUPON, storeId);
        }

        if (couponActivityList.size() == 0) {
            return null;
        } else if (couponActivityList.size() != 1) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "查询活动时：活动类型为：" + type + "的活动数据重复！！！");
        }
        CouponActivity activity = couponActivityList.get(0);
        GetRegisterOrStoreCouponResponse response = new GetRegisterOrStoreCouponResponse();
        boolean flag = this.checkCustomerQualify(customerId, type, activity.getActivityId());
        if (!flag) {
            return null;
        }
        //如果剩下的数量<0,返回空
        if (activity.getLeftGroupNum() == 0) {
            return null;
        }
        response.setDesc(activity.getActivityDesc());
        response.setTitle(activity.getActivityTitle());
        // 2、未领完则 先领取（并发如果很大，可对这部分加锁）
        int num = couponActivityRepository.getCouponGroup(activity.getActivityId());
        if (num == 0) {
            return null;
        }
        if (num != 1) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "更新剩余数量时：活动类型为：" + type + "的活动数据重复！！！");
        }
        // 3、再生成用户优惠券数据
        List<CouponActivityConfig> couponActivityConfigList = couponActivityConfigService.queryByActivityId(activity
                .getActivityId());
        List<CouponInfo> couponInfoList = couponInfoRepository.queryByIds(couponActivityConfigList.stream().map(
                CouponActivityConfig::getCouponId).collect(Collectors.toList()));
        List<GetCouponGroupResponse> getCouponGroupResponse = KsBeanUtil.copyListProperties(couponInfoList,
                GetCouponGroupResponse.class);
        getCouponGroupResponse = getCouponGroupResponse.stream().peek(item -> couponActivityConfigList.forEach(config
                -> {
            if (item.getCouponId().equals(config.getCouponId())) {
                item.setTotalCount(config.getTotalCount());
            }
        })).collect(Collectors.toList());
        couponCodeService.sendBatchCouponCodeByCustomer(getCouponGroupResponse, customerId, activity.getActivityId(),null);
        //4. 按金额大小 从大到小排序
        getCouponGroupResponse.sort(Comparator.comparing(GetCouponGroupResponse::getDenomination).reversed());
        response.setCouponList(getCouponGroupResponse);
        return response;
    }

    /**
     * @param request
     * @return
     * @description 查询用户注册券信息
     * @author wur
     * @date: 2021/12/3 11:28
     **/
    public GetRegisterOrStoreCouponResponse queryRegisterCoupon(GetRegisterCouponRequest request) {
        GetRegisterOrStoreCouponResponse response = new GetRegisterOrStoreCouponResponse();
        //查询注册券活动
        List<CouponActivity> couponActivityList = couponActivityRepository.queryActivityByType(request.getCouponActivityType(), Constant.BOSS_DEFAULT_STORE_ID);
        if(CollectionUtils.isEmpty(couponActivityList) || couponActivityList.size() > 1) {
            return response;
        }
        CouponActivity activity = couponActivityList.get(0);
        //查询用户以领取该活动券的信息
        List<CouponCode> couponCodes = couponCodeService.findByCustomerIdAndActivityId(request.getCustomerId(), activity.getActivityId());
        if (CollectionUtils.isEmpty(couponCodes)) {
            return response;
        }
        List<CouponInfo> couponInfoList = couponInfoRepository.queryByIds(couponCodes.stream().map(
                CouponCode::getCouponId).collect(Collectors.toList()));
        List<GetCouponGroupResponse> getCouponGroupResponse = KsBeanUtil.copyListProperties(couponInfoList,
                GetCouponGroupResponse.class);
        List<CouponActivityConfig> couponActivityConfigList = couponActivityConfigService.queryByActivityId(activity
                .getActivityId());
        getCouponGroupResponse = getCouponGroupResponse.stream().peek(item -> couponActivityConfigList.forEach(config
                -> {
            if (item.getCouponId().equals(config.getCouponId())) {
                item.setTotalCount(config.getTotalCount());
            }
        })).collect(Collectors.toList());
        getCouponGroupResponse.sort(Comparator.comparing(GetCouponGroupResponse::getDenomination).reversed());
        response.setCouponList(getCouponGroupResponse);
        response.setDesc(activity.getActivityDesc());
        response.setTitle(activity.getActivityTitle());
        return response;
    }

    /**
     * 查询活动（注册赠券活动、进店赠券活动）不可用的时间范围
     *
     * @param request
     * @return
     */
    public CouponActivityDisabledTimeResponse queryActivityEnableTime(CouponActivityDisabledTimeRequest request) {
        if (CouponActivityType.ALL_COUPONS == request.getCouponActivityType()
                || CouponActivityType.SPECIFY_COUPON == request.getCouponActivityType()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<CouponActivity> couponActivityList = couponActivityRepository.queryActivityDisableTime(request
                .getCouponActivityType(), request.getStoreId());
        if (StringUtils.isNotBlank(request.getActivityId()) && !couponActivityList.isEmpty()) {
            couponActivityList = couponActivityList.stream().filter(
                    item -> !item.getActivityId().equals(request.getActivityId())).collect(Collectors.toList());
        }
        CouponActivityDisabledTimeResponse disabledTimeResponses = new CouponActivityDisabledTimeResponse();
        disabledTimeResponses.setCouponActivityDisabledTimeVOList(
                couponActivityList.stream().map(item -> {
                    CouponActivityDisabledTimeVO disabledTime = new CouponActivityDisabledTimeVO();
                    disabledTime.setStartTime(item.getStartTime());
                    disabledTime.setEndTime(item.getEndTime());
                    return disabledTime;
                }).collect(Collectors.toList()));
        return disabledTimeResponses;
    }

    /**
     * 判断用户领券资格
     *
     * @param customerId
     * @param type
     * @param activityId
     * @return
     */
    private boolean checkCustomerQualify(String customerId, CouponActivityType type, String activityId) {
        boolean flag = false;
        if (CouponActivityType.REGISTERED_COUPON == type || CouponActivityType.ENTERPRISE_REGISTERED_COUPON == type) {
            //注册赠券(注册赠券、企业会员注册赠券)，校验当前用户是否有券，如果有券了就不可用领注册券
            Integer num = couponCodeService.countByCustomerId(customerId);
            if (num == 0) {
                flag = true;
            }
        } else if (CouponActivityType.STORE_COUPONS == type) {
            //进店赠券，根据customerId和activityId判断当前用户在本次活动中是否已经有券
            Integer num = couponCodeService.countByCustomerIdAndActivityId(customerId, activityId);
            if (num == 0) {
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 指定活动赠券
     *
     * @param request
     * @return
     */
    @Transactional
    public SendCouponResponse sendCouponGroup(SendCouponGroupRequest request) {
        SendCouponResponse response = new SendCouponResponse();
        couponCodeService.sendBatchCouponCodeByCustomer(request.getCouponInfos(), request.getCustomerId(), request
                .getActivityId(),null);
        response.setCouponList(request.getCouponInfos());
        return response;
    }

    /**
     * 邀新注册-发放优惠券
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean sendCouponGroup(CouponGroupAddRequest request) {

        String requestCustomerId = request.getRequestCustomerId();

        List<DistributionRewardCouponDTO> list = request.getDistributionRewardCouponDTOList();

        if (StringUtils.isBlank(requestCustomerId) || CollectionUtils.isEmpty(list)) {
            return Boolean.FALSE;
        }

        List<String> couponIdList = list.stream().map(DistributionRewardCouponDTO::getCouponId).collect(Collectors
                .toList());

        Map<String, Integer> map = list.stream().collect(Collectors.toMap(DistributionRewardCouponDTO::getCouponId,
                DistributionRewardCouponDTO::getCount));

        Integer sum = list.stream().map(DistributionRewardCouponDTO::getCount).reduce(Integer::sum).orElse
                (NumberUtils.INTEGER_ZERO);

        List<CouponInfo> couponInfoList = couponInfoRepository.queryByIds(couponIdList);

        List<GetCouponGroupResponse> couponGroupResponseList = couponInfoList.stream().map(couponInfo -> {
            GetCouponGroupResponse getCouponGroupResponse = KsBeanUtil.convert(couponInfo, GetCouponGroupResponse
                    .class);
            getCouponGroupResponse.setTotalCount(map.get(couponInfo.getCouponId()).longValue());
            return getCouponGroupResponse;
        }).collect(Collectors.toList());

        CouponActivity couponActivity = findDistributeCouponActivity();

        List<CouponCode> couponCodeList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        couponGroupResponseList.forEach(item -> {
            for (int i = 0; i < item.getTotalCount(); i++) {
                CouponCode couponCode = new CouponCode();
                couponCode.setCouponCode(CodeGenUtil.toSerialCode(RandomUtils.nextInt(1, 10000)).toUpperCase());
                couponCode.setCouponId(item.getCouponId());
                couponCode.setActivityId(couponActivity.getActivityId());
                couponCode.setCustomerId(requestCustomerId);
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
                couponCode.setCreatePerson(requestCustomerId);
                couponCodeList.add(couponCode);
            }
        });
        List<CouponCode> codeList = couponCodeRepository.saveAll(couponCodeList);

        return sum == codeList.size() ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 查询分销邀新赠券活动
     *
     * @return
     */
    public CouponActivity findDistributeCouponActivity() {
        return couponActivityRepository.findDistributeCouponActivity();
    }

    /**
     * 保存优惠券活动目标客户作用范围
     *
     * @param customerScopeIds
     * @param couponActivity
     */
    private List<CouponMarketingCustomerScope> saveMarketingCustomerScope(List<String> customerScopeIds, CouponActivity
            couponActivity) {
        //全部删除活动关联的目标客户作用范围
        couponMarketingCustomerScopeRepository.deleteByActivityId(couponActivity.getActivityId());
        //保存优惠券活动目标客户作用范围
        if (CollectionUtils.isNotEmpty(customerScopeIds)) {
            List<CouponMarketingCustomerScope> couponMarketingCustomerScopes = new ArrayList<>(10);
            for (String item : customerScopeIds) {
                CouponMarketingCustomerScope temp = new CouponMarketingCustomerScope();
                temp.setActivityId(couponActivity.getActivityId());
                temp.setCustomerId(item);
                couponMarketingCustomerScopes.add(temp);
            }
            couponMarketingCustomerScopeRepository.saveAll(couponMarketingCustomerScopes);
            return couponMarketingCustomerScopes;
        }
        return null;
    }

    /**
     * 查询客户等级
     *
     * @param couponActivity
     * @return
     */
    private List<CustomerLevelVO> customerLevels(CouponActivity couponActivity) {
        List<CustomerLevelVO> customerLevels = null;
        MarketingJoinLevel marketingJoinLevel = getMarketingJoinLevel(couponActivity.getJoinLevel());
        //其他等级
        if (Objects.equals(MarketingJoinLevel.LEVEL_LIST, marketingJoinLevel)) {

            CustomerLevelRequest customerLevelRequest = CustomerLevelRequest.builder().storeId(couponActivity
                            .getStoreId())
                    .levelType(couponActivity.getJoinLevelType()).build();
            BaseResponse<CustomerLevelInfoResponse> customerLevelInfoResponse = storeLevelQueryProvider
                    .queryCustomerLevelInfo(customerLevelRequest);
            customerLevels = customerLevelInfoResponse.getContext().getCustomerLevelVOList();
        }
        return customerLevels;
    }

    /**
     * 活动关联客户信息
     *
     * @param couponMarketingCustomerScope
     * @param couponActivity
     * @return
     */
    private List<CustomerVO> getCouponMarketingCustomers(List<CouponMarketingCustomerScope> couponMarketingCustomerScope,
                                                         CouponActivity
                                                                 couponActivity) {
        if (CollectionUtils.isNotEmpty(couponMarketingCustomerScope)) {
            List<String> customerIds = couponMarketingCustomerScope.stream().map
                    (CouponMarketingCustomerScope::getCustomerId).collect(Collectors.toList());
            CustomerIdsListRequest request = new CustomerIdsListRequest();
            request.setCustomerIds(customerIds);
            List<CustomerVO> detailResponseList = customerQueryProvider.getCustomerListByIds(request)
                    .getContext().getCustomerVOList();
            return detailResponseList;

        }
        return null;
    }

    public MarketingJoinLevel getMarketingJoinLevel(String joinLevel) {
        if (joinLevel.equals(Constants.STR_0)) {
            return MarketingJoinLevel.ALL_LEVEL;
        } else if (joinLevel.equals(Constants.STR_MINUS_1)) {
            return MarketingJoinLevel.ALL_CUSTOMER;
        } else if (joinLevel.equals(Constants.STR_MINUS_2)) {
            return MarketingJoinLevel.SPECIFY_CUSTOMER;
        } else {
            return MarketingJoinLevel.LEVEL_LIST;
        }
    }

    /**
     * 分页查询优惠券活动
     *
     * @param request
     * @return
     */
    public List<CouponActivityBaseVO> page(CouponActivityListPageRequest request) {

        List<String> activityIds = CollectionUtils.isNotEmpty(request.getActivityIds()) ? request.getActivityIds() : couponActivityRepository.listByPage(request.getPageRequest());

        List<CouponActivity> couponActivityList = couponActivityRepository.findAllById(activityIds);

        if (CollectionUtils.isEmpty(couponActivityList)) {
            return Lists.newArrayList();
        }

        return couponActivityMapper.couponActivityToCouponActivityBaseVO(couponActivityList);
    }


    /**
     * 拼凑删除es-提供给findOne去调
     *
     * @param id 编号
     * @return "{index}:{id}"
     */
    private Object getDeleteIndex(String id) {
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.DOC_COUPON_ACTIVITY, id);
    }

    /**
     * @param couponActivityType
     * @param startTime
     * @return void
     * @description 校验开始时间不能早于当前时间，注册赠券 权益赠券 企业会员注册赠券时间范围精确到天，其他精确到分
     * @author 张文昌
     * @date 2021/4/9 19:36
     */
    public void checkStartTime(CouponActivityType couponActivityType,
                               LocalDateTime startTime) {
        LocalDateTime now = LocalDateTime.now();
        now = now.withNano(0);
        now = now.withSecond(0);
        //注册赠券 权益赠券 企业会员注册赠券时间范围精确到天，其他精确到分
        if (CouponActivityType.REGISTERED_COUPON == couponActivityType
                || CouponActivityType.RIGHTS_COUPON == couponActivityType
                || CouponActivityType.ENTERPRISE_REGISTERED_COUPON == couponActivityType
        ) {
            now = now.withHour(0);
            now = now.withMinute(0);
        }
        if (Objects.nonNull(startTime) && startTime.isBefore(now)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080108);
        }
    }

    /**
     * @param id
     * @param operatorId
     * @return
     * @description 关闭活动
     * @author xuyunpeng
     * @date 2021/6/23 10:30 上午
     */
    @Transactional
    public void closeActivity(String id, String operatorId) {

        CouponActivity couponActivity = getCouponActivityByPk(id);

        if (CouponActivityType.RIGHTS_COUPON == couponActivity.getCouponActivityType()
                && CouponActivityType.SPECIFY_COUPON == couponActivity.getCouponActivityType()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080058);
        }
        //只有进行中的活动才可以关闭
        if (couponActivity.getStartTime().isAfter(LocalDateTime.now())) {
            //活动未开始
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080050);
        } else if (couponActivity.getEndTime().isBefore(LocalDateTime.now())) {
            //活动已结束
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080051);
        } else if (couponActivity.getPauseFlag() == DefaultFlag.YES) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080057);
        }
        couponActivityRepository.closeActivity(id, operatorId);
        Query query = Query.query(Criteria.where("couponActivityId").is(id));
        LocalDateTime now = LocalDateTime.now();
        Update update = Update.update("couponActivity.endTime", now);
        mongoTemplate.updateFirst(query, update, CouponCache.class);

        //更新es
        if (CouponActivityType.ALL_COUPONS.equals(couponActivity.getCouponActivityType())){
            couponScopeCacheService.update(EsActivityCouponModifyRequest.builder().activityId(couponActivity.getActivityId())
                    .activityEndTime(now).build());
        }
    }

    /**
     * @param minute
     * @return java.util.List<CouponActivityVO>
     * @description 查询距离开始指定时间范围内的数据并标记为已扫描
     * @author Bob
     * @date: 2021/9/9 17:44
     */
    public List<CouponActivityVO> getActivityByStartTime(int minute) {
        List<Object> resultList = couponActivityRepository.getActivityByStartTime(minute);
        List<CouponActivityVO> couponActivityVOS = new ArrayList<>();
        resultList.forEach(
                result -> {
                    Object[] results = StringUtil.cast(result, Object[].class);
                    if (results != null && results.length > 0) {
                        CouponActivityVO vo = new CouponActivityVO();
                        String id = StringUtil.cast(results, 0, String.class);
                        vo.setActivityId(id);
                        Timestamp startDate = StringUtil.cast(results, 1, Timestamp.class);
                        vo.setStartTime(startDate.toLocalDateTime());
                        BigInteger storeId = StringUtil.cast(results, 2, BigInteger.class);
                        vo.setStoreId(storeId != null ? storeId.longValue() : null);
                        String scanVersion = StringUtil.cast(results, 3, String.class);
                        vo.setScanVersion(scanVersion);
                        couponActivityVOS.add(vo);
                    }
                });
//        List<String> activityIds = couponActivityVOS.stream()
//                .map(CouponActivityVO::getActivityId)
//                .collect(Collectors.toList());
//        if (CollectionUtils.isNotEmpty(activityIds)) {
//            couponActivityRepository.updateForSanType(activityIds);
//        }
        return couponActivityVOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateForSanType(String activityId, Integer scanType){
        couponActivityRepository.updateForSanType(activityId, scanType);
    }

    /**
     * 根据小程序二维码scene获取活动配置信息
     *
     * @param scene 小程序二维码scene
     * @return
     */
    public CouponActivityConfig getCouponActivityConfigByScene(String scene) {
        List<CouponActivityConfig> couponActivityConfigs = couponActivityConfigRepository.findByScene(scene);
        return couponActivityConfigs.stream().findFirst().orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    public CouponActivityModifyResponse modifyPauseCouponActivity(CouponActivityModifyRequest request) {

        CouponActivity couponActivity = couponActivityRepository.findById(request.getActivityId()).orElse(null);

        if (Objects.isNull(couponActivity)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080190);
        }

        if (LocalDateTime.now().isAfter(couponActivity.getEndTime())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080054);
        }

        if (request.getEndTime().isBefore(couponActivity.getEndTime())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (CollectionUtils.isNotEmpty(request.getCouponActivityConfigs())) {
            List<CouponActivityConfig> configs = KsBeanUtil.convertList(request
                    .getCouponActivityConfigs(), CouponActivityConfig.class);
            // 校验 活动结束时间必须大于已选优惠券结束时间
            List<String> errorIds = this.checkCoupon(request.getEndTime(), configs);
            if (!errorIds.isEmpty()) {
                throw new SbcRuntimeException(errorIds, MarketingErrorCodeEnum.K080048);
            }
        }

        // 校验 进店赠券活动、注册赠券活动，同一时间段内只能各有1个！
        if (couponActivityService.checkActivity(request.getStartTime(),
                request.getEndTime(), request.getCouponActivityType(),
                request.getStoreId(), request.getActivityId(), request.getPluginType())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080055);
        }

        if (CouponActivityType.ENTERPRISE_REGISTERED_COUPON.equals(couponActivity.getCouponActivityType())
                || CouponActivityType.REGISTERED_COUPON.equals(couponActivity.getCouponActivityType())
                || CouponActivityType.STORE_COUPONS.equals(couponActivity.getCouponActivityType())) {
            //企业注册赠券/注册赠券
            if (request.getIncreaseGroupNum() < 0) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            couponActivity.setReceiveCount(couponActivity.getReceiveCount() + request.getIncreaseGroupNum());
            couponActivity.setLeftGroupNum(couponActivity.getLeftGroupNum() + request.getIncreaseGroupNum());

        } else if (CouponActivityType.ALL_COUPONS.equals(couponActivity.getCouponActivityType())) {
            //全场赠券
            List<CouponActivityConfig> couponActivityConfigs = couponActivityConfigRepository.findByActivityId(couponActivity.getActivityId());
            couponActivityConfigs.forEach(config -> {
                CouponActivityConfigSaveRequest configRequest = request.getCouponActivityConfigs()
                        .stream()
                        .filter(c -> config.getCouponId().equals(c.getCouponId()))
                        .findFirst()
                        .orElse(null);

                if (Objects.isNull(configRequest) || configRequest.getTotalCount() < config.getTotalCount()) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }

                if (configRequest.getTotalCount() > config.getTotalCount()) {
                    config.setHasLeft(DefaultFlag.YES);
                }
                long moreCount = configRequest.getTotalCount() - config.getTotalCount();
                log.info("modifyPauseCouponActivity moreCount = " + moreCount);
                config.setTotalCount(configRequest.getTotalCount());


                couponActivityConfigRepository.save(config);

                //更新redis缓存
                String redisKey = couponCodeService.getCouponBankKey(config.getActivityId(), config.getCouponId());
                String couponCountCache = redisService.getString(redisKey);

                long leftCount;
                if (couponCountCache != null) {
                    log.info("modifyPauseCouponActivity couponCountCache = " + couponCountCache);
                    leftCount = Long.parseLong(couponCountCache) + moreCount;
                }else{
                    leftCount = moreCount;
                }

                //剩余张数已存入redis
                redisService.setString(redisKey, leftCount + "");
            });
        }

        List<String> oldJoinLevelList = Arrays.stream(couponActivity.getJoinLevel().split(",")).collect(Collectors.toList());
        List<String> newJoinLevelList = Arrays.stream(request.getJoinLevel().split(",")).collect(Collectors.toList());

        int oldLevel = Integer.parseInt(oldJoinLevelList.get(0));
        int newLevel = Integer.parseInt(newJoinLevelList.get(0));

        //当初始目标客户为全平台客户时,不做修改
        if (oldLevel != MarketingJoinLevel.ALL_CUSTOMER.toValue()) {
            if (newLevel == MarketingJoinLevel.ALL_CUSTOMER.toValue()) {
                couponActivity.setJoinLevel(newJoinLevelList.get(0));
            } else if (oldLevel != MarketingJoinLevel.ALL_LEVEL.toValue()) {
                if (newLevel != MarketingJoinLevel.ALL_LEVEL.toValue() && oldJoinLevelList.size() > newJoinLevelList.size()) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                if (newLevel == MarketingJoinLevel.ALL_LEVEL.toValue()) {
                    couponActivity.setJoinLevel(newJoinLevelList.get(0));
                } else {
                    couponActivity.setJoinLevel(StringUtils.join(CollectionUtils.union(oldJoinLevelList, newJoinLevelList), ","));
                }
            }
        }

        couponActivity.setEndTime(request.getEndTime());
        couponActivity.setUpdateTime(LocalDateTime.now());
        couponActivity.setScanVersion(generatorService.generate("V"));
        couponActivity = couponActivityRepository.save(couponActivity);

        //全场赠券刷新范围
        if (CouponActivityType.ALL_COUPONS.equals(couponActivity.getCouponActivityType())){
            CouponActivity finalCouponActivity = couponActivity;
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    couponScopeCacheService.refresh(finalCouponActivity.getActivityId(),true);
                }
            });

        }
        CouponActivityVO vo = couponActivityMapper.couponActivityToCouponActivityVO(couponActivity);

        return new CouponActivityModifyResponse(vo);
    }

    /**
     * 根据抽奖活动ID获取优惠券Id
     * @param drawActivityId
     * @return
     */
    public CouponActivityGetByIdResponse getByDrawActivityId(Long drawActivityId) {
        CouponActivity couponActivity = couponActivityRepository.getActivityByDrawActivityId(drawActivityId);
        return KsBeanUtil.convert(couponActivity,CouponActivityGetByIdResponse.class);
    }

    /**
     * 查询新人专享券活动
     * @return
     */
    public CouponActivity findNewComerActivity(){
        return couponActivityRepository.findNewComerActivity();
    }
}
