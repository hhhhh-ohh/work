package com.wanmi.sbc.marketing.coupon.service;

import com.google.common.base.Splitter;
import com.wanmi.sbc.common.enums.AuditState;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreCustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerListCustomerIdByPageableRequest;
import com.wanmi.sbc.customer.api.request.store.StoreCustomerRelaListCustomerIdByStoreIdRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityGetDetailByIdAndStoreIdRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponMarketingCustomerScopePageRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityDetailByActivityIdResponse;
import com.wanmi.sbc.marketing.bean.dto.CouponActivityConfigAndCouponInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.CouponInfoDTO;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityConfigVO;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivity;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivityConfig;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.model.root.CouponMarketingCustomerScope;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bob
 * @description 精准发券-全平台、店铺等级、指定用户
 * @date 2021/9/10 15:37
 */
@Slf4j
@Service
public class PrecisionVouchersService {

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private StoreCustomerQueryProvider storeCustomerQueryProvider;

    @Autowired
    private CouponActivityService couponActivityService;

    @Autowired
    private CouponActivityConfigService couponActivityConfigService;

    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    private CouponMarketingCustomerScopeService couponMarketingCustomerScopeService;

    @Resource
    private EntityManager entityManager;

    @Autowired
    private CouponCodeService couponCodeService;

    @Transactional(rollbackFor = Exception.class)
    public void execute(CouponActivityGetDetailByIdAndStoreIdRequest request) {
        entityManager.flush();
        log.info("精准发券，优惠券活动ID：{},定时任务开始运行！", request.getId());
        CouponActivityDetailByActivityIdResponse response =
                this.getByActivityId(request.getId(), request.getScanVersion());
        CouponActivityVO couponActivityVO = response.getCouponActivity();
        List<CouponActivityConfigVO> couponActivityConfigVOList =
                response.getCouponActivityConfigList();
        List<CouponInfoVO> couponInfoList = response.getCouponInfoList();
        if (Objects.isNull(couponActivityVO)
                || CollectionUtils.isEmpty(couponActivityConfigVOList)
                || CollectionUtils.isEmpty(couponInfoList)
                || CouponActivityType.SPECIFY_COUPON != couponActivityVO.getCouponActivityType()
                || DeleteFlag.YES == couponActivityVO.getDelFlag()
                || StringUtils.isBlank(couponActivityVO.getJoinLevel())
                || AuditState.CHECKED != couponActivityVO.getAuditState()) {
            log.info(
                    "根据优惠券活动ID：{}, version: {},未查询到活动信息/关联优惠券信息，或者此活动类型不是指定赠券/此活动已删除，或者未审核",
                    request.getId(),
                    request.getScanVersion());
            return;
        }

        log.info(
                "根据优惠券活动ID：{},活动信息：{}，关联优惠券信息：{},优惠券信息：{}，精准发券流程开始",
                request.getId(),
                couponActivityVO,
                couponActivityConfigVOList,
                couponInfoList);

        // 组装发券信息
        List<CouponInfoDTO> couponInfoDTOList =
                KsBeanUtil.convert(couponInfoList, CouponInfoDTO.class);
        final Map<String, CouponInfoDTO> couponInfoDTOMap =
                couponInfoDTOList.stream()
                        .collect(Collectors.toMap(CouponInfoDTO::getCouponId, Function.identity()));
        List<CouponActivityConfigAndCouponInfoDTO> configAndCouponInfoDTOS =
                couponActivityConfigVOList.stream()
                        .map(
                                couponActivityConfigVO -> {
                                    CouponActivityConfigAndCouponInfoDTO dto =
                                            new CouponActivityConfigAndCouponInfoDTO();
                                    dto.setActivityConfigId(
                                            couponActivityConfigVO.getActivityConfigId());
                                    dto.setActivityId(couponActivityConfigVO.getActivityId());
                                    dto.setCouponId(couponActivityConfigVO.getCouponId());
                                    dto.setHasLeft(couponActivityConfigVO.getHasLeft());
                                    dto.setTotalCount(couponActivityConfigVO.getTotalCount());
                                    dto.setCouponInfoDTO(
                                            couponInfoDTOMap.get(
                                                    couponActivityConfigVO.getCouponId()));
                                    return dto;
                                })
                        .collect(Collectors.toList());

        // 处理精准发放优惠券
        handlePrecisionVouchers(couponActivityVO, configAndCouponInfoDTOS);
        // 更新优惠券变更为已扫描
        couponActivityService.updateForSanType(request.getId(), Constants.yes);
    }

