package com.wanmi.sbc.giftcard.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponGoodsListResponse;
import com.wanmi.sbc.marketing.bean.vo.GiftCardVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 优惠券凑单页返回
 */
@Schema
@Data
public class GiftCardGoodsPageResponse extends BasicResponse {

    /**
     * 商品列表数据
     */
    @Schema(description = "商品列表数据")
    private EsGoodsInfoResponse esGoodsInfoResponse;

    /**
     * 礼品卡信息
     */
    @Schema(description = "礼品卡信息")
    private GiftCardVO giftCardVO;
}
