package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FlashSaleRecordRequest
 * @author xufeng
 * @date 2021/7/12 9:43
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleRecordRequest extends BaseRequest {

    private static final long serialVersionUID = -3163866974852569563L;

    /**
     * 会员ID
     */
    private String customerId;

    /**
     * skuID
     */
    private String goodsInfoId;

    /**
     * 购买的数量
     */
    private Long purchaseNum;

    /**
     * 秒杀商品主键
     */
    private Long flashGoodsId;

}
