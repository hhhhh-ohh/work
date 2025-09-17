package com.wanmi.sbc.customer.api.response.distribution.performance;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionPerformanceByMonthVO;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * <p>分销业绩查询近6个月数据response</p>
 * Created by of628-wenzhi on 2019-04-18-18:41.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributionPerformanceByLast6MonthsQueryResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 分销业绩集合
     */
    @Schema(description = "分销业绩集合")
    private List<DistributionPerformanceByMonthVO> dataList;

    /**
     * 销售额合计
     */
    @Schema(description = "销售额合计")
    private BigDecimal totalSaleAmount = BigDecimal.ZERO.setScale(2, RoundingMode.DOWN);

    /**
     * 预估收益合计
     */
    @Schema(description = "预估收益合计")
    private BigDecimal totalCommission = BigDecimal.ZERO.setScale(2, RoundingMode.DOWN);

}
