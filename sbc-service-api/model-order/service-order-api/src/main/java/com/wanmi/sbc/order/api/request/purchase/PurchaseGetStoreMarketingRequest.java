package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsMarketingDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-03
 */
@Data
@Schema
public class PurchaseGetStoreMarketingRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品营销信息")
    @NotNull
    @Size(min = 1)
    private List<GoodsMarketingDTO> goodsMarketings;

    @Schema(description = "客户信息")
    private CustomerDTO customer;

    @Schema(description = "商品ids")
    private List<String> goodsInfoIdList;

    @Schema(description = "采购单信息")
    private PurchaseFrontRequest frontReq;
}
