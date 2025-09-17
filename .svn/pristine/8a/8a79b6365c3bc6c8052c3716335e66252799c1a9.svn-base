package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * program: sbc-micro-service
 * 店铺商品总数
 *
 * @date 2019-04-03 10:42
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCountByStoreIdResponse extends BasicResponse {

    private static final long serialVersionUID = 3403426009849568992L;

    /***
     * 商品总数
     */
    @Schema(description = "商品总数")
    private long goodsCount;
}
