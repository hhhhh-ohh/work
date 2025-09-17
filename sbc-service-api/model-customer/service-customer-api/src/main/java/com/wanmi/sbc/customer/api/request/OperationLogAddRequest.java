package com.wanmi.sbc.customer.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 操作日志信息
 * Created by daiyitian on 2017/4/26.
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class OperationLogAddRequest extends BaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 员工编号
     */
    @Schema(description = "员工编号")
    private String employeeId;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 公司Id
     */
    @Schema(description = "公司Id")
    private Long companyInfoId;

    /**
     * 操作人账号
     */
    @Schema(description = "操作人账号")
    private String opAccount;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private String opName;

    /**
     * 操作人角色
     */
    @Schema(description = "操作人角色")
    private String opRoleName;

    /**
     * 操作模块
     */
    @Schema(description = "操作模块")
    private String opModule;

    /**
     * 操作关类型
     */
    @Schema(description = "操作关类型")
    private String opCode;

    /**
     * 操作内容
     */
    @Schema(description = "操作内容")
    private String opContext;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime opTime;

    /**
     * 操作IP
     */
    @Schema(description = "操作IP")
    private String opIp;

    /**
     * 操作MAC地址
     */
    @Schema(description = "操作MAC地址")
    private String opMac;

    /**
     * 运营商
     */
    @Schema(description = "操作MAC地址")
    private String opIsp;

    /**
     * 第三方ID
     */
    @Schema(description = "第三方ID")
    private String thirdId;

    /**
     * 所在国家
     */
    @Schema(description = "所在国家")
    private String opCountry;

    /**
     * 所在省份
     */
    @Schema(description = "所在省份")
    private String opProvince;

    /**
     * 所在城市
     */
    @Schema(description = "所在城市")
    private String opCity;

    /**
     * 操作UserAgent
     */
    @Schema(description = "操作UserAgent")
    private String opUserAgent;
}
