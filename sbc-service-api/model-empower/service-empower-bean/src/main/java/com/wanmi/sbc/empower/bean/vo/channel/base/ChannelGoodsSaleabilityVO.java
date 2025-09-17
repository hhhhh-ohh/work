package com.wanmi.sbc.empower.bean.vo.channel.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 第三方商品库存VO
 * @author daiyitian
 * @date 2021/5/11 16:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelGoodsSaleabilityVO implements Serializable {
    private static final long serialVersionUID = 6246961037582549416L;

    @Schema(description = "第三方sku编号")
    private String thirdSkuId;

    @Schema(description = "第三方商品可售标识 true:可售")
    private Boolean saleability;

    @Schema(description = "第三方商品上架标识 true:上架")
    private Boolean addedFlag;

    @Schema(description = "第三方商品不受限标识 true:不受限")
    private Boolean freeFlag;
}
