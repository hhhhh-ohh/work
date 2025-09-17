package com.wanmi.sbc.marketing.coupon.service;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivityConfig;
import com.wanmi.sbc.marketing.coupon.repository.CouponActivityConfigRepository;
import com.wanmi.sbc.marketing.coupon.request.CouponCacheInitRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 优惠券活动配置
 */
@Service
public class CouponActivityConfigService {

    @Autowired
    private CouponActivityConfigRepository couponActivityConfigRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private RedisUtil redisService;

    /**
     * 插入活动配置信息
     */
    @Transactional
    public void insertCouponActivityConfig(CouponActivityConfig couponActivityConfig) {
        couponActivityConfigRepository.save(couponActivityConfig);
    }


    /**
     * 批量插入活动配置信息
     */
    @Transactional
    public void batchInsertCouponActivityConfig(List<CouponActivityConfig> couponActivityConfigList) {
        //找出未关联的活动
        couponActivityConfigList = couponActivityConfigList.parallelStream().filter(couponActivityConfig -> {
            Long aLong = countByActivityIdAndCouponId(couponActivityConfig.getActivityId(), couponActivityConfig.getCouponId());
            return aLong == 0;
        }).collect(Collectors.toList());
        couponActivityConfigRepository.saveAll(couponActivityConfigList);
    }

    /**
     * 根据优惠券id获取活动配置信息
     *
     * @param couponIds 优惠券id
     * @return
     */
    public List<CouponActivityConfig> queryByCouponIds(List<String> couponIds) {
        return couponActivityConfigRepository.findByCouponIds(couponIds);
    }

    /**
     * 根据优惠券id获取活动配置信息
     *
     * @param couponId
     * @return
     */
    public List<CouponActivityConfig> findByCouponId(String couponId) {
        return couponActivityConfigRepository.findByCouponId(couponId);
    }

    /**
     * 根据活动id获取活动配置信息
     *
     * @param activityId 活动id
     * @return
     */
    public List<CouponActivityConfig> queryByActivityId(String activityId) {
        return couponActivityConfigRepository.findByActivityId(activityId);
    }

    /**
     * 根据活动id获取活动配置信息
     *
     * @param
     * @return
     */
    public List<CouponActivityConfig> queryByActivityIds(List<String> activityIds) {
        return couponActivityConfigRepository.findByActivityIdIn(activityIds);
    }

    /**
     * 根据活动id和优惠券id，查询具体规则
     *
     * @param activityId
     * @param couponId
     * @return
     */
    public CouponActivityConfig queryByActivityIdAndCouponId(String activityId, String couponId) {
        return couponActivityConfigRepository.findByActivityIdAndCouponId(activityId, couponId);
    }

    /**
     * 根据活动id和优惠券id，查询具体规则
     *
     * @param activityId
     * @param couponId
     * @return
     */
    public Long countByActivityIdAndCouponId(String activityId, String couponId) {
        return couponActivityConfigRepository.countByActivityIdAndCouponId(activityId, couponId);
    }

    /**
     * 根据活动id和券id更新配置
     *
     */
    @Transactional
    public void updateHasLeft(String activityId, String couponId, DefaultFlag hasLeft) {
        couponActivityConfigRepository.updateHasLeft(activityId,couponId,hasLeft);
    }

    @Transactional
    public void updateActivityId(String activityId) {
        couponActivityConfigRepository.updateActivityId(activityId);
    }


    /**
     * 获取已经开始的优惠券活动信息
     *
     * @param request
     * @return
     */
    public List<CouponCache> queryCouponStarted(CouponCacheInitRequest request) {
        request.setIsO2o(whetherO2o());
        Query query = entityManager.createNativeQuery(request.getQuerySql());
        if (CollectionUtils.isNotEmpty(request.getCouponActivityIds())) {
            query.setParameter("couponActivityIds", request.getCouponActivityIds());
        }
        if (request.getQueryStartTime() != null) {
            query.setParameter("queryStartTime", DateUtil.format(request.getQueryStartTime(), DateUtil.FMT_TIME_1));
        }
        if (request.getQueryEndTime() != null) {
            query.setParameter("queryEndTime", DateUtil.format(request.getQueryEndTime(), DateUtil.FMT_TIME_1));
        }
        if (Objects.nonNull(request.getCreateTimeStart())) {
            query.setParameter("createTimeStart", DateUtil.format(request.getCreateTimeStart(), DateUtil.FMT_TIME_1));
        }
        if (Objects.nonNull(request.getCreateTimeEnd())) {
            query.setParameter("createTimeEnd", DateUtil.format(request.getCreateTimeEnd(), DateUtil.FMT_TIME_1));
        }
        if (Objects.nonNull(request.getUpdateTimeStart())) {
            query.setParameter("updateTimeStart", DateUtil.format(request.getUpdateTimeStart(), DateUtil.FMT_TIME_1));
        }
        if (Objects.nonNull(request.getUpdateTimeEnd())) {
            query.setParameter("updateTimeEnd", DateUtil.format(request.getUpdateTimeEnd(), DateUtil.FMT_TIME_1));
        }
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return CouponCacheInitRequest.converter(query.getResultList());
    }


