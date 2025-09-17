package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 满赠
 */
@Schema
@Data
public class MarketingFullGiftDetailVO extends BasicResponse {

    private static final long serialVersionUID = -4675531927951755074L;

    /**
     *  满赠赠品Id
     */
    @Schema(description = "满赠赠品主键Id")
    private Long giftDetailId;

    /**
     *  满赠多级促销Id
     */
    @Schema(description = "满赠多级促销Id")
    private Long giftLevelId;

    /**
     *  赠品Id
     */
    @Schema(description = "赠品Id")
    private String productId;

    /**
     *  赠品数量
     */
    @Schema(description = "赠品数量")
    private Long productNum;

    /**
     *  满赠ID
     */
    @Schema(description = "满赠营销ID")
    private Long marketingId;

    @Schema(description = "满赠库存")
    private Integer productStock;

    @Schema(description = "商品信息")
    private GoodsInfoVO goodsInfo;

}
