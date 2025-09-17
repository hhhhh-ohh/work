package com.wanmi.sbc.marketing.provider.impl.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.TaskBizType;
import com.wanmi.sbc.common.enums.TaskResult;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityCloseRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityDeleteByIdAndOperatorIdRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityGetDetailByIdAndStoreIdRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityModifyRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityPauseByIdRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityStartByIdRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponGroupAddRequest;
import com.wanmi.sbc.marketing.api.request.coupon.GetCouponGroupRequest;
import com.wanmi.sbc.marketing.api.request.coupon.GetRegisterCouponRequest;
import com.wanmi.sbc.marketing.api.request.coupon.SendCouponGroupRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityDetailResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityModifyResponse;
import com.wanmi.sbc.marketing.api.response.coupon.GetRegisterOrStoreCouponResponse;
import com.wanmi.sbc.marketing.api.response.coupon.SendCouponResponse;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.coupon.service.CouponActivityService;
import com.wanmi.sbc.marketing.coupon.service.PrecisionVouchersService;
import com.wanmi.sbc.setting.api.provider.TaskLogProvider;
import com.wanmi.sbc.setting.api.request.TaskLogAddRequest;
import com.wanmi.sbc.setting.api.request.TaskLogCountByParamsRequest;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-24
 */
@Slf4j
@Validated
@RestController
public class CouponActivityController implements CouponActivityProvider {

    @Autowired
    private CouponActivityService couponActivityService;

    @Autowired
    private PrecisionVouchersService precisionVouchersService;

    @Autowired
    private TaskLogProvider taskLogProvider;

    /**
     * 创建活动
     *
     * @param request 创建活动请求结构 {@link CouponActivityAddRequest}
     * @return
     */
    @Override
    public BaseResponse<CouponActivityDetailResponse> add(@RequestBody @Valid CouponActivityAddRequest request) {
        // 权益赠券活动没有开始结束时间
        if (request.getCouponActivityType() == CouponActivityType.RIGHTS_COUPON) {
            request.setStartTime(null);
            request.setEndTime(null);
        }
        if (Objects.nonNull(request.getStartTime())) {
            couponActivityService.checkStartTime(request.getCouponActivityType(), request.getStartTime());
        }
        return BaseResponse.success(KsBeanUtil.convert(couponActivityService.addCouponActivity(request), CouponActivityDetailResponse.class));
    }

    /**
     * 编辑活动
     *
     * @param request 编辑活动请求结构 {@link CouponActivityModifyRequest}
     * @return
     */
    @Override
    public BaseResponse<CouponActivityModifyResponse> modify(@RequestBody @Valid CouponActivityModifyRequest request) {
        // 权益赠券活动没有开始结束时间
        if (request.getCouponActivityType() == CouponActivityType.RIGHTS_COUPON) {
            request.setStartTime(null);
            request.setEndTime(null);
        }
        if (Objects.nonNull(request.getStartTime())){
            couponActivityService.checkStartTime(request.getCouponActivityType(), request.getStartTime());
        }
        CouponActivityModifyResponse response = couponActivityService.modifyCouponActivity(request);
        return BaseResponse.success(response);
    }

    /**
     * 开始活动请求结构
     *
     * @param request 开始活动请求结构 {@link CouponActivityStartByIdRequest}
     * @return
     */
    @Override
    public BaseResponse start(@RequestBody @Valid CouponActivityStartByIdRequest request) {
        couponActivityService.startActivity(request.getId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 暂停活动请求结构
     *
     * @param request 暂停活动请求结构 {@link CouponActivityPauseByIdRequest}
     * @return
     */
    @Override
    public BaseResponse pause(@RequestBody @Valid CouponActivityPauseByIdRequest request) {
        couponActivityService.pauseActivity(request.getId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除活动
     *
     * @param request 删除活动请求结构 {@link CouponActivityDeleteByIdAndOperatorIdRequest}
     * @return
     */
    @Override
    public BaseResponse deleteByIdAndOperatorId(@RequestBody @Valid CouponActivityDeleteByIdAndOperatorIdRequest request) {
        couponActivityService.deleteActivity(request.getId(), request.getOperatorId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 领取一组优惠券 （注册活动或者进店活动）
     * 用户注册成功或者进店后，发放赠券
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<GetRegisterOrStoreCouponResponse> getCouponGroup(@RequestBody @Valid GetCouponGroupRequest request) {
        GetRegisterOrStoreCouponResponse response = couponActivityService.getCouponGroup(request.getCustomerId(), request.getType(), request.getStoreId());
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GetRegisterOrStoreCouponResponse> queryRegisterCoupon(@Valid GetRegisterCouponRequest request) {
        GetRegisterOrStoreCouponResponse response = couponActivityService.queryRegisterCoupon(request);
        return BaseResponse.success(response);
    }


    /**
     * 领取一组优惠券 （指定优惠券活动）
     * 邀新注册奖励一组优惠券
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<SendCouponResponse> sendCouponGroup(@RequestBody @Valid SendCouponGroupRequest request) {
        SendCouponResponse response = couponActivityService.sendCouponGroup(request);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse addCouponGroup(@RequestBody @Valid CouponGroupAddRequest request) {
        Boolean result = couponActivityService.sendCouponGroup(request);
        return BaseResponse.success(result);
    }

    @Override
    public BaseResponse closeActivity(@RequestBody @Valid CouponActivityCloseRequest request) {
        couponActivityService.closeActivity(request.getId(), request.getOperatorId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    @Transactional
    public BaseResponse precisionVouchers(@RequestBody @Valid CouponActivityGetDetailByIdAndStoreIdRequest request) {
        TaskLogCountByParamsRequest paramsRequest = new TaskLogCountByParamsRequest();
        paramsRequest.setBizId(request.getId());
        paramsRequest.setTaskBizType(TaskBizType.PRECISION_VOUCHERS);
        paramsRequest.setTaskResult(TaskResult.EXECUTE_FAIL);
        Long count = taskLogProvider.count(paramsRequest).getContext().getCount();
        // 失败超过3次就定为已扫描
        if(count >= Constants.THREE){
            couponActivityService.updateForSanType(request.getId(), Constants.TWO);
            return BaseResponse.SUCCESSFUL();
        }
        try {
            precisionVouchersService.execute(request);
        } catch (Exception e) {
            taskLogProvider.add(
                    TaskLogAddRequest.builder()
                            .bizId(request.getId())
                            .taskResult(TaskResult.EXECUTE_FAIL)
                            .taskBizType(TaskBizType.PRECISION_VOUCHERS)
                            .storeId(request.getStoreId())
                            .stackMessage(ExceptionUtils.getStackTrace(e))
                            .createTime(LocalDateTime.now())
                            .remarks("精准发券失败， activityId:" + request.getId() + ",version:" + request.getScanVersion())
                            .build());
            log.error("精准发券失败,活动ID：{}", request.getId(), e);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<CouponActivityModifyResponse> modifyPauseCouponActivity(@Valid CouponActivityModifyRequest request) {
        return BaseResponse.success(couponActivityService.modifyPauseCouponActivity(request));
    }
}
