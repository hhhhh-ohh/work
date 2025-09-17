package com.wanmi.sbc.communityactivity.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 团长中心返回数据
 * Created by 王超 on 2023/7/17.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaderCenterResponse extends BasicResponse {

    /**
     * 团长id
     */
    @Schema(description = "团长id")
    private String leaderId;


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
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    /**
     * 考虑到后面可能会有很多类似“企业会员”的标签，用List存放标签内容
     */
    @Schema(description = "会员标签")
    private List<String> customerLabelList = new ArrayList<>();

    /**
     * 企业会员名称
     */
    @Schema(description = "企业会员名称")
    private String enterpriseCustomerName;

    /**
     * 企业会员logo
     */
    @Schema(description = "企业会员logo")
    private String enterpriseCustomerLogo;

    /**
     * 跟团人数
     */
    @Schema(description = "跟团人数")
    private Long followNum;

    /**
     * 拓客人数
     */
    @Schema(description = "拓客人数")
    private Long inviteNum;

    /**
     * 可用余额
     */
    @Schema(description = "可用余额")
    private BigDecimal balanceTotal;

}
