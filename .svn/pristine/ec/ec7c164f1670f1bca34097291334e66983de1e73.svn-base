package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wanggang
 * @createDate: 2018/12/3 10:06
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class MergeGoodsInfoResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;
    /**
     * 订单商品数据，仅包含skuId/价格
     */
    @Schema(description = "订单商品数据 仅包含skuId/价格")
    private List<TradeItemVO> tradeItems;
}
