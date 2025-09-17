package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统计店铺商品总数
 * @program sbc-micro-service
 * @datetime 2019-04-03 10:38
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCountByStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1994840889457270469L;

    /***
     * 店铺id
     */
    @Schema(description = "店铺id")
    private long storeId;
}