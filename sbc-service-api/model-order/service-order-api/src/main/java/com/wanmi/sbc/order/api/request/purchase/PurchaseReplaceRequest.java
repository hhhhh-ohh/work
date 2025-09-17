package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.order.bean.dto.PurchaseSaveDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
*
 * @description   购物车商品更换请求
 * @author  wur
 * @date: 2022/2/10 9:41
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class PurchaseReplaceRequest extends PurchaseSaveDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "删除的SKUId")
    @NotEmpty
    private String deleteGoodsInfoId;

}
