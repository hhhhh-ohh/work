package com.wanmi.sbc.job;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.NodeType;
import com.wanmi.sbc.common.enums.node.OrderProcessType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.marketing.bean.vo.AppointmentRecordVO;
import com.wanmi.sbc.marketing.bean.vo.AppointmentSaleGoodsInfoVO;
import com.wanmi.sbc.message.notice.NoticeService;
import com.wanmi.sbc.order.api.provider.appointmentrecord.AppointmentRecordQueryProvider;
import com.wanmi.sbc.order.api.request.appointmentrecord.AppointmentRecordPageCriteriaRequest;
import com.wanmi.sbc.order.api.response.appointmentrecord.AppointmentRecordListResponse;
import com.wanmi.sbc.order.bean.dto.AppointmentQueryDTO;
import com.xxl.job.core.handler.annotation.XxlJob;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 预约开售通知通知通知定时任务
 */
@Component
@Slf4j
public class AppointmentSaleActivityJobHandler {
    @Autowired
    private AppointmentRecordQueryProvider appointmentRecordQueryProvider;

    @Resource
    private NoticeService noticeService;

    @XxlJob(value = "appointmentSaleActivityJobHandler")
    public void execute() throws Exception {
        // 获取已经订阅的未开始的活动
        AppointmentRecordListResponse response = appointmentRecordQueryProvider.listSubscribeNotStartActivity(
                AppointmentRecordPageCriteriaRequest.builder().appointmentQueryDTO
                        (AppointmentQueryDTO.builder().snapUpStartTimeBegin(LocalDateTime.now()).build()).build()).getContext();
        if (Objects.isNull(response) || CollectionUtils.isEmpty(response.getAppointmentRecordVOList())) {
            log.info("=====暂无预约开售活动通知======");
            return;
        }
        List<AppointmentRecordVO> appointmentRecordList = response.getAppointmentRecordVOList().stream().filter(appointmentRecord -> judgeHalfHourActivity(appointmentRecord.getAppointmentSaleInfo().getSnapUpStartTime()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(appointmentRecordList)) {
            return;
        }
        response.getAppointmentRecordVOList().forEach(appointmentRecord -> {
            try {
                @NotNull
                AppointmentSaleGoodsInfoVO appointmentSaleGoodsInfo = appointmentRecord.getAppointmentSaleGoodsInfo();
                String customerId = appointmentRecord.getBuyerId();
                noticeService.sendMessage(NodeType.ORDER_PROGRESS_RATE.toValue(),
                        OrderProcessType.APPOINTMENT_SALE.toValue(),
                        OrderProcessType.APPOINTMENT_SALE.getType(),
                        appointmentRecord.getId(), appointmentRecord.getGoodsInfoId(),
                        appointmentSaleGoodsInfo.getSkuName(), customerId,
                        appointmentSaleGoodsInfo.getSkuPic(), appointmentRecord.getCustomer().getAccount());
            } catch (Exception e) {
                log.error("消息处理失败:" + JSON.toJSONString(appointmentRecord), e);
            }
        });
    }

    public boolean judgeHalfHourActivity(LocalDateTime snapUpStartTime) {
        return Duration.between(snapUpStartTime, LocalDateTime.now()).toMinutes() < Constants.NUM_30;
    }
}
