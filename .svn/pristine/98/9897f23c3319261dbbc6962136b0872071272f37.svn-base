package com.wanmi.sbc.goods.api.request.enterprise.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.math.BigDecimal;

/**
 * 企业价格修改——单个接口
 * @author by 柏建忠 on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterprisePriceUpdateRequest extends BaseRequest {

    private static final long serialVersionUID = 4347442174978124265L;

    /**
     * skuId
     */
    @Schema(description = "模拟goodsId")
    @NotEmpty
    private String goodsInfoId;

    /**
     * 企业价格
     */
    @Schema(description = "企业价")
    @NotNull
    private BigDecimal enterPrisePrice;


    /**
     *  企业商品审核 0: 不需要审核 1: 需要审核
     */
    @Schema(description = " 企业商品审核 0: 不需要审核 1: 需要审核 ")
    private DefaultFlag enterpriseGoodsAuditFlag;

}
