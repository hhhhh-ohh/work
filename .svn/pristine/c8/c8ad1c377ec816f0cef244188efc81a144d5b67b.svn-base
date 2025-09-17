package com.wanmi.sbc.order.api.request.distribution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author xuyunpeng
 * @className DistributionTaskTempLedgerRequest
 * @description
 * @date 2022/7/26 10:43 AM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistributionTaskTempLedgerRequest extends BaseRequest {
    private static final long serialVersionUID = -980690541478965642L;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    @NotBlank
    private String orderId;

    /**
     * 业务参数
     */
    @Schema(description = "业务参数")
    @NotBlank
    private String params;

    /**
     * 入账时间
     */
    @Schema(description = "入账时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime ledgerTime;
}
