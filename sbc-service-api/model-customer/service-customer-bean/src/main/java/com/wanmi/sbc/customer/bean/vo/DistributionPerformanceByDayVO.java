package com.wanmi.sbc.customer.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * <p>分销业绩日统计记录</p>
 * Created by of628-wenzhi on 2019-04-17-16:01.
 */
@Data
@Schema
public class DistributionPerformanceByDayVO extends BasicResponse {

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
     * 日期 (yyyy-MM-dd)
     */
    @Schema(description = "日期 (yyyy-MM-dd)")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate targetDate;

}
