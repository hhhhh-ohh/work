package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.account.api.provider.credit.CustomerApplyRecordProvider;
import com.wanmi.sbc.account.api.request.credit.CreditApplyQueryRequest;
import com.wanmi.sbc.customer.api.provider.mqconsumer.CustomerMqConsumerProvider;
import com.wanmi.sbc.customer.api.request.mqconsumer.CustomerMqConsumerRequest;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerProvider;
import com.wanmi.sbc.elastic.api.provider.customerInvoice.EsCustomerInvoiceProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerInitRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoicePageRequest;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardDetailProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeReturnByIdRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardCustomerCancelRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardCancelResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author xufeng
 * @className CustomerLogoutService
 * @description 用户注销延迟MQ生产者消息通道
 * @date 2022/4/01 10:12 上午
 */
@Slf4j
@Service
public class CustomerLogoutService {

    @Autowired
    private CustomerMqConsumerProvider customerMqConsumerProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @Autowired
    private EsDistributionCustomerProvider esDistributionCustomerProvider;

    @Autowired
    private CouponCodeProvider couponCodeProvider;

    @Autowired
    private CustomerApplyRecordProvider customerApplyRecordProvider;

    @Autowired
    private EsCustomerInvoiceProvider esCustomerInvoiceProvider;

    @Autowired
    private GiftCardDetailProvider giftCardDetailProvider;

    @Bean
    public Consumer<Message<String>> mqCustomerLogoutService() {
        return message->{
            String json = message.getPayload();
            log.info("用户注销mq执行开始...");
            CustomerMqConsumerRequest customerMqConsumerRequest = new CustomerMqConsumerRequest();
            customerMqConsumerRequest.setMqContentJson(json);
            boolean loggingOffFlag = (boolean) customerMqConsumerProvider.customerLogout(customerMqConsumerRequest).getContext();
            log.info("用户注销mq,loggingOffFlag:{}", loggingOffFlag);
            if (loggingOffFlag){
                // 优惠券过期时间更新
                couponCodeProvider.modifyByCustomerId(CouponCodeReturnByIdRequest.builder().customerId(json).build());
                // 授信待审核驳回
                customerApplyRecordProvider.modifyByCustomerId(CreditApplyQueryRequest.builder().customerId(json).build());
                //同步ES
                esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder()
                        .idList(Collections.singletonList(json)).build());
                esDistributionCustomerProvider.init(EsDistributionCustomerInitRequest.builder().customerId(json).build());
                EsCustomerInvoicePageRequest esCustomerInvoicePageRequest = new EsCustomerInvoicePageRequest();
                esCustomerInvoicePageRequest.setCustomerIds(Collections.singletonList(json));
                esCustomerInvoiceProvider.init(esCustomerInvoicePageRequest);
                // 注销用户礼品卡
                GiftCardCancelResultResponse cancelCustomerCardRes =
                        giftCardDetailProvider.cancelCustomerCard(GiftCardCustomerCancelRequest.builder().customerId(json).build()).getContext();
                if (Objects.nonNull(cancelCustomerCardRes) && CollectionUtils.isNotEmpty(cancelCustomerCardRes.getCancelErrorGiftCardNoList())) {
                    log.info("注销用户礼品卡，部分礼品卡注销失败：{}", cancelCustomerCardRes.getCancelErrorGiftCardNoList());
                }
            }
            log.info("用户注销mq执行结束...");
        };
    }
}
