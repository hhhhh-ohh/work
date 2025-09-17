package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Schema
@Data
public class DistributionInviteNewSupplyAgainRequest extends BaseRequest {


    /**
     * 邀新记录表主键
     */
    @Schema(description = "邀新记录表主键ID")
    @NotBlank
    private String recordId;

    /**
     * 奖励是否入账，0：否，1：是
     */
    @Schema(description = "奖励是否入账，0：否，1：是")
    private InvalidFlag rewardRecorded;

    /**
     * 奖励金额(元)
     */
    @Schema(description = "奖励金额(元)")
    private BigDecimal rewardCash;

    /**
     * 奖励优惠券
     */
    @Schema(description = "奖励优惠券")
    private String rewardCoupon;

    /**
     * 终端
     */
    @Schema(description = "终端")
    private TerminalSource terminalSource;

}
