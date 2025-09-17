package com.wanmi.sbc.order.api.request.trade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author houshuai
 * @date 2021/3/2 14:11
 * @description <p> 未还款订单查询 </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditTradePageRequest extends BaseQueryRequest implements Serializable {
    private static final long serialVersionUID = 2203336020183346334L;

    /**
     * 会员id
     */
    @NotBlank
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 授信开始时间
     */
    @Schema(description = "授信开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 授信截止时间
     */
    @Schema(description = "授信截止时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 支付状态
     */
    @Schema(description = "支付状态(支付、已支付定金)")
    private List<String> payOrderStatusList;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    private String flowState;

    /**
     * 是否还款
     */
    @Schema(description = "是否已还款 false 否，true 是")
    private Boolean hasRepaid;

    /**
     * 是否使用记录
     */
    @Schema(description = "是否使用记录")
    private Boolean isUsed;

    /**
     * 需要授信还款
     */
    @Schema(description = "需要授信还款")
    private Boolean needCreditRepayFlag;
}
