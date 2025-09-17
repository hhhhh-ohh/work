package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <p>分销业绩月统计记录</p>
 * Created by of628-wenzhi on 2019-04-17-16:01.
 */
@Data
public class DistributionPerformanceByMonthVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 分销员id
     */
    @Schema(description = "分销员id")
    private String distributionId;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 销售额
     */
    @Schema(description = "销售额")
    private BigDecimal saleAmount = BigDecimal.ZERO.setScale(2, RoundingMode.DOWN);

    /**
     * 预估收益
     */
    @Schema(description = "预估收益")
    private BigDecimal commission = BigDecimal.ZERO.setScale(2, RoundingMode.DOWN);

    /**
     * 年月（yyyy-MM）
     */
    @Schema(description = "年月（yyyy-MM）")
    private String targetMonth;

}
