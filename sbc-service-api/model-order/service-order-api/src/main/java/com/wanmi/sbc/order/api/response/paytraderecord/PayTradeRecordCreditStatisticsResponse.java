package com.wanmi.sbc.order.api.response.paytraderecord;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chenli
 * @date 2021/4/22 14:25
 * @description
 *     <p>统计授信账户数据
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayTradeRecordCreditStatisticsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /** 授信已使用额度（当前周期） */
    @Schema(description = "授信已使用额度（当前周期）")
    private BigDecimal creditUsedAmount = BigDecimal.ZERO;

    /** 授信已还款额度（当前周期） */
    @Schema(description = "授信已还款额度（当前周期）")
    private BigDecimal creditHasRepaidAmount = BigDecimal.ZERO;
}
