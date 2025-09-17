package com.wanmi.sbc.vas.bean.vo.sellplatform;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className SellPlatformDeliveryVO
 * @description TODO
 * @date 2022/4/21 19:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
@Builder
public class SellPlatformDeliveryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "物流公司Id")
    private String deliveryId;

    @Schema(description = "物流单号")
    private String waybillId;

    @Schema(description = "商品信息")
    private List<SellPlatformDeliveryGoodsVO> goodsList;

}