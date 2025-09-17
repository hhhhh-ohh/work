package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.order.bean.vo.TradeSimpleBuyCycleVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className BuyCycleInfoResponse
 * @description
 * @date 2022/10/18 2:34 PM
 **/
@Data
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleInfoResponse extends BasicResponse {

    private static final long serialVersionUID = -3869333418568144915L;

    /**
     * 商品信息
     */
    @Schema(description = "商品信息")
    private TradeItemVO tradeItemVO;

    /**
     * 店铺信息
     */
    @Schema(description = "店铺信息")
    private StoreVO storeVO;

    /**
     * 周期购信息
     */
    @Schema(description = "周期购信息")
    private TradeSimpleBuyCycleVO tradeBuyCycleVO;
}
