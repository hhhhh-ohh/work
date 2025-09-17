package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.order.bean.dto.PurchaseGoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 未登录时,前端迷你采购单入参
 * @author bail
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PurchaseFrontMiniRequest extends BaseRequest {

    private static final long serialVersionUID = 2120025584368311291L;

    /**
     * 前端采购单缓存中的多个sku
     */
    @Schema(description = "前端采购单缓存中的多个sku")
    @NotNull
    @Valid
    private List<PurchaseGoodsInfoDTO> goodsInfoDTOList;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    String inviteeId;

    /**
     * 门店id
     */
    @Schema(description = "门店id")
    private Long storeId;

    /**
     * 终端标识
     */
    @Schema(description = "终端标识")
    private TerminalSource terminalSource;



}
