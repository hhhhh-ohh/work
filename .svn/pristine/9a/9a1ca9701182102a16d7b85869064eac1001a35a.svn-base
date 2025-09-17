package com.wanmi.sbc.account.api.request.funds;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.FundsSubType;
import com.wanmi.sbc.account.bean.enums.FundsType;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema
@Data
public class GrantAmountRequest extends BaseRequest {

    @NotBlank
    @Schema(description = "会员ID")
    private String customerId;

    @NotNull
    @Schema(description = "奖励金额")
    private BigDecimal amount;

    @NotNull
    @Schema(description = "奖励发放时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    @NotNull
    @Schema(description = "账务类型")
    private FundsType type;


    @Schema(description = "账务子类型")
    private FundsSubType subType;

    @Schema(description = "业务编号")
    private String businessId;

    /**
     * 会员账号
     */
    @Schema(description = "会员账号")
    private String customerAccount;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;

    /**
     * 是否是分销员
     */
    @Schema(description = "是否是分销员，0：否 1：是")
    private Integer distributor = NumberUtils.INTEGER_ZERO;

    /**
     * 是否是社区购团长
     */
    @Schema(description = "是否是社区购团长，0：否 1：是")
    private Integer communityLeader = NumberUtils.INTEGER_ZERO;
}
