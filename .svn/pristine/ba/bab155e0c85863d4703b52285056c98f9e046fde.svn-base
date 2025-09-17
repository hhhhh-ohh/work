package com.wanmi.sbc.order.common;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.setting.api.request.OperationLogAddRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author liguang
 * @description do something here
 * @date 2018年11月21日 18:04
 */
@Service
public class OperationLogMq {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 记录操作日志
     *
     * @param operator
     * @param opCode
     * @param opContext
     */
    public void convertAndSend(Operator operator, String opCode, String opContext) {
        OperationLogAddRequest operationLog = new OperationLogAddRequest();
        operationLog.setEmployeeId(operator.getUserId());
        operationLog.setOpName(operator.getName());
        if (StringUtils.isNotEmpty(operator.getStoreId())) {
            operationLog.setStoreId(Long.valueOf(operator.getStoreId()));
        } else {
            operationLog.setStoreId(0L);
        }
        operationLog.setOpModule("订单");
        operationLog.setOpCode(opCode);
        operationLog.setOpContext(opContext);
        operationLog.setOpIp(operator.getIp());
        operationLog.setOpTime(LocalDateTime.now());
        operationLog.setOpAccount(operator.getAccount());
        operationLog.setOpUserAgent(HttpUtil.getUserAgent());
        operationLog.setCompanyInfoId(operator.getCompanyInfoId());
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.OPERATE_LOG_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(operationLog));
        mqSendProvider.send(mqSendDTO);
    }
}