    /**
     * 处理精准发放优惠券
     *
     * @param couponActivityVO
     */
    private void handlePrecisionVouchers(
            CouponActivityVO couponActivityVO,
            List<CouponActivityConfigAndCouponInfoDTO> configAndCouponInfoDTOS) {
        String activityId = couponActivityVO.getActivityId();

        String joinLevel = couponActivityVO.getJoinLevel();
        MarketingJoinLevel marketingJoinLevel;
        if (joinLevel.equals(String.valueOf(MarketingJoinLevel.ALL_CUSTOMER.toValue()))
                || joinLevel.equals(String.valueOf(MarketingJoinLevel.SPECIFY_CUSTOMER.toValue()))
                || joinLevel.equals(String.valueOf(MarketingJoinLevel.ALL_LEVEL.toValue()))) {
            marketingJoinLevel = MarketingJoinLevel.fromValue(Integer.parseInt(joinLevel));
        } else {
            marketingJoinLevel = MarketingJoinLevel.LEVEL_LIST;
        }
        log.info(
                "根据优惠券活动ID：{},关联的客户等级：{}，发券详情信息：{},精准发券开始！",
                activityId,
                marketingJoinLevel,
                configAndCouponInfoDTOS);

        Integer pageNum = NumberUtils.INTEGER_ZERO;
        Integer pageSize = 2000;
        while (true) {
            List<String> customerIds;
            if (MarketingJoinLevel.SPECIFY_CUSTOMER.equals(marketingJoinLevel)) {
                // 指定用户
                CouponMarketingCustomerScopePageRequest customerScopePageRequest = new CouponMarketingCustomerScopePageRequest();
                customerScopePageRequest.setActivityId(configAndCouponInfoDTOS.get(0).getActivityId());
                customerScopePageRequest.setPageNum(pageNum);
                customerScopePageRequest.setPageSize(pageSize);
                List<CouponMarketingCustomerScope> customerScopes =
                        couponMarketingCustomerScopeService.page(customerScopePageRequest).getContent();
                customerIds =
                        customerScopes.stream()
                                .map(CouponMarketingCustomerScope::getCustomerId)
                                .collect(Collectors.toList());
            } else if (MarketingJoinLevel.ALL_CUSTOMER.equals(marketingJoinLevel)) {
                // 全平台
                CustomerListCustomerIdByPageableRequest request =
                        new CustomerListCustomerIdByPageableRequest();
                request.setPageSize(pageSize);
                request.setPageNum(pageNum);
                customerIds =
                        customerQueryProvider
                                .listCustomerIdByPageable(request)
                                .getContext()
                                .getCustomerIds();
            } else if (MarketingJoinLevel.ALL_LEVEL.equals(marketingJoinLevel)) {
                // 全店铺
                StoreCustomerRelaListCustomerIdByStoreIdRequest request =
                        new StoreCustomerRelaListCustomerIdByStoreIdRequest();
                request.setStoreId(couponActivityVO.getStoreId());
                request.setPageSize(pageSize);
                request.setPageNum(pageNum);
                customerIds =
                        storeCustomerQueryProvider
                                .listCustomerIdByStoreId(request)
                                .getContext()
                                .getCustomerIds();
            } else if (MarketingJoinLevel.LEVEL_LIST.equals(marketingJoinLevel)) {
                DefaultFlag joinLevelType = couponActivityVO.getJoinLevelType();
                List<Long> storeLevelIds =
                        Splitter.on(",")
                                .trimResults()
                                .splitToList(couponActivityVO.getJoinLevel())
                                .stream()
                                .map(Long::valueOf)
                                .collect(Collectors.toList());
                if (DefaultFlag.NO == joinLevelType) {
                    StoreCustomerRelaListCustomerIdByStoreIdRequest request =
                            new StoreCustomerRelaListCustomerIdByStoreIdRequest();
                    request.setStoreId(couponActivityVO.getStoreId());
                    request.setStoreLevelIds(storeLevelIds);
                    request.setPageSize(pageSize);
                    request.setPageNum(pageNum);
                    customerIds =
                            storeCustomerQueryProvider
                                    .listCustomerIdByStoreId(request)
                                    .getContext()
                                    .getCustomerIds();
                } else {
                    CustomerListCustomerIdByPageableRequest request =
                            new CustomerListCustomerIdByPageableRequest();
                    request.setPageSize(pageSize);
                    request.setPageNum(pageNum);
                    request.setCustomerLevelIds(storeLevelIds);
                    customerIds =
                            customerQueryProvider
                                    .listCustomerIdByPageable(request)
                                    .getContext()
                                    .getCustomerIds();
                }
            } else {
                log.info(
                        "根据优惠券活动ID：{},发券类型：{}，发券类型不符合要求，请检查活动配置相关信息",
                        activityId,
                        marketingJoinLevel);
                break;
            }
            if (CollectionUtils.isEmpty(customerIds)) {
                log.info("根据优惠券活动ID：{},发券类型：{}，已查询不到用户，发券成功", activityId, marketingJoinLevel);
                break;
            }
            log.info(
                    "根据优惠券活动ID：{},发券类型：{}，发放指定用户ID集合详情信息：{}，开始发券！",
                    activityId,
                    marketingJoinLevel,
                    customerIds);
            couponCodeService.precisionVouchers(customerIds, configAndCouponInfoDTOS);
            pageNum++;
        }
    }

    private CouponActivityDetailByActivityIdResponse getByActivityId(
            String activityId, String scanVersion) {
        CouponActivityVO couponActivityVO = null;
        List<CouponActivityConfigVO> couponActivityConfigList = null;
        List<CouponInfoVO> couponInfoVOList = null;
        // 1、查询活动基本信息
        CouponActivity couponActivity = couponActivityService.getCouponActivityByPk(activityId);
        if (Objects.nonNull(couponActivity)
                && couponActivity.getScanVersion().equals(scanVersion)) {
            couponActivityVO = KsBeanUtil.convert(couponActivity, CouponActivityVO.class);
        }
        // 2、查询关联优惠券信息
        List<CouponActivityConfig> couponActivityConfigs =
                couponActivityConfigService.queryByActivityId(activityId);
        if (CollectionUtils.isNotEmpty(couponActivityConfigs)) {
            couponActivityConfigList =
                    KsBeanUtil.convert(couponActivityConfigs, CouponActivityConfigVO.class);
            // 3、优惠券信息
            List<String> couponIds =
                    couponActivityConfigs.stream()
                            .map(CouponActivityConfig::getCouponId)
                            .collect(Collectors.toList());
            List<CouponInfo> couponInfoList = couponInfoService.queryByIds(couponIds);
            if (CollectionUtils.isNotEmpty(couponInfoList)) {
                couponInfoVOList = KsBeanUtil.convert(couponInfoList, CouponInfoVO.class);
            }
        }
        return new CouponActivityDetailByActivityIdResponse(
                couponActivityVO, couponActivityConfigList, couponInfoVOList);
    }
}
