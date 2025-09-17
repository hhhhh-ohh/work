package com.wanmi.sbc.account.api.response.credit.repay;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.CreditRepayTypeEnum;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 * @date 2021/3/2 17:14
 * @description <p> 授信还款响应 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Builder
public class CreditRepayOverviewPageResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 还款单id
     */
    @Schema(description = "还款单id")
    private String id;

    /**
     * 还款单号
     */
    @Schema(description = "还款单号")
    private String repayOrderCode;


    /**
     * 客户主键（
     */
    @Schema(description = "客户主键")
    private String customerId;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String customerAccount;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    /**
     * 授信金额
     */
    @Schema(description = "授信金额")
    private BigDecimal creditAmount;

    /**
     * 生效天数
     */
    @Schema(description = "生效天数")
    private Long effectiveDays;

    /**
     * 还款金额
     */
    @Schema(description = "还款金额")
    private BigDecimal repayAmount;

    /**
     * 还款状态
     */
    @Schema(description = "还款状态")
    private CreditRepayStatus repayStatus;

    /**
     * 还款方式 0:线上 1:线下
     */
    @Schema(description = "还款方式 0:线上 1:线下")
    private Integer repayWay;

    /**
     * 还款附件 repayWay为1时有值
     */
    @Schema(description = "还款附件 repayWay为1时有值")
    private String repayFile;

    /**
     * 驳回理由
     */
    @Schema(description = "驳回理由")
    private String auditRemark;

    /**
     * 还款方式
     */
    @Schema(description = "还款方式")
    private CreditRepayTypeEnum repayType;

    /**
     * 还款时间
     */
    @Schema(description = "还款时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime repayTime;

    /**
     * 还款说明
     */
    @Schema(description = "还款说明")
    private String repayNotes;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    public CreditRepayOverviewPageResponse(String id, String repayOrderCode, String customerId, String customerAccount, String customerName, BigDecimal creditAmount, Long effectiveDays, BigDecimal repayAmount, CreditRepayStatus repayStatus, CreditRepayTypeEnum repayType, LocalDateTime repayTime, String repayNotes) {
        this.id = id;
        this.repayOrderCode = repayOrderCode;
        this.customerId = customerId;
        this.customerAccount = customerAccount;
        this.customerName = customerName;
        this.creditAmount = creditAmount;
        this.effectiveDays = effectiveDays;
        this.repayAmount = repayAmount;
        this.repayStatus = repayStatus;
        this.repayType = repayType;
        this.repayTime = repayTime;
        this.repayNotes = repayNotes;
    }

}
