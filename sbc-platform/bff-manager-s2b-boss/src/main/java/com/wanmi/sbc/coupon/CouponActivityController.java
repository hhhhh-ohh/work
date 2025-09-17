package com.wanmi.sbc.coupon;

import com.google.common.base.Splitter;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelListByCustomerLevelNameRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.api.response.store.ListStoreByNameResponse;
import com.wanmi.sbc.customer.bean.dto.MarketingCustomerLevelDTO;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.MarketingCustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponActivityProvider;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponActivityQueryProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityAddRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityPageRequest;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponActivityDTO;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponActivityVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityDisabledTimeRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityModifyRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityDetailResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityDisabledTimeResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityModifyResponse;
import com.wanmi.sbc.marketing.bean.constant.Constant;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityDisabledTimeVO;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;

import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Validated
@Tag(name = "CouponActivityController", description = "S2B 平台端-优惠券活动管理API")
@RequestMapping("/coupon-activity")
public class CouponActivityController {

    @Autowired
    private CouponActivityProvider couponActivityProvider;

    @Autowired
    private CouponActivityQueryProvider couponActivityQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsCouponActivityProvider esCouponActivityProvider;

    @Autowired
    private EsCouponActivityQueryProvider esCouponActivityQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Value("${scan-task.within-time}")
    private int withinTime;

    /**
     * 新增活动
     *
     * @param couponActivityAddRequest
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "新增优惠券活动")
    @MultiSubmit
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResponse add(@Valid @RequestBody CouponActivityAddRequest couponActivityAddRequest) {
        if (CouponActivityType.REGISTERED_COUPON.equals(couponActivityAddRequest.getCouponActivityType())) {
            if (StringUtils.isBlank(couponActivityAddRequest.getActivityTitle()) ||
                    StringUtils.isBlank(couponActivityAddRequest.getActivityDesc())) {
                return BaseResponse.builder().code(CommonErrorCodeEnum.K000009.getCode()).message("参数不正确").build();
            }
        }
        List<String> customerScopeIds = couponActivityAddRequest.getCustomerScopeIds();
        if (CollectionUtils.isNotEmpty(customerScopeIds)){
            Map<String, LogOutStatus> logOutStatusMap = customerCacheService.getLogOutStatus(customerScopeIds);
            boolean logOutFlag = customerScopeIds.stream().anyMatch(customerId -> {
                LogOutStatus logOutStatus = logOutStatusMap.get(customerId);
                return Objects.nonNull(logOutStatus)
                        && Objects.equals(logOutStatus, LogOutStatus.LOGGED_OUT);
            });
            if(logOutFlag){
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010037);
            }
        }

        couponActivityAddRequest.setPlatformFlag(DefaultFlag.YES);
        couponActivityAddRequest.setCreatePerson(commonUtil.getOperatorId());
        couponActivityAddRequest.setStoreId(Constant.BOSS_DEFAULT_STORE_ID);
        //设置是否平台等级
        couponActivityAddRequest.setJoinLevelType(DefaultFlag.YES);
        couponActivityAddRequest.setWithinTime(withinTime);
        BaseResponse<CouponActivityDetailResponse> response = couponActivityProvider.add(couponActivityAddRequest);
        //记录操作日志
        if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
            CouponActivityVO couponActivity = response.getContext().getCouponActivity();
            EsCouponActivityDTO esCouponActivityDTO = KsBeanUtil.convert(couponActivity, EsCouponActivityDTO.class);
            List<String> joinLevels = Splitter.on(",").trimResults().splitToList(couponActivity.getJoinLevel());
            esCouponActivityDTO.setJoinLevels(joinLevels);
            operateLogMQUtil.convertAndSend("营销", "创建优惠券活动", "优惠券活动：" + couponActivityAddRequest.getActivityName());
            esCouponActivityProvider.add(new EsCouponActivityAddRequest(esCouponActivityDTO));
        }

        return response;
    }

    /**
     * 修改活动
     *
     * @param couponActivityModifyRequest
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "修改优惠券活动")
    @MultiSubmit
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public BaseResponse modify(@Valid @RequestBody CouponActivityModifyRequest couponActivityModifyRequest) {
        couponActivityModifyRequest.setPlatformFlag(DefaultFlag.YES);
        couponActivityModifyRequest.setUpdatePerson(commonUtil.getOperatorId());
        couponActivityModifyRequest.setStoreId(Constant.BOSS_DEFAULT_STORE_ID);
        couponActivityModifyRequest.setWithinTime(withinTime);

        CouponActivityVO couponActivity = new CouponActivityVO();
        if (Objects.nonNull(couponActivityModifyRequest.getStartTime())
                && LocalDateTime.now().isAfter(couponActivityModifyRequest.getStartTime())
                && DefaultFlag.YES.equals(couponActivityModifyRequest.getPauseFlag())) {
            //已开始且暂停中活动,可修改
            if (CouponActivityType.ALL_COUPONS.equals(couponActivityModifyRequest.getCouponActivityType())
                    || CouponActivityType.REGISTERED_COUPON.equals(couponActivityModifyRequest.getCouponActivityType())
                    || CouponActivityType.ENTERPRISE_REGISTERED_COUPON.equals(couponActivityModifyRequest.getCouponActivityType())) {
                //boss端注册赠券、企业注册赠券、全场赠券可修改
                BaseResponse<CouponActivityModifyResponse> response = couponActivityProvider.modifyPauseCouponActivity(couponActivityModifyRequest);
                if (Objects.isNull(response.getContext())){
                    return response;
                }
                couponActivity = response.getContext().getCouponActivity();
            }

        } else {
            couponActivity = couponActivityProvider.modify(couponActivityModifyRequest).getContext().getCouponActivity();
        }

        EsCouponActivityDTO esCouponActivityDTO = KsBeanUtil.convert(couponActivity, EsCouponActivityDTO.class);
        List<String> joinLevels = Splitter.on(",").trimResults().splitToList(couponActivity.getJoinLevel());
        esCouponActivityDTO.setJoinLevels(joinLevels);
        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "编辑优惠券活动", "优惠券活动：" + couponActivityModifyRequest.getActivityName());
        esCouponActivityProvider.add(new EsCouponActivityAddRequest(esCouponActivityDTO));
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 查询活动（注册赠券活动、进店赠券活动）不可用的时间范围
     *
     * @return
     */
    @Operation(summary = "查询活动（注册赠券活动、进店赠券活动）不可用的时间范围")
    @Parameters({
            @Parameter(name = "couponActivityType", description =
                    "优惠券活动类型，0全场赠券，1指定赠券，2进店赠券，3注册赠券， 4权益赠券",
                    required = true),
            @Parameter(name = "activityId", description = "优惠券活动Id",
                    required = true)
    })
    @RequestMapping(value = "/activity-disable-time/{couponActivityType}/{activityId}", method = RequestMethod.GET)
    public BaseResponse<List<CouponActivityDisabledTimeVO>> queryActivityEnableTime(@PathVariable @NotNull int
                                                                                            couponActivityType,
                                                                                    @PathVariable String activityId) {
        CouponActivityDisabledTimeRequest request = new CouponActivityDisabledTimeRequest();
        request.setCouponActivityType(CouponActivityType.fromValue(couponActivityType));
        if (!Constants.STR_MINUS_1.equals(activityId)) {
            request.setActivityId(activityId);
        }
        request.setStoreId(Constant.BOSS_DEFAULT_STORE_ID);
        CouponActivityDisabledTimeResponse response = couponActivityQueryProvider.queryActivityEnableTime(request)
                .getContext();
        return BaseResponse.success(response.getCouponActivityDisabledTimeVOList());
    }

