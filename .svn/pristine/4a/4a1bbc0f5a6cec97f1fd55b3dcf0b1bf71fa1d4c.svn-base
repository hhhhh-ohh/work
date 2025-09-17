package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:
 * @Description: 下单可用优惠券列表
 * @Date: 2018-12-04 11:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PurchaseCouponCodeListResponse extends BasicResponse {

    /**
     * 可用数量
     */
    @Schema(description = "可用数量")
    private long availableCount = 0;


    /**
     * 订单商品不可用数量
     */
    @Schema(description = "订单商品不可用数量")
    private long noAvailableCount = 0;

    /**
     * 优惠券列表
     */
    @Schema(description = "优惠券列表")
    private List<CouponCodeVO> couponCodeList = new ArrayList<>();

}
