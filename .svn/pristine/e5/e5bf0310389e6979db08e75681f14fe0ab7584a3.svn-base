package com.wanmi.sbc.coupon;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.coupon.service.CouponActivityService;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.distribution.DistributionCacheService;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponActivityProvider;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponActivityQueryProvider;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponScopeProvider;
import com.wanmi.sbc.elastic.api.request.coupon.*;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponActivityVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityDetailResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityGetByIdResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
@Tag(name = "CouponActivityBaseController", description = "S2B 管理端公用-优惠券活动管理API")
@RequestMapping("/coupon-activity")
@Validated
public class CouponActivityBaseController {

    @Autowired
    private CouponActivityQueryProvider couponActivityQueryProvider;

    @Autowired
    private CouponActivityProvider couponActivityProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsCouponActivityQueryProvider esCouponActivityQueryProvider;

    @Autowired
    private EsCouponActivityProvider esCouponActivityProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private CouponActivityService couponActivityService;

    @Autowired
    private EsCouponScopeProvider esCouponScopeProvider;

    @Autowired
    private DistributionCacheService distributionCacheService;

    /**
     * 开始活动
     *
     * @param id
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "开始活动")
    @Parameter(name = "id", description = "优惠券活动Id", required = true)
    @RequestMapping(value = "/start/{id}", method = RequestMethod.PUT)
    public BaseResponse startActivity(@PathVariable String id) {
        if (StringUtils.isBlank(id)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        couponActivityProvider.start(new CouponActivityStartByIdRequest(id));

        esCouponActivityProvider.start(new EsCouponActivityStartByIdRequest(id));

        CouponActivityGetByIdRequest queryRequest = new CouponActivityGetByIdRequest();
        queryRequest.setId(id);
        CouponActivityGetByIdResponse response = couponActivityQueryProvider.getById(queryRequest).getContext();

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "开始优惠券活动",
                "优惠券活动：" + (nonNull(response) ? response.getActivityName() : ""));

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 暂停活动
     *
     * @param id
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "暂停活动")
    @Parameter(name = "id", description = "优惠券活动Id", required = true)
    @RequestMapping(value = "/pause/{id}", method = RequestMethod.PUT)
    public BaseResponse pauseActivity(@PathVariable String id) {
        if (StringUtils.isBlank(id)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        couponActivityProvider.pause(new CouponActivityPauseByIdRequest(id));

        esCouponActivityProvider.pause(new EsCouponActivityPauseByIdRequest(id));

        CouponActivityGetByIdRequest queryRequest = new CouponActivityGetByIdRequest();
        queryRequest.setId(id);
        CouponActivityGetByIdResponse response = couponActivityQueryProvider.getById(queryRequest).getContext();

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "暂停优惠券活动",
                "优惠券活动：" + (nonNull(response) ? response.getActivityName() : ""));

        return BaseResponse.SUCCESSFUL();
    }

    @GlobalTransactional
    @Operation(summary = "删除活动")
    @Parameter(name = "id", description = "优惠券活动Id", required = true)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteActivity(@PathVariable String id) {
        if (StringUtils.isBlank(id)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CouponActivityGetByIdRequest queryRequest = new CouponActivityGetByIdRequest();
        queryRequest.setId(id);
        CouponActivityGetByIdResponse response = couponActivityQueryProvider.getById(queryRequest).getContext();
        //越权校验
        commonUtil.checkStoreId(response.getStoreId());
        couponActivityProvider.deleteByIdAndOperatorId(new CouponActivityDeleteByIdAndOperatorIdRequest(id,
                commonUtil.getOperatorId()));

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "删除优惠券活动",
                "优惠券活动：" + (nonNull(response.getActivityName()) ? response.getActivityName() : ""));


        esCouponActivityProvider.deleteById(new EsCouponActivityDeleteByIdRequest(id));
        //删除scope缓存
        esCouponScopeProvider.deleteByActivityId(new EsCouponScopeDeleteByActivityIdRequest(id));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 获取活动详情
     *
     * @param id
     * @return
     */
    @Operation(summary = "获取活动详情")
    @Parameter(name = "id", description = "优惠券活动Id", required = true)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BaseResponse<CouponActivityDetailResponse> getActivityDetail(@PathVariable String id) {
        if (StringUtils.isBlank(id)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CouponActivityDetailResponse response =
                couponActivityQueryProvider
                        .getDetailByIdAndStoreId(
                                CouponActivityGetDetailByIdAndStoreIdRequest.builder()
                                        .id(id)
                                        .storeId(commonUtil.getStoreId())
                                        .build())
                        .getContext();
        if (response.getCouponActivity().getStoreId()>0){
            commonUtil.checkStoreId(response.getCouponActivity().getStoreId());
            StoreVO storeVO = storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder()
                            .storeId(response.getCouponActivity().getStoreId())
                            .build())
                    .getContext()
                    .getStoreVO();
            response.getCouponActivity().setStoreName(storeVO.getStoreName());
        }


        //邀新赠券
        if (Objects.nonNull(response.getCouponActivity())
                && CouponActivityType.DISTRIBUTE_COUPON.equals(response.getCouponActivity().getCouponActivityType())
                && CollectionUtils.isNotEmpty(response.getCouponInfoList())) {
            DistributionSettingGetResponse setting = distributionCacheService.querySettingCache();
            //如果开启优惠券赠送
            if (DefaultFlag.YES.equals(setting.getDistributionSetting().getRewardCouponFlag())) {
                List<String> couponInfos = distributionCacheService.getCouponInfos().stream()
                        .map(CouponInfoVO::getCouponId).collect(Collectors.toList());
                response.setCouponInfoList(response.getCouponInfoList().stream()
                        .filter(i -> couponInfos.contains(i.getCouponId())).collect(Collectors.toList()));
            } else {
                response.setCouponInfoList(Collections.emptyList());
            }
        }
        return BaseResponse.success(response);
    }

