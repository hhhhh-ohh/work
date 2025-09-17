package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsVO;
import com.wanmi.sbc.order.bean.dto.TradeGoodsInfoPageDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: yinxianzhi
 * @createDate: 2018/5/20 10:06
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class VerifyPointsGoodsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 订单商品数据，仅包含skuId与购买数量
     */
    @Schema(description = "订单商品数据，仅包含skuId与购买数量")
    private TradeItemDTO tradeItem;

    /**
     * 积分商品信息
     */
    @Schema(description = "积分商品信息")
    private PointsGoodsVO pointsGoodsVO;

    /**
     * 关联商品信息
     */
    @Schema(description = "关联商品信息")
    private TradeGoodsInfoPageDTO goodsInfoResponse;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

}
