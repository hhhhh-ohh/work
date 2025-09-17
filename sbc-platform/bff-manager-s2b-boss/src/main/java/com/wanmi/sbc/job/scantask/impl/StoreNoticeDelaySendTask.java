package com.wanmi.sbc.job.scantask.impl;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.job.scantask.ScanAndSendTask;
import com.wanmi.sbc.message.api.provider.storenoticesend.StoreNoticeSendProvider;
import com.wanmi.sbc.message.api.provider.storenoticesend.StoreNoticeSendQueryProvider;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendListRequest;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendModifyScanFlagRequest;
import com.wanmi.sbc.message.bean.enums.SendType;
import com.wanmi.sbc.message.bean.vo.StoreNoticeSendVO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 商家公告延时发送定时扫表任务
 * @author malianfeng
 * @date 2022/7/8 19:11
 */
@Service
@Slf4j
public class StoreNoticeDelaySendTask implements ScanAndSendTask {

    @Autowired private StoreNoticeSendQueryProvider storeNoticeSendQueryProvider;

    @Autowired private StoreNoticeSendProvider storeNoticeSendProvider;

    @Autowired private MqSendProvider mqSendProvider;

    @Value("${scan-task.within-time}")
    private int withinTime;

    @Async
    @Override
    public void scanAndSendMQ() {
        log.info("开始扫描商家公告 {}", LocalDateTime.now());

        // 检索N分钟内将要发送的公告，即 now <= sendTime <=（now + withinTime）
        LocalDateTime now = LocalDateTime.now();
        StoreNoticeSendListRequest listRequest = StoreNoticeSendListRequest.builder()
                .sendTimeType(SendType.DELAY)
                .sendTimeBegin(now)
                .sendTimeEnd(now.plusMinutes(withinTime))
                .scanFlag(BoolFlag.NO)
                .delFlag(DeleteFlag.NO).build();
        List<StoreNoticeSendVO> storeNoticeList = storeNoticeSendQueryProvider.list(listRequest).getContext().getStoreNoticeSendVOList();

        if (!CollectionUtils.isEmpty(storeNoticeList)) {
            // 成功发送的公告ids
            List<Long> successNoticeIds = new ArrayList<>();
            try {
                // 遍历公告列表
                for (StoreNoticeSendVO storeNotice : storeNoticeList) {
                    // 循环发送MQ消息
                    Duration duration = Duration.between(LocalDateTime.now(), storeNotice.getSendTime());
                    MqSendDelayDTO mqSendDTO = new MqSendDelayDTO();
                    mqSendDTO.setData(storeNotice.getId().toString());
                    mqSendDTO.setTopic(ProducerTopic.STORE_NOTICE_SEND);
                    mqSendDTO.setDelayTime(duration.isNegative() ? 6000L : duration.toMillis());
                    mqSendProvider.sendDelay(mqSendDTO);
                    successNoticeIds.add(storeNotice.getId());
                }
            } catch (Exception e) {
                log.error("商家公告定时发送产生异常 {}", storeNoticeList, e);
            }
            // 批量更新成功发送的公告扫描标识为已扫描
            storeNoticeSendProvider.modifyScanFlag(StoreNoticeSendModifyScanFlagRequest.builder()
                    .noticeIds(successNoticeIds)
                    .scanFlag(BoolFlag.YES)
                    .build());
            log.info("共扫描到 {} 个公告，并发送至MQ延时队列", storeNoticeList.size());
        } else {
            log.info("当前不存在 {} 分钟内要发送的公告", withinTime);
        }
    }
}
