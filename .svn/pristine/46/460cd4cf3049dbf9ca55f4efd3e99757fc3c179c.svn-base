package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema
@Data
public class AfterSettleUpdateDistributorRequest extends BaseRequest {

    @Schema(description = "分销员id")
    @NotBlank
    private String distributeId;

    @Schema(description = "邀新人数")
    @Min(0)
    private Integer inviteNum = 1;

    @Schema(description = "邀新奖励金额")
    @Min(0)
    private BigDecimal inviteAmount;

    @Schema(description = "订单数")
    @Min(0)
    private Integer orderNum = 0;

    @Schema(description = "减少的销售额")
    @Min(0)
    private BigDecimal amount;

    @Schema(description = "发放的分销佣金")
    @Min(0)
    private BigDecimal grantAmount;

    @Schema(description = "原来预计的分销佣金")
    @Min(0)
    private BigDecimal totalDistributeAmount;

    /**
     * 分销员等级设置信息
     */
    @Schema(description = "分销员等级设置信息")
    private List<DistributorLevelVO> distributorLevelVOList;

    /**
     * 基础邀新奖励限制
     */
    @Schema(description = "基础邀新奖励限制")
    private DefaultFlag baseLimitType;

    /**
     * 是否有分销员资格false:否，true:是
     */
    @Schema(description = "是否有分销员资格false:否，true:是")
    private Boolean distributorFlag;
}
