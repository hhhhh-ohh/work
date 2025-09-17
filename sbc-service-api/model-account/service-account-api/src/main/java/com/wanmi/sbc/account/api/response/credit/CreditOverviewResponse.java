package com.wanmi.sbc.account.api.response.credit;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 授信总览返回对象
 * @author zhengyang
 * @since 2021/3/12 18:46
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditOverviewResponse extends BasicResponse {

    /***
     * 授信总额
     */
    @Schema(description = "授信总额")
    private BigDecimal totalCreditAmount;

    /***
     * 客户总数
     */
    @Schema(description = "客户总数")
    private int totalCustomer;

    /***
     * 可用总额
     */
    @Schema(description = "可用总额")
    private BigDecimal totalUsableAmount;

    /***
     * 已使用总额
     */
    @Schema(description = "已使用总额")
    private BigDecimal totalUsedAmount;

    /***
     * 待还总额
     */
    @Schema(description = "待还总额")
    private BigDecimal totalRepayAmount;

    /***
     * 已还总额
     */
    @Schema(description = "已还总额")
    private BigDecimal totalRepaidAmount;
}
