package com.wanmi.sbc.job.scantask.impl;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.job.scantask.ScanAndSendTask;
import com.wanmi.sbc.message.api.provider.minimsgactivitysetting.MiniMsgActivitySettingProvider;
import com.wanmi.sbc.message.api.provider.minimsgactivitysetting.MiniMsgActivitySettingQueryProvider;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingModifyByIdsRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingQueryRequest;
import com.wanmi.sbc.message.bean.enums.ProgramSendStatus;
import com.wanmi.sbc.message.bean.vo.MiniMsgActivitySettingVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huangzhao 扫描活动消息推送并发送mq
 */
@Component
@Slf4j
public class MiniMsgSendTask implements ScanAndSendTask {

    @Autowired
    private MiniMsgActivitySettingQueryProvider miniMsgActivitySettingQueryProvider;

    @Autowired
    private MiniMsgActivitySettingProvider miniMsgActivitySettingProvider;

    /**
     * 扫描N分钟内生效的
     */
    @Value("${scan-task.within-time}")
    private long withinTime;

    @Async
    @Override
    public void scanAndSendMQ() {
        log.info("开始扫描批量活动消息推送 {}", LocalDateTime.now());
        LocalDateTime now = LocalDateTime.now().plusDays(Constants.NUM_30L);
        // 检索N分钟内将要生效的调价单，即 now <= 生效时间 <=（now + withinTime）
        LocalDateTime effectiveTimeBegin = now;
        LocalDateTime effectiveTimeEnd = now.plusMinutes(withinTime);
        List<MiniMsgActivitySettingVO> list = miniMsgActivitySettingQueryProvider
                .list(MiniMsgActivitySettingQueryRequest.builder()
                        .sendTimeBegin(effectiveTimeBegin)
                        .sendTimeEnd(effectiveTimeEnd)
                        .delFlag(DeleteFlag.NO)
                        .sendStatus(ProgramSendStatus.NOT_SEND)
                        .scanFlag(Boolean.FALSE)
                        .build())
                .getContext()
                .getList();
        if (CollectionUtils.isNotEmpty(list)){
            List<Long> ids = Lists.newArrayList();
            list.forEach(messageRequest->{
                // 定时发送，计算间隔时间
                miniMsgActivitySettingProvider.dealCustomerRecord(messageRequest);
                ids.add(messageRequest.getId());
            });
            // 更新状态
            miniMsgActivitySettingProvider.modifyScanFlagByIds(
                    MiniMsgActivitySettingModifyByIdsRequest.builder()
                            .ids(ids).build());
        }
    }
}
