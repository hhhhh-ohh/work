package com.wanmi.sbc.coupon;

import com.google.common.base.Splitter;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponActivityProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityAddRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityInitRequest;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponActivityDTO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityDisabledTimeRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityModifyRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityDetailResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityDisabledTimeResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityModifyResponse;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Tag(name = "CouponActivityController", description = "优惠券活动 API")
@RestController
@Validated
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

    @Value("${scan-task.within-time}")
    private int withinTime;

    /**
     * 新增活动
     *
     * @param couponActivityAddRequest
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "新增活动")
    @MultiSubmit
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResponse add(@Valid @RequestBody CouponActivityAddRequest couponActivityAddRequest) {
        couponActivityAddRequest.setPlatformFlag(DefaultFlag.NO);
        couponActivityAddRequest.setCreatePerson(commonUtil.getOperatorId());
        couponActivityAddRequest.setStoreId(commonUtil.getStoreId());
        if (StoreType.O2O == commonUtil.getStoreType()) {
            couponActivityAddRequest.setPluginType(PluginType.O2O);
        }
        //设置是否平台等级
        couponActivityAddRequest.setJoinLevelType(commonUtil.getCustomerLevelType());
        couponActivityAddRequest.setWithinTime(withinTime);
        BaseResponse<CouponActivityDetailResponse> response =couponActivityProvider.add(couponActivityAddRequest);

        //记录操作日志
        if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
            operateLogMQUtil.convertAndSend("营销", "创建优惠券活动", "优惠券活动：" + couponActivityAddRequest.getActivityName());
            CouponActivityVO couponActivity  = response.getContext().getCouponActivity();
            EsCouponActivityDTO esCouponActivityDTO = KsBeanUtil.convert(couponActivity, EsCouponActivityDTO.class);
            List<String> joinLevels = Splitter.on(",").trimResults().splitToList(couponActivity.getJoinLevel());
            esCouponActivityDTO.setJoinLevels(joinLevels);
            esCouponActivityProvider.add(new EsCouponActivityAddRequest(esCouponActivityDTO));
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改活动
     *
     * @param couponActivityModifyRequest
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "修改活动")
    @MultiSubmit
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public BaseResponse modify(@Valid @RequestBody CouponActivityModifyRequest couponActivityModifyRequest) {
        couponActivityModifyRequest.setPlatformFlag(DefaultFlag.NO);
        couponActivityModifyRequest.setUpdatePerson(commonUtil.getOperatorId());
        couponActivityModifyRequest.setStoreId(commonUtil.getStoreId());
        couponActivityModifyRequest.setWithinTime(withinTime);
        if (StoreType.O2O == commonUtil.getStoreType()) {
            couponActivityModifyRequest.setPluginType(PluginType.O2O);
        }
        CouponActivityVO couponActivity = new CouponActivityVO();
        if (Objects.nonNull(couponActivityModifyRequest.getStartTime())
                && LocalDateTime.now().isAfter(couponActivityModifyRequest.getStartTime())
                && DefaultFlag.YES.equals(couponActivityModifyRequest.getPauseFlag())) {
            //已开始且暂停中活动,可修改
            if (CouponActivityType.ALL_COUPONS.equals(couponActivityModifyRequest.getCouponActivityType())
                    || CouponActivityType.STORE_COUPONS.equals(couponActivityModifyRequest.getCouponActivityType())) {
                //supplier端注册赠券、全场赠券可修改
                BaseResponse<CouponActivityModifyResponse> response = couponActivityProvider.modifyPauseCouponActivity(couponActivityModifyRequest);
                if (Objects.isNull(response.getContext())){
                    return response;
                }
                couponActivity = response.getContext().getCouponActivity();
            }

        } else {
            couponActivity = couponActivityProvider.modify(couponActivityModifyRequest).getContext().getCouponActivity();
        }

        operateLogMQUtil.convertAndSend("营销", "编辑优惠券活动", "优惠券活动：" + couponActivityModifyRequest.getActivityName());

        EsCouponActivityInitRequest initRequest = new EsCouponActivityInitRequest();
        initRequest.setIdList(Arrays.asList(couponActivity.getActivityId()));
        esCouponActivityProvider.init(initRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询活动（注册赠券活动、进店赠券活动）不可用的时间范围
     *
     * @return
     */
    @Operation(summary = "查询活动（注册赠券活动、进店赠券活动）不可用的时间范围")
    @Parameters({
            @Parameter(name = "couponActivityType", description = "活动类型", required = true),
            @Parameter(name = "activityId", description = "优惠券活动Id", required = true)
    })
    @RequestMapping(value = "/activity-disable-time/{couponActivityType}/{activityId}", method = RequestMethod.GET)
    public BaseResponse<List<CouponActivityDisabledTimeVO>> queryActivityEnableTime(@PathVariable @NotNull int
                                                                                            couponActivityType,
                                                                                    @PathVariable String activityId) {
        CouponActivityDisabledTimeRequest request = new CouponActivityDisabledTimeRequest();
        request.setStoreId(commonUtil.getStoreId());
        request.setCouponActivityType(CouponActivityType.fromValue(couponActivityType));
        if (!Constants.STR_MINUS_1.equals(activityId)) {
            request.setActivityId(activityId);
        }
        CouponActivityDisabledTimeResponse response = couponActivityQueryProvider.queryActivityEnableTime(request)
                .getContext();
        return BaseResponse.success(response.getCouponActivityDisabledTimeVOList());
    }
}
