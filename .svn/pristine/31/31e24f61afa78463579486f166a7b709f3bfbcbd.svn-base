package com.wanmi.sbc.order.api.request.trade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xuyunpeng
 * @className BuyCyclePlanRequest
 * @description
 * @date 2022/10/19 5:10 PM
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyCyclePlanRequest implements Serializable {
    private static final long serialVersionUID = -8192076476871705898L;

    /**
     * 首期送达日期
     */
    @Schema(description = "首期送达日期")
    @NotNull
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate deliveryDate;

    /**
     * 期数
     */
    @Schema(description = "期数")
    @NotNull
    private Integer cycleNum;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String customerId;

    @Schema(description = "用户终端的token")
    private String terminalToken;
}
