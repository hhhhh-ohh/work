package com.wanmi.sbc.elastic.bean.vo.operationlog;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author houshuai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsOperationLogVO extends BasicResponse {


    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 员工编号
     */
    @Schema(description = "员工编号")
    private String employeeId;

    /**
     * 门店Id
     */
    @Schema(description = "门店Id")
    private String storeId;

    /**
     * 公司信息Id
     */
    @Schema(description = "公司信息Id")
    private Long companyInfoId;

    /**
     * 操作人账号
     */
    @Schema(description = "操作人账号")
    private String opAccount;

    /**
     * 操作人名称
     */
    @Schema(description = "操作人名称")
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
     * 操作关键字
     */
    @Schema(description = "操作关键字")
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
    @Schema(description = "运营商")
    private String opIsp;

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
}