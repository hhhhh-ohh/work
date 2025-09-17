package com.wanmi.ares.report.trade.model.request;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 交易报表查询
 * Created by sunkun on 2017/9/26.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeCollect{

    /**
     * 开始时候
     */
    @NotBlank
    private LocalDate beginDate;

    /**
     * 结束时间
     */
    @NotBlank
    private LocalDate endDate;

    private Integer storeType;

    /**
     * 商户id
     */
    @NotBlank
    private String companyId;

    /**
     * 第几页
     */
    private Integer beginIndex;

    /**
     * 每页多少条
     */
    private Integer pageSize;
}
