package com.wanmi.sbc.customer.api.response.company;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: songhanlin
 * @Date: Created In 上午11:38 2017/11/14
 * @Description: 工商信息Response
 */
@Schema
@Data
public class CompanyInfoResponse extends BasicResponse {

    /**
     * 公司信息Id
     */
    @Schema(description = "公司信息Id")
    private Long companyInfoId;

    /**
     * 社会信用代码
     */
    @Schema(description = "社会信用代码")
    private String socialCreditCode;

    /**
     * 企业名称
     */
    @Schema(description = "企业名称")
    private String companyName;

    /**
     * 住所
     */
    @Schema(description = "住所")
    private String address;

    /**
     * 法定代表人
     */
    @Schema(description = "法定代表人")
    private String legalRepresentative;


    /**
     * 注册资本
     */
    @Schema(description = "注册资本")
    private BigDecimal registeredCapital;

    /**
     * 成立日期
     */
    @Schema(description = "成立日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime foundDate;

    /**
     * 营业期限自
     */
    @Schema(description = "营业期限自")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime businessTermStart;

    /**
     * 营业期限至
     */
    @Schema(description = "营业期限至")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime businessTermEnd;

    /**
     * 经营范围
     */
    @Schema(description = "经营范围")
    private String businessScope;

    /**
     * 营业执照副本电子版
     */
    @Schema(description = "营业执照副本电子版")
    private String businessLicence;

    /**
     * 法人身份证正面
     */
    @Schema(description = "法人身份证正面")
    @SensitiveWordsField(signType = SignWordType.IMG)
    private String frontIDCard;

    /**
     * 法人身份证反面
     */
    @Schema(description = "法人身份证反面")
    @SensitiveWordsField(signType = SignWordType.IMG)
    private String backIDCard;

}
