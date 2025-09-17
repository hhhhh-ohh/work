package com.wanmi.sbc.elastic.bean.dto.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 商品sku信息同步
 * @author daiyitian
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsGoodsSkuSyncDTO extends BaseRequest {

    @Schema(description = "spuId")
    private String spuId;

    @Schema(description = "skuId")
    private String skuId;

    @Schema(description = "来源")
    private Integer goodsSource;

    @Schema(description = "库存")
    private Long stock;
}