    /**
     * 商家活动列表分页
     *
     * @param request
     * @return
     */
    @Operation(summary = "商家活动列表分页")
    @RequestMapping(value = "/supplier/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<EsCouponActivityVO>> page(@RequestBody EsCouponActivityPageRequest request) {
        request.setSupplierFlag(DefaultFlag.YES);
        MicroServicePage<EsCouponActivityVO> response = new MicroServicePage<>();
        if (StringUtils.isNotBlank(request.getStoreName())){
            ListStoreByNameResponse listStoreByNameResponse = storeQueryProvider
                    .listByName(ListStoreByNameRequest
                            .builder()
                            .storeName(request.getStoreName())
                            .storeType(StoreType.SUPPLIER)
                            .build())
                    .getContext();
            if (CollectionUtils.isEmpty(listStoreByNameResponse.getStoreVOList())){
                return BaseResponse.success(response);
            }
            request.setStoreIds(listStoreByNameResponse.getStoreVOList()
                            .stream()
                            .map(StoreVO::getStoreId)
                            .collect(Collectors.toList()));
        }
        response = esCouponActivityQueryProvider.page(request).getContext().getCouponActivityVOPage();

        //获取会员名称
        for (EsCouponActivityVO esCouponActivityVO : response.getContent()) {

            List<MarketingCustomerLevelDTO> customerLevelDTOList = new ArrayList<>();
            MarketingCustomerLevelDTO dto = new MarketingCustomerLevelDTO();
            dto.setStoreId(esCouponActivityVO.getStoreId());
            dto.setJoinLevel(esCouponActivityVO.getJoinLevel());
            customerLevelDTOList.add(dto);

            List<MarketingCustomerLevelVO> marketingCustomerLevelVOList = customerLevelQueryProvider.
                    listByCustomerLevelName(new CustomerLevelListByCustomerLevelNameRequest(customerLevelDTOList)).
                    getContext().getCustomerLevelVOList();

            esCouponActivityVO.setJoinLevelName(marketingCustomerLevelVOList.get(0).getLevelName());
            esCouponActivityVO.setStoreName(marketingCustomerLevelVOList.get(0).getStoreName());
        }
        return BaseResponse.success(response);
    }
}