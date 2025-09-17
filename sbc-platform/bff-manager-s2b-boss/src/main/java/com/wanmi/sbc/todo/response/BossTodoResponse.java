package com.wanmi.sbc.todo.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商家待处理面板
 * Created by daiyitian on 2017/9/18.
 */
@Schema
@Data
public class BossTodoResponse extends BasicResponse {

    /**
     * 待审核商家
     */
    @Schema(description = "待审核商家")
    private long waitSupplier;

    /**
     * 待审核商品
     */
     @Schema(description = "待审核商品")
    private long waitGoods;

    /**
     * 待付款
     */
     @Schema(description = "待付款")
    private long waitPay;

    /**
     * 待退款
     */
     @Schema(description = "待退款")
    private long waitRefund;

    /**
     * 待审核客户
     */
     @Schema(description = "待审核客户")
    private long waitAuditCustomer;

    /**
     * 待审核增票资质
     */
     @Schema(description = "待审核增票资质")
    private long waitAuditCustomerInvoice;

    /**
     * 待结算账单
     */
     @Schema(description = "待结算账单")
    private long waitSettle;

    /**
     * 商品审核开关 true:开启 false:关闭
     */
     @Schema(description = "商品审核开关")
    private boolean checkGoodsFlag = false;

    /**
     * 客户审核开关 true:开启 false:关闭
     */
     @Schema(description = "客户审核开关")
    private boolean checkCustomerFlag = false;

    /**
     * 增票资质审核开关 true:开启 false:关闭
     */
     @Schema(description = "增票资质审核开关")
    private boolean checkCustomerInvoiceFlag = false;

    /**
     * 待审核供应商
     */
    @Schema(description = "待审核供应商")
    private long waitProvider;
}
