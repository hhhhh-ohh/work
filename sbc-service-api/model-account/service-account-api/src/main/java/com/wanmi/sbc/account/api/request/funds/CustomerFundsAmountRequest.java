package com.wanmi.sbc.account.api.request.funds;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFundsAmountRequest extends BaseRequest {

    private static final long serialVersionUID = -8876623187145687667L;
    /**
     * 会员编号
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 开始时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * Tab类型 0: 全部, 1: 收入, 2: 支出, 3:分销佣金&邀新记录
     */
    @Schema(description = "Tab类型 0: 全部, 1: 收入, 2: 支出, 3:分销佣金&邀新记录")
    private Integer tabType;
}
