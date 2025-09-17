package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsMarketingDTO;
import com.wanmi.sbc.order.bean.dto.PurchaseGoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 未登录时,前端采购单入参
 * @author bail
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PurchaseFrontRequest extends BaseRequest {

    private static final long serialVersionUID = 1870025584368342290L;

    /**
     * 前端采购单缓存中的多个sku
     */
    @Schema(description = "前端采购单缓存中的多个sku")
    @NotNull
    @Valid
    private List<PurchaseGoodsInfoDTO> goodsInfoDTOList;

    /**
     * 前端采购单勾选的skuIdList
     */
    @Schema(description = "前端采购单勾选的skuIdList")
    @NotNull
    private List<String> goodsInfoIds;

    /**
     * 用户针对各sku选择的营销活动id信息
     */
    @Schema(description = "用户针对各sku选择的营销活动id信息")
    @NotNull
    private List<GoodsMarketingDTO> goodsMarketingDTOList;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    String inviteeId;


    /**
     * 门店id
     */
    @Schema(description = "门店id")
    Long storeId;

}
