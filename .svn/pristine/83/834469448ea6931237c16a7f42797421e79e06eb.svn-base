package com.wanmi.sbc.elastic.bean.vo.communityleader;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.SensitiveUtils;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsCommunityLeaderVO extends BasicResponse {


    /**
     * 团长id
     */
    @Schema(description = "团长id")
    private String leaderId;

    /**
     * 团长账号
     */
    @Schema(description = "团长账号")
    private String leaderAccount;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 团长名称
     */
    @Schema(description = "团长名称")
    private String leaderName;

    /**
     * 团长简介
     */
    @Schema(description = "团长简介")
    private String leaderDescription;

    /**
     * 审核状态, 0:未审核 1:审核通过 2:审核失败 3:禁用中
     */
    @Schema(description = "审核状态, 0:未审核 1:审核通过 2:审核失败 3:禁用中")
    private LeaderCheckStatus checkStatus;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String checkReason;

    /**
     * 禁用原因
     */
    @Schema(description = "禁用原因")
    private String disableReason;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 审核时间
     */
    @Schema(description = "审核时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime checkTime;

    /**
     * 禁用时间
     */
    @Schema(description = "禁用时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime disableTime;

    /**
     * 是否帮卖 0:否 1:是
     */
    @Schema(description = "是否帮卖 0:否 1:是")
    private Integer assistFlag;

    /**
     * 会员头像
     */
    @Schema(description = "会员头像")
    private String headImg;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    /**
     * 已入账佣金
     */
    @Schema(description = "已入账佣金")
    private BigDecimal commissionReceived = BigDecimal.ZERO;

    /**
     * 已入账自提佣金
     */
    @Schema(description = "已入账自提佣金")
    private BigDecimal commissionReceivedPickup = BigDecimal.ZERO;

    /**
     * 已入账帮卖佣金
     */
    @Schema(description = "已入账帮卖佣金")
    private BigDecimal commissionReceivedAssist = BigDecimal.ZERO;

    /**
     * 未入账佣金
     */
    @Schema(description = "未入账佣金")
    private BigDecimal commissionPending = BigDecimal.ZERO;

    /**
     * 未入账自提佣金
     */
    @Schema(description = "未入账自提佣金")
    private BigDecimal commissionPendingPickup = BigDecimal.ZERO;

    /**
     * 未入账帮卖佣金
     */
    @Schema(description = "未入账帮卖佣金")
    private BigDecimal commissionPendingAssist = BigDecimal.ZERO;

    /**
     * 会员登录账号|手机号-脱敏
     */
    public String getLeaderAccount() {
        return SensitiveUtils.handlerMobilePhone(leaderAccount);
    }

    /**
     * 会员名称-脱敏
     */
    public String getLeaderName() {
        return SensitiveUtils.handlerMobilePhone(leaderName);
    }
}