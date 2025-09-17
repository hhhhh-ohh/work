package com.wanmi.sbc.empower.bean.vo.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsCheckAccessInfoVO
 * @description 审核状态
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PlatformOrderDeliveryProductVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * SPUID
     */
    @Schema(description = "SPUID")
    private String out_product_id;

    /**
     * 快递单号
     */
    @NotNull
    @Schema(description = "SKU ID")
    private String out_sku_id;

}