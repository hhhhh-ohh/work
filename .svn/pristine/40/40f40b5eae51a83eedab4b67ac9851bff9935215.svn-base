package com.wanmi.sbc.job.scantask.impl;

import com.wanmi.sbc.job.scantask.ScanAndSendTask;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityByStartTimeRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityGetDetailByIdAndStoreIdRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityByStartTimeResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityVO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author edz
 * @className PrecisionVouchersTask
 * @description Bob
 * @date 2021/9/9 17:04
 */
@Service
public class PrecisionVouchersTask implements ScanAndSendTask {
    @Autowired private CouponActivityQueryProvider couponActivityQueryProvider;

    @Autowired private MqSendProvider mqSendProvider;

    @Value("${scan-task.within-time}")
    private int withinTime;

    @Async
    @Override
    public void scanAndSendMQ() {
        CouponActivityByStartTimeResponse response =
                couponActivityQueryProvider
                        .getActivityByStartTime(
                                CouponActivityByStartTimeRequest.builder().minute(withinTime).build())
                        .getContext();
        List<CouponActivityVO> couponActivityVOS = response.getCouponActivityVOList();
        couponActivityVOS.forEach(
                couponActivityVO -> {
                    MqSendDelayDTO dto = new MqSendDelayDTO();
                    Duration duration =
                            Duration.between(LocalDateTime.now(), couponActivityVO.getStartTime());
                    dto.setDelayTime(duration.toMillis());
                    dto.setData(
                            CouponActivityGetDetailByIdAndStoreIdRequest.builder()
                                    .id(couponActivityVO.getActivityId())
                                    .storeId(couponActivityVO.getStoreId())
                                    .scanVersion(couponActivityVO.getScanVersion())
                                    .build());
                    dto.setTopic(ProducerTopic.PRECISION_VOUCHERS);
                    mqSendProvider.sendDelay(dto);
                });
    }
}
