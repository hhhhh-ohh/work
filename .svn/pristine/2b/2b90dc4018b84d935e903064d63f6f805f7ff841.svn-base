package com.wanmi.sbc.message.smssend.service;

import com.google.common.collect.Lists;
import com.wanmi.ares.provider.CustomerBaseQueryProvider;
import com.wanmi.ares.request.CustomerQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.message.SmsBaseResponse;
import com.wanmi.sbc.message.SmsProxy;
import com.wanmi.sbc.message.api.request.smssenddetail.SmsSendDetailQueryRequest;
import com.wanmi.sbc.message.api.request.smssign.SmsSignQueryRequest;
import com.wanmi.sbc.message.bean.constant.ReceiveGroupType;
import com.wanmi.sbc.message.bean.constant.SmsResponseCode;
import com.wanmi.sbc.message.bean.enums.ReceiveType;
import com.wanmi.sbc.message.bean.enums.ReviewStatus;
import com.wanmi.sbc.message.bean.enums.SendDetailStatus;
import com.wanmi.sbc.message.bean.enums.SendStatus;
import com.wanmi.sbc.message.smssend.model.root.SmsSend;
import com.wanmi.sbc.message.smssend.repository.SmsSendRepository;
import com.wanmi.sbc.message.smssenddetail.model.root.SmsSendDetail;
import com.wanmi.sbc.message.smssenddetail.service.SmsSendDetailService;
import com.wanmi.sbc.message.smssign.model.root.SmsSign;
import com.wanmi.sbc.message.smssign.repository.SmsSignRepository;
import com.wanmi.sbc.message.smssign.service.SmsSignWhereCriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 发送任务服务
 * 除了sendDetail方法，其他均无用，迁移至boss的bff下
 * @see com.wanmi.sbc.mq.SmsSendTaskService
 */
@Service
@Slf4j
public class SmsSendTaskService {
    @Autowired
    private SmsProxy smsProxy;
    @Autowired
    CustomerBaseQueryProvider customerBaseQueryProvider;
    @Autowired
    private SmsSendDetailService smsSendDetailService;
    @Autowired
    private SmsSendRepository smsSendRepository;
    @Autowired
    private SmsSignRepository smsSignRepository;
    /*@Autowired
    private CrmGroupProvider crmGroupProvider;*/

    private final long PAGE_SIZE=1000L;

    /**
     * 发送详情
     * @param smsSend
     * @return
     */
    public boolean sendDetail(SmsSend smsSend){
        SmsSendDetailQueryRequest detailQueryRequest = SmsSendDetailQueryRequest
                .builder()
                .sendId(smsSend.getId())
                .notStatus(SendDetailStatus.SUCCESS)
                .build();
        long taskCount = smsSendDetailService.count(detailQueryRequest);
        if(taskCount>0){
            long pageCount = taskCount % PAGE_SIZE == 0 ? taskCount / PAGE_SIZE : taskCount / PAGE_SIZE + 1;
            for (long pageNum = 0; pageNum < pageCount; pageNum++) {
                detailQueryRequest.setPageNum((int) pageNum);
                detailQueryRequest.setPageSize((int) PAGE_SIZE);
                Page<SmsSendDetail> page = this.smsSendDetailService.page(detailQueryRequest);
                for (SmsSendDetail detail : page) {
                    detail.setTemplateCode(smsSend.getTemplateCode());
                    detail.setSignName(smsSend.getSignName());
                    detail.setSendTime(LocalDateTime.now());
                    SmsBaseResponse smsBaseResponse = smsProxy.sendSms(detail);
                    detail.setCode(smsBaseResponse.getCode());
                    detail.setMessage(smsBaseResponse.getMessage());
                    detail.setBizId(smsBaseResponse.getBizId());
                    this.smsSendDetailService.modify(detail);
                    if (!smsBaseResponse.getCode().equals(SmsResponseCode.SUCCESS)) {
                        detail.setStatus(SendDetailStatus.FAILED);
                        this.smsSendDetailService.modify(detail);
                        smsSend.setStatus(SendStatus.FAILED);
                        smsSend.setResendType(SmsResponseCode.resendType(smsBaseResponse.getCode()));
                        smsSend.setMessage(smsBaseResponse.getMessage());
                        return false;
                    }
                    detail.setStatus(SendDetailStatus.SUCCESS);
                    this.smsSendDetailService.modify(detail);
                }
            }
        }

        smsSend.setStatus(SendStatus.END);
        smsSend.setMessage(SmsResponseCode.SUCCESS);
        smsSend.setSendTime(LocalDateTime.now());
        this.smsSendRepository.save(smsSend);
        log.info("发送任务结束：sendId={}",smsSend.getId());
        return true;
    }


}
