package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: yinxianzhi
 * @createDate: 2019/5/20 10:06
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class VerifyPointsGoodsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 订单商品数据，仅包含skuId与购买数量
     */
    @Schema(description = "订单商品数据")
    private TradeItemVO tradeItem;
}
