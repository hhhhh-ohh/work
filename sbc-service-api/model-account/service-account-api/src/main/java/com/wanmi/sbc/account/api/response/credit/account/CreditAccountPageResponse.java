package com.wanmi.sbc.account.api.response.credit.account;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
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
 * @date 2021/2/27 14:11
 * @description <p> 订单账户分页信息 </p>
 */
@Data
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class CreditAccountPageResponse extends BasicResponse {

    private static final long serialVersionUID = -7064876673321418790L;
    /**
     * 主键
     */
    @Schema(description = "主键ID")
    private String id;

    /**
     * 会员id
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
     * 会员账号
     */
    @Schema(description = "会员账号")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String customerAccount;

    /**
     * 是否使用
     */
    @Schema(description = "是否使用 0未使用 1已使用")
    private BoolFlag usedStatus;

    /**
     * 是否过期
     */
    @Schema(description = "是否过期 0未过期 1已过期")
    private BoolFlag expireStatus;

    /**
     * 授信额度
     */
    @Schema(description = "授信额度")
    private BigDecimal creditAmount;

    /**
     * 可用额度
     */
    @Schema(description = "可用额度")
    private BigDecimal usableAmount;

    /**
     * 待还款额度
     */
    @Schema(description = "待还款额度")
    private BigDecimal repayAmount;

    /**
     * 使用周期
     */
    @Schema(description = "使用周期")
    private Long effectiveDays;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 到期时间
     */
    @Schema(description = "到期时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;
}
