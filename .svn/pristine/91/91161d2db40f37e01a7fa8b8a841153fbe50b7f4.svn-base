package com.wanmi.sbc.account.api.response.credit.account;

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
public class CreditAccountStatisticsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "授信账户总数")
    private Long creditAccountNum = 0L;

    /** 所有账户的授信待还款 */
    @Schema(description = "所有账户的授信待还款")
    private BigDecimal creditRepayAmount = BigDecimal.ZERO;

    /** 所有账户的授信可用额度 */
    @Schema(description = "所有账户的授信可用额度")
    private BigDecimal creditUsableAmount = BigDecimal.ZERO;
}
