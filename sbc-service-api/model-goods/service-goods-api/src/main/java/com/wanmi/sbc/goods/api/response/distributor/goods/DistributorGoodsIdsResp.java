package com.wanmi.sbc.goods.api.response.distributor.goods;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * program: sbc-micro-service-B
 *
 * @date: 2020-07-17 13:43
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorGoodsIdsResp implements Serializable {

    private static final long serialVersionUID = -798955744122347731L;

    @Schema(description = "分销商品ID")
    private List<String> goodsIds;
}
