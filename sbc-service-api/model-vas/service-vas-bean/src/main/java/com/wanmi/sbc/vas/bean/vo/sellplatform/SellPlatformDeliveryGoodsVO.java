package com.wanmi.sbc.vas.bean.vo.sellplatform;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className SellPlatformDeliveryGoodsVO
 * @description 发货商品信息
 * @date 2022/4/21 19:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
@Builder
public class SellPlatformDeliveryGoodsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "spuId")
    private String goodsId;

    @Schema(description = "skuId")
    private String goodsInfoId;

}