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
 * @date 2021/3/1 11:44
 * @description <p> 授信账户详情 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditAccountDetailResponse extends BasicResponse {

    private static final long serialVersionUID = -6277531468659268810L;
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
     * 启用状态 0未启用 1已启用
     * 当前账户是否可用
     */
    @Schema(description = "启用状态 0未启用 1已启用")
    private BoolFlag enabled;

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
     * 已使用额度
     */
    @Schema(description = "已用额度")
    private BigDecimal usedAmount;

    /**
     * 已还款额度
     */
    @Schema(description = "已还款额度")
    private BigDecimal hasRepaidAmount;

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

    @Schema(description = "授信显示别名")
    private String alias;

    /**
     * 授信次数
     */
    @Schema(description = "授信次数")
    private Integer creditNum;

    /**
     * 是否开启: 0关闭 1开启
     */
    @Schema(description = "是否开启")
    private Integer isOpen;

    /**
     * 变更额度记录ID
     */
    @Schema(description = "变更额度记录ID")
    private String changeRecordId;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;
}
