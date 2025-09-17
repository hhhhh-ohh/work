package com.wanmi.sbc.job.scantask.impl;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.goods.api.provider.priceadjustmentrecord.PriceAdjustmentRecordProvider;
import com.wanmi.sbc.goods.api.provider.priceadjustmentrecord.PriceAdjustmentRecordQueryProvider;
import com.wanmi.sbc.goods.api.request.adjustprice.AdjustPriceExecuteRequest;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecord.PriceAdjustmentRecordModifyScanTypeRequest;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecord.PriceAdjustmentRecordWillEffectiveListRequest;
import com.wanmi.sbc.goods.api.response.price.adjustment.PriceAdjustmentRecordListResponse;
import com.wanmi.sbc.goods.bean.vo.PriceAdjustmentRecordVO;
import com.wanmi.sbc.job.scantask.ScanAndSendTask;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 扫描调价单，并发送MQ消息
 * @author malianfeng
 * @date 2021/9/9 14:25
 */
@Component
@Slf4j
public class PriceAdjustScanAndSendTask implements ScanAndSendTask {

    /**
     * 扫描N分钟内生效的调价单
     */
    @Value("${scan-task.within-time}")
    private long withinTime;

    @Autowired private PriceAdjustmentRecordQueryProvider priceAdjustmentRecordQueryProvider;

    @Autowired private PriceAdjustmentRecordProvider priceAdjustmentRecordProvider;

    @Autowired private MqSendProvider mqSendProvider;

    @Async
    @Override
    public void scanAndSendMQ() {
        log.info("开始扫描批量调价单 {}", LocalDateTime.now());
        LocalDateTime now = LocalDateTime.now();
        // 检索N分钟内将要生效的调价单，即 now <= 生效时间 <=（now + withinTime）
        LocalDateTime effectiveTimeBegin = now;
        LocalDateTime effectiveTimeEnd = now.plusMinutes(withinTime);
        PriceAdjustmentRecordListResponse response =
                priceAdjustmentRecordQueryProvider.willEffectiveList(PriceAdjustmentRecordWillEffectiveListRequest.builder()
                        .effectiveTimeBegin(effectiveTimeBegin)
                        .effectiveTimeEnd(effectiveTimeEnd)
                        .build()).getContext();
        if (!CollectionUtils.isEmpty(response.getPriceAdjustmentRecordVOList())) {
            List<PriceAdjustmentRecordVO> recordVOList = response.getPriceAdjustmentRecordVOList();
            for (PriceAdjustmentRecordVO recordVO : recordVOList) {
                // 发送MQ消息
                MqSendDelayDTO mqSendDelayDTO = new MqSendDelayDTO();
                mqSendDelayDTO.setTopic(ProducerTopic.GOODS_PRICE_ADJUST);
                AdjustPriceExecuteRequest request = AdjustPriceExecuteRequest.builder()
                        .adjustNo(recordVO.getId())
                        .storeId(recordVO.getStoreId())
                        .build();
                // 延迟时间（单位毫秒）
                long delayTime = ChronoUnit.MILLIS.between(LocalDateTime.now(), recordVO.getEffectiveTime());
                delayTime = delayTime > 0 ? delayTime : 0;
                mqSendDelayDTO.setData(JSON.toJSONString(request));
                mqSendDelayDTO.setDelayTime(delayTime);
                mqSendProvider.sendDelay(mqSendDelayDTO);
            }
            // 批量更新扫描标识为已扫描
            priceAdjustmentRecordProvider.modifyScanType(PriceAdjustmentRecordModifyScanTypeRequest.builder()
                    .ids(recordVOList.stream().map(PriceAdjustmentRecordVO::getId).collect(Collectors.toList()))
                    .build());
            log.info("共扫描到 {} 个调价单，并发送至MQ延时队列", recordVOList.size());
        } else {
            log.info("当前不存在 {} 分钟内生效的调价单", withinTime);
        }
    }
}

