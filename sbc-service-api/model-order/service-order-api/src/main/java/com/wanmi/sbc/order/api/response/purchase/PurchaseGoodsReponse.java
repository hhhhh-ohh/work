package com.wanmi.sbc.order.api.response.purchase;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created by sunkun on 2017/8/16.
 */
@Data
@Schema
public class PurchaseGoodsReponse extends GoodsInfoVO {

    /**
     * spu标题
     */
    @Schema(description = "spu标题")
    private String goodsName;
}
