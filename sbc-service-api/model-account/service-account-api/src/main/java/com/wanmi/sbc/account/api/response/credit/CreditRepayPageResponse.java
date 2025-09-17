package com.wanmi.sbc.account.api.response.credit;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.CreditRepayTypeEnum;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author houshuai
 * @date 2021/3/2 17:14
 * @description <p> 授信还款响应 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditRepayPageResponse extends BasicResponse {

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
     * 还款金额
     */
    @Schema(description = "还款金额")
    private BigDecimal repayAmount;

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
     * 还款状态
     */
    @Schema(description = "还款状态")
    private CreditRepayStatus repayStatus;

    /**
     * 还款说明
     */
    @Schema(description = "还款说明")
    private String repayNotes;

    /**
     * 关联订单数量
     */
    @Schema(description = "订单数量")
    private Long orderNum;

    /**
     * 授信额度
     */
    @Schema(description = "授信额度")
    private BigDecimal creditAmount;

    @Schema(description = "周期")
    private Long effectiveDays;


}
