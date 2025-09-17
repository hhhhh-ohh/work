package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.order.api.provider.wxpayuploadshippinginfo.WxPayUploadShippingInfoProvider;
import com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo.WxPayUploadShippingInfoSyncRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className GoodsSyncNoticeService
 * @description 商品临时表同步完成通知
 * @date 2021/8/18 11:10 上午
 */
@Slf4j
@Service
public class WxPayShippingService {

    @Autowired private WxPayUploadShippingInfoProvider wxPayUploadShippingInfoProvider;

    @Bean
    public Consumer<Message<String>> mqWxPayShippingService() {
        return message->{
            String json = message.getPayload();
            log.info("=============== 小程序支付物流信息上传消费MQ处理start,参数：{} ===============", json);
            WxPayUploadShippingInfoSyncRequest request = JSONObject.parseObject(json, WxPayUploadShippingInfoSyncRequest.class);
            wxPayUploadShippingInfoProvider.syncByTradeIdList(request);
            log.info("=============== 小程序支付物流信息上传消费MQ处理end ===============");
        };
    }
}
