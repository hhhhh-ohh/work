package com.wanmi.sbc.util;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.setting.api.request.OperationLogAddRequest;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * 操作日志发送公共类
 * Created by liguang on 2018-10-17
 */
@Slf4j
@Component
public class OperateLogMQUtil {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 操作日志记录
     *
     * @param opModule
     * @param opCode
     * @param opContext
     */
    public void convertAndSend(String opModule, String opCode, String opContext) {
        Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
        this.convertAndSend(opModule, opCode, opContext, claims);
    }

    /**
     * 操作日志记录  由于在jwt.excluded-urls中配置了路径之后 获取不到Claims 所以提供手动传入的方式
     *
     * @param opModule
     * @param opCode
     * @param opContext
     * @param claims
     */
    public void convertAndSend(String opModule, String opCode, String opContext, Claims claims) {
       OperationLogAddRequest operationLog = new OperationLogAddRequest();
       if (nonNull(claims)) {
           operationLog.setEmployeeId(Objects.toString(claims.get("employeeId"), StringUtils.EMPTY));
           // accountName
           operationLog.setOpAccount(Objects.toString(claims.get("EmployeeName"), StringUtils.EMPTY));
           operationLog.setStoreId(Long.valueOf(Objects.toString(claims.get("storeId"), "0")));
           operationLog.setCompanyInfoId(Long.valueOf(Objects.toString(claims.get("companyInfoId"), "0")));
           operationLog.setOpRoleName(Objects.toString(claims.get("roleName"), StringUtils.EMPTY));
           operationLog.setOpName(Objects.toString(claims.get("realEmployeeName"), StringUtils.EMPTY));
       } else {
           operationLog.setEmployeeId(StringUtils.EMPTY);
           operationLog.setOpAccount(StringUtils.EMPTY);
           operationLog.setOpName(StringUtils.EMPTY);
           operationLog.setStoreId(0L);
           operationLog.setCompanyInfoId(0L);
           operationLog.setOpRoleName(StringUtils.EMPTY);
       }
       operationLog.setOpModule(opModule);
       operationLog.setOpCode(opCode);
       operationLog.setOpContext(opContext);
       operationLog.setOpIp(HttpUtil.getIpAddr());
       operationLog.setOpUserAgent(HttpUtil.getUserAgent());
       operationLog.setOpTime(LocalDateTime.now());
       MqSendDTO mqSendDTO = new MqSendDTO();
       mqSendDTO.setTopic(ProducerTopic.OPERATE_LOG_ADD);
       mqSendDTO.setData(JSONObject.toJSONString(operationLog));
       mqSendProvider.send(mqSendDTO);
    }

    /**
     * 操作日志记录  由于在jwt.excluded-urls中配置了路径之后 获取不到Claims 所以提供手动传入的方式
     *
     * @param opModule
     * @param opCode
     * @param opContext
     * @param operator 操作人
     */
    public void convertAndSend(String opModule, String opCode, String opContext, Operator operator) {
        OperationLogAddRequest operationLog = new OperationLogAddRequest();
        if (nonNull(operator)) {
            operationLog.setEmployeeId(Objects.toString(operator.getUserId(), StringUtils.EMPTY));
            // accountName
            operationLog.setOpAccount(Objects.toString(operator.getAccount(), StringUtils.EMPTY));
            operationLog.setStoreId(Long.valueOf(Objects.toString(operator.getStoreId(), "0")));
            operationLog.setCompanyInfoId(Long.valueOf(Objects.toString(operator.getCompanyInfoId(), "0")));
            operationLog.setOpRoleName(Objects.toString(operator.getRoleName(), StringUtils.EMPTY));
            operationLog.setOpName(Objects.toString(operator.getName(), StringUtils.EMPTY));
        } else {
            operationLog.setEmployeeId(StringUtils.EMPTY);
            operationLog.setOpAccount(StringUtils.EMPTY);
            operationLog.setOpName(StringUtils.EMPTY);
            operationLog.setStoreId(0L);
            operationLog.setCompanyInfoId(0L);
            operationLog.setOpRoleName(StringUtils.EMPTY);
        }
        operationLog.setOpModule(opModule);
        operationLog.setOpCode(opCode);
        operationLog.setOpContext(opContext);
        operationLog.setOpIp(HttpUtil.getIpAddr());
        operationLog.setOpUserAgent(HttpUtil.getUserAgent());
        operationLog.setOpTime(LocalDateTime.now());
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.OPERATE_LOG_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(operationLog));
        mqSendProvider.send(mqSendDTO);
    }
}
