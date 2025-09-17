package com.wanmi.sbc.customer.api.request.distribution;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * <p>分销员新增参数</p>
 *
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class DistributionCustomerAddRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

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
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 邀新人数
     */
    @Schema(description = "邀新人数")
    private Integer inviteCount;

    /**
     * 邀新奖金(元)
     */
    @Schema(description = "邀新奖金(元)")
    private BigDecimal rewardCash;

    /**
     * 未入账邀新奖金(元)
     */
    @Schema(description = "未入账邀新奖金(元)")
    private BigDecimal rewardCashNotRecorded;

    /**
     * 邀请人会员ID集合
     */
    @Schema(description = "邀请人会员ID集合")
    private String inviteCustomerIds;
}