package com.wanmi.sbc.account.bean.vo;

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
 * @ClassName CustomerCreditRepayVO
 * @Description 在线还款查询授信账户和还款详情
 * @author chenli
 * @date 2021/3/3 16:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CustomerCreditRepayVO extends BasicResponse {

    private static final long serialVersionUID = -4995060787499522589L;
    /**
     * 会员id
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 授信额度
     */
    @Schema(description = "授信额度")
    private BigDecimal creditAmount;

    /**
     * 待还款额度
     */
    @Schema(description = "待还款额度")
    private BigDecimal totalRepayAmount;

    /**
     * 还款单号
     */
    @Schema(description = "还款单号")
    private String repayOrderCode;

    /**
     * 订单还款金额
     */
    @Schema(description = "订单还款金额")
    private BigDecimal repayAmount;

    /**
     * 还款状态 0 待还款 1 还款成功 2 已作废
     */
    @Schema(description = "还款状态 0 待还款 1 还款成功 2 已作废")
    private CreditRepayStatus repayStatus;

    /**
     * 还款方式 0银联，1微信，2支付宝
     */
    @Schema(description = "还款方式 0银联，1微信，2支付宝")
    private CreditRepayTypeEnum repayType;

    /**
     * 还款说明
     */
    @Schema(description = "还款说明")
    private String repayNotes;

    /**
     * 还款时间
     */
    @Schema(description = "还款时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime repayTime;

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
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

}
