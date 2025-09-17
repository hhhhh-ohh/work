package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xuyunpeng
 * @className TradeEconomicalPageResponse
 * @description
 * @date 2022/5/24 10:26 AM
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeEconomicalPageResponse extends BasicResponse {
    private static final long serialVersionUID = 7753727629418310705L;

    /**
     * 总优惠金额
     */
    @Schema(description = "总优惠金额")
    private BigDecimal totalDiscount;

    /**
     * 订单分页数据
     */
    @Schema(description = "订单分页数据")
    private MicroServicePage<TradeVO> tradePage;
}
