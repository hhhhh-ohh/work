package com.wanmi.sbc.order.api.response.orderperformance;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 订单业绩汇总响应类
 * 用于返回订单业绩相关的汇总统计数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单业绩汇总响应")
public class OrderPerformanceDetailSummaryResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 二级代理商信息汇总
     */
    @Schema(description = "二级代理商信息汇总")
    private List<OrderPerformanceSummaryNewResponse> resultList;


}
