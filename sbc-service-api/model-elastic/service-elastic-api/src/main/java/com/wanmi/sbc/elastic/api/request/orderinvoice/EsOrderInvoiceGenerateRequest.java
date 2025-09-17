package com.wanmi.sbc.elastic.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author houshuai
 *
 * 订单开票信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsOrderInvoiceGenerateRequest extends BaseRequest {


    /**
     * 批量操作 订单开票id
     */
    @Schema(description = "批量订单开票ID")
    private List<String> orderInvoiceIds;

    /**
     * 订单开票ID
     */
    @Schema(description = "订单开票ID")
    private String orderInvoiceId;

    /**
     * 发票类型
     */
    @Schema(description = "发票类型")
    private Integer invoiceType;

    /**
     * 开票项id
     */
    @Schema(description = "开票项id")
    private String projectId;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String customerId;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderNo;

    /**
     * 发票title
     */
    @Schema(description = "发票title")
    private String invoiceTitle;

    /**
     * 发票地址
     */
    @Schema(description = "发票地址")
    private String invoiceAddress;

    /**
     * 开票时间
     */
    @Schema(description = "开票时间")
    private String invoiceTime;

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    private String contacts;

    /**
     * 收货人联系号码
     */
    @Schema(description = "收货人联系号码")
    private String phone;

    /**
     * 收货地址
     */
    @Schema(description = "收货地址")
    private String address;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId = 0L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId = 0L;

    /**
     * 纳税人识别号
     */
    @Schema(description = "纳税人识别号")
    private String taxpayerNumber;

    /**
     * 订单开票收货地址
     */
    @Schema(description = "订单开票收货地址")
    private String addressInfoId;

    /**
     * 员工id
     */
    @Schema(description = "员工id")
    private String employeeId;

    /**
     * 发票状态
     */
    @Schema(description = "发票状态")
    private Integer invoiceState;

    /**
     * 订单金额
     */
    @Schema(description = "订单金额")
    private BigDecimal orderPrice;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 支付状态
     */
    @Schema(description = "支付状态")
    private Integer payOrderStatus;

    /**
     * 流程状态（订单状态）
     */
    @Schema(description = "流程状态（订单状态）")
    private FlowState flowState;

    /**
     * 门店/店铺名称
     */
    @Schema(description = "门店/店铺名称")
    private String storeName;

}