    /**
     * 活动列表分页
     *
     * @param request
     * @return
     */
    @Operation(summary = "优惠券活动列表分页")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<EsCouponActivityVO>> page(@RequestBody EsCouponActivityPageRequest request) {
        request.setStoreId(commonUtil.getStoreId());
        StoreType storeType = commonUtil.getStoreType();
        couponActivityService.populateRequest(request, storeType);
        request.setSupplierFlag(DefaultFlag.NO);
        MicroServicePage<EsCouponActivityVO> response =
                esCouponActivityQueryProvider.page(request).getContext().getCouponActivityVOPage();
        return BaseResponse.success(response);
    }

    /**
     * @description 魔方优惠券活动分页查询(boss/supplier)
     * @author  EDZ
     * @date 2021/6/11 11:26
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.common.base.MicroServicePage<com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponActivityVO>>
     **/
    @Operation(summary = "魔方优惠券活动分页查询(boss/supplier)")
    @RequestMapping(value = "/magic/coupon-activity/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<EsCouponActivityVO>> magicCouponActivityPage(
            @RequestBody EsMagicCouponActivityPageRequest request) {
        Platform platform = commonUtil.getOperator().getPlatform();
        switch (platform) {
            case PLATFORM:
                request.setStoreId(null);
                if (request.getPlatformFlag() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                break;
            case SUPPLIER:
                request.setStoreId(commonUtil.getStoreId());
                request.setPlatformFlag(null);
                break;
            default:
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        MicroServicePage<EsCouponActivityVO> response =
                esCouponActivityQueryProvider
                        .magicPage(request)
                        .getContext()
                        .getCouponActivityVOPage();
        return BaseResponse.success(response);
    }

    @GlobalTransactional
    @Operation(summary = "关闭活动")
    @Parameter(name = "id", description = "优惠券活动Id", required = true)
    @RequestMapping(value = "/close/{id}", method = RequestMethod.PUT)
    public BaseResponse closeActivity(@PathVariable String id) {
        if (StringUtils.isBlank(id)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        CouponActivityGetByIdRequest queryRequest = new CouponActivityGetByIdRequest();
        queryRequest.setId(id);
        CouponActivityGetByIdResponse response = couponActivityQueryProvider.getById(queryRequest).getContext();

        Long storeId = commonUtil.getOperator().getPlatform() == Platform.PLATFORM ? Constants.BOSS_DEFAULT_STORE_ID : commonUtil.getStoreId();
        //防止不同商户越权
        if (!Constants.BOSS_DEFAULT_STORE_ID.equals(storeId)){
            if(isNull(response) || !Objects.equals(storeId, response.getStoreId())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        couponActivityProvider.closeActivity(CouponActivityCloseRequest.builder().id(id).operatorId(commonUtil.getOperatorId()).build());

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "关闭优惠券活动",
                "优惠券活动：" + response.getActivityName());

        //更新es
        EsCouponActivityInitRequest initRequest = new EsCouponActivityInitRequest();
        initRequest.setIdList(Lists.newArrayList(id));
        esCouponActivityProvider.init(initRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
