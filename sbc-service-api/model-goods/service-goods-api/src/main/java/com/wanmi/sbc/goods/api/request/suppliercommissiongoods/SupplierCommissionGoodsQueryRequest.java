package com.wanmi.sbc.goods.api.request.suppliercommissiongoods;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author wur
 * @className DelSupplierCommissionGoodsRequest
 * @description
 * @date 2021/9/14 11:02
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCommissionGoodsQueryRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 商品Id
     */
    @Schema(description = "商品Id")
    private String goodsId;

    /**
     * 供应商商品Id
     */
    @Schema(description = "供应商商品Id")
    private String providerGoodsId;

    /**
     * 是否已同步：0：否，1：是
     */
    @Schema(description = "是否已同步：0：否，1：是")
    private DefaultFlag synStatus;

    /**
     * 是否有更新：0：否，1：是  用于方便商家变更记录查询使用
     */
    @Schema(description = "是否有更新：0：否，1：是")
    private DefaultFlag updateFlag;

    /**
     * 删除标识：0：否，1：是
     */
    @Schema(description = "删除标识：0：否，1：是")
    private DeleteFlag delFlag;

}