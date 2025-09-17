package com.wanmi.sbc.order.api.request.purchase;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhanggaolei
 * @className CartInfoRequest
 * @description
 * @date 2022/5/30 16:02
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartGoodsInfoRequest {
    /**
     * SkuId
     */
    @Schema(description = "SkuId")
    private String goodsInfoId;

    /**
     * 登录用户
     */
    @Schema(description = "购买量")
    private Long buyCount = 0L;
}
