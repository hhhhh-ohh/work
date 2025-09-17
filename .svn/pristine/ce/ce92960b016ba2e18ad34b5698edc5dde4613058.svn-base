package com.wanmi.sbc.goods.api.request.distributor.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商家-社交分销开关，更新对应的分销员商品状态对象
 * @author: Geek Wang
 * @createDate: 2019/2/28 14:22
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorGoodsInfoModifyByStoreIdAndStatusRequest extends BaseRequest {

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    @NotNull
    private Long storeId;

    /**
     * 是否删除,0：否，1：是
     */
    @Schema(description = "是否删除,0：否，1：是")
    @NotNull
    private Integer status;

    /**
     * 分销员id
     */
    @Schema(description = "分销员id")
    private String customerId;
}
