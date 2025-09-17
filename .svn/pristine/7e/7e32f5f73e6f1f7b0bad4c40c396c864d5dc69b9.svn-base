package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 保存订单商品快照请求结构，修改商品数量
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class TradeItemModifyGoodsNumRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    @NotBlank
    private String customerId;

    /**
     * skuId
     */
    @Schema(description = "skuId")
    @NotBlank
    private String skuId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    @Min(1L)
    @NotNull
    private Long num;

    /**
     * 快照商品详细信息，包含所属商家，店铺等信息
     */
    @Schema(description = "快照商品详细信息，包含所属商家，店铺等信息")
    @NotEmpty
    private List<GoodsInfoDTO> skuList;

    private String terminalToken;
}
