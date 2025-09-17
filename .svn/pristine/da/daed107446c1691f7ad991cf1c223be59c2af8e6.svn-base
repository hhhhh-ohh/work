package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>查询店铺已签约的类目列表请求类</p>
 * author: sunkun
 * Date: 2018-11-05
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractCateListCateByStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = 8827130111519059267L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    /**
     * 商品类型
     */
    @Schema(description = "店铺id")
    private GoodsType goodsType;
}
