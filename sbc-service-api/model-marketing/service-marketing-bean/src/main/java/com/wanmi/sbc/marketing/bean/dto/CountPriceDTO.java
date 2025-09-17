package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
*
 * @description 营销算价请求
 * @author  wur
 * @date: 2022/2/24 10:49
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountPriceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "商家id")
    private Long storeId;

    @Schema(description = "订单快照商品信息")
    private List<CountPriceGoodsInfoDTO> goodsInfoVOList;

    @Schema(description = "订单快照营销活动信息")
    private List<CountPriceMarketingDTO> marketingVOList;

}