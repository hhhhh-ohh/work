package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会员增票信息
 */
@Schema
@Data
public class CustomerInvoiceVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 增专资质ID
     */
    @Schema(description = "增专资质ID")
    private Long customerInvoiceId;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    /**
     * 单位全称
     */
    @Schema(description = "单位全称")
    private String companyName;

    /**
     * 纳税人识别号
     */
    @Schema(description = "纳税人识别号")
    private String taxpayerNumber;

    /**
     * 单位电话
     */
    @Schema(description = "单位电话")
    private String companyPhone;

    /**
     * 单位地址
     */
    @Schema(description = "单位地址")
    private String companyAddress;

    /**
     * 银行基本户号
     */
    @Schema(description = "银行基本户号")
    private String bankNo;

    /**
     * 开户行
     */
    @Schema(description = "开户行")
    private String bankName;

    /**
     * 营业执照复印件
     */
    @Schema(description = "营业执照复印件")
    private String businessLicenseImg;

    /**
     * 一般纳税人认证资格复印件
     */
    @Schema(description = "一般纳税人认证资格复印件")
    private String taxpayerIdentificationImg;

    /**
     * 增票资质审核状态  0:待审核 1:已审核通过 2:审核未通过
     */

    @Schema(description = "增票资质审核状态")
    private CheckState checkState;

    /**
     * 审核未通过原因
     */
    @Schema(description = "审核未通过原因")
    private String rejectReason;

    /**
     * 增专资质是否作废 0：否 1：是
     */
    @Schema(description = "增专资质是否作废")
    private InvalidFlag invalidFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    @Schema(description = "发票类型：0-增值税发票；1-个人发票；2-单位发票")
    private InvoiceStyle invoiceStyle;

}
