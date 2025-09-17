package com.wanmi.sbc.elastic.bean.dto.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * SPU信息同步参数
 * @author wanmi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsGoodsSpuSyncDTO extends BaseRequest {

    @Schema(description = "spuId")
    private String spuId;

    @Schema(description = "来源")
    private Integer goodsSource;

    @Schema(description = "库存")
    private Long stock;
}
