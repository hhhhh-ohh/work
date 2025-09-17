package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsGoodsInfoModifyDistributionGoodsStatusRequest extends BaseRequest {

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 分销商品状态，配合分销开关使用
     */
    @Schema(description = "分销商品状态，配合分销开关使用")
    private Integer distributionGoodsStatus;


}
