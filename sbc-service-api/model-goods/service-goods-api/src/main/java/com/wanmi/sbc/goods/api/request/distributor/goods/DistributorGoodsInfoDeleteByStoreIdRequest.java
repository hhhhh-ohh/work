package com.wanmi.sbc.goods.api.request.distributor.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 分销员商品-根据店铺ID删除分销员商品对象
 * @author: Geek Wang
 * @createDate: 2019/2/28 14:22
 * @version: 1.0
 */
@Data
public class DistributorGoodsInfoDeleteByStoreIdRequest extends BaseRequest {

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;
}
