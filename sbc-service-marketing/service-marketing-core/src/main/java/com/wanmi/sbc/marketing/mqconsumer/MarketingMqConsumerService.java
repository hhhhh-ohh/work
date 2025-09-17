package com.wanmi.sbc.marketing.mqconsumer;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.coupon.CouponGroupAddRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityModifyStatisticsNumByIdRequest;
import com.wanmi.sbc.marketing.api.response.coupon.GetCouponGroupResponse;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivityConfig;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.repository.CouponInfoRepository;
import com.wanmi.sbc.marketing.coupon.service.CouponActivityConfigService;
import com.wanmi.sbc.marketing.coupon.service.CouponActivityService;
import com.wanmi.sbc.marketing.coupon.service.CouponCodeService;
import com.wanmi.sbc.marketing.grouponactivity.service.GrouponActivityConsumerService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lvzhenwei
 * @className MarketingMqConsumerService
 * @description mq消费方法处理
 * @date 2021/8/13 5:07 下午
 **/
@Slf4j
@Service
public class MarketingMqConsumerService {

    @Autowired
    private CouponActivityService couponActivityService;

    @Autowired
    private CouponActivityConfigService couponActivityConfigService;

    @Autowired
    private CouponInfoRepository couponInfoRepository;

    @Autowired
    private CouponCodeService couponCodeService;

    @Autowired
    private GrouponActivityConsumerService grouponActivityConsumerService;

    /**
     * @description 邀新注册-发放优惠券
     * @author  lvzhenwei
     * @date 2021/8/16 11:34 上午
     * @param json
     * @return void
     **/
    public void marketCouponInviteNewAdd(String json) {
        try {
            CouponGroupAddRequest request = JSONObject.parseObject(json, CouponGroupAddRequest.class);
            Boolean result = couponActivityService.sendCouponGroup(request);
            log.info("邀新注册-发放优惠券，是否成功 ? {}",Boolean.FALSE.equals(result) ? "失败" : "成功");
        } catch (Exception e) {
            log.error("邀新注册-发放优惠券，发生异常! param={}", json, e);
        }
    }

    /**
     * @description 发放优惠券
     * @author  lvzhenwei
     * @date 2021/8/16 11:34 上午
     * @param msg
     * @return void
     **/
    public void levelRightsIssueCoupons(String msg) {
        // 解析消息数据
        Map<String, Object> response = JSONObject.parseObject(msg);
        String customerId = response.get("customerId").toString();
        String activityId = response.get("activityId").toString();
        String recordId = "";
        if (response.containsKey("recordId")) {
            recordId = response.get("recordId").toString();
        }
        // 查询券礼包权益关联的优惠券活动配置列表
        List<CouponActivityConfig> couponActivityConfigList = couponActivityConfigService.queryByActivityId(activityId);
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
        // 批量发放优惠券
        couponCodeService.sendBatchCouponCodeByCustomer(getCouponGroupResponse, customerId, activityId,recordId);
    }

    /**
     * @description 根据不同拼团状态更新不同的统计数据（已成团、待成团、团失败人数）
     * @author  lvzhenwei
     * @date 2021/8/17 1:51 下午
     * @param json
     * @return void
     **/
    public void  grouponStatisticsNum(String json) {
        try {
            GrouponActivityModifyStatisticsNumByIdRequest request = JSONObject.parseObject(json, GrouponActivityModifyStatisticsNumByIdRequest.class);
            Integer result = grouponActivityConsumerService.updateStatisticsNumByGrouponActivityId(request.getGrouponActivityId(),request.getGrouponNum(),request.getGrouponOrderStatus());
            log.info("根据不同拼团状态更新不同的统计数据（已成团、待成团、团失败人数），参数详细信息：{}，是否成功 ? {}",json,result > 0 ? "成功" : "失败" );
        } catch (Exception e) {
            log.error("根据不同拼团状态更新不同的统计数据（已成团、待成团、团失败人数），发生异常! param={}", json, e);
        }
    }
}
