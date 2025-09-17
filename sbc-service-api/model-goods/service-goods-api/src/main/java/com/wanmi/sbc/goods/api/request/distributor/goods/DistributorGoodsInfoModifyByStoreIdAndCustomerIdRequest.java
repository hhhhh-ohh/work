package com.wanmi.sbc.goods.api.request.distributor.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

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
public class DistributorGoodsInfoModifyByStoreIdAndCustomerIdRequest extends BaseRequest {

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 分销员id
     */
    @Schema(description = "分销员id")
    private String customerId;
}
