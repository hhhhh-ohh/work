package com.wanmi.sbc.order.api.request.trade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xuyunpeng
 * @className TradeBuyCycleDTO
 * @description
 * @date 2022/10/17 2:15 PM
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeBuyCycleModifyRequest implements Serializable {
    private static final long serialVersionUID = -7092888098165220088L;


    /**
     * 订单id
     */
    @Schema(description = "订单id")
    @NotEmpty
    private String tradeId;


    /**
     * 原本的配送时间
     */
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @Schema(description = "原本的配送时间")
    @NotNull
    private LocalDate deliveryDate;


    /**
     * 每周自选和每月自选 修改配送时间
     */
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @Schema(description = "新的的配送时间")
    private LocalDate newDeliveryDate;

    /**
     * 是否顺延
     */
    @Schema(description = "是否顺延",hidden = true)
    private Boolean isDefer;


    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id",hidden = true)
    private Long storeId;

    @Schema(description = "是否改期")
    private Boolean isUpdate;
}
