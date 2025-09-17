package com.wanmi.sbc.marketing.api.request.bargaingoods;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotNull;

/**
 * <p>单个查询砍价商品请求参数</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainGoodsByIdRequest {
    private static final long serialVersionUID = 1L;

    /**
     * bargainGoodsId
     */
    @Schema(description = "bargainGoodsId")
    @NotNull
    private Long bargainGoodsId;
}