    /**
     * 获取优惠券分页
     * @param request
     * @return
     */
    public MicroServicePage<CouponCache> queryCouponStartPage(CouponCacheInitRequest request) {
        request.setIsO2o(whetherO2o());
        Query query = entityManager.createNativeQuery(request.getQuerySql());
        query.setFirstResult(request.getPageNum() * request.getPageSize());
        query.setMaxResults(request.getPageSize());
        if (CollectionUtils.isNotEmpty(request.getCouponActivityIds())) {
            query.setParameter("couponActivityIds", request.getCouponActivityIds());
        }
        if (request.getQueryStartTime() != null) {
            query.setParameter("queryStartTime", DateUtil.format(request.getQueryStartTime(), DateUtil.FMT_TIME_1));
        }
        if (request.getQueryEndTime() != null) {
            query.setParameter("queryEndTime", DateUtil.format(request.getQueryEndTime(), DateUtil.FMT_TIME_1));
        }
        if (Objects.nonNull(request.getCreateTimeStart())) {
            query.setParameter("createTimeStart", DateUtil.format(request.getCreateTimeStart(), DateUtil.FMT_TIME_1));
        }
        if (Objects.nonNull(request.getCreateTimeEnd())) {
            query.setParameter("createTimeEnd", DateUtil.format(request.getCreateTimeEnd(), DateUtil.FMT_TIME_1));
        }
        if (Objects.nonNull(request.getUpdateTimeStart())) {
            query.setParameter("updateTimeStart", DateUtil.format(request.getUpdateTimeStart(), DateUtil.FMT_TIME_1));
        }
        if (Objects.nonNull(request.getUpdateTimeEnd())) {
            query.setParameter("updateTimeEnd", DateUtil.format(request.getUpdateTimeEnd(), DateUtil.FMT_TIME_1));
        }
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<CouponCache> list = CouponCacheInitRequest.converter(query.getResultList());
        long count = NumberUtils.INTEGER_ZERO;
        if (CollectionUtils.isNotEmpty(list)) {
            Query countQuery = entityManager.createNativeQuery("SELECT COUNT(1) ".concat(request.getQuerySql().substring(request.getQuerySql().indexOf("FROM"))));
            if (CollectionUtils.isNotEmpty(request.getCouponActivityIds())) {
                countQuery.setParameter("couponActivityIds", request.getCouponActivityIds());
            }
            if (request.getQueryStartTime() != null) {
                countQuery.setParameter("queryStartTime", DateUtil.format(request.getQueryStartTime(), DateUtil.FMT_TIME_1));
            }
            if (request.getQueryEndTime() != null) {
                countQuery.setParameter("queryEndTime", DateUtil.format(request.getQueryEndTime(), DateUtil.FMT_TIME_1));
            }
            if (Objects.nonNull(request.getCreateTimeStart())) {
                countQuery.setParameter("createTimeStart", DateUtil.format(request.getCreateTimeStart(), DateUtil.FMT_TIME_1));
            }
            if (Objects.nonNull(request.getCreateTimeEnd())) {
                countQuery.setParameter("createTimeEnd", DateUtil.format(request.getCreateTimeEnd(), DateUtil.FMT_TIME_1));
            }
            if (Objects.nonNull(request.getUpdateTimeStart())) {
                countQuery.setParameter("updateTimeStart", DateUtil.format(request.getUpdateTimeStart(), DateUtil.FMT_TIME_1));
            }
            if (Objects.nonNull(request.getUpdateTimeEnd())) {
                countQuery.setParameter("updateTimeEnd", DateUtil.format(request.getUpdateTimeEnd(), DateUtil.FMT_TIME_1));
            }
            count = Long.parseLong(countQuery.getSingleResult().toString());
        }
        PageImpl<CouponCache> page = new PageImpl<>(list, PageRequest.of(request.getPageNum(), request.getPageSize()), count);
        return KsBeanUtil.convertPage(page, CouponCache.class);
    }

    /**
     * 是否购买O2O增值服务
     * @return 是否购买O2O
     */
    private Boolean whetherO2o() {
        String vas = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toString(), VASConstants.VAS_O2O_SETTING.toValue());
        if (StringUtils.isNotBlank(vas)){
            return StringUtils.equals(VASStatus.ENABLE.toValue(), vas);
        }
        return false;
    }
}
