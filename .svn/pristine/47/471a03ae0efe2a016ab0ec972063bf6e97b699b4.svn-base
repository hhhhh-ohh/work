package com.wanmi.sbc.customer.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>月分销业绩数据Bean</p>
 * Created by of628-wenzhi on 2019-04-18-16:27.
 */
@Data
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistributionMonthPerformanceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 业务员id
     */
    @Schema(description = "业务员id")
    @NotNull
    private String distributionId;

    /**
     * 会员id，此处做冗余
     */
    @Schema(description = "会员id")
    @NotNull
    private String customerId;

    /**
     * 销售额
     */
    @Schema(description = "销售额")
    @NotNull
    @Min(0)
    private BigDecimal saleAmount;

    /**
     * 预估收益
     */
    @Schema(description = "预估收益")
    @NotNull
    @Min(0)
    private BigDecimal commission;

    /**
     * 统计的目标日期，月报表中记录的是该月最后一天，用于分区
     */
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @Schema(description = "统计的目标日期，月报表中记录的是该月最后一天，用于分区")
    @NotNull
    private LocalDate targetDate;
}
