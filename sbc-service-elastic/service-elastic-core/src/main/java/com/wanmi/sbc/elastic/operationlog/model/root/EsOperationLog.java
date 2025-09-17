package com.wanmi.sbc.elastic.operationlog.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

/**
 * @author houshuai
 *
 * 操作日志实体信息
 */
@Data
@Document(indexName = EsConstants.SYSTEM_OPERATION_LOG)
public class EsOperationLog {

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 员工编号
     */
    private String employeeId;

    /**
     * 门店Id
     */
    private Long storeId;

    /**
     * 公司信息Id
     */
    private Long companyInfoId;

    /**
     * 操作人账号
     */
    private String opAccount;

    /**
     * 操作人名称
     */
    private String opName;

    /**
     * 操作人角色
     */
    private String opRoleName;

    /**
     * 操作模块
     */
    private String opModule;

    /**
     * 操作类型
     */
    private String opCode;

    /**
     * 操作内容
     */
    private String opContext;

    /**
     * 操作时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime opTime;

    /**
     * 操作IP
     */
    private String opIp;

    /**
     * 操作MAC地址
     */
    private String opMac;

    /**
     * 运营商
     */
    private String opIsp;

    /**
     * 所在国家
     */
    private String opCountry;

    /**
     * 所在省份
     */
    private String opProvince;

    /**
     * 所在城市
     */
    private String opCity;

    /**
     * 操作UserAgent
     */
    private String opUserAgent;
}
