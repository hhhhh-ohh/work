package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityGetDetailByIdAndStoreIdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author edz
 * @className PrecisionVouchersService
 * @description TODO
 * @date 2021/9/10 10:14
 */
@Service
@Slf4j
public class PrecisionVouchersTaskService {

    @Autowired private CouponActivityProvider couponActivityProvider;

    @Bean
    public Consumer<Message<String>> mqPrecisionVouchersTaskService() {
        return message->{
            String json = message.getPayload();
            CouponActivityGetDetailByIdAndStoreIdRequest request =
                    JSON.parseObject(json, CouponActivityGetDetailByIdAndStoreIdRequest.class);
            log.info("mq consumer：precision vouchers id {}", request.getId());
            couponActivityProvider.precisionVouchers(request);
            log.info("mq consumer end");
        };
    }
}
