package com.wanmi.sbc.marketing.api.request.bargaingoods;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>砍价商品新修改参数</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainGoodsModifyRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @NotNull
    private Long bargainGoodsId;

    /**
     * 追加砍价库存
     */
    @Schema(description = "追加砍价库存")
    @NotNull
    @Min(1)
    @Max(9999999999L)
    private Long addBargainStock;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;
}