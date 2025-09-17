package com.wanmi.sbc.mq.consumer.service;


import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.marketing.api.provider.drawactivity.DrawActivitySaveProvider;
import com.wanmi.sbc.marketing.api.provider.drawprize.DrawPrizeSaveProvider;
import com.wanmi.sbc.marketing.api.request.drawactivity.DrawActivityPauseByIdRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeByIdRequest;
import com.wanmi.sbc.marketing.bean.vo.DrawRecordVO;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * MQ消费者
 * @author: wwc
 * @createDate: 2021/4/16 9:28
 */
@Slf4j
@Service
public class DrawConsumerService {

    @Autowired
    private DrawActivitySaveProvider drawActivitySaveProvider;

    @Autowired
    private DrawPrizeSaveProvider drawPrizeSaveProvider;

    @Bean
    public Consumer<Message<String>> mqDrawConsumerService() {
        return message->{
            String json = message.getPayload();
            log.info("抽奖记录处理开始,drawRecord==========>");
            try {
                if (StringUtils.isNotEmpty(json)) {

                    DrawRecordVO drawRecordVO = JSONObject.parseObject(json, DrawRecordVO.class);
                    //添加活动抽奖次数
                    DrawActivityPauseByIdRequest request = new DrawActivityPauseByIdRequest();
                    request.setId(drawRecordVO.getActivityId());
                    drawActivitySaveProvider.addDrawCount(request);
                    if (Objects.nonNull(drawRecordVO.getPrizeId())) {
                        //扣除奖品库存
                        drawPrizeSaveProvider.subPrizeStock(DrawPrizeByIdRequest.builder().id(drawRecordVO.getPrizeId()).build());
                        //添加活动中奖奖次数
                        drawActivitySaveProvider.addAwardCount(request);
                    }
                }

            } catch (Exception e) {
                log.error("抽奖记录处理失败,drawRecord==========>" + json, e);
            }
        };
    }
}
