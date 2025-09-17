package com.wanmi.sbc.util;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.setting.api.request.OperationLogAddRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 操作日志发送公共类
 * Created by liguang on 2018-10-17
 */
@Slf4j
@Component
public class CallbackOperateLogMQUtil {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 操作日志记录
     * @param opModule
     * @param opCode
     * @param opContext
     */
    public void convertAndSend(String opModule, String opCode, String opContext) {
       OperationLogAddRequest operationLog = new OperationLogAddRequest();
       operationLog.setEmployeeId(StringUtils.EMPTY);
       operationLog.setOpAccount(StringUtils.EMPTY);
       operationLog.setOpName(StringUtils.EMPTY);
       operationLog.setStoreId(0L);
       operationLog.setCompanyInfoId(0L);
       operationLog.setOpRoleName(StringUtils.EMPTY);
       operationLog.setOpModule(opModule);
       operationLog.setOpCode(opCode);
       operationLog.setOpContext(opContext);
       operationLog.setOpIp(HttpUtil.getIpAddr());
       operationLog.setOpTime(LocalDateTime.now());
       MqSendDTO mqSendDTO = new MqSendDTO();
       mqSendDTO.setTopic(ProducerTopic.OPERATE_LOG_ADD);
       mqSendDTO.setData(JSONObject.toJSONString(operationLog));
       mqSendProvider.send(mqSendDTO);
    }
}
