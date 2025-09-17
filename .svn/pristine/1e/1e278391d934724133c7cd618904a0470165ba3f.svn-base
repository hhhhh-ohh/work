package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema
@Data
public class DistributionInviteNewUpdateRequest extends BaseRequest {
    /**
     * 受邀人ID
     */
    @Schema(description = "受邀人ID")
    @NotBlank
    private String invitedNewCustomerId;

    /**
     * 是否有效邀新，0：否，1：是
     */
    @Schema(description = "是否有效邀新，0：否，1：是")
    @NotNull
    private InvalidFlag availableDistribution;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @NotBlank
    private String orderCode;

    /**
     * 订单完成时间
     */
    @Schema(description = "订单完成时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @NotNull
    private LocalDateTime orderFinishTime;

    /**
     * 奖励是否入账，0：否，1：是
     */
    @Schema(description = "奖励是否入账，0：否，1：是")
    @NotNull
    private InvalidFlag rewardRecorded;

    /**
     * 奖励入账时间
     */
    @Schema(description = "奖励入账时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @NotNull
    private LocalDateTime rewardRecordedTime;

    @Schema(description = "是否超过邀新奖励上限")
    @NotNull
    private DefaultFlag overLimit;

    /**
     * 终端
     */
    @Schema(description = "终端")
    private TerminalSource terminalSource;

}
