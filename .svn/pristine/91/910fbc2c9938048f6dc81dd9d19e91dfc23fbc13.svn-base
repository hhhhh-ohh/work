package com.wanmi.sbc.elastic.api.request.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author houshuai
 *  分页查询分销员信息
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsDistributionCustomerPageRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "token")
    private String token;

    /**
     * 批量查询-分销员标识UUIDList
     */
    @Schema(description = "批量查询-分销员标识")
    private List<String> distributionIdList;

    /**
     * 分销员标识UUID
     */
    @Schema(description = "分销员标识UUID")
    private String distributionId;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;

    /**
     * 会员登录账号|手机号
     */
    @Schema(description = "会员登录账号|手机号")
    private String customerAccount;

    /**
     * 搜索条件:创建时间开始
     */
    @Schema(description = "搜索条件:创建时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeBegin;
    /**
     * 搜索条件:创建时间截止
     */
    @Schema(description = "搜索条件:创建时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeEnd;


    /**
     * 是否禁止分销 0: 启用中  1：禁用中
     */
    @Schema(description = "是否禁止分销")
    private DefaultFlag forbiddenFlag;


    /**
     * 是否有分销员资格0：否，1：是
     */
    @Schema(description = "是否有分销员资格")
    private DefaultFlag distributorFlag;

    /**
     * 邀新人数-从
     */
    @Schema(description = "邀新人数-从")
    private Integer inviteCountStart;

    /**
     * 邀新人数-至
     */
    @Schema(description = "邀新人数-至")
    private Integer inviteCountEnd;

    /**
     * 有效邀新人数-从
     */
    @Schema(description = "有效邀新人数-从")
    private Integer inviteAvailableCountStart;

    /**
     * 有效邀新人数-至
     */
    @Schema(description = "有效邀新人数-至")
    private Integer inviteAvailableCountEnd;

    /**
     * 邀新奖金(元)-从
     */
    @Schema(description = "邀新奖金(元)-从")
    private BigDecimal rewardCashStart;
    /**
     * 邀新奖金(元)-至
     */
    @Schema(description = "邀新奖金(元)-至")
    private BigDecimal rewardCashEnd;

    /**
     * 分销订单(笔)-从
     */
    @Schema(description = "分销订单(笔)-从")
    private Integer distributionTradeCountStart;

    /**
     * 分销订单(笔)-至
     */
    @Schema(description = "分销订单(笔)-至")
    private Integer distributionTradeCountEnd;

    /**
     * 销售额(元)-从
     */
    @Schema(description = "销售额(元)-从")
    private BigDecimal salesStart;

    /**
     * 销售额(元)-至
     */
    @Schema(description = "销售额(元)-至")
    private BigDecimal salesEnd;

    /**
     * 分销佣金(元)-从
     */
    @Schema(description = "分销佣金(元)-从")
    private BigDecimal commissionStart;

    /**
     * 分销佣金(元)-至
     */
    @Schema(description = "分销佣金(元)-至")
    private BigDecimal commissionEnd;

    /**
     * 未入账分销佣金(元)
     */
    @Schema(description = "未入账分销佣金(元)")
    private BigDecimal commissionNotRecorded;

    /**
     * 分销员等级ID
     */
    @Schema(description = "分销员等级ID ")
    private String distributorLevelId;

    /**
     * 是否删除标志 0：否，1：是
     */
    @Schema(description = "是否删除标志")
    private DeleteFlag delFlag = DeleteFlag.NO;

    /**
     * 批量查询-分销员标识UUIDList
     */
    @Schema(description = "批量查询-分销员标识")
    private List<String> idList;

    /**
     * 批量查询-业务员相关会员id
     */
    @Schema(description = "批量查询-业务员相关会员id", hidden = true)
    private List<String> employeeCustomerIds;

}